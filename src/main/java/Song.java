public final class Song {
    private final int songID;
    private final String title;
    private final String artist;

    public Song(int songID, String title, String artist) {
        if (songID <= 0) throw new IllegalArgumentException("Song ID must be positive");
        if (title == null || title.trim().isEmpty() || title.length() > 100)
            throw new IllegalArgumentException("Title must be 1-100 characters");
        if (artist == null || artist.trim().isEmpty())
            throw new IllegalArgumentException("Artist must not be blank");
        this.songID = songID;
        this.title = title.trim();
        this.artist = artist.trim();
    }
    public int getSongID() { return songID; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    @Override public String toString() { return title + " — " + artist; }
}
