package menu;

import game.GameGraphics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import sort.*;

/**
 * Realized list of file on the base ListView
 *
 * Takes Collection of String and displays this
 */
public class FileList extends ListView<String> {

  private GameInfo[] items;
  public static final int WIDTH = MenuItem.WIDTH;
  public static final int HEIGHT = 350;
  private final int spacingY = 40;

  public FileList(String[] addenItems) {
    setPrefSize(WIDTH, HEIGHT);
    setTranslateX((GameGraphics.WIN_WIDTH - WIDTH) / 2);
    setTranslateY(spacingY);
    items = new GameInfo[addenItems.length];
    for (int i = 0; i < addenItems.length; i++) {
      items[i] = new GameInfo(addenItems[i]);
    }
    ObservableList<String> ol = FXCollections.observableArrayList();
    ol.addAll(addenItems);
    setItems(ol);
  }

  public void setFiles(String[] addenItems) {
    ObservableList<String> ol = FXCollections.observableArrayList();
    ol.addAll(addenItems);
    items = new GameInfo[addenItems.length];
    for (int i = 0; i < addenItems.length; i++) {
      items[i] = new GameInfo(addenItems[i]);
    }
    setItems(ol);
  }

  public void javaSort() {
    QuickSort quickSort = new QuickSort();
    long start = System.nanoTime();
    quickSort.sort(items);
    long end = System.nanoTime();
    System.out.printf("Java: %d\n", end - start);
    ObservableList<String> ol = FXCollections.observableArrayList();
    for (int i = 0; i < items.length; i++) {
      ol.add(items[i].getFileName());
    }
    setItems(ol);
  }

  public void scalaSort() {
    QuickSortScala quickSort = new QuickSortScala();
    long start = System.nanoTime();
    quickSort.sort(items);
    long end = System.nanoTime();
    System.out.printf("Scala: %d\n", end - start);
    ObservableList<String> ol = FXCollections.observableArrayList();
    for (int i = 0; i < items.length; i++) {
      ol.add(items[i].getFileName());
    }
    setItems(ol);
  }
}
