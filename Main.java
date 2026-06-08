package BstPbRB;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RedBlackTree tree = new RedBlackTree();

        menu(sc, tree);
    }

    private static void menu(Scanner sc, RedBlackTree tree) {

        System.out.println("\n========== ÁRVORE RED-BLACK ==========");
        System.out.println("1 - Inserir valor");
        System.out.println("2 - Buscar valor");
        System.out.println("3 - Mostrar Pré-Ordem");
        System.out.println("4 - Mostrar Raiz");
        System.out.println("5 - Remover valor");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma opção: ");

        int opcao = sc.nextInt();

        switch (opcao) {

            case 1:

                System.out.print("Digite o valor: ");
                int valor = sc.nextInt();

                tree.insert(valor);

                System.out.println("Valor inserido!");
                break;

            case 2:

                System.out.print("Digite o valor a buscar: ");
                valor = sc.nextInt();

                if (tree.search(valor)) {
                    System.out.println("Valor encontrado!");
                } else {
                    System.out.println("Valor não encontrado!");
                }

                break;

            case 3:

                System.out.println("\nPré-Ordem:");

                tree.preOrder();

                break;

            case 4:

                Node root = tree.getRoot();

                if (root == null) {

                    System.out.println("Árvore vazia.");

                } else {

                    System.out.print("Raiz: ");

                    if (root.color == Node.RED) {

                        System.out.println(
                                "\u001B[31m" +
                                        root.data +
                                        "(R)\u001B[0m");

                    } else {

                        System.out.println(
                                "\u001B[37m" +
                                        root.data +
                                        "(B)\u001B[0m");
                    }
                }

                break;

            case 5:

                System.out.print("Digite o valor a remover: ");
                valor = sc.nextInt();

                tree.delete(valor);

                System.out.println("Operação concluída!");

                break;

            case 6:

                System.out.println("Programa encerrado.");
                sc.close();
                return;

            default:

                System.out.println("Opção inválida.");
        }

        menu(sc, tree);
    }
}