import java.util.*;

class Node {
    String username;

    Node (String name){
        this.username = name;
    }
}


public class Graph {

   int [][] distanceMatrix;
   Set<Node> nodes = new HashSet<>();
   Map<Node, Integer> nodeIndices = new HashMap<>();

   // O(n)
   public void addNodes(Node [] nodes){
       this.nodes.addAll(Arrays.asList(nodes)); // O(n)
       // Storing nodes and indices
       int index = 0;
       for (Node node: this.nodes){
           nodeIndices.put(node, index);    // O(n)
           index++;
       }

       generateMatrix();    // Initializing matrix takes O(n^2) time
   }

   // O(1)
   public void addEdges(Node node1, Node node2, int distance){
       int index1 = nodeIndices.get(node1);
       int index2 = nodeIndices.get(node2);
       distanceMatrix[index1][index2] = distance;
       distanceMatrix[index2][index1] = distance;   // Undirected graph, also adding edge for other node
   }

    // O(n^2)
    private void generateMatrix() {
        int size = nodes.size();
        distanceMatrix = new int[size][size];
        for (int[] row : distanceMatrix) Arrays.fill(row, 0); // Init each row with 0 (no edge)
    }

    // O(n^2)
    private void printMatrix(){
       for (Node node : nodes) System.out.print(node.username.charAt(0) + " ");
       System.out.println("");

       for (int i = 0; i < nodes.size(); i++){
           for (int j = 0; j < nodes.size(); j++){
               System.out.print(distanceMatrix[i][j] + " ");
           }
           System.out.println("");
       }
    }

    public ArrayList<Node> suggestFriends(Node rootNode){
       ArrayList<Node> suggestedFriends = new ArrayList<>();
       ArrayList<Node> currentFriends = new ArrayList<>();
       ArrayList<Node> prevFriends = new ArrayList<>(); // To store the friend of the suggested friend
       int rootNodeIndex = nodeIndices.get(rootNode);

       // Search through nodes to find which nodes share an edge with given rootNode O(n)
       for (Node node : nodes){
           int nodeIndex = nodeIndices.get(node);
           if (distanceMatrix[rootNodeIndex][nodeIndex] != 0) {
               currentFriends.add(node);
           }
       }

       // Search through currentFriends to find nodes that it has edges with but no edges with the rootNode
        for (Node friend : currentFriends){
            int friendIndex = nodeIndices.get(friend);

            for (Node node: nodes){
                if (node == rootNode) break;
                int nodeIndex = nodeIndices.get(node);
                if (distanceMatrix[friendIndex][nodeIndex] != 0 && distanceMatrix[rootNodeIndex][nodeIndex] == 0){
                    suggestedFriends.add(node);
                    prevFriends.add(friend);
                }
            }
        }

        // Printing suggested friends
        System.out.println("Suggested friends for " + rootNode.username + ":");
        for (int i = 0; i < suggestedFriends.size(); i ++){
            System.out.println(suggestedFriends.get(i).username + " b/c " + rootNode.username + " is friends with " + prevFriends.get(i).username);
        }

        return suggestedFriends;
    }

    // Dijkstra's algorithm O(n^2)
    public void shortestPath(Node rootNode){
       boolean[] visitedArr = new boolean[nodes.size()];
       int[] distanceArr = new int[nodes.size()];
       Node [] prevNode = new Node[nodes.size()];
       Node[] nodeArr = nodes.toArray(new Node[0]);

       // Init all distances to be infinity, except the first/root node
        for (int i = 0; i < nodes.size(); i++) distanceArr[i] = Integer.MAX_VALUE;
        int rootIndex = nodeIndices.get(rootNode);
        distanceArr[rootIndex] = 0;

        for (int i = 0; i < nodes.size(); i++){
            int minDistance = Integer.MAX_VALUE;
            int minIndex = -1;

            // Find node with the smallest distance that has not been visited
            for (int j = 0; j < nodes.size(); j++){
                if (distanceArr[j] < minDistance && !visitedArr[j]){
                    minDistance = distanceArr[j];
                    minIndex = j;
                }
            }

            visitedArr[minIndex] = true; // Marking as visited
            if (minIndex == -1) break; // No edges found, no other nodes to visit

            // Find distance to neighbouring nodes from prev node with the smallest distance
            // and update the shortest distance to that node
            for (int j = 0; j < nodes.size(); j++){
                if (distanceMatrix[minIndex][j] != 0 && !visitedArr[j]){
                    int newDistance = minDistance + distanceMatrix[minIndex][j];
                    if (newDistance < distanceArr[j]) {
                        distanceArr[j] = newDistance;
                        prevNode[j] = nodeArr[minIndex];
                    }
                }
            }
        }

        // Print out shortest node:
        for (int i =0; i < nodes.size(); i ++){
            String otherUsername = nodeArr[i].username;
            int distance = distanceArr[i];
            String prevNodeUsername = rootNode.username;
            if (prevNode[i] != null) prevNodeUsername = prevNode[i].username;
            System.out.println(rootNode.username + " to " + otherUsername + " " + distance + ". Previous vertex: " + prevNodeUsername);
        }
    }

    public static void main(String[] args) {
        Node A = new Node("Alpha");
        Node B = new Node("Bravo");
        Node C = new Node("Charlie");
        Node D = new Node("Delta");

        Graph graph = new Graph();
        graph.addNodes(new Node[]{A,B,C,D});
        graph.addEdges(A, B, 1);
        graph.addEdges(A, C, 2);
        graph.addEdges(B, D, 4);
        graph.addEdges(C, D, 2);

        graph.printMatrix();
        graph.shortestPath(A);
        graph.suggestFriends(A);

    }
}
