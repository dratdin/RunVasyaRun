package game;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.Box;
import objects.Cat;
import objects.Fish;
import objects.JumpingBox;
import objects.MovingObject;

/**
 * Implement process of game Have 3 modes 1) handle 2) auto 3) generation
 */
public class ExtendedGameProcess extends Pane {

  private AnimationTimer timer;
  private HashMap<KeyCode, Boolean> keys;

  private Stage primaryStage;
  private Scene mainMenu;
  private Scene gameScene;
  private Pane gamePane = this;
  private GameGraphics graphics;

  private Cat vasya;
  private ArrayList<MovingObject> boxes;
  private ArrayList<MovingObject> fishes;
  private ArrayList<MovingObject> jBoxes;
  private ObjectsController objectsController;

  private int level = 1;
  private double score = 1;
  private final double scoreIncreasing = 0.25;
  private int eatingFishes = 0;

  /**
   * Constructor for game
   * 
   * @param primaryStage
   * @param mainMenu
   */
  public ExtendedGameProcess(Stage primaryStage, Scene mainMenu) {
    this.primaryStage = primaryStage;
    this.mainMenu = mainMenu;
    graphics = new GameGraphics();

    keys = new HashMap<>();
    vasya = new Cat(0, GameGraphics.WIN_HEIGHT / 2);
    boxes = new ArrayList<>();
    fishes = new ArrayList<>();
    jBoxes = new ArrayList<>();
    objectsController = new ObjectsController(gamePane, boxes, fishes, vasya);
    getChildren().addAll(graphics, vasya);
    gameScene = new Scene(this, GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT);
  }


  /**
   * Method that start game . Have 2 modes: automatic game, handle game. Set handlers for saving
   * codes of pressed key, create result file and run animation timer.
   */
  public void play() {
    resetGame();
    gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
    gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        controlVasya();
        randomCreatingObjects();
        update();
      }
    };
    timer.start();
    primaryStage.setScene(gameScene);
  }

  /**
   * Hand control
   */
  private void controlVasya() {
    if (isPressed(KeyCode.UP)) {
      vasya.moveNorth();
    } else if (isPressed(KeyCode.DOWN)) {
      vasya.moveSouth();
    } else if (isPressed(KeyCode.LEFT)) {
      vasya.moveWest();
    } else if (isPressed(KeyCode.RIGHT)) {
      vasya.moveEast();
    } else if (isPressed(KeyCode.ESCAPE)) {
      pause();
    }
  }

  private int randomRegion = 200;

  /**
   * Trying create box and fish and save his results. So boxes appear 3 times more fish.
   */
  private void randomCreatingObjects() {
    if (level >= (int) (Math.random() * randomRegion * 4)) {
      JumpingBox jBox = new JumpingBox();
      jBox.setStartLocale();
      jBox.startJumping();
      jBoxes.add(jBox);
      this.getChildren().add(jBox);
    }

    if (level >= (int) (Math.random() * randomRegion)) {
      Box newBox = new Box();
      newBox.setStartLocale();
      boxes.add(newBox);
      this.getChildren().add(newBox);
    }

    if (level >= (int) (Math.random() * randomRegion * 3)) {
      Fish newFish = new Fish();
      newFish.setStartLocale();
      fishes.add(newFish);
      this.getChildren().add(newFish);
    }
  }

  /**
   * This method its game LOGIC that using in play and replay Updating game process : 1) move
   * objects 2) check object out the range 3) check conflicts 4) increase difficulty level 5)
   * increase
   */
  private void update() {
    objectsController.move(fishes);
    objectsController.move(boxes);
    objectsController.move(jBoxes);
    objectsController.isMovingObjectOut(boxes);
    objectsController.isMovingObjectOut(fishes);
    objectsController.isMovingObjectOut(jBoxes);
    checkConflicts();
    increaseDifficultyLevel();
    increaseScore();
  }

  private void checkConflicts() {
    if (objectsController.conflicts(vasya, boxes, false)
        || objectsController.conflicts(vasya, jBoxes, false)) {
      gameOver();
    }
    if (objectsController.conflicts(vasya, fishes, true)) {
      increaseFish();
    }
  }

  /**
   * Increasing level, vasya speed, objects speed every 50 scores increasing speed of objects every
   * 200 scores increasing level every 1000 scores increasing speed of vasya
   */
  private void increaseDifficultyLevel() {
    if (score % 50 == 0) {
      MovingObject.increaseSpeed();
    }
    if (score % 200 == 0) {
      level++;
    }
    if (score % 300 == 0) {
      JumpingBox.increaseJumpingSpeed();
    }
    if (score % 1000 == 0) {
      vasya.increaseSpeed();
    }
  }

  private void increaseScore() {
    score += scoreIncreasing;
    graphics.scoreLabel.setText("Score: " + (int) score);
  }

  private void increaseFish() {
    eatingFishes++;
    graphics.fishLabel.setText("Fish: " + (int) eatingFishes);
  }

  private boolean isPressed(KeyCode key) {
    return keys.getOrDefault(key, false);
  }

  private void resetGame() {
    MovingObject.resetSpeed();
  }

  /**
   * Method stop game and show pause menu
   */
  private void pause() {
    vasya.animationStop();
    timer.stop();
    graphics.pauseMenu(timer, vasya, mainMenu, primaryStage);
  }

  /**
   * Method implement game over animation and stop game Play music Play animation Set wasted picture
   * Show game over menu
   */
  private void gameOver() {
    vasya.animationStop();
    timer.stop();
    graphics.gameOverAnimation(gamePane);
    graphics.gameOverMenu(gamePane, primaryStage, mainMenu);
  }
}
