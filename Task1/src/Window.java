import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Window {
    private final JFrame frame = new JFrame();
    private final JPanel coordinatePanel = new JPanel();
    private final JTextField fieldWithResults = new JTextField();

    public Window() {
        fieldWithResultsInitialization();
        coordinatePanelInitialization();
        frameInitialization();
    }
    private void frameInitialization() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setBackground(Color.lightGray);
        frame.setTitle("Coordinates of mouse click");
        frame.add(coordinatePanel);
        frame.add(fieldWithResults, BorderLayout.NORTH);
        frame.setVisible(true);
    }
    private void coordinatePanelInitialization() {
        coordinatePanel.setSize(500,400);
        coordinatePanel.setLayout(new BorderLayout());
        coordinatePanel.setBackground(Color.CYAN);
        coordinatePanel.setBorder(BorderFactory.createLineBorder(Color.black, 4, false));
        initializationMouseListener();
    }
    private void fieldWithResultsInitialization() {
        fieldWithResults.setSize(500,100);
        fieldWithResults.setBackground(Color.white);
        fieldWithResults.setColumns(5);
    }
    private void initializationMouseListener() {
        coordinatePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point pointOfMouseClick = e.getPoint();
                fieldWithResults.setText("X: " + pointOfMouseClick.x + ", Y: " + pointOfMouseClick.y);
            }
        });
    }




}
