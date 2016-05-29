package statistics

import sort.GameInfo
import scala.collection.mutable.MutableList
import java.io.RandomAccessFile
import java.io.File
import game.GameGraphics
import game.ResultsController

/**
 * Implements math logic of statistics
 */
class Statistics() {

  /**
   * Add coordinates to received MutableList if this has not and increase count if has
   */
  private def addCoords(coords: Coords, list: MutableList[Coords]): MutableList[Coords] = {
    for (current <- list if coords.equals(current)) {
      current.increaseCount()
      return list
    }
    list.+=(coords)

    for (cur <- list if cur.getCoordY != ResultsController.END_BOXES &&
        cur.getCoordY != ResultsController.END_FISHES) yield cur
  }

  /**
   * Find max count of coordinates in ArrayBuffer and return his
   */
  private def findFreqCoords(list: MutableList[Coords]): Coords = {
    var freqCoords = list(0)
    for (cur <- list if freqCoords.getCount() < cur.getCount()) {
      freqCoords = cur
    }
    freqCoords
  }

  /**
   * Calculate average number of score for all games
   */
  def calculateAverageScore(games: Array[GameInfo]) = {
    var sum: Long = 0
    games.foreach((cur: GameInfo) => sum += cur.getScore())
    sum / games.length
  }

  private var fishesCoordsY = MutableList[Coords]()
  private var boxesCoordsY = MutableList[Coords]()
  private var vasyaCoords = MutableList[Coords]()

  /**
   * Find more frequency Y coordinate in the games for fishes and boxes
   * Find more frequency coordinates in the game for vasya
   */
  def collectInformations(gameInfo: Array[GameInfo]) {
    for (current <- gameInfo) {
      var file = new RandomAccessFile(new File(current.getFileName), "rw")
      while (file.getFilePointer != file.length()) {
        vasyaCoords = addCoords(new Coords(file.readDouble(), file.readDouble()), vasyaCoords)
        if (file.readDouble() != ResultsController.END_BOXES) {
          boxesCoordsY = addCoords(new Coords(file.readDouble()), boxesCoordsY)
          file.readDouble()
        }
        if (file.readDouble() != ResultsController.END_FISHES) {
          fishesCoordsY = addCoords(new Coords(file.readDouble()), fishesCoordsY)
          file.readDouble()
        }
      }
      file.close()
    }
  }

  def getFreqVasya() = {
    findFreqCoords(vasyaCoords)
  }

  def getFishesCoords() = {
    fishesCoordsY.toArray
  }

  def getBoxesCoords() = {
    boxesCoordsY.toArray
  }
}