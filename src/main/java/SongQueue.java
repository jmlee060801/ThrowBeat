import java.util.ArrayList;
import java.util.NoSuchElementException;

public class SongQueue {
    public static final int MAX_CAPACITY = 1000;
    private static class Node {
        Song data;
        Node next;
        Node(Song data) { this.data = data; }
    }
    private Node front;
    private Node rear;
    private int size;

    public void enqueue(Song song) {
        if (song == null) throw new IllegalArgumentException("Song cannot be null");
        if (size == MAX_CAPACITY) throw new IllegalStateException("Queue is full");
        Node node = new Node(song);
        if (rear == null) front = node;
        else rear.next = node;
        rear = node;
        size++;
    }
    public Song dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Song result = front.data;
        front = front.next;
        size--;
        if (front == null) rear = null;
        return result;
    }
    public Song peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return front.data;
    }
    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }
    public ArrayList<Song> toArrayList() {
        ArrayList<Song> result = new ArrayList<>();
        for (Node node = front; node != null; node = node.next) result.add(node.data);
        return result;
    }
}
