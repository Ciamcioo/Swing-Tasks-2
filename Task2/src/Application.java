import javax.swing.*;

public class Application {
    private static Window application;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            application = new Window();
            application.setVisible(true);
        });
    }
}
