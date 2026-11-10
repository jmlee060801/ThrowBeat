import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class SongQueueTest {
    private final Song a = new Song(1, "A", "Artist");
    private final Song b = new Song(2, "B", "Artist");
    @Test void fifoAndSize() {
        SongQueue queue = new SongQueue();
        assertTrue(queue.isEmpty());
        queue.enqueue(a); queue.enqueue(b);
        assertEquals(2, queue.size());
        assertSame(a, queue.peek());
        assertEquals(2, queue.size());
        assertEquals(java.util.List.of(a, b), queue.toArrayList());
        assertSame(a, queue.dequeue());
        assertSame(b, queue.dequeue());
        assertTrue(queue.isEmpty());
        queue.enqueue(a);
        assertSame(a, queue.dequeue());
    }
    @Test void invalidOperations() {
        SongQueue queue = new SongQueue();
        assertThrows(NoSuchElementException.class, queue::dequeue);
        assertThrows(NoSuchElementException.class, queue::peek);
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
        assertEquals(0, queue.size());
    }
    @Test void capacityLimit() {
        SongQueue queue = new SongQueue();
        for (int i = 0; i < SongQueue.MAX_CAPACITY; i++) queue.enqueue(a);
        assertThrows(IllegalStateException.class, () -> queue.enqueue(b));
        assertEquals(1000, queue.size());
    }
    @Test void returnedListCannotModifyQueue() {
        SongQueue queue = new SongQueue();
        queue.enqueue(a);
        queue.toArrayList().clear();
        assertEquals(1, queue.size());
    }
}
