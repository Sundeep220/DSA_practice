package Problems.LinkedList.Basics.SLL;

public class Node {
    public int data;
    public Node next;

    public Node(int data, Node next){
        this.data = data;
        this.next = next;
    }

    public Node(int data){
        this.data = data;
        this.next = null;
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }
}
