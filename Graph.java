import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Graph {
    int[][] matrix;
    ArrayList<Node> nodes;

    Graph (int size){
        nodes = new ArrayList<>();
        matrix = new int[size][size];
    }


    public void addNodes (Node[] nodes){
        for (Node node : nodes){
            addNode(node);
        }
    }
    public void addNode (Node node){
        nodes.add(node);
    }

    public void generateFriendMatrix(){
        int size = nodes.size();
        this.matrix = new int[size][size];
        // O(n^2) complexity
        for (int i = 0; i < nodes.size(); i++){
            Node currentNode = nodes.get(i);
            for (int j = 0; j < nodes.size(); j++){
                Node otherNode = nodes.get(j);
                // if otherNode is a friend of a current node, add an edge
                if (currentNode.checkIfFriend(otherNode)){  //checkIfFriend is O(1) since HashSet is used
                    this.matrix[i][j] = 1;
                }
            }
        }
    }

    public void printFriendMatrix(){
        for (int i = 0; i < nodes.size(); i ++){
            System.out.print((nodes.get(i).username).charAt(0) + " ");
        }
        System.out.println("");

        for (int i =0; i < nodes.size(); i++){
            for (int j = 0; j < nodes.size(); j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private int getIndex(Node node){
        int index;
        for (index = 0; index < nodes.size(); index++){
            if (node == nodes.get(index)) return index;
        }
        return -1;
    }

    public boolean checkEdge(Node node1, Node node2){
        int index1 = getIndex(node1); // O(n)
        int index2 = getIndex(node2); // O(n)
        if (this.matrix[index1][index2] == 1) return true; // O(c)
        else return false; // Total time complexity: O(n)
    }

    // Returns a of friends of friends that user is not friends with
    public Set<Node> suggestFriends(Node node){
        Set<Node> suggestedFriends = new HashSet<>();
        // Total time complexity: O(n^2), two nested for loops with O(1) complexity in the inner loop
        for (Node friend : node.friends){
            for (Node friendsFriend : friend.friends){
                // O(1) + O(1) + O(1)
                if (friendsFriend != node & !suggestedFriends.contains(friendsFriend) && node.checkIfFriend(friendsFriend) == false){
                    suggestedFriends.add(friendsFriend);
                }
            }
        }
        System.out.println("Suggested friends for " + node.username + ":");
        printSuggestedFriends(suggestedFriends);
        return suggestedFriends;
    }

    public void printSuggestedFriends (Set<Node> suggestedFriends){
        for (Node node : suggestedFriends) System.out.print(node.username + " ");
        System.out.println("");
    }

}

class Node{
    String username;
    Set<Node> friends;

    Node (String name){
        username = name;
        friends = new HashSet<>();
    }

    void addFriends(Node[] friends){
        this.friends.addAll(Arrays.asList(friends)); // O(n) bc friends is an arraylist
    }

    boolean checkIfFriend(Node node) {
        if (this.friends.contains(node)) return true; // Hashset used for friends, so O(1) average for contains op
        return false;
    }

    void printFriends() {
        for (Node friend : friends) System.out.print(friend.username + " ");
        System.out.println("");
    }


    public static void main(String[] args) {
        Node A = new Node("ryat");
        Node B = new Node("laura");
        Node C = new Node("taufiq");
        Node D = new Node("bejoy");
        Node E = new Node("taqrim");
        Node F = new Node("abrar");

        A.addFriends(new Node[]{B, C, D, E});
        B.addFriends(new Node[] {A, C, D, E});
        C.addFriends(new Node[] {A, B, E});
        D.addFriends(new Node[] {A,B});
        E.addFriends(new Node[]{A, B, C,F});
        F.addFriends(new Node[] {E});

        Graph graph = new Graph(6);
        graph.addNodes(new Node[] {A,B,C,D,E,F});
        graph.generateFriendMatrix();
        graph.printFriendMatrix();
        graph.suggestFriends(E);
    }
}
