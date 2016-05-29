package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.layout.Pane;
import objects.Fish;
import objects.MovingObject;
import objects.Box;
import objects.Cat;


/**
 * Class that control saving and replaying of game
 */
public class ResultsController {
  // this numbers used as flag for end coordinates in file
  public static final double END_BOXES = 2000;
  public static final double END_FISHES = 3000;

  private RandomAccessFile file;

  /**
   * Create file for saving with extend .save
   */
  public void createResultFile() {
    try {
      file = new RandomAccessFile(new File(new Date().toString() + ".save"), "rw");
    } catch (FileNotFoundException e) {
      System.out.println("File was not created!");
    }
  }

  /**
   * @param fileName name of opening file
   */
  public void openResultFile(String fileName) {
    try {
      file = new RandomAccessFile(new File(fileName), "rw");
    } catch (FileNotFoundException e) {
      System.out.println("File was not opened!");
    }
  }

  /**
   * Save Cat coordinates
   * 
   * @param vasya
   */
  public void saveVasya(Cat vasya) {
    try {
      file.writeDouble(vasya.getTranslateX());
      file.writeDouble(vasya.getTranslateY());
    } catch (IOException e) {
      System.out.println("Error of writing!");
    }
  }

  /**
   * Save object coordinates
   * 
   * @param object
   */
  public void saveObject(MovingObject object) {
    try {
      file.writeDouble(object.getTranslateX());
      file.writeDouble(object.getTranslateY());
    } catch (IOException e) {
      System.out.println("Error of writing!");
    }
  }

  /**
   * Save flag of end Boxes
   */
  public void saveEndBoxes() {
    try {
      file.writeDouble(END_BOXES);
    } catch (IOException e) {
      System.out.println("Error of writing!");
    }
  }

  /**
   * Save flag of end fishes
   */
  public void saveEndFishes() {
    try {
      file.writeDouble(END_FISHES);
    } catch (IOException e) {
      System.out.println("Error of writing!");
    }
  }

  /**
   * Set Cat coordinates from file
   * 
   * @param vasya
   * @return
   */
  public boolean loadVasya(Cat vasya) {
    try {
      if (file.getFilePointer() == file.length()) {
        return false;
      }

      double coordX = file.readDouble();
      double coordY = file.readDouble();
      if (coordX != vasya.getTranslateX()) {
        vasya.setTranslateX(coordX);
      }
      if (coordY != vasya.getTranslateY()) {
        vasya.setTranslateY(coordY);
      }
      return true;
    } catch (IOException e) {
      System.out.println("Error of reading!");
    }
    return false;
  }

  /**
   * Trying load coordinates of Box, and if successfully create new Box
   * 
   * @param boxes
   * @param gamePane
   * @return first reading double value
   */
  public double tryLoadBox(ArrayList<MovingObject> boxes, Pane gamePane) {
    try {
      double readDouble = file.readDouble();
      if (readDouble != END_BOXES) {
        Box box = new Box();
        box.setTranslateX(readDouble);
        box.setTranslateY(file.readDouble());
        boxes.add(box);
        gamePane.getChildren().add(box);
      }
      return readDouble;
    } catch (IOException e) {
      System.out.println("Error of reading!");
    }
    return END_BOXES;
  }

  /**
   * Trying load coordinates of Fish, and if successfully create new Fish
   * 
   * @param fishes (in this list will be added)
   * @param gamePane (-/-)
   * @return first reading double value
   */
  public double tryLoadFish(ArrayList<MovingObject> fishes, Pane gamePane) {
    try {
      double readDouble = file.readDouble();
      if(readDouble != END_FISHES) {
        Fish fish = new Fish();
        fish.setTranslateX(readDouble);
        fish.setTranslateY(file.readDouble());
        fishes.add(fish);
        gamePane.getChildren().add(fish);
      }
      return readDouble;
    } catch (IOException e) {
      System.out.println("Error of reading!");
    }
    return END_FISHES;
  }

  /**
   * Save score in file
   * 
   * @param score
   */
  public void saveScore(int score) {
    try {
      file.writeInt(score);
    } catch (IOException e) {
      System.out.println("Error of writing score!!!");
      e.printStackTrace();
    }
  }

  /**
   * Load and return score from file
   * 
   * @return positive number if it score, and negative if error of reading
   */
  public int loadScore() {
    try {
      long currentPointer = file.getFilePointer();
      file.seek(file.length() - Double.BYTES);
      int score = file.readInt();
      file.seek(currentPointer);
      return score;

    } catch (IOException e) {
      System.out.println("Error of loading score!!!");
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * Close results file
   */
  public void finalize() {
    try {
      file.close();
    } catch (IOException e) {
      System.out.println("File was not closed!");
    }
  }

  /**
   * 
   * @return directory content(but only items ".save")
   */
  public static String[] folderContent() {
    File folder = new File(".");
    File[] listOfFiles = folder.listFiles();
    int length = 0;
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].getName().contains(".save")) {
        length++;
      }
    }
    String[] content = new String[length];
    for (int i = 0, j = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].getName().contains(".save")) {
        content[j] = listOfFiles[i].getName();
        j++;
      }
    }
    return content;
  }

  public long calculateScore(String fileName) {
    try {
      RandomAccessFile sFile = new RandomAccessFile(new File(fileName), "rw");
      long partCount = 0;
      while (true) {
        if (sFile.getFilePointer() == sFile.length()) {
          break;
        }
        if (sFile.readDouble() == END_BOXES) {
          partCount++;
        }
      }
      sFile.close();
      return partCount / 4;
    } catch (FileNotFoundException e) {
      System.out.println("File was not find!");
    } catch (IOException e) {
      System.out.println("Read error score calculate!");
    }
    return 0;
  }
}
