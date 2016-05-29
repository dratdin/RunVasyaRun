package statistics;

/**
 * Implement class for work with couple coordinates or single coordinate
 */
public class Coords {
  private double coordX;
  private double coordY;

  private long count;

  /**
   * Constructor for couple coordinates
   * 
   * @param coordX
   * @param coordY
   */
  public Coords(double coordX, double coordY) {
    this.coordX = coordX;
    this.coordY = coordY;
    count = 1;
  }

  /**
   * Constructor for single coordinate
   * 
   * @param coordY
   */
  public Coords(double coordY) {
    this.coordY = coordY;
    count = 1;
  }

  public boolean equals(Coords coords) {
    if (this.coordX != coords.coordX) {
      return false;
    }
    if (this.coordY != coords.coordY) {
      return false;
    }
    return true;
  }

  public void increaseCount() {
    count++;
  }

  public double getCoordX() {
    return coordX;
  }

  public double getCoordY() {
    return coordY;
  }

  public long getCount() {
    return count;
  }
}
