package menu;

import game.GameGraphics;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Main element of menu that including several submenu
 */
public class MenuBox extends Pane {

  static SubMenu subMenu;

  public MenuBox(SubMenu subMenu) {
    MenuBox.subMenu = subMenu;

    setVisible(true);
    Rectangle bg = new Rectangle(GameGraphics.WIN_WIDTH, GameGraphics.WIN_HEIGHT, Color.BLACK);
    bg.setOpacity(0.025);
    getChildren().addAll(bg, subMenu);
  }

  public void setSubMenu(SubMenu subMenu) {
    getChildren().remove(MenuBox.subMenu);
    MenuBox.subMenu = subMenu;
    getChildren().add(MenuBox.subMenu);
  }

  public void setItem(Node item) {
    getChildren().add(item);
  }
}
