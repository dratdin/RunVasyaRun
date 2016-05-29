package game;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import objects.MovingObject;
import objects.Cat;

/**
 * This class controls: 1) out the range 2) movement objects 3) a conflicts Realized SERVER part
 */
public class ObjectsController implements Runnable {

  private Pane parentPane;

  /**
   * 
   */
  public ObjectsController(Pane parentPane) {
    this.parentPane = parentPane;
  };

  /**
   * Check out the range if object out the range (left side of window) delete this object
   * 
   * @param objectList
   */
  public void isMovingObjectOut(ArrayList<MovingObject> objectList) {
    for (MovingObject object : objectList) {
      if (object.getTranslateX() < -1 * object.getWidth()) {
        objectList.remove(object);
        parentPane.getChildren().remove(object);
        break;
      }
    }
  }

  /**
   * Movement objects
   * 
   * @param objectList
   */
  public void move(ArrayList<MovingObject> objectList) {
    for (MovingObject object : objectList) {
      object.move();
    }
  }

  /**
   * Check conflicts between cat and moving objects
   * 
   * @param vasya
   * @param mObjects
   * @return true if conflict was
   */
  public boolean conflicts(Cat vasya, ArrayList<MovingObject> mObjects, boolean destroy) {
    for (MovingObject object : mObjects) {
      if (vasya.conflictWith(object)) {
        if (destroy) {
          parentPane.getChildren().remove(object);
          mObjects.remove(object);
        }
        return true;
      }
    }
    return false;
  }

  /**
   * SERVER MODE
   */
  private ArrayList<MovingObject> boxes;
  private Cat vasya;
  private boolean gameOverFlag = false;

  /**
   * Constructor for server mode
   * 
   * @param parentPane
   * @param boxes
   * @param fishes
   * @param vasya
   */
  public ObjectsController(Pane parentPane, ArrayList<MovingObject> boxes,
      ArrayList<MovingObject> fishes, Cat vasya) {
    this.parentPane = parentPane;
    this.boxes = boxes;
    this.vasya = vasya;
  }

  /**
   * Check conflicts between boxes and vasya, and set game over flag
   * 
   * @return
   */
  private boolean checkOnGameOver() {
    for (MovingObject object : boxes) {
      if (vasya.conflictWith(object)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void run() {
    if (checkOnGameOver()) {
      gameOverFlag = true;
    }
  }

  public boolean getGameOverFlag() {
    return gameOverFlag;
  }
}
