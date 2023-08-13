package nz.ac.auckland.se281.datastructures;

/** this is a node implementation for a doubly linked list. */
public class DoubleLinkedNode<T> extends SingleLinkedNode<T> {
  private DoubleLinkedNode<T> previousNode;

  public DoubleLinkedNode(T vertex) {
    super(vertex);
    previousNode = null;
  }

  public DoubleLinkedNode<T> getPrevious() {
    return previousNode;
  }

  public void setPrevious(DoubleLinkedNode<T> next) {
    previousNode = next;
  }
}
