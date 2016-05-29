package substitution;

import game.GameGraphics;
import game.ResultsController;

/**
 * Class implement parsing notations to user view Include 2 parts 1) Calculating duration of action
 * 2) Parsing this action to string and print to console
 */
public class Substitution {

  private double vasyaX = 0;
  private double vasyaY = GameGraphics.WIN_HEIGHT / 2;
  private char vasyaAction = 's';
  private long actionTime = 1;

  /**
   * Analyze vasya cords and compare with previous cords And calculate duration of action
   * 
   * @param x - cord x of now step
   * @param y - cord y
   */
  public void analyzeVasya(double x, double y) {
    if (x == vasyaX && y == vasyaY) {
      if (vasyaAction == 's') {
        actionTime++;
      } else {
        printAction();
        updateAction('s');
      }
    } else if (x > vasyaX) {
      if (vasyaAction == 'r') {
        actionTime++;
      } else {
        printAction();
        updateAction('r');
      }
    } else if (x < vasyaX) {
      if (vasyaAction == 'l') {
        actionTime++;
      } else {
        printAction();
        updateAction('l');
      }
    } else if (y > vasyaY) {
      if (vasyaAction == 'u') {
        actionTime++;
      } else {
        printAction();
        updateAction('u');
      }
    } else if (y < vasyaY) {
      if (vasyaAction == 'd') {
        actionTime++;
      } else {
        printAction();
        updateAction('d');
      }
    }
    vasyaX = x;
    vasyaY = y;
  }

  /**
   * Analyzes whether the box was created
   * 
   * @param coord
   */
  public void analyzeBoxes(double coord) {
    Matching m = new Matching();
    if (coord != ResultsController.END_BOXES) {
      System.out.println(m.parseNotation('b'));
    }
  }

  /**
   * Analyzes whether the fish was created
   * 
   * @param coord
   */
  public void analyzeFishes(double coord) {
    Matching m = new Matching();
    if (coord != ResultsController.END_FISHES) {
      System.out.println(m.parseNotation('f'));
    }
  }

  /**
   * Reset Vasya action
   * 
   * @param action
   */
  private void updateAction(char action) {
    vasyaAction = action;
    actionTime = 1;
  }

  /**
   * Parsing action with help Matching scala class
   */
  private void printAction() {
    Matching m = new Matching();
    switch (vasyaAction) {
      case 's':
        System.out
            .println(m.parseNotation("удерживал позицию") + m.parseNotation((double) actionTime));
        return;
      case 'r':
        System.out.println(m.parseNotation("двигался вправо") + m.parseNotation((int) actionTime));
        return;
      case 'l':
        System.out.println(m.parseNotation("двигался влево") + m.parseNotation((int) actionTime));
        return;
      case 'u':
        System.out.println(m.parseNotation("двигался вверх") + m.parseNotation((int) actionTime));
        return;
      case 'd':
        System.out.println(m.parseNotation("двигался вниз") + m.parseNotation((int) actionTime));
        return;
    }
  }
}
