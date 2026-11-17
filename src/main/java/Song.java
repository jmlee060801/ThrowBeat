public final class Song {
    private final int songID;
    private final String title;
    private final String artist;
    private Mood mood;
    private boolean liked;

    public Song(int songID, String title, String artist) { this(songID, title, artist, null); }
    public Song(int songID, String title, String artist, Mood mood) {
        if (songID <= 0) throw new IllegalArgumentException("Song ID must be positive");
        if (title == null || title.trim().isEmpty() || title.length() > 100) throw new IllegalArgumentException("Title must be 1-100 characters");
        if (artist == null || artist.trim().isEmpty()) throw new IllegalArgumentException("Artist must not be blank");
        this.songID = songID; this.title = title.trim(); this.artist = artist.trim(); this.mood = mood;
    }
    public int getSongID() { return songID; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public Mood getMood() { return mood; }
    public void setMood(Mood mood) { if (mood == null) throw new IllegalArgumentException("Mood required"); this.mood = mood; }
    public void clearMood() { this.mood = null; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    @Override public String toString() { return (liked ? "♥ " : "") + title + " — " + artist + (mood == null ? "" : " [" + mood + "]"); }
}
