package objects;

import game.GameGraphics;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * 
 * Main character represents Pane which placed sprite animation
 */
public class Cat extends Pane {

  public final int WIDTH = 56;
  public final int HEIGHT = 46;

  private final double startSpeed = 7;
  private final double maxSpeed = 9;
  private final double increasingSpeed = 1;
  private double speed = startSpeed;

  private Image spriteImage = new Image(getClass().getResourceAsStream("vasya_sprite.png"));
  private ImageView vasyaView = new ImageView(spriteImage);

  final int COUNT = 3;
  final int COLUMNS = 3;
  final int OFFSETX = 0;
  final int OFFSETY = 0;

  private int animationDelay = 450;
  private Animation animation = new SpriteAnimation(vasyaView, Duration.millis(animationDelay),
      COUNT, COLUMNS, OFFSETX, OFFSETY, WIDTH, HEIGHT);

  /**
   * establishes coordinates and start sprite animation
   */
  public Cat(int translateX, int translateY) {
    setTranslateX(translateX);
    setTranslateY(translateY);
    vasyaView.setViewport(new Rectangle2D(OFFSETX, OFFSETY, WIDTH, HEIGHT));
    animation.setCycleCount(Animation.INDEFINITE);
    animation.play();
    getChildren().add(vasyaView);
  }

  public void animationStop() {
    animation.stop();
  }

  public void animationPlay() {
    animation.play();
  }

  /**
   * Check conflict between cat and moving object
   * 
   * @param object
   * @return true if conflict was
   */
  public boolean conflictWith(MovingObject object) {
    if (getBoundsInParent().intersects(object.getBoundsInParent())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Movement
   */
  public void moveNorth() {
    if ((getTranslateY() - speed) <= GameGraphics.INDENT_INFORMATION)
      return;
    setTranslateY(getTranslateY() - speed);
  }

  public void moveSouth() {
    if (getTranslateY() + getHeight() + speed >= GameGraphics.WIN_HEIGHT)
      return;
    setTranslateY(getTranslateY() + speed);
  }

  public void moveWest() {
    if (getTranslateX() - speed <= 0)
      return;
    setTranslateX(getTranslateX() - speed);
  }

  public void moveEast() {
    if ((getTranslateX() + WIDTH + speed) >= GameGraphics.WIN_WIDTH)
      return;
    setTranslateX(getTranslateX() + speed);
  }

  public void increaseSpeed() {
    if (speed >= maxSpeed)
      return;
    speed += increasingSpeed;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double newSpeed) {
    speed = newSpeed;
  }
}
