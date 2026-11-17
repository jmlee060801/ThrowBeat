import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** Dark music-library interface; all state changes still go through the controller. */
public class ThrowBeatView {
    private static final Color BG = new Color(13, 13, 16);
    private static final Color PANEL = new Color(25, 25, 30);
    private static final Color PANEL2 = new Color(35, 35, 42);
    private static final Color FG = new Color(245, 245, 248);
    private static final Color MUTED = new Color(165, 165, 177);
    private static final Color ACCENT = new Color(255, 53, 85);
    private final JFrame frame = new JFrame("ThrowBeat | Music");
    private final DefaultListModel<Song> libraryListModel = new DefaultListModel<>();
    private final JList<Song> songList = new JList<>(libraryListModel);
    private final DefaultListModel<Song> queueListModel = new DefaultListModel<>();
    private final JList<Song> queueList = new JList<>(queueListModel);
    private final JLabel currentSongLabel = new JLabel("Nothing playing");
    private final JLabel currentArtistLabel = new JLabel("Choose a song and press Play Next");
    private final JLabel libraryCount = new JLabel("0 tracks");
    private final JLabel queueCount = new JLabel("0 waiting");
    private final JButton addButton = button("+ Add to queue", ACCENT);
    private final JButton playNextButton = button("▶  Play Next", ACCENT);
    // A custom dark popup avoids macOS native JComboBox white background issues.
    private Mood selectedMood = Mood.HAPPY;
    private final JButton moodSelector = button("HAPPY  ▾", PANEL2);
    private final JPopupMenu moodMenu = new JPopupMenu();
    private final JLabel actionFeedback = label("Select a song to get started", 12, MUTED, Font.PLAIN);
    private final JButton moodButton = button("Generate mood queue", PANEL2);
    private final JButton saveMoodButton = button("Save mood", PANEL2);
    private final JButton clearMoodButton = button("Remove mood", PANEL2);
    private final JButton likeButton = button("♡  Like / Unlike", PANEL2);
    private final JButton shuffleButton = button("⇄  Shuffle", PANEL2);
    private final JButton clearQueueButton = button("✕  Clear Queue", PANEL2);
    private final CardLayout pageLayout = new CardLayout();
    private final JPanel pages = new JPanel(pageLayout);
    private final DefaultListModel<Song> likedListModel = new DefaultListModel<>();
    private final JList<Song> likedList = new JList<>(likedListModel);
    private final JLabel homeLibraryCount = label("0 songs in your library", 16, FG, Font.BOLD);
    private final JLabel homeQueueCount = label("0 songs waiting", 14, MUTED, Font.PLAIN);
    private final JLabel homeLikedCount = label("0 liked songs", 14, MUTED, Font.PLAIN);
    private final JLabel pageTitle = label("Home", 30, FG, Font.BOLD);
    private final JButton homeNav = navButton("⌂  Home");
    private final JButton libraryNav = navButton("♫  Your Library");
    private final JButton likedNav = navButton("♥  Liked Tracks");
    private final JButton queueNav = navButton("≡  Play Queue");
    private final JCheckBox repeatOneCheck = new JCheckBox("↻  Repeat one");

    public ThrowBeatView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(950, 650));
        frame.setSize(1200, 780);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG);
        frame.setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(19, 19, 23));
        sidebar.setBorder(new EmptyBorder(28, 22, 20, 22));
        sidebar.setPreferredSize(new Dimension(205, 0));
        JLabel brand = label("◉ ThrowBeat", 23, FG, Font.BOLD);
        sidebar.add(brand);
        sidebar.add(Box.createVerticalStrut(40));
        for (JButton nav : new JButton[]{homeNav, libraryNav, likedNav, queueNav}) {
            sidebar.add(nav);
            sidebar.add(Box.createVerticalStrut(12));
        }
        homeNav.addActionListener(e -> navigate("HOME"));
        libraryNav.addActionListener(e -> navigate("LIBRARY"));
        likedNav.addActionListener(e -> navigate("LIKED"));
        queueNav.addActionListener(e -> navigate("QUEUE"));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(label("SECOND PROTOTYPE", 10, MUTED, Font.BOLD));
        frame.add(sidebar, BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout(0, 22));
        main.setBackground(BG);
        main.setBorder(new EmptyBorder(28, 30, 20, 30));
        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));
        headingText.add(label("YOUR MUSIC", 11, ACCENT, Font.BOLD));
        headingText.add(Box.createVerticalStrut(6));
        headingText.add(pageTitle);
        headingText.add(Box.createVerticalStrut(6));
        headingText.add(label("Songs start untagged. Assign a mood only when you want.", 13, MUTED, Font.PLAIN));
        heading.add(headingText, BorderLayout.WEST);
        main.add(heading, BorderLayout.NORTH);

        styleList(songList);
        styleList(queueList);
        styleList(likedList);
        // Selecting a liked track also selects it in the canonical library list.
        likedList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && likedList.getSelectedValue() != null)
                songList.setSelectedValue(likedList.getSelectedValue(), true);
        });
        pages.setOpaque(false);
        JPanel home = new JPanel();
        home.setBackground(PANEL);
        home.setBorder(new EmptyBorder(36, 36, 36, 36));
        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
        home.add(label("WELCOME TO THROWBEAT", 14, ACCENT, Font.BOLD));
        home.add(Box.createVerticalStrut(24));
        home.add(homeLibraryCount);
        home.add(Box.createVerticalStrut(16));
        home.add(homeLikedCount);
        home.add(Box.createVerticalStrut(16));
        home.add(homeQueueCount);
        home.add(Box.createVerticalStrut(30));
        home.add(label("Use the sidebar to explore your music.", 14, MUTED, Font.PLAIN));
        pages.add(home, "HOME");
        pages.add(section("Your Library", libraryCount, songList), "LIBRARY");
        JLabel likedCount = label("Liked songs", 12, MUTED, Font.PLAIN);
        pages.add(section("Liked Tracks", likedCount, likedList), "LIKED");
        pages.add(section("Play Queue", queueCount, queueList), "QUEUE");
        main.add(pages, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        actions.setOpaque(false);
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        JPanel topActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 5));
        topActions.setOpaque(false);
        topActions.add(addButton);
        topActions.add(likeButton);
        topActions.add(shuffleButton);
        topActions.add(clearQueueButton);
        actions.add(topActions);
        actionFeedback.setForeground(new Color(145, 222, 165));
        actionFeedback.setBorder(new EmptyBorder(2, 10, 5, 0));
        actions.add(actionFeedback);
        JPanel moodActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 5));
        moodActions.setOpaque(false);
        moodActions.add(label("Mood", 13, MUTED, Font.BOLD));
        moodSelector.setBackground(PANEL2);
        moodSelector.setForeground(Color.WHITE);
        moodSelector.setOpaque(true);
        moodSelector.setContentAreaFilled(false);
        moodSelector.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 102)),
                new EmptyBorder(9, 14, 9, 14)));
        moodSelector.setFont(new Font("SansSerif", Font.BOLD, 13));
        moodSelector.setPreferredSize(new Dimension(145, 40));
        moodMenu.setBackground(PANEL2);
        moodMenu.setOpaque(true);
        moodMenu.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 102)));
        for (Mood option : Mood.values()) {
            JMenuItem item = new JMenuItem(option.name());
            item.setOpaque(true);
            item.setBackground(PANEL2);
            item.setForeground(Color.WHITE);
            item.setFont(new Font("SansSerif", Font.BOLD, 13));
            item.setBorder(new EmptyBorder(10, 16, 10, 16));
            item.addActionListener(e -> {
                selectedMood = option;
                moodSelector.setText(option.name() + "  ▾");
            });
            moodMenu.add(item);
        }
        moodSelector.addActionListener(e -> moodMenu.show(moodSelector, 0, moodSelector.getHeight()));
        moodActions.add(moodSelector);
        moodActions.add(saveMoodButton);
        moodActions.add(clearMoodButton);
        moodActions.add(moodButton);
        actions.add(moodActions);
        main.add(actions, BorderLayout.SOUTH);
        frame.add(main, BorderLayout.CENTER);

        JPanel player = new JPanel(new BorderLayout(20, 0));
        player.setBackground(PANEL);
        player.setBorder(new EmptyBorder(16, 28, 16, 28));
        JPanel nowPlaying = new JPanel();
        nowPlaying.setOpaque(false);
        nowPlaying.setLayout(new BoxLayout(nowPlaying, BoxLayout.Y_AXIS));
        nowPlaying.add(label("NOW PLAYING", 10, ACCENT, Font.BOLD));
        nowPlaying.add(Box.createVerticalStrut(5));
        currentSongLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        currentSongLabel.setForeground(FG);
        nowPlaying.add(currentSongLabel);
        currentArtistLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        currentArtistLabel.setForeground(MUTED);
        nowPlaying.add(currentArtistLabel);
        player.add(nowPlaying, BorderLayout.WEST);
        JPanel playback = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        playback.setOpaque(false);
        repeatOneCheck.setOpaque(false);
        repeatOneCheck.setForeground(FG);
        repeatOneCheck.setFont(new Font("SansSerif", Font.PLAIN, 13));
        repeatOneCheck.setFocusPainted(false);
        playback.add(repeatOneCheck);
        playback.add(playNextButton);
        player.add(playback, BorderLayout.EAST);
        frame.add(player, BorderLayout.SOUTH);
        navigate("LIBRARY");
    }

    private static JButton navButton(String title) {
        JButton button = button(title, new Color(19, 19, 23));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(175, 45));
        return button;
    }

    private void navigate(String page) {
        pageLayout.show(pages, page);
        String title = page.equals("HOME") ? "Home" : page.equals("LIBRARY") ? "Your Library"
                : page.equals("LIKED") ? "Liked Tracks" : "Play Queue";
        pageTitle.setText(title);
        JButton[] buttons = {homeNav, libraryNav, likedNav, queueNav};
        String[] names = {"HOME", "LIBRARY", "LIKED", "QUEUE"};
        for (int i = 0; i < buttons.length; i++)
            buttons[i].setBackground(names[i].equals(page) ? new Color(85, 29, 44) : new Color(19, 19, 23));
    }

    private static JLabel label(String text, int size, Color color, int style) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", style, size));
        l.setForeground(color);
        return l;
    }
    private static JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setForeground(FG);
        b.setBackground(color);
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(11, 15, 11, 15));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
    private static JLabel sidebarLabel(String text) {
        return label(text, 14, FG, Font.BOLD);
    }
    private static void styleList(JList<Song> list) {
        list.setBackground(PANEL);
        list.setForeground(FG);
        list.setSelectionBackground(new Color(76, 37, 49));
        list.setSelectionForeground(FG);
        list.setFixedCellHeight(56);
        list.setFont(new Font("SansSerif", Font.PLAIN, 14));
        list.setBorder(new EmptyBorder(8, 8, 8, 8));
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean selected, boolean focused) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, selected, focused);
                if (value instanceof Song) {
                    Song s = (Song) value;
                    l.setText("  " + (s.isLiked() ? "♥  " : "♫  ") + s.getTitle()
                            + "   ·   " + s.getArtist() + (s.getMood() == null ? "" : "   [" + s.getMood() + "]"));
                }
                l.setBackground(selected ? new Color(76, 37, 49) : PANEL);
                l.setForeground(FG);
                l.setOpaque(true);
                l.setBorder(new EmptyBorder(4, 8, 4, 8));
                return l;
            }
        });
    }
    private static JPanel section(String title, JLabel count, JList<Song> list) {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(PANEL);
        panel.setBorder(new EmptyBorder(18, 16, 16, 16));
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(label(title, 19, FG, Font.BOLD), BorderLayout.WEST);
        count.setForeground(MUTED);
        count.setFont(new Font("SansSerif", Font.PLAIN, 12));
        header.add(count, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(PANEL);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    public Integer getSelectedSongID() {
        Song song = songList.getSelectedValue();
        return song == null ? null : song.getSongID();
    }
    public Mood getSelectedMood() { return selectedMood; }
    public void showQueueAndConfirm(String message) {
        navigate("QUEUE");
        actionFeedback.setText(message);
    }
    public void showActionFeedback(String message) {
        actionFeedback.setText(message);
    }
    public boolean isRepeatOneSelected() { return repeatOneCheck.isSelected(); }
    public void updateLibrary(List<Song> songs) {
        Integer selectedId = getSelectedSongID();
        libraryListModel.clear();
        int selectedIndex = -1;
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            libraryListModel.addElement(song);
            if (selectedId != null && selectedId.intValue() == song.getSongID()) selectedIndex = i;
        }
        if (selectedIndex >= 0) songList.setSelectedIndex(selectedIndex);
        libraryCount.setText(songs.size() + " tracks");
        homeLibraryCount.setText(songs.size() + " songs in your library");
        likedListModel.clear();
        for (Song song : songs) if (song.isLiked()) likedListModel.addElement(song);
        homeLikedCount.setText(likedListModel.size() + " liked songs");
    }
    public void updateQueue(List<Song> songs) {
        queueListModel.clear();
        for (Song song : songs) queueListModel.addElement(song);
        queueCount.setText(songs.size() + " waiting");
        homeQueueCount.setText(songs.size() + " songs waiting");
    }
    public void updateCurrentSong(Song song) {
        currentSongLabel.setText(song == null ? "Nothing playing" : song.getTitle());
        currentArtistLabel.setText(song == null ? "Choose a song and press Play Next" : song.getArtist());
    }
    public void updateRepeatOne(boolean enabled) { repeatOneCheck.setSelected(enabled); }
    public void onAddSong(Runnable action) { addButton.addActionListener(e -> action.run()); }
    public void onPlayNext(Runnable action) { playNextButton.addActionListener(e -> action.run()); }
    public void onGenerateMood(Runnable action) { moodButton.addActionListener(e -> action.run()); }
    public void onSaveMood(Runnable action) { saveMoodButton.addActionListener(e -> action.run()); }
    public void onClearMood(Runnable action) { clearMoodButton.addActionListener(e -> action.run()); }
    public void onToggleLike(Runnable action) { likeButton.addActionListener(e -> action.run()); }
    public void onShuffle(Runnable action) { shuffleButton.addActionListener(e -> action.run()); }
    public void onClearQueue(Runnable action) { clearQueueButton.addActionListener(e -> action.run()); }
    public void onRepeatOne(Runnable action) { repeatOneCheck.addActionListener(e -> action.run()); }
    public void showMessage(String message) { JOptionPane.showMessageDialog(frame, message); }
    public void show() { frame.setVisible(true); }
}
