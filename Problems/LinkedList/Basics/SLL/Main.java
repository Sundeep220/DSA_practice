package Problems.LinkedList.Basics.SLL;

public class Main {
    public static void main(String[] args) {
        SingleLinkedList list = new SingleLinkedList();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addLast(4);
        list.addLast(5);
        list.addLast(6);
        list.addAtIndex(2, 7);
        list.removeFirst();
        list.removeLast();
        list.removeAtIndex(2);
        System.out.println(list);
        System.out.println(list.find(1));
        System.out.println(list.getSize());
        System.out.println(list.getHead());
        System.out.println(list.getTail());
    }
}
