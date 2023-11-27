import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Application {
    private final static int mutualPanelSizes = 5;
    private final JFrame paintFrame = new JFrame();
    private final JPanel buttonPanel = new JPanel();
    private DrawPanel drawPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Application();
        });
    }

    /**
     * Constructor for application class. Invokes series of command which set up application.
     */
    public Application() {
        paintFrameInitialization();
        buttonPanelInitialization();
        drawPanelInitialization();
        addComponentsToFrame();
        displayApplication();
    }

    /**
     * Method sets up application frame with default settings.
     */
    private void paintFrameInitialization() {
        paintFrame.setLayout(new BorderLayout());
        paintFrame.setTitle("Drawing Interface");
        paintFrame.setSize(500, 500);
        paintFrame.setResizable(false);
        paintFrame.setBackground(Color.GRAY);
        paintFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Method invokes adding every single component to application frame.
     */
    private void addComponentsToFrame() {
        paintFrame.add(buttonPanel, BorderLayout.EAST);
        paintFrame.add(drawPanel, BorderLayout.WEST);
    }

    /**
     * Method sets visibility of frame to true. Tantamount to displaying application to the user.
     */
    private void displayApplication() {
        paintFrame.setVisible(true);

    }

    private void buttonPanelInitialization() {
        buttonPanel.setPreferredSize(new Dimension(paintFrame.getWidth()/mutualPanelSizes, paintFrame.getHeight()));
        buttonPanel.setLayout(new GridLayout(0,1));
        for (int i = 0;  i < ButtonConst.NUMBER_OF_BUTTONS; i++)
            buttonPanel.add(generateButtons(i));
        buttonPanel.setVisible(true);
    }

    private JButton generateButtons(int buttonNumber) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(buttonPanel.getWidth(), buttonPanel.getHeight()));
        switch (buttonNumber) {
            case ButtonConst.RECTANGLE -> button.setText("Rectangle");
            case ButtonConst.CIRCLE -> button.setText("Circle");
            case ButtonConst.LINE -> button.setText("Line");
            case ButtonConst.ELLIPSE -> button.setText("Ellipse");
            case ButtonConst.SELECT -> button.setText("Select");
            case ButtonConst.CLEAR -> button.setText("Clear drawing board");
        }
        addListener(button);
        button.setVisible(true);
        return button;
    }
    private void addListener(JButton button) {
        button.addActionListener(e -> {
            switch (button.getText()) {
                case "Rectangle" -> {
                    drawPanel.setDrawMode(true);
                }
                case "Circle" -> {
                    drawPanel.setDrawMode(true);
                }
            }
        });
    }

    private void drawPanelInitialization() {
        drawPanel = new DrawPanel(paintFrame.getWidth()/mutualPanelSizes * 4, paintFrame.getHeight());
    }



    private static class ButtonConst {
        private static final int RECTANGLE = 0;
        private static final int CIRCLE = 1;
        private static final int LINE = 2;
        private static final int ELLIPSE = 3;
        private static final int SELECT = 4;
        private static final int CLEAR = 5;
        private static final int NUMBER_OF_BUTTONS = 6;
    }

}
