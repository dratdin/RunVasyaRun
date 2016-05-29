package application;

import javafx.application.Application;
import javafx.stage.Stage;
import menu.MainMenu;

/**
 * Main class for this application that show main menu
 */
public class RunVasya extends Application {

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("runVASYArun");
    MainMenu menu = new MainMenu(primaryStage);
    menu.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}
