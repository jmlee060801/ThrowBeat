import java.util.ArrayList;
import java.util.Random;

public class ThrowBeatModel {
    private final ArrayList<Song> songLibrary = new ArrayList<>();
    private final SongQueue songQueue = new SongQueue();
    private final Random random;
    private Song currentSong;
    private boolean repeatOne;

    public ThrowBeatModel() { this(new Random()); }
    public ThrowBeatModel(Random random) {
        if (random == null) throw new IllegalArgumentException("Random required");
        this.random = random;
        songLibrary.add(new Song(1, "First Light", "Demo Artist A"));
        songLibrary.add(new Song(2, "City Nights", "Demo Artist B"));
        songLibrary.add(new Song(3, "Blue Horizon", "Demo Artist C"));
        songLibrary.add(new Song(4, "Final Sprint", "Demo Artist D"));
    }
    public ArrayList<Song> getSongLibrary() { return new ArrayList<>(songLibrary); }
    public Song findSongByID(int id) { for (Song song : songLibrary) if (song.getSongID() == id) return song; return null; }
    public boolean addSongToQueue(int id) {
        Song song = findSongByID(id);
        if (song == null) return false;
        songQueue.enqueue(song);
        return true;
    }
    public int addSongsByMood(Mood mood) {
        if (mood == null) return 0;
        int added = 0;
        for (Song song : songLibrary) {
            if (song.getMood() == mood && songQueue.size() < SongQueue.MAX_CAPACITY) { songQueue.enqueue(song); added++; }
        }
        return added;
    }
    public boolean updateSongMood(int id, Mood mood) {
        Song song = findSongByID(id);
        if (song == null || mood == null) return false;
        song.setMood(mood); return true;
    }
    public boolean clearSongMood(int id) {
        Song song = findSongByID(id);
        if (song == null) return false;
        song.clearMood();
        return true;
    }
    public boolean toggleLike(int id) {
        Song song = findSongByID(id);
        if (song == null) return false;
        song.setLiked(!song.isLiked()); return true;
    }
    public boolean isLiked(int id) { Song song = findSongByID(id); return song != null && song.isLiked(); }
    public void shuffleQueue() { songQueue.shuffle(random); }
    public void clearQueue() { songQueue.clear(); }
    public boolean isRepeatOne() { return repeatOne; }
    public void setRepeatOne(boolean enabled) { repeatOne = enabled; }
    public Song playNext() {
        if (repeatOne && currentSong != null) return currentSong;
        if (songQueue.isEmpty()) return null;
        currentSong = songQueue.dequeue(); return currentSong;
    }
    public Song getCurrentSong() { return currentSong; }
    public ArrayList<Song> getQueueSongs() { return songQueue.toArrayList(); }
    public boolean isQueueEmpty() { return songQueue.isEmpty(); }
}
