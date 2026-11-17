public class ThrowBeatController {
    private final ThrowBeatModel model;
    private final ThrowBeatView view;
    public ThrowBeatController(ThrowBeatModel model, ThrowBeatView view) {
        if (model == null || view == null) throw new IllegalArgumentException("Model and view required");
        this.model = model; this.view = view;
        view.onAddSong(this::handleAddSong);
        view.onPlayNext(this::handlePlayNext);
        view.onGenerateMood(this::handleGenerateMood);
        view.onSaveMood(this::handleSaveMood);
        view.onClearMood(this::handleClearMood);
        view.onToggleLike(this::handleToggleLike);
        view.onShuffle(this::handleShuffle);
        view.onClearQueue(this::handleClearQueue);
        view.onRepeatOne(this::handleRepeatOne);
        refreshView();
    }
    public void handleAddSong() {
        Integer id = view.getSelectedSongID();
        if (id == null) { view.showMessage("Select a song first."); return; }
        try {
            if (!model.addSongToQueue(id)) {
                view.showMessage("Song not found.");
                return;
            }
            refreshView();
            Song added = model.findSongByID(id);
            view.showQueueAndConfirm("✓ Added '" + added.getTitle() + "' — "
                    + model.getQueueSongs().size() + " song(s) waiting");
        } catch (IllegalStateException e) {
            view.showMessage(e.getMessage());
        }
    }
    public void handleGenerateMood() {
        int added = model.addSongsByMood(view.getSelectedMood());
        refreshView();
        if (added == 0) view.showMessage("No matching songs or queue is full.");
        else view.showQueueAndConfirm("✓ Added " + added + " " + view.getSelectedMood()
                + " song(s) — " + model.getQueueSongs().size() + " waiting");
    }
    public void handleSaveMood() {
        Integer id = view.getSelectedSongID();
        if (id == null) { view.showMessage("Select a song to assign its mood."); return; }
        if (!model.updateSongMood(id, view.getSelectedMood())) view.showMessage("Could not update mood.");
        refreshView();
        view.showActionFeedback("Mood saved for selected song.");
    }
    public void handleClearMood() {
        Integer id = view.getSelectedSongID();
        if (id == null) { view.showMessage("Select a song first."); return; }
        if (model.clearSongMood(id)) {
            refreshView();
            view.showActionFeedback("Mood removed. Song is now untagged.");
        } else view.showMessage("Song not found.");
    }
    public void handleToggleLike() {
        Integer id = view.getSelectedSongID();
        if (id == null) { view.showMessage("Select a song to like or unlike."); return; }
        if (!model.toggleLike(id)) view.showMessage("Song not found.");
        refreshView();
    }
    public void handleClearQueue() {
        model.clearQueue();
        refreshView();
        view.showQueueAndConfirm("Queue cleared — 0 songs waiting");
    }
    public void handleShuffle() {
        model.shuffleQueue(); refreshView();
    }
    public void handleRepeatOne() {
        model.setRepeatOne(view.isRepeatOneSelected()); refreshView();
    }
    public void handlePlayNext() {
        if (model.playNext() == null) view.showMessage("Queue is empty. Add a song first.");
        refreshView();
    }
    public void refreshView() {
        view.updateLibrary(model.getSongLibrary());
        view.updateQueue(model.getQueueSongs());
        view.updateCurrentSong(model.getCurrentSong());
        view.updateRepeatOne(model.isRepeatOne());
    }
}
