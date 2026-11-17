import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ClearQueueTest
{
    @Test
    void clearRemovesAllWaitingSongs()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        model.addSongToQueue(1);
        model.addSongToQueue(2);
        model.clearQueue();
        assertTrue(model.isQueueEmpty());
        assertEquals(0, model.getQueueSongs().size());
        assertNull(model.playNext());
    }


    @Test
    void clearingEmptyQueueIsSafeAndRepeatable()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        assertDoesNotThrow(() -> {
            model.clearQueue();
            model.clearQueue();
        });
        assertTrue(model.isQueueEmpty());
    }


    @Test
    void clearingWaitingQueueDoesNotResetCurrentSong()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        model.addSongToQueue(1);
        Song current = model.playNext();
        model.addSongToQueue(2);
        model.clearQueue();
        assertSame(current, model.getCurrentSong());
    }
}
