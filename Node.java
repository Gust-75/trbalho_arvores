package BstPbRB;
public class Node {

    public static final boolean RED = true;
    public static final boolean BLACK = false;

    int data;
    Node left;
    Node right;
    Node parent;
    boolean color;

    public Node(int data) {
        this.data = data;
        this.color = RED;
    }
}