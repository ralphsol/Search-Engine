public class AvlNode {
    public AvlNode left;
    public AvlNode right;
    public AvlNode parent;
    public Position key;
    public int balance;
    
    public AvlNode(Position k) {
        left = right = parent = null;
        balance = 0;
        key = k;
    }
    public String toString() {
        return "" + key;
    }

}