package menu;

import game.GameGraphics;
import javafx.scene.layout.VBox;

/**
 * Submenu
 */
public class SubMenu extends VBox {

  public static final int SPACING = 2;
  private int itemNumber;

  public SubMenu(MenuItem... items) {
    itemNumber = items.length;
    setSpacing(SPACING);
    // locate submenu on a windows center
    setTranslateY(
        (GameGraphics.WIN_HEIGHT - (MenuItem.HEIGHT * itemNumber + SPACING * itemNumber)) / 2);
    setTranslateX((GameGraphics.WIN_WIDTH - MenuItem.WIDTH) / 2);
    for (MenuItem item : items) {
      getChildren().addAll(item);
    }
  }
}
