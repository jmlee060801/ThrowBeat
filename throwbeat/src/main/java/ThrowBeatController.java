public class ThrowBeatController {
    private final ThrowBeatModel model;
    private final ThrowBeatView view;

    public ThrowBeatController(ThrowBeatModel model, ThrowBeatView view) {
        if (model == null || view == null) throw new IllegalArgumentException("Model and view required");
        this.model = model;
        this.view = view;
        view.onAddSong(this::handleAddSong);
        view.onPlayNext(this::handlePlayNext);
        refreshView();
    }
    public void handleAddSong() {
        Integer id = view.getSelectedSongID();
        if (id == null) { view.showMessage("Select a song first."); return; }
        try {
            if (!model.addSongToQueue(id)) view.showMessage("Song not found.");
        } catch (IllegalStateException e) {
            view.showMessage(e.getMessage());
        }
        refreshView();
    }
    public void handlePlayNext() {
        if (model.playNext() == null) view.showMessage("Queue is empty.");
        refreshView();
    }
    public void refreshView() {
        view.updateLibrary(model.getSongLibrary());
        view.updateQueue(model.getQueueSongs());
        view.updateCurrentSong(model.getCurrentSong());
    }
}
