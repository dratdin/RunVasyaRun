package menu;

import game.ExtendedGameProcess;
import game.GameGraphics;
import game.GameProcess;
import game.ResultsController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import statistics.StatisticsArea;


/**
 * Class that show main menu and handle users clicks on this menu
 */
public class MainMenu extends Pane {

  private Stage primaryStage;
  private Scene scene;
  private Rectangle background;

  public MainMenu(Stage primaryStage) {
    this.primaryStage = primaryStage;
    scene = new Scene(this, GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT);
    background = new Rectangle(GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT, Color.ANTIQUEWHITE);
  }

  private final int FOOTPRINTS_WIDTH = 500;
  private final int FOOTPRINTS_HEIGHT = 500;

  /**
   * Show main menu
   */
  public void show() {
    getChildren().add(background);

    Image footprints = new Image(getClass().getResourceAsStream("footprints.png"));
    ImageView footprintsView = new ImageView(footprints);

    footprintsView.setFitWidth(FOOTPRINTS_WIDTH);
    footprintsView.setFitHeight(FOOTPRINTS_HEIGHT);
    footprintsView.setLayoutY(GameGraphics.WIN_HEIGHT - FOOTPRINTS_HEIGHT);
    getChildren().add(footprintsView);

    MenuItem newGame = new MenuItem("НОВАЯ ИГРА");
    MenuItem replay = new MenuItem("ЗАГРУЗИТЬ");
    MenuItem statistics = new MenuItem("СТАТИСТИКА");
    MenuItem generation = new MenuItem("СГЕНЕРИРОВАТЬ ИГРЫ");
    MenuItem exitGame = new MenuItem("ВЫХОД");
    SubMenu mainMenu = new SubMenu(newGame, replay, statistics, generation, exitGame);

    MenuItem newGameClassic = new MenuItem("КЛАССИЧЕСКАЯ");
    MenuItem newGameExtended = new MenuItem("РАСШИРЕННАЯ");
    MenuItem newGameAuto = new MenuItem("БОТ");
    MenuItem newGameBack = new MenuItem("НАЗАД");
    SubMenu newGameMenu = new SubMenu(newGameClassic, newGameExtended, newGameAuto, newGameBack);

    MenuItem replayStart = new MenuItem("ВОСПРОИЗВЕСТИ");
    MenuItem replayJavaSort = new MenuItem("JAVA СОРТИРОВКА");
    MenuItem replayScalaSort = new MenuItem("SCALA СОРТИРОВКА");
    MenuItem replayTest = new MenuItem("ТЕСТИРОВАНИЕ СОРТИРОВОК");
    MenuItem replayBack = new MenuItem("НАЗАД");
    SubMenu replayGameMenu =
        new SubMenu(replayStart, replayJavaSort, replayScalaSort, replayTest, replayBack);

    ResultsController results = new ResultsController();
    FileList fileList = new FileList(ResultsController.folderContent());
    replayGameMenu.setTranslateY(fileList.getTranslateY() + FileList.HEIGHT + SubMenu.SPACING);
    MenuBox menuBox = new MenuBox(mainMenu);

    newGame.setOnMouseClicked(event -> menuBox.setSubMenu(newGameMenu));
    exitGame.setOnMouseClicked(event -> System.exit(0));
    newGameClassic.setOnMouseClicked(event -> {
      menuBox.setSubMenu(mainMenu);
      GameProcess game = new GameProcess(primaryStage, scene, false);
      game.play();
    });
    newGameExtended.setOnMouseClicked(event -> {
      ExtendedGameProcess game = new ExtendedGameProcess(primaryStage, scene);
      game.play();
    });
    newGameAuto.setOnMouseClicked(event -> {
      menuBox.setSubMenu(mainMenu);
      GameProcess game = new GameProcess(primaryStage, scene, true);
      game.play();
    });
    newGameBack.setOnMouseClicked(event -> {
      menuBox.setSubMenu(mainMenu);
    });
    replay.setOnMouseClicked(event -> {
      fileList.setFiles(ResultsController.folderContent());
      menuBox.setSubMenu(replayGameMenu);
      menuBox.setItem(fileList);
    });
    replayStart.setOnMouseClicked(event -> {
      if (fileList.getSelectionModel().isEmpty()) {
        return;
      } else {
        String fileName = fileList.getSelectionModel().getSelectedItem();
        results.openResultFile(fileName);
        GameProcess game = new GameProcess(primaryStage, scene, results);
        game.replay();
      }
    });
    replayJavaSort.setOnMouseClicked(event -> {
      fileList.javaSort();
    });
    replayScalaSort.setOnMouseClicked(event -> {
      fileList.scalaSort();
    });
    replayTest.setOnMouseClicked(event -> {
      fileList.sortTests();
    });
    generation.setOnMouseClicked(event -> {
      GameProcess game = new GameProcess(primaryStage, scene, true);
      game.generateGame();
    });
    replayBack.setOnMouseClicked(event -> {
      menuBox.getChildren().remove(fileList);
      menuBox.setSubMenu(mainMenu);
    });
    statistics.setOnMouseClicked(event -> {
      StatisticsArea statisticsArea = new StatisticsArea(primaryStage, scene);
      statisticsArea.show();
    });

    getChildren().addAll(menuBox);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
