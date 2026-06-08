package BstPbRB;

public class RedBlackTree {

    private Node root;

    public Node getRoot() {
        return root;
    }

    private void rotateLeft(Node x) {

        Node y = x.right;

        x.right = y.left;

        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node y) {

        Node x = y.left;

        y.left = x.right;

        if (x.right != null) {
            x.right.parent = y;
        }

        x.parent = y.parent;

        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }

        x.right = y;
        y.parent = x;
    }

    public void insert(int value) {

        Node node = new Node(value);

        Node parent = null;
        Node current = root;

        while (current != null) {

            parent = current;

            if (value < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        node.parent = parent;

        if (parent == null) {
            root = node;
        } else if (value < parent.data) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        fixInsert(node);
    }

    private void fixInsert(Node node) {

        while (node != root &&
                node.parent != null &&
                node.parent.color == Node.RED) {

            Node parent = node.parent;
            Node grandParent = parent.parent;

            if (parent == grandParent.left) {

                Node uncle = grandParent.right;

                if (uncle != null &&
                        uncle.color == Node.RED) {

                    parent.color = Node.BLACK;
                    uncle.color = Node.BLACK;
                    grandParent.color = Node.RED;

                    node = grandParent;

                } else {

                    if (node == parent.right) {
                        node = parent;
                        rotateLeft(node);
                    }

                    parent.color = Node.BLACK;
                    grandParent.color = Node.RED;

                    rotateRight(grandParent);
                }

            } else {

                Node uncle = grandParent.left;

                if (uncle != null &&
                        uncle.color == Node.RED) {

                    parent.color = Node.BLACK;
                    uncle.color = Node.BLACK;
                    grandParent.color = Node.RED;

                    node = grandParent;

                } else {

                    if (node == parent.left) {
                        node = parent;
                        rotateRight(node);
                    }

                    parent.color = Node.BLACK;
                    grandParent.color = Node.RED;

                    rotateLeft(grandParent);
                }
            }
        }

        root.color = Node.BLACK;
    }

    public boolean search(int value) {

        Node current = root;

        while (current != null) {

            if (value == current.data) {
                return true;
            }

            if (value < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return false;
    }

    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node node) {

        if (node != null) {

            if (node.color == Node.RED) {
                System.out.print("\u001B[31m" + node.data + "(R)\u001B[0m ");
            } else {
                System.out.print("\u001B[37m" + node.data + "(B)\u001B[0m ");
            }

            preOrder(node.left);
            preOrder(node.right);
        }
    }
}