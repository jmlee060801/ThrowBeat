import javax.swing.SwingUtilities;

public class ThrowBeatApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThrowBeatModel model = new ThrowBeatModel();
            ThrowBeatView view = new ThrowBeatView();
            new ThrowBeatController(model, view);
            view.show();
        });
    }
}
