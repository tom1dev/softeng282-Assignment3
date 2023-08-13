package nz.ac.auckland.se281.datastructures;

/** this is a node implementation for a singly linked list. */
public class SingleLinkedNode<T> {
  protected T data;
  protected SingleLinkedNode<T> nextNode;

  public SingleLinkedNode(T vertex) {
    this.data = vertex;
    nextNode = null;
  }

  public T getData() {
    return data;
  }

  public void setData(T vertex) {
    this.data = vertex;
  }

  public SingleLinkedNode<T> getNext() {
    return nextNode;
  }

  public void setNext(SingleLinkedNode<T> next) {
    nextNode = next;
  }
}
