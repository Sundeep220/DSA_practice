package Problems.Heaps.Basics;

import java.util.ArrayList;
import java.util.List;

public class MinHeapArray {
    private final int[] heap;
    private int size;
    private final int capacity;

    public MinHeapArray(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Insert element into heap
    public void insert(int value) {
        if (size == capacity) {
            throw new RuntimeException("Heap is full");
        }
        heap[size] = value;
        int current = size;
        size++;

        // Heapify-up (bubble up smaller element)
        while (current > 0 && heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    // Extract min element
    public int extractMin() {
        if (size == 0) throw new RuntimeException("Heap is empty");
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;

        heapifyDown(0);
        return min;
    }

    // Heapify-down (push down larger element) (Recursive)
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && heap[left] < heap[smallest]) {
            smallest = left;
        }
        if (right < size && heap[right] < heap[smallest]) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }

    // Heapify-down (push down larger element) (Iterative)
    private void heapifyDownII(int i) {
        while (true) {
            int smallest = i;
            int left = leftChild(i);
            int right = rightChild(i);

            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }
            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
            }

            if (smallest != i) {
                swap(i, smallest);
                i = smallest; // keep going down
            } else {
                break; // stop when heap property is satisfied
            }
        }
    }

    // Peek min
    public int peek() {
        if (size == 0) throw new RuntimeException("Heap is empty");
        return heap[0];
    }

    // Print heap
    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    // Heap Sort (returns sorted list)
    public static List<Integer> heapSort(int[] arr) {
        MinHeapArray minHeap = new MinHeapArray(arr.length);

        // Step 1: Build heap
        for (int num : arr) {
            minHeap.insert(num);
        }

        // Step 2: Extract min repeatedly
        List<Integer> sorted = new ArrayList<>();
        while (minHeap.size > 0) {
            sorted.add(minHeap.extractMin());
        }

        return sorted;
    }

}
