package statistics;

import java.util.ArrayList;

import game.GameGraphics;
import game.ResultsController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import menu.MenuItem;
import menu.SubMenu;
import objects.Cat;
import sort.GameInfo;

/**
 * Implement statistics area Represents as Pane that set on main Stage
 */
public class StatisticsArea extends Pane {

  private Stage primaryStage;
  private Scene mainMenu;
  private Scene statisticsScene;
  private Statistics scalaStatistics;

  public StatisticsArea(Stage primaryStage, Scene mainMenu) {
    this.primaryStage = primaryStage;
    this.mainMenu = mainMenu;
    scalaStatistics = new Statistics();
  }

  /**
   * Calculate statistics about games, used Scala class Statistics. And translate elements to
   * calculated coordinates
   */
  public void show() {
    String[] files = ResultsController.folderContent();
    GameInfo[] gameInfo = new GameInfo[files.length];
    for (int i = 0; i < files.length; i++) {
      gameInfo[i] = new GameInfo(files[i]);
    }
    scalaStatistics.collectInformations(gameInfo);

    Label averageScore =
        new Label("Average score: " + scalaStatistics.calculateAverageScore(gameInfo));
    Font font = new Font(16);
    averageScore.setFont(font);

    Cat vasya = new Cat((int) scalaStatistics.getFreqVasya().getCoordX(),
        (int) scalaStatistics.getFreqVasya().getCoordY());

    Rectangle background =
        new Rectangle(GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT, Color.ANTIQUEWHITE);

    MenuItem mMenu = new MenuItem("ГЛАВНОЕ МЕНЮ");
    SubMenu menu = new SubMenu(mMenu);
    mMenu.setOnMouseClicked(event -> {
      primaryStage.setScene(mainMenu);
    });

    getChildren().addAll(background, averageScore, vasya, menu);
    drawBoxesStatistics();
    drawFishesStatistics();
    statisticsScene = new Scene(this, GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT);
    primaryStage.setScene(statisticsScene);
    infoWindow();
  }

  private int percentZoneCount = GameGraphics.WIN_HEIGHT / PercentZone.HEIGHT;
  ArrayList<PercentZone> boxes = new ArrayList<>();
  ArrayList<PercentZone> fishes = new ArrayList<>();

  /**
   * Drawing statistics of random coordinates generation for boxes
   */
  private void drawBoxesStatistics() {
    Coords[] boxesCoords = scalaStatistics.getBoxesCoords();
    double[] percentZones = new double[percentZoneCount];

    for (int i = 0; i < percentZones.length; i++) {
      for (int j = 0; j < boxesCoords.length; j++) {
        if (boxesCoords[j].getCoordY() >= i * PercentZone.HEIGHT + GameGraphics.INDENT_INFORMATION
            && boxesCoords[j].getCoordY() < i * PercentZone.HEIGHT + PercentZone.HEIGHT
                + GameGraphics.INDENT_INFORMATION) {
          percentZones[i] += boxesCoords[j].getCount();
        }
      }
    }
    double sum = 0;
    for (int i = 0; i < percentZones.length; i++) {
      sum += percentZones[i];
    }
    for (int i = 0, indentY =
        GameGraphics.INDENT_INFORMATION; i < percentZones.length; i++, indentY +=
            PercentZone.HEIGHT) {
      PercentZone pz = new PercentZone(GameGraphics.WIN_WIDTH - PercentZone.WIDTH, indentY,
          convertToPercent(percentZones[i], sum), Color.BROWN);
      boxes.add(pz);
    }
    getChildren().addAll(boxes);
  }

  /**
   * Drawing statistics of random coordinates generation for fishes
   */
  private void drawFishesStatistics() {
    Coords[] fishesCoords = scalaStatistics.getFishesCoords();
    double[] percentZones = new double[percentZoneCount];
    for (int i = 0; i < percentZones.length; i++) {
      for (int j = 0; j < fishesCoords.length; j++) {
        if (fishesCoords[j].getCoordY() >= i * PercentZone.HEIGHT + GameGraphics.INDENT_INFORMATION
            && fishesCoords[j].getCoordY() < i * PercentZone.HEIGHT + PercentZone.HEIGHT
                + GameGraphics.INDENT_INFORMATION) {
          percentZones[i] += fishesCoords[j].getCount();
        }
      }
    }
    double sum = 0;
    for (int i = 0; i < percentZones.length; i++) {
      sum += percentZones[i];
    }
    for (int i = 0, indentY =
        GameGraphics.INDENT_INFORMATION; i < percentZones.length; i++, indentY +=
            PercentZone.HEIGHT) {
      PercentZone pz = new PercentZone(GameGraphics.WIN_WIDTH - PercentZone.WIDTH * 2, indentY,
          convertToPercent(percentZones[i], sum), Color.AQUA);
      fishes.add(pz);
    }
    getChildren().addAll(fishes);
  }


  private double convertToPercent(double num, double sum) {
    return num / sum * 100;
  }

  private final int INFO_WINDOW_WIDTH = 650;
  private final int INFO_WINDOW_HEIGHT = 200;

  /**
   * Drawing information window
   */
  private void infoWindow() {
    Stage window = new Stage();
    Pane pane = new Pane();
    Rectangle background = new Rectangle(INFO_WINDOW_WIDTH, INFO_WINDOW_HEIGHT, Color.ANTIQUEWHITE);
    Label text = new Label("На игровом поле представлены: "
        + "\n -среднее количество очков по играм" + "\n -самое посещаемое место (анимация кота) "
        + "\n -статистические показатели по случайной генерации координаты Y"
        + "\n a). для рыб(голубой цвет) б). для ящиков(коричневый цвет)");
    Font font = new Font(16);
    text.setFont(font);
    pane.getChildren().addAll(background, text);
    Scene scene = new Scene(pane, INFO_WINDOW_WIDTH, INFO_WINDOW_HEIGHT);
    window.setScene(scene);
    window.setTitle("Статистика");
    window.showAndWait();
  }
}
