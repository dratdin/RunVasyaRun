package sort;

import java.io.File;
import game.ResultsController;

/**
 * Class container for information about game It contain file name, file size, score.
 */
public class GameInfo {
  private String fileName = new String();
  private long fileSize;
  private long score;

  public GameInfo(String fileName) {
    this.fileName = fileName;
    this.fileSize = new File(fileName).length();
  }

  public String getFileName() {
    return fileName;
  }

  public long getFileSize() {
    return fileSize;
  }

  public long getScore() {
    ResultsController rc = new ResultsController();
    score = rc.calculateScore(fileName);
    return score;
  }
}

