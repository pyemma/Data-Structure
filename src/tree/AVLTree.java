package tree;

import java.util.*;

public class AVLTree<T extends Comparable> {

    private class TreeNode<T extends Comparable> {
        T key;
        TreeNode<T> left, right, parent;
        int height;

        public TreeNode(T key) {
            this.key = key;
            left = right = parent = null;
            height = 0;
        }
    }

    private TreeNode<T> root;

    public AVLTree() {
        this.root = null;
    }

    public boolean search(T key) {
        return search(key, root);
    }

    private boolean search(T key, TreeNode<T> root) {
        if (root == null)
            return false;
        else {
            if (key.compareTo(root.key) < 0)
                return search(key, root.left);
            else if (key.compareTo(root.key) > 0)
                return search(key, root.right);
            else 
                return true;
        }
    }

    public void insert(T key) {
        root = insert(key, root);
    }

    private TreeNode<T> insert(T key, TreeNode<T> root) {
        if (root == null) {
            return new TreeNode<T>(key);
        } else {
            if (key.compareTo(root.key) < 0) {
                root.left = insert(key, root.left);
                root.left.parent = root;
            }
            else {
                root.right = insert(key, root.right);
                root.right.parent = root;
            }

            updateHeight(root);
            // when unbalance happen
            if (getHeight(root.left) - getHeight(root.right) > 1) {
                if (heavy(root.left) > 0) {
                    root.left = leftRotate(root.left);
                }
                return rightRotate(root);
            } else if (getHeight(root.right) - getHeight(root.left) > 1) {
                if (heavy(root.right) < 0) {
                    root.right = rightRotate(root.right);
                }
                return leftRotate(root);
            }
            return root;
        }
    }

    /**
        Return -1 if left heavy, return 0 if equal, return 1 if right heavy
    */
    private int heavy(TreeNode<T> node) {
        if (node == null) 
            return 0;
        else {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            if (leftHeight > rightHeight)
                return -1;
            else if (leftHeight == rightHeight)
                return 0;
            else 
                return 1;
        }
    }

    private int getHeight(TreeNode<T> node) {
        if (node == null)
            return -1;
        else 
            return node.height;
    }

    private void updateHeight(TreeNode<T> node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private TreeNode<T> rightRotate(TreeNode<T> node) {
        assert(node != null);
        System.out.println("Right rotate: " + node.key);

        TreeNode<T> left = node.left;
        assert(left != null);

        TreeNode<T> temp = left.right;
        left.right = node;
        node.left = temp;
        left.parent = node.parent;
        node.parent = left;
        if (temp != null)
            temp.parent = node;

        updateHeight(node);
        updateHeight(left);

        return left;
    }

    private TreeNode<T> leftRotate(TreeNode<T> node) {
        assert(node != null);
        System.out.println("Left rotate: " + node.key);

        TreeNode<T> right = node.right;
        assert(right != null);

        TreeNode<T> temp = right.left;
        right.left = node;
        node.right = temp;
        right.parent = node.parent;
        node.parent = right;
        if (temp != null)
            temp.parent = node;

        updateHeight(node);
        updateHeight(right);

        return right;
    }

    public void printTree() {
        printTree(root, "");
    }

    private void printTree(TreeNode<T> root, String perfix) {
        if (root == null)
            return;
        System.out.println(perfix + root.key);
        if (perfix.length() > 0)
            perfix = perfix.substring(0, perfix.length() - 1) + " |-";
        else
            perfix = "|-";
        printTree(root.left, perfix);
        printTree(root.right, perfix);
    }
}