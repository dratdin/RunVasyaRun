package objects;

import game.GameGraphics;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Implement Box in this game 
 * Represent box picture that move in left
 */
public class Box extends MovingObject {

  public static final int PICTURE_WIDTH = 64;
  public static final int PICTURE_HEIGHT = 64;

  // sprite image
  private Image spriteImage = new Image(getClass().getResourceAsStream("box.png"));
  private ImageView fishView = new ImageView(spriteImage);

  public Box() {
    getChildren().add(fishView);
  }

  public void setStartLocale() {
    setTranslateX(GameGraphics.WIN_WIDTH + PICTURE_WIDTH);
    int position = (int) (Math.random() * (GameGraphics.WIN_HEIGHT / PICTURE_HEIGHT));
    position *= PICTURE_HEIGHT;
    position += GameGraphics.INDENT_INFORMATION;
    setTranslateY(position);
  }
}
