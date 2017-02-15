/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

/**
 *
 * @author John
 */
public class Node {

    private int num;
    private Node left, right;

    public Node(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public Node getLeft() {
        try {
            return left;
        } catch (Exception ex) {
            return null;
        }
    }

    public Node getRight() {
        try {
            return right;
        } catch (Exception ex) {
            return null;
        }
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

}