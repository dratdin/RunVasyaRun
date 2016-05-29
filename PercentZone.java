package statistics;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import objects.Box;

/**
 * Class represents zone on statistics area with percent value
 */
public class PercentZone extends Pane {

  public static final int WIDTH = Box.PICTURE_WIDTH;
  public static final int HEIGHT = Box.PICTURE_HEIGHT;

  public PercentZone(int coordX, int coordY, double percent, Color color) {
    Rectangle background = new Rectangle(WIDTH, HEIGHT, color);
    background.setOpacity(0.5);
    background.setStroke(Color.BLACK);
    background.setStrokeWidth(1);

    Label label = new Label(String.format("%.2f", percent) + "%");
    Font font = new Font(16);
    label.setTextFill(Color.WHITE);
    label.setFont(font);
    label.setTranslateX(getTranslateX() + 5);
    label.setTranslateY(getTranslateY() + 25);
    getChildren().addAll(background, label);
    setTranslateX(coordX);
    setTranslateY(coordY);
  }
}
