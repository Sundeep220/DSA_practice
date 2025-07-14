package Problems.LinkedList.Hard;


public class DualLink {
    public int val;
    public DualLink next;
    public DualLink child;

    public DualLink(int x) {
        val = x;
    }

    public String toString() {
        return val + " -> " + next;
    }
}
