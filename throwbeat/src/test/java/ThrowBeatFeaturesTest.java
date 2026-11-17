import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ThrowBeatFeaturesTest
{
    @Test
    void customMoodChangesSmartQueue()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        assertTrue(model.updateSongMood(1, Mood.WORKOUT));
        assertEquals(Mood.WORKOUT, model.findSongByID(1).getMood());
        assertEquals(1, model.addSongsByMood(Mood.WORKOUT));
        assertEquals(1, model.playNext().getSongID());
    }


    @Test
    void invalidMoodUpdateDoesNotChangeSong()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        assertFalse(model.updateSongMood(999, Mood.HAPPY));
        assertFalse(model.updateSongMood(1, null));
        assertNull(model.findSongByID(1).getMood());
        assertThrows(
            IllegalArgumentException.class,
            () -> model.findSongByID(1).setMood(null));
    }


    @Test
    void likeCanBeToggledAndInvalidIdIsSafe()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        assertFalse(model.isLiked(1));
        assertTrue(model.toggleLike(1));
        assertTrue(model.isLiked(1));
        assertTrue(model.toggleLike(1));
        assertFalse(model.isLiked(1));
        assertFalse(model.toggleLike(-100));
        assertFalse(model.isLiked(-100));
    }


    @Test
    void shufflePreservesElementsAndHandlesEmptyAndSingle()
    {
        ThrowBeatModel model = new ThrowBeatModel(new Random(123));
        model.shuffleQueue();
        assertTrue(model.isQueueEmpty());
        model.addSongToQueue(1);
        model.shuffleQueue();
        assertEquals(1, model.playNext().getSongID());
        model.addSongToQueue(1);
        model.addSongToQueue(2);
        model.addSongToQueue(3);
        List<Song> before = model.getQueueSongs();
        model.shuffleQueue();
        List<Song> after = model.getQueueSongs();
        assertEquals(3, after.size());
        assertTrue(after.containsAll(before));
    }


    @Test
    void repeatOnePreservesQueueAndStopsWhenDisabled()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        model.setRepeatOne(true);
        assertNull(model.playNext());
        model.addSongToQueue(1);
        model.addSongToQueue(2);
        Song first = model.playNext();
        assertEquals(1, first.getSongID());
        assertSame(first, model.playNext());
        assertEquals(1, model.getQueueSongs().size());
        model.setRepeatOne(false);
        assertFalse(model.isRepeatOne());
        assertEquals(2, model.playNext().getSongID());
    }


    @Test
    void queueShuffleRejectsNullRandom()
    {
        SongQueue queue = new SongQueue();
        assertThrows(IllegalArgumentException.class, () -> queue.shuffle(null));
    }
}
