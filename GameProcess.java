package game;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.Fish;
import objects.JumpingBox;
import objects.MovingObject;
import substitution.Substitution;
import objects.Box;
import objects.Cat;

/**
 * Implement process of game Have 3 modes 1) handle 2) auto 3) generation
 */
public class GameProcess extends Pane {

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
  private ObjectsController objectsController;
  private ResultsController results;
  private Substitution subs = new Substitution();

  private int level = 1;
  private boolean autoControlling;

  private double score = 1;
  private int eatingFishes = 0;
  private final double scoreIncreasing = 0.25;

  /**
   * Constructor for game
   * 
   * @param primaryStage
   * @param mainMenu
   * @param autoControlling
   */
  public GameProcess(Stage primaryStage, Scene mainMenu, boolean autoControlling) {
    this.results = new ResultsController();
    this.primaryStage = primaryStage;
    this.mainMenu = mainMenu;
    this.autoControlling = autoControlling;

    keys = new HashMap<>();
    vasya = new Cat(0, GameGraphics.WIN_HEIGHT / 2);
    boxes = new ArrayList<>();
    fishes = new ArrayList<>();
    objectsController = new ObjectsController(gamePane, boxes, fishes, vasya);
    graphics = new GameGraphics();
    getChildren().addAll(graphics, vasya);
    gameScene = new Scene(this, GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT);
  }

  /**
   * Constructor for replaying
   * 
   * @param primaryStage
   * @param mainMenu
   * @param results
   */
  public GameProcess(Stage primaryStage, Scene mainMenu, ResultsController results) {
    this.results = results;
    this.primaryStage = primaryStage;
    this.mainMenu = mainMenu;

    keys = new HashMap<>();
    vasya = new Cat(0, GameGraphics.WIN_HEIGHT / 2);
    boxes = new ArrayList<>();
    fishes = new ArrayList<>();
    objectsController = new ObjectsController(gamePane, boxes, fishes, vasya);
    graphics = new GameGraphics();
    getChildren().addAll(graphics, vasya);
    gameScene = new Scene(this, GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT);
  }


  /**
   * Method that start game . Have 2 modes: automatic game, handle game. Set handlers for saving
   * codes of pressed key, create result file and run animation timer.
   */
  public void play() {
    gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
    gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    results.createResultFile();
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (autoControlling) {
          autoControlVasya();
        } else {
          controlVasya();
        }
        results.saveVasya(vasya);
        randomCreatingObjects();
        update();
        checkConflicts();
      }
    };
    timer.start();
    primaryStage.setScene(gameScene);
  }

  public void generateGame() {
    gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
    gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    results.createResultFile();
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        for (int i = 0; i < 100; i++) {
          autoControlVasya();
          results.saveVasya(vasya);
          randomCreatingObjects();
          update();

          Thread thread = new Thread(objectsController);
          thread.start();
          try {
            thread.join();
          } catch (InterruptedException e) {
            System.out.println("InterruptedException in GameProcess.play()");
            e.printStackTrace();
          }
          if (objectsController.getGameOverFlag()) {
            gameOver();
            primaryStage.setScene(mainMenu);
            break;
          }
          if (objectsController.conflicts(vasya, fishes, true)) {
            increaseFish();
          }
        }
      }
    };
    timer.start();
    primaryStage.setScene(gameScene);
  }

  private int randomRegion = 200;

  /**
   * Trying create box and fish and save his results. So boxes appear 3 times more fish.
   */
  private void randomCreatingObjects() {
    if (level >= (int) (Math.random() * randomRegion)) {
      Box newBox = new Box();
      newBox.setStartLocale();
      boxes.add(newBox);
      this.getChildren().add(newBox);
      results.saveObject(newBox);
    }
    results.saveEndBoxes();
    if (level >= (int) (Math.random() * randomRegion * 3)) {
      Fish newFish = new Fish();
      newFish.setStartLocale();
      fishes.add(newFish);
      this.getChildren().add(newFish);
      results.saveObject(newFish);
    }
    results.saveEndFishes();
  }

  /**
   * Method that replay game from file that was transferred in replay constructor
   * 
   * @see #GameProcess(Stage, Scene, ResultsController)
   * 
   */
  public void replay() {
    gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
    gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (isPressed(KeyCode.ESCAPE)) {
          pause();
        }
        if (!loadState()) {
          gameOver();
        }
        update();
        checkConflicts();
      }
    };
    timer.start();
    primaryStage.setScene(gameScene);
  }

  /**
   * Load game state
   * 
   * @return true if the file is not ended
   */
  private boolean loadState() {
    if (!results.loadVasya(vasya)) {
      return false;
    }
    subs.analyzeVasya(vasya.getTranslateX(), vasya.getTranslateY());
    subs.analyzeBoxes(results.tryLoadBox(boxes, gamePane));
    subs.analyzeFishes(results.tryLoadFish(fishes, gamePane));
    return true;
  }

  /**
   * This method its game LOGIC that using in play and replay Updating game process : 1) move
   * objects 2) check object out the range 3) check conflicts 4) increase difficulty level 5)
   * increase
   */
  private void update() {
    objectsController.move(fishes);
    objectsController.move(boxes);
    objectsController.isMovingObjectOut(boxes);
    objectsController.isMovingObjectOut(fishes);
    increaseDifficultyLevel();
    increaseScore();
  }

  /**
   * Check the conflicts between vasya and objects, SERVER-CLEINT model implemented here
   * 
   * Server check conflicts between vasya and boxes. If in Server conflict flag was set => GAME OVER
   * Controller check conflicts between vasya and fishes.
   * 
   * @see class ObjectsController
   */
  private void checkConflicts() {
    Thread thread = new Thread(objectsController);
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      System.out.println("InterruptedException in GameProcess.play()");
      e.printStackTrace();
    }
    if (objectsController.getGameOverFlag()) {
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
    if (score % 400 == 0) {
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

  private boolean isPressed(KeyCode key) {
    return keys.getOrDefault(key, false);
  }

  private int botVision = 120;
  private int botVisionIncreasing = 35;

  /**
   * Auto control analyze where the center vasya relatively higher or lower and depending on this
   * moving up or down
   */
  private void autoControlVasya() {
    for (MovingObject box : boxes) {
      if (box.getTranslateX() <= vasya.getTranslateX() + botVision
          + (level * botVisionIncreasing)) {
        double northCoordVasya = vasya.getTranslateY();
        double southCoordVasya = northCoordVasya + vasya.HEIGHT;
        double centerCoordY = southCoordVasya - vasya.HEIGHT / 2;

        double northCoordBox = box.getTranslateY();
        double southCoordBox = northCoordBox + box.getHeight();
        double conterCoordBox = southCoordBox - box.getHeight() / 2;
        if ((northCoordVasya >= northCoordBox) && (northCoordVasya <= southCoordBox)
            || (southCoordVasya >= northCoordBox) && (southCoordVasya <= southCoordBox)) {
          if (centerCoordY >= conterCoordBox) {
            vasya.moveSouth();
          } else {
            vasya.moveNorth();
          }
        }
      }
    }
    if (isPressed(KeyCode.ESCAPE)) {
      pause();
    }
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
   * Method that implement game over
   */
  private void gameOver() {
    vasya.animationStop();
    timer.stop();
    graphics.gameOverAnimation(gamePane);
    graphics.gameOverMenu(gamePane, primaryStage, mainMenu);
    results.finalize();
  }
}
