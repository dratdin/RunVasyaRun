package objects;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * A moving object Represents a panel that added element obtained in  Constructor (be it a
 * rectangle, picture, etc.) This object will be a super class for all bonuses, barriers, etc.
 */
public abstract class MovingObject extends Pane {

  private static final double MIN_SPEED = 7;
  private static final double MAX_SPEED = 15;
  private static final double INCREASING = 0.5;
  private static double speed = MIN_SPEED;

  public MovingObject() {}

  public MovingObject(Node node) {
    getChildren().add(node);
  }

  public void move() {
    setTranslateX(getTranslateX() - speed);
  }

  public static void increaseSpeed() {
    if (speed >= MAX_SPEED)
      return;
    speed += INCREASING;
  }

  public static void resetSpeed() {
    speed = MIN_SPEED;
  }

  public static double getSpeed() {
    return speed;
  }

  public static void setSpeed(double newSpeed) {
    speed = newSpeed;
  }
}
