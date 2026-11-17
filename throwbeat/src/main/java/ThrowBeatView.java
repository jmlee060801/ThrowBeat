import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;

public class ThrowBeatView {
    private final JFrame frame = new JFrame("ThrowBeat — Music Queue Player");
    private final DefaultListModel<Song> libraryListModel = new DefaultListModel<>();
    private final JList<Song> songList = new JList<>(libraryListModel);
    private final DefaultListModel<Song> queueListModel = new DefaultListModel<>();
    private final JLabel currentSongLabel = new JLabel("Now Playing: Nothing yet");
    private final JButton addButton = new JButton("Add Selected Song");
    private final JButton playNextButton = new JButton("Play Next");

    public ThrowBeatView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel lists = new JPanel(new GridLayout(1, 2, 12, 0));
        lists.add(labeledList("Song Library", new JScrollPane(songList)));
        lists.add(labeledList("Waiting Queue", new JScrollPane(new JList<>(queueListModel))));
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel();
        buttons.add(addButton);
        buttons.add(playNextButton);
        bottom.add(currentSongLabel, BorderLayout.NORTH);
        bottom.add(buttons, BorderLayout.SOUTH);
        frame.add(lists, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setSize(660, 350);
        frame.setLocationRelativeTo(null);
    }
    private JPanel labeledList(String title, JScrollPane scroll) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    public Integer getSelectedSongID() {
        Song song = songList.getSelectedValue();
        return song == null ? null : song.getSongID();
    }
    public void updateLibrary(List<Song> songs) {
        libraryListModel.clear();
        for (Song song : songs) libraryListModel.addElement(song);
    }
    public void updateQueue(List<Song> songs) {
        queueListModel.clear();
        for (Song song : songs) queueListModel.addElement(song);
    }
    public void updateCurrentSong(Song song) {
        currentSongLabel.setText("Now Playing: " + (song == null ? "Nothing yet" : song));
    }
    public void onAddSong(Runnable action) { addButton.addActionListener(e -> action.run()); }
    public void onPlayNext(Runnable action) { playNextButton.addActionListener(e -> action.run()); }
    public void showMessage(String message) { JOptionPane.showMessageDialog(frame, message); }
    public void show() { frame.setVisible(true); }
}
