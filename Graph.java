import java.util.ArrayList;
import java.util.Arrays;


public class Graph {
    int[][] matrix;
    ArrayList<Node> nodes; // Reimplement this with hashmap to get O(c) for checkFriends()

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
        // O(n^3) complexity
        for (int i = 0; i < nodes.size(); i++){
            Node currentNode = nodes.get(i);
            for (int j = 0; j < nodes.size(); j++){
                Node otherNode = nodes.get(j);
                // If hashmap: if (currentNode.friends.get(otherNode) != null) add the edge
                // This means otherNode exists in the friends hashmap of currentNode
                if (currentNode.checkIfFriend(otherNode)){  //checkIfFriend is O(n) if hashmap is used, the get(k) of hashmap is O(c)
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

    // Returns an array of friends of friends that user is not friends with
    public ArrayList suggestFriends(Node node){
        ArrayList suggestedFriends = new ArrayList<>();

        // O(n^3)
        // Adding friends of friends, if they are not the same person needing suggestions, or if not already suggested
        for (int i = 0; i < node.friends.size(); i++){
            Node currentFriend = (Node) node.friends.get(i);

            for (int j = 0; j < currentFriend.friends.size(); j++){
                Node friendsFriend = (Node) currentFriend.friends.get(j);
                // If: O(c) + O(n) + O(n) = O(n)
                if (friendsFriend != node && !suggestedFriends.contains(friendsFriend) && checkEdge(node, friendsFriend) == false) {
                    suggestedFriends.add(friendsFriend);
                }
            }

        }
        System.out.println("Suggested friends for " + node.username + ":");
        printSuggestedFriends(suggestedFriends);
        return suggestedFriends;
    }

    public void printSuggestedFriends (ArrayList suggestedFriends){
        for (int i = 0; i < suggestedFriends.size(); i++){
            Node currentSuggestedFriend = (Node) suggestedFriends.get(i);
            System.out.print(currentSuggestedFriend.username + " ");
        }
    }

}

class Node{
    String username;
    ArrayList friends;

    Node (String name){
        username = name;
        friends = new ArrayList<Node>();
    }

    void addFriends(Node[] friends){
        this.friends.addAll(Arrays.asList(friends)); // O(n) bc friends is an arraylist
    }

    boolean checkIfFriend(Node node){
        for (int i = 0; i < this.friends.size(); i++){
            Node currentFriend = (Node) this.friends.get(i);
            if (node == currentFriend) return true;
        }
        return false;
    }

    void printFriends(){
        for (int i = 0; i < friends.size(); i++){
            Node currentFriend = (Node) friends.get(i);
            System.out.print(currentFriend.username + " ");
        }
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
