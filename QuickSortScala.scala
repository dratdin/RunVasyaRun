package sort

/**
 * Implement quick sort on Scala
 */
class QuickSortScala {
  def sort(currentArray: Array[GameInfo]) {
    def swap(i: Int, j: Int) {
      val temp = currentArray(i);
      currentArray(i) = currentArray(j);
      currentArray(j) = temp
    }

    def quickSort(low: Int, high: Int) {
      val pivot = currentArray((low + high) / 2).getFileSize()
      var i = low;
      var j = high
      while (i <= j) {
        while (currentArray(i).getFileSize() > pivot) i += 1
        while (currentArray(j).getFileSize() < pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (low < j) quickSort(low, j)
      if (j < high) quickSort(i, high)
    }
    quickSort(0, currentArray.length - 1)
  }
}