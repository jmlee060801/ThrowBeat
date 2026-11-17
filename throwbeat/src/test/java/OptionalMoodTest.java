import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class OptionalMoodTest
{
    @Test
    void songsStartUntaggedAndMoodQueueStartsEmpty()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        for (Song song : model.getSongLibrary())
            assertNull(song.getMood());
        assertEquals(0, model.addSongsByMood(Mood.HAPPY));
        assertTrue(model.isQueueEmpty());
    }


    @Test
    void userAssignsMoodAndCanRemoveIt()
    {
        ThrowBeatModel model = new ThrowBeatModel();
        assertTrue(model.updateSongMood(2, Mood.HAPPY));
        assertEquals(1, model.addSongsByMood(Mood.HAPPY));
        assertEquals(2, model.playNext().getSongID());
        assertTrue(model.clearSongMood(2));
        assertNull(model.findSongByID(2).getMood());
        assertEquals(0, model.addSongsByMood(Mood.HAPPY));
        assertFalse(model.clearSongMood(999));
    }
}
