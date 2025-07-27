package Problems.StacksAndQueue.Basics;

public class QueueUsingArrays {
    private int[] queue = new int[3];
    private int head = 0, tail = 0;


    public void enqueue(int data) {
        if(isFull()){
            System.out.println("Queue is full");
            return;
        }
        if(tail == queue.length){
            tail = tail % queue.length;
        }
        queue[tail++] = data;
    }

    public int dequeue() {
        if(isEmpty()){
            System.out.println("Queue is empty");
            return -1;
        }
        if(head == queue.length){
            head = head % queue.length;
        }
        return queue[head++];
    }

    public int peek() {
        return queue[head];
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        return tail == queue.length;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = head; i < tail; i++) {
            sb.append(queue[i]).append(" -> ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        QueueUsingArrays queue = new QueueUsingArrays();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println(queue);
        System.out.println("Popped: " + queue.dequeue());
        System.out.println(queue);
        System.out.println("Is Empty: " + queue.isEmpty());
        System.out.println("Is Full: " + queue.isFull());
        System.out.println("Peek: " + queue.peek());
    }


}
