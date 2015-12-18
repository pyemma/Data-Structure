import tree.SuffixTree;
import java.util.*;

public class SuffixTreeTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            String str = scan.next();
            SuffixTree st = new SuffixTree(str);
            st.build(true);
            st.print();
        }
    }
}
