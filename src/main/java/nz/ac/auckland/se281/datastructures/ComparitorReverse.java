package nz.ac.auckland.se281.datastructures;

import java.util.Comparator;

// Class 1
// Helper class
class ComparitorReverse<T> implements Comparator<T> {

  // Method
  // To compare two strings
  public int compare(T str1, T str2) {

    String a = (String) str1;
    String b = (String) str2;

    int type = 0;
    if (Integer.parseInt(a) > Integer.parseInt(b)) {
      type = -1;
    } else if (Integer.parseInt(a) > Integer.parseInt(b)) {
      type = 1;
    }

    // using compareTo() to ensure
    return type;
  }
}
