package Problems.Heaps.Basics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MaxHeap {
    private final int[] heap;
    private int size;
    private final int capacity;

    public MaxHeap(int capacity) {
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

        // Heapify-up
        while (current > 0 && heap[current] > heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    // Extract max element
    public int extractMax() {
        if (size == 0) throw new RuntimeException("Heap is empty");
        int max = heap[0];
        heap[0] = heap[size - 1];
        size--;

        heapifyDown(0);
        return max;
    }

    // Heapify-down
    private void heapifyDown(int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && heap[left] > heap[largest]) {
            largest = left;
        }
        if (right < size && heap[right] > heap[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(i, largest);
            heapifyDown(largest);
        }
    }

    // Peek max
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
    public List<Integer> heapSort(int[] arr) {
      MaxHeap maxHeap = new MaxHeap(arr.length);
      for (int num : arr) {
        maxHeap.insert(num);
      }

      List<Integer> sortedArr = new ArrayList<>();
      while (maxHeap.size > 0) {
        sortedArr.add(maxHeap.extractMax());
      }
      Collections.reverse(sortedArr);
      return sortedArr;
    }
}
