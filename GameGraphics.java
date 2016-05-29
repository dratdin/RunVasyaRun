package game;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import menu.MenuItem;
import menu.SubMenu;
import objects.Cat;
import objects.MovingObject;

public class GameGraphics extends Pane {
  public static final int WIN_WIDTH = 1200;
  public static final int WIN_HEIGHT = 700;
  public final static int INDENT_INFORMATION = 15;
  /**
   * Friendly fields for package game
   */
  Rectangle background;
  Label scoreLabel;
  Label fishLabel;

  public GameGraphics() {
    background = new Rectangle(GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT, Color.ANTIQUEWHITE);
    scoreLabel = new Label("Score: 0");
    fishLabel = new Label("Fish: 0");
    initialRendering();
  }

  /**
   * Sets background, labels and other
   */
  private void initialRendering() {
    fishLabel.setTranslateX(300);
    Font font = new Font(16);
    scoreLabel.setFont(font);
    fishLabel.setFont(font);
    getChildren().addAll(background, scoreLabel, fishLabel);
  }

  public void gameOverAnimation(Pane gamePane) {
    Media media = new Media(getClass().getResource("wasted.mp3").toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();

    Rectangle slick = new Rectangle(GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT);
    slick.setOpacity(0.7);
    gamePane.getChildren().add(slick);
    FillTransition st = new FillTransition(Duration.seconds(2), slick);
    st.setFromValue((Color) background.getFill());
    st.setToValue(Color.GRAY);
    st.play();

    Image wasted = new Image(getClass().getResourceAsStream("wasted.png"));
    ImageView wastedView = new ImageView(wasted);
    wastedView.setTranslateX((GameGraphics.WIN_WIDTH - wasted.getWidth()) / 2);
    wastedView.setTranslateY((GameGraphics.WIN_HEIGHT - wasted.getHeight()) / 2);
    gamePane.getChildren().add(wastedView);
  }

  public void gameOverMenu(Pane gamePane, Stage primaryStage, Scene mainMenu) {
    MovingObject.resetSpeed();
    MenuItem menu = new MenuItem("ГЛАВНОЕ МЕНЮ");
    MenuItem exitGame = new MenuItem("ВЫХОД");
    SubMenu endMenu = new SubMenu(menu, exitGame);
    int menuIndentX = 50;
    int menuIndentY = 25;
    endMenu.setTranslateX(GameGraphics.WIN_WIDTH - MenuItem.WIDTH - menuIndentX);
    endMenu.setTranslateY(
        GameGraphics.WIN_HEIGHT - (MenuItem.HEIGHT + SubMenu.SPACING) * 2 - menuIndentY);
    exitGame.setOnMouseClicked(event -> {
      System.exit(0);
    });
    menu.setOnMouseClicked(event -> {
      primaryStage.setScene(mainMenu);
    });
    gamePane.getChildren().add(endMenu);
  }

  public void pauseMenu(AnimationTimer timer, Cat vasya, Scene mainMenu, Stage primaryStage) {
    MenuItem cont = new MenuItem("ПРОДОЛЖИТЬ");
    MenuItem menu = new MenuItem("ГЛАВНОЕ МЕНЮ");
    MenuItem exitGame = new MenuItem("ВЫХОД");
    SubMenu pauseMenu = new SubMenu(cont, menu, exitGame);
    cont.setOnMouseClicked(event -> {
      getChildren().remove(pauseMenu);
      vasya.animationPlay();
      timer.start();
    });
    menu.setOnMouseClicked(event -> {
      MovingObject.resetSpeed();
      primaryStage.setScene(mainMenu);
    });
    exitGame.setOnMouseClicked(event -> {
      System.exit(0);
    });
    getChildren().add(pauseMenu);
  }
}
