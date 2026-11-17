public class ThrowBeatModelDriver {
    public static void main(String[] args) {
        ThrowBeatModel model = new ThrowBeatModel();
        check(model.addSongToQueue(1), "Add first song");
        check(model.addSongToQueue(2), "Add second song");
        check(model.addSongToQueue(3), "Add third song");
        check(model.getQueueSongs().size() == 3, "Queue size");
        check(model.playNext().getSongID() == 1, "First song plays first");
        check(model.playNext().getSongID() == 2, "Second song plays second");
        check(model.playNext().getSongID() == 3, "Third song plays third");
        check(model.playNext() == null, "Empty queue returns null");
        check(model.getCurrentSong().getSongID() == 3, "Current song unchanged on empty queue");
        check(!model.addSongToQueue(999), "Unknown song rejected");
        System.out.println("All independent model checks passed.");
    }
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        System.out.println("PASS: " + message);
    }
}
