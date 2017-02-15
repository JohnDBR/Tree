/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author John
 */
public class Tree {

    static Scanner read = new Scanner(System.in);
    static Node root;
    static int sons = -1, height = 0;
    static LinkedList<Integer> levels = new LinkedList<>();

    static public void createTree() {
        clean();
        int op = 1, num;
        do {
            System.out.println("Digite info del nodo: ");
            num = read.nextInt();
            add(num);
            System.out.println("Desea ingresar mas nodos 1-Si, 0-No");
            op = read.nextInt();
        } while (op == 1);
    }

    static public void add(int num) {
        if (!exist(num)) {
            Node q = new Node(num);
            if (root == null) {
                root = q;
            } else {
                Node antp = null;
                Node p = root;
                while (p != null) {
                    antp = p;
                    if (num < p.getNum()) {
                        p = p.getLeft();
                    } else {
                        p = p.getRight();
                    }
                }
                if (num < antp.getNum()) {
                    antp.setLeft(q);
                } else {
                    antp.setRight(q);
                }
            }
            sons++;
        }
    }

    static public boolean exist(int num) {
        if (findNode(num) != null) {
            return true;
        }
        return false;
    }

    static public void showTree() {
        LinkedList<Integer> travel = new LinkedList<>();
        System.out.println("Mostrar Arbol por 1.pre-orden - 2.in-orden 3.post-orden - 4.nivel-orden - 5.intento grafico");
        System.out.println("OPCION: ");
        int op = read.nextInt();
        System.out.println("");
        switch (op) {
            case 1:
                preOrder(root, travel);
                break;
            case 2:
                inOrder(root, travel);
                break;
            case 3:
                postOrder(root, travel);
                break;
            case 4:
                levelOrder(root, travel);
                break;
            case 5:
                //int num = read.nextInt(); //fix, cousins from uncles...
                //Node p = findNode(num);
                graphicTree(root);
                break;
            default:
                break;
        }
        for (Integer num : travel) {
            System.out.print(num + "-");
        }
    }

    static public void graphicTree(Node p) { //fix, missing Grandchildren from a missing son... I really need to fix this?...
        if (root != null) {                  //fix, add spaces for a better view...
            height(root, 0);
            int hght = Tree.height;
            int level = level(p.getNum()) + 1;
            //System.out.println(level + "<" + height);
            System.out.println(p.getNum() + "-");
            while (level <= hght) {
                clean();
                levelNodes(root, level);
                for (Integer node : levels) {
                    System.out.print(node + "-");
                }
                System.out.println("");
                level++;
            }
        }
    }

    static public void levelNodes(Node p, int level) {
        if (p != null) {
            int num = p.getNum();
            int lvl = level(num);
            if (lvl == level && !levels.contains(num)) {
                levels.add(num);
            }
            if (p.getLeft() == null && lvl + 1 == level) {
                levels.add(-1);
            }
            levelNodes(p.getLeft(), level);
            if (p.getRight() == null && lvl + 1 == level) {
                levels.add(-1);
            }
            levelNodes(p.getRight(), level);
            //levelNodes(p.getLeft(), level);
            //levelNodes(p.getRight(), level);
        }
    }

    static public void preOrder(Node p, LinkedList travel) {
        if (p != null) {
            travel.add(p.getNum());
            preOrder(p.getLeft(), travel);
            preOrder(p.getRight(), travel);
        }
    }

    static public void inOrder(Node p, LinkedList travel) {
        if (p != null) {
            inOrder(p.getLeft(), travel);
            travel.add(p.getRight());
            inOrder(p.getRight(), travel);
        }
    }

    static public void postOrder(Node p, LinkedList travel) {
        if (p != null) {
            postOrder(p.getLeft(), travel);
            postOrder(p.getRight(), travel);
            travel.add(p.getNum());
        }
    }

    static public void levelOrder(Node root, LinkedList travel) {
        if (root != null) {
            LinkedList<Node> queue = new LinkedList<>();
            queue.addLast(root);
            while (queue.size() > 0) {
                Node p = queue.pollFirst();
                travel.add(p.getNum());
                if (p.getLeft() != null) {
                    queue.addLast(p.getLeft());
                }
                if (p.getRight() != null) {
                    queue.addLast(p.getRight());
                }
            }
        }
    }

    static public Node findNode(int num) {
        Node p = root;
        while (p != null) {
            if (num == p.getNum()) {
                return p;
            } else if (num < p.getNum()) {
                p = p.getLeft();
            } else {
                p = p.getRight();
            }
        }
        return null;
    }

    static public boolean leaf(Node p) {
        if (p != null) {
            if (p.getLeft() != null || p.getRight() != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    static public void offspring(Node p) {
        if (p != null) {
            sons++;
            offspring(p.getLeft());
            offspring(p.getRight());
        }
    }

    static public int nodes(Node p) {
        clean();
        offspring(p);
        return sons + 1;
    }

    static public void height(Node p, int num) {
        if (p != null) {
            //if (num > height) { //Doesnt matter...
            //    height = num;
            //}
            height(p.getLeft(), num + 1);
            if (num > height) {
                height = num;
            }
            height(p.getRight(), num + 1);
        }
    }

    static public int level(int num) {
        int level = 0;
        Node p = root;
        while (p != null) {
            if (num == p.getNum()) {
                return level;
            } else if (num < p.getNum()) {
                p = p.getLeft();
            } else {
                p = p.getRight();
            }
            level++;
        }
        return -1;
    }

    static public void deleteNode(int num) {
        System.out.println("");
        graphicTree(root);
        System.out.println("");
        Node p = root, antp = null;
        int op = 1;
        while (p != null && op == 1) {
            if (num == p.getNum()) {
                if (!leaf(p)) {
                    System.out.println("El nodo a eliminar tiene Hijos, desea eliminarlo? 1.Si, 2.No");
                    System.out.println("OPCION:");
                    op = read.nextInt();
                }
                if (op == 1) {
                    op = 2;
                    if (antp != null) {
                        if (antp.getRight() != null) {
                            if (antp.getRight().getNum() == num) {
                                antp.setRight(null);
                            } else {
                                antp.setLeft(null);
                            }
                        } else {
                            antp.setLeft(null);
                        }
                    } else {
                        root = null;
                    }
                    System.out.println("Done!");
                }
            } else if (num < p.getNum()) {
                antp = p;
                p = p.getLeft();
            } else {
                antp = p;
                p = p.getRight();
            }
        }
        System.out.println("");
        graphicTree(root);
    }

    static public int uncle(int num) {
        int sw = 1;
        Node p = root, antp = null, grandpa = null;
        while (p != null && sw == 1) {
            if (num == p.getNum()) {
                if (grandpa != null) {
                    if (grandpa.getRight() != null) {
                        if (grandpa.getRight().getNum() == antp.getNum()) {
                            if (grandpa.getLeft() != null) {
                                return grandpa.getLeft().getNum();
                            }
                        } else {
                            return grandpa.getRight().getNum();
                        }
                    }
                }
                sw = 0;
            } else if (num < p.getNum()) {
                grandpa = antp;
                antp = p;
                p = p.getLeft();
            } else {
                grandpa = antp;
                antp = p;
                p = p.getRight();
            }
        }
        return -1;
    }

    static public void clean() {
        sons = -1;
        height = 0;
        levels = new LinkedList<>();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int op, num;
        do {
            System.out.println(
                    "NOTA: el valor -1 en mi laboratorio es nulo, vacio\n"
                    + "porfavor usar arboles de numeros positivos\n\n"
                    + "Opciones:\n"
                    + " 1. Crear Arbol\n"
                    + " 2. Mostrar Arbol\n"
                    + " 3. Informacion detallada del nodo (punto 2 y punto 5)\n"
                    + " 4. Eliminar nodo\n"
                    + " 5. Agregar nodo\n"
                    + " 0. Salir"
            );
            System.out.println("OPCION: ");
            op = read.nextInt();
            System.out.println("");
            switch (op) {
                case 1:
                    root = null;
                    createTree();
                    break;
                case 2:
                    showTree();
                    break;
                case 3:
                    clean();
                    System.out.println("Nodo:");
                    num = read.nextInt();
                    System.out.println("");
                    graphicTree(root);
                    System.out.println("");
                    Node p = findNode(num);
                    height(p, 0);
                    offspring(p);
                    System.out.println(leaf(p) + "- hoja");
                    System.out.println(height + "- altura");
                    System.out.println(sons + "- descendencia");
                    System.out.println(nodes(p) + "- nodos del arbol/sub-arbol");
                    System.out.println(level(num) + "- nivel");
                    System.out.println(uncle(num) + "- tio");
                    break;
                case 4:
                    System.out.println("Nodo:");
                    num = read.nextInt();
                    deleteNode(num);
                    break;
                case 5:
                    createTree();
                    break;
                case 6:

                    break;
                default:
                    op = 0;
                    break;
            }
            System.out.println("\n");
            //System.out.println("\n\n");
        } while (op != 0);
    }

}
