package nz.ac.auckland.se281.datastructures;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  /**
   * find if another edges source and destination are the same, outputs true or false depending.
   *
   * @param source the other edges given source
   * @param destination the other edges given destination
   * @return boolean value, if they are the same return true false otherwise
   */
  public boolean equalEdge(T source, T destination) {
    if (this.source.equals(source) && this.destination.equals(destination)) {
      return true;
    }
    return false;
  }

  public T getSource() {
    return source;
  }

  public T getDestination() {
    return destination;
  }
}
