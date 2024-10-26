import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Priority_Queue<T extends Comparable<T>> {
    private List<T> heap;

    // Constructor to initialize the priority queue
    public Priority_Queue() {
        this.heap = new ArrayList<>();
    }

    // Insert a new element into the min-heap
    public void insert(T element) {
        heap.add(element);
        bubbleUp(heap.size() - 1); // Restore heap order
    }

    // Remove and return the smallest element (root)
    public T delMin() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        T min = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            bubbleDown(0); // Restore heap order
        }
        return min;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Get the size of the queue
    public int size() {
        return heap.size();
    }

    // Restore heap property by bubbling up
    private void bubbleUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    // Restore heap property by bubbling down
    private void bubbleDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;

        if (leftChild < heap.size() && heap.get(leftChild).compareTo(heap.get(smallest)) < 0) {
            smallest = leftChild;
        }

        if (rightChild < heap.size() && heap.get(rightChild).compareTo(heap.get(smallest)) < 0) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swap(index, smallest);
            bubbleDown(smallest);
        }
    }

    // Swap two elements in the heap
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
