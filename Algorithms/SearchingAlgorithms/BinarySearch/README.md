# Binary Search in Java

Binary Search is an efficient algorithm for finding an item from a sorted array. It works by repeatedly dividing the search interval in half. If the value of the search key is less than the item in the middle of the interval, narrow the interval to the lower half. Otherwise, narrow it to the upper half. Repeatedly check until the value is found or the interval is empty.

---

## 🔍 Binary Search Precondition:

* The array **must be sorted** in ascending or descending order (usually ascending).

---

## ✅ Iterative Implementation

```java
public class BinarySearchIterative {
    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid; // Found target
            } else if (arr[mid] < target) {
                low = mid + 1; // Search right half
            } else {
                high = mid - 1; // Search left half
            }
        }

        return -1; // Not found
    }
}
```

---

## 🔁 Recursive Implementation

```java
public class BinarySearchRecursive {
    public static int binarySearch(int[] arr, int target, int low, int high) {
        if (low > high) {
            return -1; // Base case: not found
        }

        int mid = low + (high - low) / 2;

        if (arr[mid] == target) {
            return mid; // Found target
        } else if (arr[mid] < target) {
            return binarySearch(arr, target, mid + 1, high); // Search right half
        } else {
            return binarySearch(arr, target, low, mid - 1); // Search left half
        }
    }
}
```

---

## 🧪 Example Usage

```java
public class Main {
    public static void main(String[] args) {
        int[] sortedArray = {1, 3, 5, 7, 9, 11, 13};
        int target = 7;

        // Iterative Search
        int indexIterative = BinarySearchIterative.binarySearch(sortedArray, target);
        System.out.println("Iterative Search: Index = " + indexIterative);

        // Recursive Search
        int indexRecursive = BinarySearchRecursive.binarySearch(sortedArray, target, 0, sortedArray.length - 1);
        System.out.println("Recursive Search: Index = " + indexRecursive);
    }
}
```

---

## 🧠 Time Complexity

* **Best Case:** O(1) — when the element is found at the middle
* **Average/Worst Case:** O(log n)

## 📦 Space Complexity

* **Iterative:** O(1)
* **Recursive:** O(log n) due to recursion stack

---

## ✅ Summary

| Feature     | Iterative | Recursive          |
| ----------- | --------- | ------------------ |
| Code Size   | Longer    | Shorter            |
| Space       | O(1)      | O(log n) (stack)   |
| Readability | Good      | Elegant (for some) |

Binary Search is a classic example of divide-and-conquer and is foundational in many algorithms.
