package tree;

import java.util.*;

public class SuffixTree {

    // the edge information is stored internally in each node,
    // the string is represented via start and end index,
    // use hashmap to hold all out-going nodes
    public class Node {
        int start, end;
        HashMap<Character, Node> nodes;
        Node suffixLink;

        public Node(int start, int end) {
            this.start = start;
            this.end = end;
            nodes = new HashMap<Character, Node>();
            suffixLink = null;
        }

        public String toString() {
            return "" + "[" + start + "," + end + "]" + " SuffixLink: " + suffixLink;
        }
    }

    Node root, activeNode, lastNode;
    int activeEdge, activeLength, remainder, index;
    String str;

    public SuffixTree(String str) {
        root = new Node(-1, -1);
        activeNode = root;
        activeEdge = -1;
        activeLength = remainder = index = 0;
        // append a special character at the end to easy the handle of
        // the end of the string
        this.str = str + "$";
    }

    public void build(boolean verbose) {
        for (int i = 0; i < str.length(); ++i) {
            ++index;
            // this.print();
            ++remainder;
            lastNode = null;
            char currentChar = str.charAt(i);
            if (verbose)
                System.out.println("Active Node: (" + activeNode.start + ", " +
                    activeNode.end + ") " + "Active Edge: " + activeEdge +
                    ", Active Length: " + activeLength + " " + currentChar +
                    " Remainder: " + remainder);
            while (remainder > 0) {
                if (verbose)
                    System.out.println("Active Node: (" + activeNode.start + ", " +
                        activeNode.end + ") " + "Active Edge: " + activeEdge +
                        ", Active Length: " + activeLength + " Remainder: " + remainder);
                if (activeLength == 0)
                    activeEdge = i;
                char suffixBegin = str.charAt(activeEdge);
                if (activeNode.nodes.containsKey(suffixBegin) == false) {
                    // this only appear when activeLength = 0 since currentChar
                    // only record the begin of suffix string
                    activeNode.nodes.put(suffixBegin, new Node(i, -1));
                    // if there is other new internal node, create suffix link
                    if (lastNode != null)
                        lastNode.suffixLink = activeNode;
                    --remainder;
                } else {
                    Node edgeNode = activeNode.nodes.get(suffixBegin);
                    int start = edgeNode.start;
                    int end = edgeNode.end;
                    int edgeLength = (end == -1) ? (i - start + 1) : (end - start + 1);
                    // walk down necessarily
                    if (activeLength >= edgeLength) {
                        if (verbose)
                            System.out.println("Move down");
                        activeNode = edgeNode;
                        activeLength = activeLength - edgeLength;
                        activeEdge = start + edgeLength;
                        continue;
                    } else {
                        if (str.charAt(start + activeLength) == currentChar) {
                            // appear on the edge, do noting
                            ++activeLength;
                            break;
                        } else {
                            // need to split an edge
                            Node internalNode = new Node(start, start + activeLength - 1);
                            edgeNode.start = start + activeLength;
                            internalNode.nodes.put(str.charAt(start + activeLength), edgeNode);
                            internalNode.nodes.put(currentChar, new Node(i, -1));
                            activeNode.nodes.put(suffixBegin, internalNode);

                            if (lastNode != null) {
                                lastNode.suffixLink = internalNode;
                            }
                            lastNode = internalNode;

                            if (activeNode == root) {
                                // if the active node is root, shift active edge to right by 1
                                // decrease active length and remainder
                                --activeLength;
                                ++activeEdge;
                                --remainder;
                                continue;
                            } else {
                                // if the node has suffix link, then follow the suffix link,
                                // the destination would either be another internal node, which share the same suffix
                                // or the root node
                                if (activeNode.suffixLink != null) {
                                    activeNode = activeNode.suffixLink;
                                }
                                --remainder;
                            }
                        }
                    }
                }
            }
            if (verbose)
                System.out.println("Remainder: " + remainder);
        }
    }

    public void print() {
        print(root, "");
    }

    private void print(Node root, String perfix) {
        // System.out.println(root);
        for (char key : root.nodes.keySet()) {
            int start = root.nodes.get(key).start;
            int end = root.nodes.get(key).end;
            end = (end == -1) ? index - 1 : end;
            System.out.println(perfix + str.substring(start, end+1));
            print(root.nodes.get(key), perfix + "--");
        }
    }
}
