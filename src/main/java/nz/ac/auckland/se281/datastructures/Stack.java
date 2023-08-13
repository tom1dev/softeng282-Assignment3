package nz.ac.auckland.se281.datastructures;

/** a stack type data structure that works with T data type. */
public class Stack<T> {
  private SingleLinkedNode<T> top;

  public Stack() {
    this.top = null;
  }

  /**
   * creates a singily linked node from the inputed data, and places it at the top of the stack.
   *
   * @param data an inputted vertex
   */
  public void push(T data) {
    SingleLinkedNode<T> temp = new SingleLinkedNode<T>(data);
    if (top == null) {
      top = temp;
    } else {
      temp.setNext(top);
      top = temp;
    }
  }

  /**
   * removes the node at the top of the stack and returns it.
   *
   * @return the vertex at the top of the stack
   */
  public T pop() {
    SingleLinkedNode<T> temp = top;

    // if there is no next element after the current top, reference null to top. and return current
    // top data.
    if (top.getNext() == null) {
      top = null;
      T returningval = temp.getData();
      return returningval;
    }
    // if there is a next element after current top, turn it into the top and return the current top
    // data.
    else {
      top = temp.getNext();
      T returningval = temp.getData();
      return returningval;
    }
  }

  /**
   * gets the vertex that the top of the stack and returns it
   *
   * @return vertex at the top of the stack
   */
  public T peek() {
    return top.getData();
  }

  /**
   * finds the amount of nodes in the stack and returns it as an integer. it does this by looping
   * through the next elements of the nodes starting with the top and stops when there is no next
   * element.
   *
   * @return the size of the stack
   */
  public int size() {
    SingleLinkedNode<T> temptop = top;
    int i = 0;

    // while there is a next top element, set temptop as the next top element and increment the
    // count.
    if (temptop != null) {
      i = 1;

      while (temptop.getNext() != null) {
        temptop = temptop.getNext();
        i += 1;
      }
    }
    return i;
  }

  /**
   * finds if the stack is empty or not and returns a boolean.
   *
   * @return boolean returns true if stack is empty, otherwise false
   */
  public boolean isEmpty() {
    if (this.size() == 0) {
      return true;
    }
    return false;
  }
}
