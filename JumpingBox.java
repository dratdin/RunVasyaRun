package objects;

import javafx.animation.AnimationTimer;

public class JumpingBox extends Box {

  public JumpingBox() {
    initJumping();
  }

  private static final double MIN_JUMPING_SPEED = 1;
  private static final double MAX_JUMPING_SPEED = 6;
  private static double jumpingSpeed = MIN_JUMPING_SPEED;

  public static void increaseJumpingSpeed() {
    if (jumpingSpeed < MAX_JUMPING_SPEED) {
      jumpingSpeed++;
    }
  }

  private void jump() {
    setTranslateY(getTranslateY() - jumpingSpeed);
  }

  private void fall() {
    setTranslateY(getTranslateY() + jumpingSpeed);
  }

  private double jumpingRange = 75;
  AnimationTimer timer;

  public void initJumping() {
    timer = new AnimationTimer() {
      double rangePointer = jumpingRange;
      boolean up = true;

      @Override
      public void handle(long now) {
        if (getTranslateX() < 0 - PICTURE_WIDTH) {
          stop();
        }
        if (up) {
          jump();
          if (rangePointer <= 0 ) {
            up = false;
            rangePointer = jumpingRange;
          }
        } else {
          fall();
          if (rangePointer <= 0) {
            up = true;
            rangePointer = jumpingRange;
          }
        }
        rangePointer--;
      }
    };
  }
  
  /*private boolean checkOutUp() {
    
    return false;
  }*/

  public void startJumping() {
    timer.start();
  }

  public void stopJumping() {
    timer.stop();
  }
}
