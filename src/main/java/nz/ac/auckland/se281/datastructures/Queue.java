package nz.ac.auckland.se281.datastructures;

/** a queue data structure that works with the T data type. */
public class Queue<T> {
  private DoubleLinkedNode<T> head;
  private DoubleLinkedNode<T> tail;

  public Queue() {
    head = null;
    tail = null;
  }

  /**
   * enqueues the inputted data into the queue, this is set as the tail of the queue, and the
   * inputed data is used to create a new doubly linked node for the queue.
   *
   * @param data inputted vertex for the queue
   */
  public void enqueue(T data) {
    DoubleLinkedNode<T> temp = new DoubleLinkedNode<T>(data);

    // if there is no elements in the queue set inputted data as both the head and tail
    if (head == null) {
      head = temp;
      tail = temp;
    }
    // if there is only one element in the queue set the current node as tail, and connect the head
    // and tail
    else if (head.equals(tail)) {
      tail = temp;
      tail.setNext(head);
      head.setPrevious(tail);
    }
    // if there is more than one element, connect the current tail to the new data then set current
    // node to tail.
    else {
      tail.setPrevious(temp);
      tail = temp;
    }
  }

  /**
   * dequeues the head node from the queue and removes it from the queue. the node behind the former
   * head becomes the head.
   *
   * @return returns the data at the head of the queue
   */
  public T dequeue() {

    DoubleLinkedNode<T> temp = head;

    // if there is no next node in the queue set the tail and head references to 0 and return the
    // current tail
    if (head.getPrevious() == null) {
      head = null;
      tail = null;
      T returningVal = temp.getData();
      return returningVal;
    }

    // set head to the the next element in the queue and return the current head.
    else {
      head = temp.getPrevious();
      T returningVal = temp.getData();
      return returningVal;
    }
  }

  /**
   * gets the vertex at the head of the queue and returns it.
   *
   * @return queues head data
   */
  public T peek() {
    return head.getData();
  }

  /**
   * find the size of the queue and returns it as an integer. it does this by starting at the head
   * and looping through to the next element in the queue till its at the tail.
   *
   * @return returns the size of the queue
   */
  public int size() {

    // create a new temporary node
    DoubleLinkedNode<T> temphead = head;
    int i = 0;

    // while there is a next element in the queue add one to the count
    if (head != null) {
      i = 1;

      while (temphead.getPrevious() != null) {
        temphead = temphead.getPrevious();
        i += 1;
      }
    }

    // return the count
    return i;
  }

  /**
   * finds if the queue is empty and returns a boolean depending.
   *
   * @return boolean return is true if the queue is empty otherwise it is false
   */
  public boolean isEmpty() {
    if (head == null) {
      return true;
    }
    return false;
  }
}
