package BstPbRB;

public class Main {

    public static void main(String[] args) {

        RedBlackTree tree = new RedBlackTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(5);
        tree.insert(25);

        System.out.println("Percurso pré-ordem:");
        tree.preOrder();

        System.out.println("Busca 15: " +
                tree.search(15));

        System.out.println("Busca 99: " +
                tree.search(99));
    }
}