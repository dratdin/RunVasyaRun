package substitution

/**
 * Parse variable with help match case
 */
class Matching {
  def parseNotation(obj: Any): String = {
    obj match {
      case time: Double => " " + time / 60 + " секунд"
      case number: Int => " на " + number + " px"
      case text: String => "Вася " + text + " "
      case symb: Char => if (symb == 'f') {
        "Рыба была создана"
      } else {
        "Ящик был создан"
      }
      case _ => "Error. Sample not found."
    }
  }
}