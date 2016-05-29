package objects;

import game.GameGraphics;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Implement fish in this game 
 * Represent fish picture that move in left.
 * Fish picture randomly generated.
 */
public class Fish extends MovingObject {

  public static final int PICTURE_WIDTH = 64;
  public static final int PICTURE_HEIGHT = 64;

  private final int COUNT = 1;
  private final int COLUMNS = 0;
  
  // random offset on sprite image(this allows create different fishes)
  private final int OFFSETX = (int) (Math.random() * 2) * PICTURE_WIDTH;
  private final int OFFSETY = (int) (Math.random() * 2) * PICTURE_HEIGHT;

  private Image spriteImage = new Image(getClass().getResourceAsStream("fish_sprite2.png"));
  private ImageView fishView = new ImageView(spriteImage);
  public Animation animation = new SpriteAnimation(fishView, Duration.millis(0), COUNT, COLUMNS,
      OFFSETX, OFFSETY, PICTURE_WIDTH, PICTURE_HEIGHT);

  public Fish() {
    fishView.setViewport(new Rectangle2D(OFFSETX, OFFSETY, PICTURE_WIDTH, PICTURE_HEIGHT));
    animation.setCycleCount(Animation.INDEFINITE);
    animation.play();
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
