//Syedul Khan   15330190

import java.util.LinkedList;
import java.util.List;

public class PathFinder {

    private int N; // size of the matrix
    private String metric;
    private Node[][] nodes; // 2D array to store the nodes on the grid
    private double horizontalVerticalCost;
    private double diagonalCost;
    private int adjacentWeight;

    public PathFinder(int N, Node[][] nodes, String metric) {
        this.N = N;
        this.nodes = nodes;
        this.metric = metric;
        // Below are all the different weights for each metric type
        // that are stated in the specification
        switch (metric) {
            case "Manhattan":
                horizontalVerticalCost = 1.0;
                diagonalCost = 2.0;
                break;
            case "Euclidean":
                horizontalVerticalCost = 1.0;
                diagonalCost = 1.4;
                break;
            case "Chebyshev":
                horizontalVerticalCost = 1.0;
                diagonalCost = 1.0;
                break;
            default:
                break;
        }

    }

    // This method calculates the shortest path from the start node
    // to the end node
    public final List<Node> findPath(int startI, int startJ, int goalI, int goalJ) {
        // If the start node is the same as the goal node
        // then return an empty path
        if (startI == goalI && startJ == goalJ) {
            return new LinkedList<>();
        }
        List<Node> nodesVisited = new LinkedList<>();   // The set of nodes visited.
        List<Node> nodesNotVisited = new LinkedList<>(); // The set of nodes yet to be visited.
        Node current = null;
        nodesVisited.add(nodes[startI][startJ]);
        // This loop will be broken as soon as the current node position is
        // equal to the goal position.
        while (true) {
            current = cheapestFValueInList(nodesVisited); // Gets node with the lowest weight from nodesVisited list.
            nodesVisited.remove(current);          // Remove current node from nodesVisited list.
            nodesNotVisited.add(current);           // Add current node to nodesNotVisited list.

            if ((current.getICoordinate() == goalI) && (current.getJCoordinate() == goalJ)) {
                return calculatePath(nodes[startI][startJ], current); // Return a LinkedList containing all of the visited nodes.
            }

            List<Node> adjacentNodes = getAdjacent(current, nodesNotVisited);
            for (Node adjacent : adjacentNodes) {
                if (!nodesVisited.contains(adjacent)) {
                    adjacent.setParent(current);    // Set current node as parent for this node.
                    adjacent.setH(nodes[goalI][goalJ], metric); // Set H cost of this node.
                    nodesVisited.add(adjacent); // Add node to nodesVisited.

                } else if (adjacent.getG() > adjacent.calculateG(current)) {
                    adjacent.setParent(current); // Set current node as parent for this node.
                }
            }
            // If no path exists ...
            if (nodesVisited.isEmpty()) {
                return new LinkedList<>(); // Return an empty list.
            }
        }
    }

    //This method will return a list of all visited nodes from goal
    //node to the start node
    private List<Node> calculatePath(Node start, Node goal) {
        LinkedList<Node> path = new LinkedList<>();

        Node node = goal;
        boolean done = false;
        while (!done) {
            path.addFirst(node);
            node = node.getParent();
            if (node.equals(start)) {
                done = true;
            }
        }
        return path;
    }

    //This method analyses and compares nodes according to their F Value and will
    //return the cheapest node
    private Node cheapestFValueInList(List<Node> list) {
        Node cheapest = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getF() < cheapest.getF()) {
                cheapest = list.get(i);
            }
        }
        return cheapest;
    }

    //This method examines all possible paths adjacent to given node to check
    //if can be moved to and if it exists
    private List<Node> getAdjacent(Node node, List<Node> nodesNotVisited) {
        List<Node> adjacentNodes = new LinkedList<>();
        int i = node.getICoordinate();
        int j = node.getJCoordinate();

        Node adjacent;
        // Check top nodes
        if (i > 0) {
            //Top node
            adjacent = getNode(i - 1, j);
            if (adjacent != null) {
                adjacentWeight = adjacent.getWeight();
            }
            if (adjacent != null && adjacent.getIsNotBlocked() && !nodesNotVisited.contains(adjacent)) {
                adjacent.setG(node, horizontalVerticalCost * adjacentWeight);
                adjacentNodes.add(adjacent);
            }

            if (!metric.equals("Manhattan")) {
                // Top Left
                if (j - 1 >= 0) {
                    adjacent = getNode(i - 1, j - 1);
                    if (adjacent != null) {
                        adjacentWeight = adjacent.getWeight();
                    }
                    if (adjacent != null && adjacent.getIsNotBlocked() && !nodesNotVisited.contains(adjacent)) {
                        adjacent.setG(node, diagonalCost * adjacentWeight);
                        adjacentNodes.add(adjacent);
                    }
                }

                // Top Right
                if (j + 1 < N) {
                    adjacent = getNode(i - 1, j + 1);
                    if (adjacent != null) {
                        adjacentWeight = adjacent.getWeight();
                    }
                    if (adjacent != null && adjacent.getIsNotBlocked() && !nodesNotVisited.contains(adjacent)) {
                        adjacent.setG(node, diagonalCost * adjacentWeight);
                        adjacentNodes.add(adjacent);
                    }
                }
            }
        }

        // Check bottom nodes
        if (i < N) {
            //bottom node
            adjacent = getNode(i + 1, j);
            if (adjacent != null) {
                adjacentWeight = adjacent.getWeight();
            }
            if (adjacent != null && adjacent.getIsNotBlocked() && !nodesNotVisited.contains(adjacent)
                /*&& !metric.equals("Euclidean")*/) {
                adjacent.setG(node, horizontalVerticalCost * adjacentWeight);
                adjacentNodes.add(adjacent);
            }

            if (!metric.equals("Manhattan")) {
                //bottom left node
                if (j - 1 >= 0) {
                    adjacent = getNode(i + 1, j - 1);
                    if (adjacent != null) {
                        adjacentWeight = adjacent.getWeight();
                    }
                    if (adjacent != null && adjacent.getIsNotBlocked() && !nodesNotVisited.contains(adjacent)) {
                        adjacent.setG(node, diagonalCost * adjacentWeight);
                        adjacentNodes.add(adjacent);
                    }
                }

                //bottom right node
                if (j + 1 < N) {
                    adjacent = getNode(i + 1, j + 1);
                    if (adjacent != null) {
                        adjacentWeight = adjacent.getWeight();
                    }
                    if (adjacent != null && adjacent.getIsNotBlocked() && !nodesNotVisited.contains(adjacent)) {
                        adjacent.setG(node, diagonalCost * adjacentWeight);
                        adjacentNodes.add(adjacent);
                    }
                }
            }

        }

        // Check left node
        if (j > 0) {
            adjacent = getNode(i, j - 1);
            if (adjacent != null) {
                adjacentWeight = adjacent.getWeight();
            }
            if (adjacent != null && adjacent.getIsNotBlocked() && !nodesNotVisited.contains(adjacent)
                    /*&& !metric.equals("Euclidean")*/) {
                adjacent.setG(node, horizontalVerticalCost * adjacentWeight);
                adjacentNodes.add(adjacent);
            }

        }

        // Check right node
        if (j < N) {
            adjacent = getNode(i, j + 1);
            if (adjacent != null) {
                adjacentWeight = adjacent.getWeight();
            }
            if (adjacent != null && adjacent.getIsNotBlocked() && !nodesNotVisited.contains(adjacent)
                    /*&& !metric.equals("Euclidean")*/) {
                adjacent.setG(node, horizontalVerticalCost * adjacentWeight);
                adjacentNodes.add(adjacent);
            }
        }
        return adjacentNodes;
    }

    //If the i and j parameters are within the map boundaries, return the node
    //in the specific coordinates, null otherwise
    public Node getNode(int i, int j) {
        if (i >= 0 && i < N && j >= 0 && j < N) {
            return nodes[i][j];
        } else {
            return null;
        }
    }
}
