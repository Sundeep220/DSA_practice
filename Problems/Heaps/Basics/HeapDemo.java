package Problems.Heaps.Basics;

public class HeapDemo {
    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(10);

        maxHeap.insert(20);
        maxHeap.insert(15);
        maxHeap.insert(30);
        maxHeap.insert(5);

        maxHeap.printHeap(); // Should show heap structure

        System.out.println("Max: " + maxHeap.extractMax()); // 30
        maxHeap.printHeap();

        System.out.println("Peek: " + maxHeap.peek()); // Next max


        MinHeapArray minHeap = new MinHeapArray(10);

        minHeap.insert(20);
        minHeap.insert(15);
        minHeap.insert(30);
        minHeap.insert(5);

        minHeap.printHeap(); // Should show heap structure

        System.out.println("Min: " + minHeap.extractMin()); // 5
        minHeap.printHeap();

        System.out.println("Peek: " + minHeap.peek()); // Next min
    }
}
