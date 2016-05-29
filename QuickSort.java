package sort;

/**
 * Implement quick sort on java
 */
public class QuickSort {

  private void swap(GameInfo[] array, int i, int j) {
    GameInfo tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  private int partition(GameInfo[] array, int begin, int end) {
    int index = begin + (end - begin) / 2;
    GameInfo pivot = array[index];
    swap(array, index, end);
    for (int i = index = begin; i < end; ++i) {
      if (array[i].getFileSize() > (pivot).getFileSize()) {
        swap(array, index++, i);
      }
    }
    swap(array, index, end);
    return (index);
  }

  private void qsort(GameInfo[] array, int begin, int end) {
    if (end > begin) {
      int index = partition(array, begin, end);
      qsort(array, begin, index - 1);
      qsort(array, index + 1, end);
    }
  }

  public void sort(GameInfo[] array) {
    qsort(array, 0, array.length - 1);
  }
}
