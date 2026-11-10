import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ThrowBeatModelTest {
    @Test void fifoPlaybackAndEmptyBehavior() {
        ThrowBeatModel model = new ThrowBeatModel();
        assertTrue(model.isQueueEmpty());
        assertNull(model.getCurrentSong());
        assertTrue(model.addSongToQueue(2));
        assertTrue(model.addSongToQueue(1));
        assertEquals(2, model.getQueueSongs().get(0).getSongID());
        assertEquals(2, model.playNext().getSongID());
        assertEquals(1, model.playNext().getSongID());
        assertNull(model.playNext());
        assertEquals(1, model.getCurrentSong().getSongID());
        assertTrue(model.isQueueEmpty());
    }
    @Test void unknownIdAndDefensiveCopies() {
        ThrowBeatModel model = new ThrowBeatModel();
        assertNull(model.findSongByID(-1));
        assertFalse(model.addSongToQueue(999));
        assertTrue(model.isQueueEmpty());
        int size = model.getSongLibrary().size();
        model.getSongLibrary().clear();
        assertEquals(size, model.getSongLibrary().size());
        model.addSongToQueue(1);
        model.getQueueSongs().clear();
        assertEquals(1, model.getQueueSongs().size());
    }
}
