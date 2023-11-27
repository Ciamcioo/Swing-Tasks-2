import javax.swing.*;
import java.awt.*;

public class Window {
    private final JFrame frame = new JFrame();
    private JPanel panel;
    public Window() {
        setPanel();
        setFrame();
    }

    /**
     * Initialization of frame parameters
     */
    private void setFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Paint in Swing");
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
    }

    /**
     * Function initialize panel which is build based on mine class
     */
    private void setPanel() {
        panel = new DrawPanel(frame.getWidth(), frame.getHeight());
    }

    /**
     * Turning on the visibility of the frame
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
