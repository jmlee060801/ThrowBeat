import java.util.ArrayList;

public class ThrowBeatModel {
    private final ArrayList<Song> songLibrary = new ArrayList<>();
    private final SongQueue songQueue = new SongQueue();
    private Song currentSong;

    public ThrowBeatModel() {
        songLibrary.add(new Song(1, "First Light", "Demo Artist A"));
        songLibrary.add(new Song(2, "City Nights", "Demo Artist B"));
        songLibrary.add(new Song(3, "Blue Horizon", "Demo Artist C"));
    }
    public ArrayList<Song> getSongLibrary() { return new ArrayList<>(songLibrary); }
    public Song findSongByID(int songID) {
        for (Song song : songLibrary) if (song.getSongID() == songID) return song;
        return null;
    }
    public boolean addSongToQueue(int songID) {
        Song song = findSongByID(songID);
        if (song == null) return false;
        songQueue.enqueue(song);
        return true;
    }
    public Song playNext() {
        if (songQueue.isEmpty()) return null;
        currentSong = songQueue.dequeue();
        return currentSong;
    }
    public Song getCurrentSong() { return currentSong; }
    public ArrayList<Song> getQueueSongs() { return songQueue.toArrayList(); }
    public boolean isQueueEmpty() { return songQueue.isEmpty(); }
}
