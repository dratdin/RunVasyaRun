package menu;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Submenu element
 */
public class MenuItem extends StackPane {

  public static final int WIDTH = 450;
  public static final int HEIGHT = 50;

  public MenuItem(String name) {
    Rectangle bg = new Rectangle(WIDTH, HEIGHT, Color.SADDLEBROWN);
    bg.setOpacity(0.6);
    bg.setArcHeight(30);
    bg.setArcWidth(15);

    Text text = new Text(name);
    text.setFill(Color.WHITE);
    text.setFont(Font.font("Arial", FontWeight.BOLD, 14));

    setAlignment(Pos.CENTER);
    getChildren().addAll(bg, text);
    FillTransition st = new FillTransition(Duration.seconds(0.5), bg);
    setOnMouseEntered(event -> {
      st.setFromValue(Color.SIENNA);
      st.setToValue(Color.TAN);
      st.setCycleCount(Animation.INDEFINITE);
      st.setAutoReverse(true);
      st.play();
    });
    setOnMouseExited(event -> {
      st.stop();
      bg.setFill(Color.SADDLEBROWN);
    });
  }
}
