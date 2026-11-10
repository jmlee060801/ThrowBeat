import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SongTest {
    @Test void storesSongDetails() {
        Song song = new Song(1, "Title", "Artist");
        assertEquals(1, song.getSongID());
        assertEquals("Title", song.getTitle());
        assertEquals("Artist", song.getArtist());
    }
    @Test void rejectsBadFields() {
        assertThrows(IllegalArgumentException.class, () -> new Song(0, "T", "A"));
        assertThrows(IllegalArgumentException.class, () -> new Song(1, null, "A"));
        assertThrows(IllegalArgumentException.class, () -> new Song(1, "  ", "A"));
        assertThrows(IllegalArgumentException.class, () -> new Song(1, "T", " "));
        assertThrows(IllegalArgumentException.class, () -> new Song(1, "x".repeat(101), "A"));
    }
}
