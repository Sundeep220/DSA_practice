package Problems.Heaps.Basics;

import java.util.ArrayList;
import java.util.List;

// You can say that we implement Priority Queue here as well as Min Heap
class MinHeapArrayList {
    private final ArrayList<Integer> heap;

    public MinHeapArrayList() {
        heap = new ArrayList<>();
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Insert element
    public void insert(int value) {
        heap.add(value);
//        int current = heap.size() - 1;

//        // Heapify-up (bubble up smaller element)
//        while (current > 0 && heap.get(current) < heap.get(parent(current))) {
//            swap(current, parent(current));
//            current = parent(current);
//        }
        heapifyUp(heap.size() - 1);
    }

    // Extract min element
    public int extractMin() {
        if (heap.isEmpty()) throw new RuntimeException("Heap is empty");

        int min = heap.getFirst();
        int last = heap.removeLast();

        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return min;
    }

    // Iterative Heapify-down
    private void heapifyDown(int i) {
        int size = heap.size();
        while (true) {
            int smallest = i;
            int left = leftChild(i);
            int right = rightChild(i);

            if (left < size && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }
            if (right < size && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest != i) {
                swap(i, smallest);
                i = smallest; // move down
            } else {
                break;
            }
        }
    }

    // Peek min
    public int peek() {
        if (heap.isEmpty()) throw new RuntimeException("Heap is empty");
        return heap.getFirst();
    }

    // Print heap
    public void printHeap() {
        System.out.println(heap);
    }

    // Heap Sort (returns sorted list)
    public static List<Integer> heapSort(int[] arr) {
        MinHeapArrayList minHeap = new MinHeapArrayList();

        // Step 1: Build heap
        for (int num : arr) {
            minHeap.insert(num);
        }

        // Step 2: Extract min repeatedly
        List<Integer> sorted = new ArrayList<>();
        while (!minHeap.heap.isEmpty()) {
            sorted.add(minHeap.extractMin());
        }

        return sorted;
    }

    // Heapify-up (helper for changeKey)
    private void heapifyUp(int i) {
        while (i > 0 && heap.get(i) < heap.get(parent(i))) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    public void changeKey(int index, int newValue) {
        if (index < 0 || index >= heap.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        int oldValue = heap.get(index);
        heap.set(index, newValue);

        if (newValue < oldValue) {
            heapifyUp(index);   // Bubble up
        } else if (newValue > oldValue) {
            heapifyDown(index); // Bubble down
        }
    }

    public static void main(String[] args) {
        MinHeapArrayList minHeap = new MinHeapArrayList();

        minHeap.insert(20);
        minHeap.insert(15);
        minHeap.insert(30);
        minHeap.insert(5);

        minHeap.printHeap(); // Example: [5, 15, 30, 20]

        System.out.println("Min: " + minHeap.extractMin()); // 5
        minHeap.printHeap(); // Example: [15, 20, 30]

        System.out.println("Peek: " + minHeap.peek()); // 15
    }
}


