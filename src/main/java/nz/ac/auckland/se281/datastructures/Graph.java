package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  private Set<T> verticies;
  private Set<Edge<T>> edges;

  /**
   * constructer method for Graph class.
   *
   * @param verticies inputted vertices for graph
   * @param edges inputted edges for graph
   */
  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * finds the roots of a given graph and appends it to a treeset. first it finds if a given edges
   * source is a reflexive only root it then checks for vertices with multiple destinations and
   * appends there sources.
   *
   * @return the set of roots
   */
  public Set<T> getRoots() {
    // set of the roots that haven't been sorted into numerical order
    ArrayList<T> unsortedRoots = new ArrayList<T>();
    Set<T> sortedRoots = new TreeSet<T>(new Comparitor<T>());

    // gets only self looped vertexs and cycles
    Set<T> vertexTests = new HashSet<>(verticies);

    for (Edge<T> edge : edges) {
      TreeSet<T> equivencetest = (TreeSet<T>) getEquivalenceClass(edge.getSource());
      // checks if a given edge is reflexive and that it is only in relation to itself
      if (edge.getSource().equals(edge.getDestination())) {

        if (equivencetest.size() == 1) {
          unsortedRoots.add(edge.getSource());
        }
      }
      // finds the smallest equivelence value in an equivalnce relation
      else if (!(equivencetest.size() == 0)) {

        T smallest = equivencetest.first();
        for (T vertex : equivencetest) {
          if (Integer.parseInt((String) vertex) < Integer.parseInt((String) smallest)) {
            smallest = vertex;
          }
        }

        unsortedRoots.add((T) smallest);
      }
      // removes all vertexs that are mapped to, from the vertexTest set as they cannot be the root
      vertexTests.remove(edge.getDestination());
    }
    // regualar graphs

    unsortedRoots.addAll(vertexTests);

    while (unsortedRoots.size() != 0) {
      T smallest = unsortedRoots.get(0);
      for (T vertex : unsortedRoots) {
        if (Integer.parseInt((String) vertex) < Integer.parseInt((String) smallest)) {
          smallest = vertex;
        }
      }

      sortedRoots.add(smallest);
      unsortedRoots.remove(smallest);
    }
    return sortedRoots;
  }

  /**
   * checks if the inputted graph is reflexive and returns true or flase depending.
   *
   * @return returns true if the given graph is reflexive else it returns false
   */
  public boolean isReflexive() {
    // TODO: Task 1.
    Set<T> reflexiveVertexs = new HashSet<>();

    // checks if a given vertex maps to itself and adds it to the set
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(edge.getDestination())) {
        reflexiveVertexs.add(edge.getSource());
      }
    }

    if (reflexiveVertexs.equals(verticies)) {
      return true;
    }
    return false;
  }

  /**
   * checks if the inputted graph is symmetric and returns true or flase depending.
   *
   * @return returns true if the given graph is symmetric else it returns false
   */
  public boolean isSymmetric() {

    for (Edge<T> edge : edges) {
      int containsInverse = 0;

      // finds the vertexs of an edge then switchs them to see if there is an inverse in the set of
      // edges
      T source = edge.getSource();
      T destination = edge.getDestination();
      for (Edge<T> edgeinverse : edges) {
        if (edgeinverse.equalEdge(destination, source)) {
          containsInverse += 1;
        }
      }

      // if there is no inverse return false
      if (containsInverse == 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * checks if the inputted graph is transitive and returns true or flase depending.
   *
   * @return returns true if the given graph is transitive else it returns false
   */
  public boolean isTransitive() {
    Set<Edge<T>> transitive = new HashSet<>();

    // finds all posible cases of transitivity, and apends it to a set
    for (Edge<T> edgeA : edges) {
      for (Edge<T> edgeB : edges) {
        if (edgeA.getDestination().equals(edgeB.getSource())) {
          T edgeSourceA = edgeA.getSource();
          T edgeDestinationB = edgeB.getDestination();
          transitive.add(new Edge<T>(edgeSourceA, edgeDestinationB));
        }
      }
    }

    // checks if for all possible cases of transitivity there is an edge
    for (Edge<T> transEdge : transitive) {
      int isEdgeTrans = 0;
      for (Edge<T> edge : edges) {
        if (transEdge.equalEdge(edge.getSource(), edge.getDestination())) {
          isEdgeTrans = 1;
        }
      }
      if (isEdgeTrans == 0) {
        return false;
      }
    }

    return true;
  }

  /**
   * checks if the inputted graph is antisymmetric and returns true or flase depending.
   *
   * @return returns true if the given graph is antisymmetric else it returns false
   */
  public boolean isAntiSymmetric() {
    // for any 2 edges if a>b and B>a but !(a=b) then it is not antisymettric
    for (Edge<T> edgeA : edges) {
      for (Edge<T> edgeB : edges) {
        if (edgeA.getDestination().equals(edgeB.getSource())
            && edgeA.getSource().equals(edgeB.getDestination())) {
          if (!edgeA.equals(edgeB)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * checks if the inputted graph is a equivalence relation and returns true or flase depending.
   *
   * @return returns true if the given graph is equivalence relation else it returns false
   */
  public boolean isEquivalence() {

    if (isTransitive() && isSymmetric() && isReflexive()) {
      return true;
    }
    return false;
  }

  /**
   * finds the equivalence class for a inputted vertex. if a given vertex is not in a equivelence
   * relation then it cannot be in a equivelence class and in returns an empty string. else it finds
   * the vertexs in relation to the parameter.
   *
   * @param vertex a vertex we want to find the eqivilence class for
   * @return returns the set of the equivence class for the given vertex
   */
  public Set<T> getEquivalenceClass(T vertex) {

    TreeSet<T> equivilenceClass = new TreeSet<T>();
    // a equivalenceClass cannot be if there is no equivalence relationship
    if (isEquivalence()) {
      // if a given edge if it has the correct sorce vertex then find all related vertexs
      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(vertex)) {
          equivilenceClass.add(edge.getDestination());
        }
      }
    }
    return equivilenceClass;
  }

  /**
   * ittertivativly finds the iterative breadthFirstSearch for the given graph, and returns a List
   * of the vertex in breadth order, starting from the roots of the graph and working their way
   * downwards.
   *
   * @return the list of vertexs in breadth order
   */
  public List<T> iterativeBreadthFirstSearch() {
    Queue<T> depthFirstStack = new Queue<T>();
    TreeSet<T> roots = (TreeSet<T>) getRoots();
    List<T> depthFirstSearchList = new ArrayList<T>();

    for (T root : roots) {
      depthFirstStack.enqueue(root);

      boolean isstackempty = !depthFirstStack.isEmpty();
      // runs the algorithim until all values have been calculated.
      while (isstackempty) {
        T currentVal = depthFirstStack.dequeue();

        if (!depthFirstSearchList.contains(currentVal)) {
          depthFirstSearchList.add(currentVal);
        }

        TreeSet<T> orderOfdestinations = new TreeSet<T>();

        // finds the destinations possible from a given vertex
        for (Edge<T> edge : edges) {

          if (edge.getSource().equals(currentVal)
              && !depthFirstSearchList.contains(edge.getDestination())) {

            orderOfdestinations.add(edge.getDestination());
          }
        }
        // orders and pushs values in order of largest to smallest
        while (orderOfdestinations.size() != 0) {
          T smallest = orderOfdestinations.first();
          for (T vertex : orderOfdestinations) {
            if (Integer.parseInt((String) vertex) < Integer.parseInt((String) smallest)) {
              smallest = vertex;
            }
          }

          depthFirstStack.enqueue(smallest);
          orderOfdestinations.remove(smallest);
        }

        isstackempty = !depthFirstStack.isEmpty();
      }
    }

    return depthFirstSearchList;
  }

  /**
   * ittertivativly finds the iterative depth First order of vertexs for the given graph, and
   * returns a List of the vertex in depth order, starting from the roots of the graph and working
   * their way downwards.
   *
   * @return the list of vertexs in depth order
   */
  public List<T> iterativeDepthFirstSearch() {
    Stack<T> depthFirstStack = new Stack<T>();
    TreeSet<T> rootsReverseOrder = new TreeSet<>(new ComparitorReverse<T>());
    rootsReverseOrder.addAll((TreeSet<T>) getRoots());

    List<T> depthFirstSearchList = new ArrayList<T>();

    for (T root : rootsReverseOrder) {
      depthFirstStack.push(root);
    }
    boolean isstackempty = !depthFirstStack.isEmpty();
    // runs the algorithim until all values have been calculated.
    while (isstackempty) {
      T currentVal = depthFirstStack.pop();

      if (!depthFirstSearchList.contains(currentVal)) {
        depthFirstSearchList.add(currentVal);
      }

      TreeSet<T> orderOfdestinations = new TreeSet<T>();

      // finds the destinations possible from a given vertex
      for (Edge<T> edge : edges) {

        if (edge.getSource().equals(currentVal)
            && !depthFirstSearchList.contains(edge.getDestination())) {

          orderOfdestinations.add(edge.getDestination());
        }
      }
      // orders and pushs values in order of largest to smallest
      while (orderOfdestinations.size() != 0) {
        T largest = orderOfdestinations.first();
        for (T vertex : orderOfdestinations) {
          if (Integer.parseInt((String) vertex) > Integer.parseInt((String) largest)) {
            largest = vertex;
          }
        }

        depthFirstStack.push(largest);
        orderOfdestinations.remove(largest);
      }

      isstackempty = !depthFirstStack.isEmpty();
    }

    return depthFirstSearchList;
  }

  /**
   * recursively finds the iterative breadthFirstSearch for the given graph, and returns a List of
   * the vertex in breadth order, it passes the roots of the graph into a recursive function that
   * calculates the bredth order of a given graph.
   *
   * @return the list of vertexs in breadth order
   */
  public List<T> recursiveBreadthFirstSearch() {

    Queue<T> bredthSearchQueue = new Queue<T>();
    TreeSet<T> roots = (TreeSet<T>) getRoots();

    List<T> visited = new ArrayList<T>();

    // adds all the roots to the queue and then recursively finds breadth first search for the given
    // root, and adds them to the visited list in order.
    for (T root : roots) {
      bredthSearchQueue.enqueue(root);
      recursiveFinderBreadthFirstSearch(bredthSearchQueue, visited);
    }

    return visited;
  }

  /**
   * this function recursively calls it's self to find the breadth order of a given root and appends
   * this order to the visited list. it then calls itself recursively.
   *
   * @param breadthSearchQueue the queue that stores data on given vertexs
   * @param visited the list of vertexs that have allready visted and put into correct order by the
   *     algerithim
   */
  public void recursiveFinderBreadthFirstSearch(Queue<T> breadthSearchQueue, List<T> visited) {

    // get the current element from the queue
    T current = breadthSearchQueue.dequeue();

    // if the current element is not already in the list visited vertex's add it.
    if (!visited.contains(current)) {
      visited.add(current);
    }

    TreeSet<T> currentNodesDestination = new TreeSet<T>();

    // finds all destinations of the current node and adds them to currentNodeDestination
    for (Edge<T> a : edges) {
      if (current.equals(a.getSource()) && !visited.contains(a.getDestination())) {
        currentNodesDestination.add(a.getDestination());
      }
    }

    int amountofDestinations = currentNodesDestination.size();

    // while there is still elements in the currentNodes Destination, find the current smallest
    // vertex that is a destination of the current vertex and add it
    // to the queue and list of visted nodes.
    while (currentNodesDestination.size() != 0) {
      T smallest = currentNodesDestination.first();
      for (T vertex : currentNodesDestination) {
        if (Integer.parseInt((String) vertex) < Integer.parseInt((String) smallest)) {
          smallest = vertex;
        }
      }

      if (!visited.contains(smallest)) {
        visited.add(smallest);
        breadthSearchQueue.enqueue(smallest);
      }

      currentNodesDestination.remove(smallest);
    }

    // proforms recursiveFinderBedthFirstSearch on all nodes connected to the current node.
    if (breadthSearchQueue.size() != 0) {
      for (int i = 0; i < amountofDestinations; i++) {
        recursiveFinderBreadthFirstSearch(breadthSearchQueue, visited);
      }
    }
  }

  /**
   * recursively finds the iterative depthFirstSearch for the given graph, and returns a List of the
   * vertex in depth order, it passes the roots of the graph into a recursive function that
   * calculates the depth order of a given graph.
   *
   * @return the list of vertexs in breadth order
   */
  public List<T> recursiveDepthFirstSearch() {

    TreeSet<T> roots = (TreeSet<T>) getRoots();
    List<T> visited = new ArrayList<T>();

    for (T root : roots) {
      recursiveFinderDepthFirstSearch(visited, root);
    }
    return visited;
  }

  /**
   * this function recursively calls it's self to find the depth order of a given root and appends
   * this order to the visited list. it then calls itself recursively.
   *
   * @param visited the list of vertexs that have allready visted and put into correct order by the
   *     algerithim
   * @param current the current vertex that we are finded the edges for
   */
  public void recursiveFinderDepthFirstSearch(List<T> visited, T current) {

    // if the current node hasn't already been visited then add it to the visited list
    if (!visited.contains(current)) {
      visited.add(current);
    }

    TreeSet<T> currentNodesDestination = new TreeSet<T>();

    // finds all the destination for the current vertex
    for (Edge<T> a : edges) {
      if (current.equals(a.getSource()) && !visited.contains(a.getDestination())) {
        currentNodesDestination.add(a.getDestination());
      }
    }

    // while destinations of current vertex haven't been checked, recursively visit them.
    while (currentNodesDestination.size() != 0) {
      T smallest = currentNodesDestination.first();
      for (T vertex : currentNodesDestination) {
        if (Integer.parseInt((String) vertex) < Integer.parseInt((String) smallest)) {
          smallest = vertex;
        }
      }

      recursiveFinderDepthFirstSearch(visited, smallest);
      currentNodesDestination.remove(smallest);
    }
  }
}
