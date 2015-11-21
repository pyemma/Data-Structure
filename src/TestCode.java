import tree.AVLTree;
import java.util.*;

public class TestCode {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        HashSet<Integer> set = new HashSet<Integer>();
        int size = 15;
        Random rand = new Random();

        for (int i = 0; i < size; ++i) {
            int op = rand.nextInt(2);
            int key = rand.nextInt(size);
            if (op == 0) {
                boolean result1 = tree.search(key);
                boolean result2 = set.contains(key);
                System.out.println("Search " + key + " tree: " + result1 + " set: " + result2);
            } else {
                System.out.println("Insert " + key);
                if (!tree.search(key))
                    tree.insert(key);
                set.add(key);
            }
        }

        tree.printTree();
    } 
}