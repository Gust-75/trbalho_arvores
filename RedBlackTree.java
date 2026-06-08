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
            } else if (value > current.data){
                current = current.right;
            }else{
                System.out.println("valor já existente!!!");
                return;
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
        preOrder();
    }

    private void fixInsert(Node node) {
        while (node != root && node.parent != null && node.parent.color == Node.RED) {
            Node parent = node.parent;
            Node grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;

                if (uncle != null && uncle.color == Node.RED) {
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

                if (uncle != null && uncle.color == Node.RED) {
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

    // --- MÉTODOS DE DELEÇÃO ADICIONADOS ---

    // Método auxiliar para obter a cor de um nó de forma segura (nós nulos são BLACK)
    private boolean getColor(Node node) {
        return node == null ? Node.BLACK : node.color;
    }

    // Método auxiliar para buscar o nó que contém o valor (necessário para a deleção)
    private Node searchNode(int value) {
        Node current = root;
        while (current != null) {
            if (value == current.data) return current;
            current = (value < current.data) ? current.left : current.right;
        }
        return null;
    }

    // Método auxiliar para encontrar o valor mínimo de uma subárvore
    private Node minimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Método auxiliar para substituir uma subárvore por outra
    private void rbTransplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

    public void delete(int value) {
        Node z = searchNode(value);
        if (z == null) {
            System.out.println("Valor " + value + " não encontrado para deleção.");
            return;
        }

        Node x;
        Node y = z;
        boolean yOriginalColor = y.color;

        if (z.left == null) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == null) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent == z) {
                if (x != null) x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                if (y.right != null) y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        // Se a cor removida foi preta, precisamos corrigir as violações
        if (yOriginalColor == Node.BLACK) {
            fixDelete(x, z.parent); // Passamos o pai de z para o caso de x ser null
        }
    }

    private void fixDelete(Node x, Node xParent) {
        // Enquanto o nó não for a raiz e sua cor for preta (ou nulo, que conta como preto)
        while (x != root && getColor(x) == Node.BLACK) {

            // Define quem é o pai atual de x de forma segura
            Node currentParent = (x != null) ? x.parent : xParent;
            if (currentParent == null) break;

            if (x == currentParent.left) {
                Node sibling = currentParent.right;

                // Caso 1: O irmão é Vermelho
                if (getColor(sibling) == Node.RED) {
                    sibling.color = Node.BLACK;
                    currentParent.color = Node.RED;
                    rotateLeft(currentParent);
                    sibling = currentParent.right;
                }

                // Caso 2: O irmão é Preto e ambos os filhos do irmão são Pretos
                if (sibling == null || (getColor(sibling.left) == Node.BLACK && getColor(sibling.right) == Node.BLACK)) {
                    if (sibling != null) sibling.color = Node.RED;
                    x = currentParent;
                    xParent = x.parent;
                } else {
                    // Caso 3: O irmão é Preto, o filho esquerdo do irmão é Vermelho e o direito é Preto
                    if (getColor(sibling.right) == Node.BLACK) {
                        if (sibling.left != null) sibling.left.color = Node.BLACK;
                        sibling.color = Node.RED;
                        rotateRight(sibling);
                        sibling = currentParent.right;
                    }

                    // Caso 4: O irmão é Preto e o filho direito do irmão é Vermelho
                    if (sibling != null) {
                        sibling.color = currentParent.color;
                        if (sibling.right != null) sibling.right.color = Node.BLACK;
                    }
                    currentParent.color = Node.BLACK;
                    rotateLeft(currentParent);
                    x = root; // Termina o loop
                }
            } else { // Caso simétrico (x é o filho direito)
                Node sibling = currentParent.left;

                // Caso 1
                if (getColor(sibling) == Node.RED) {
                    sibling.color = Node.BLACK;
                    currentParent.color = Node.RED;
                    rotateRight(currentParent);
                    sibling = currentParent.left;
                }

                // Caso 2
                if (sibling == null || (getColor(sibling.left) == Node.BLACK && getColor(sibling.right) == Node.BLACK)) {
                    if (sibling != null) sibling.color = Node.RED;
                    x = currentParent;
                    xParent = x.parent;
                } else {
                    // Caso 3
                    if (getColor(sibling.left) == Node.BLACK) {
                        if (sibling.right != null) sibling.right.color = Node.BLACK;
                        sibling.color = Node.RED;
                        rotateLeft(sibling);
                        sibling = currentParent.left;
                    }

                    // Caso 4
                    if (sibling != null) {
                        sibling.color = currentParent.color;
                        if (sibling.left != null) sibling.left.color = Node.BLACK;
                    }
                    currentParent.color = Node.BLACK;
                    rotateRight(currentParent);
                    x = root;
                }
            }
        }
        if (x != null) x.color = Node.BLACK;
    }

    // --- FIM DOS MÉTODOS DE DELEÇÃO ---

    public boolean search(int value) {
        Node current = root;
        while (current != null) {
            if (value == current.data) return true;
            current = (value < current.data) ? current.left : current.right;
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