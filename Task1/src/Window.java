import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {
    private final JFrame frame = new JFrame();
    private final JPanel coordinatePanel = new JPanel();
    private final JTextField fieldWithResults = new JTextField();

    /**
     * Default constructor for Window Object
     */
    public Window() {
        setsFieldWithResult();
        setCoordinationPanel();
        setFrame();
    }
    /**
     * Function sets up default Jframe for Window Object. It also adds to JPanel and JTextField to frame.
     */
    private void setFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setBackground(Color.lightGray);
        frame.setTitle("Coordinates of mouse click");
        frame.add(coordinatePanel);
        frame.add(fieldWithResults, BorderLayout.NORTH);
    }
    /**
     * Function sets up coordinationPanel which is included in JFrame object. It also initializes
     * mouse click listener for this JPanel object.
     */

    private void setCoordinationPanel() {
        coordinatePanel.setSize(500,400);
        coordinatePanel.setLayout(new BorderLayout());
        coordinatePanel.setBackground(Color.CYAN);
        coordinatePanel.setBorder(BorderFactory.createLineBorder(Color.black, 4, false));
        initializationMouseListener();
    }

    /**
     *  Function sets up JTextField object in which will be stored the result of button press in range of JPanel.
     *  Method is also invoking another method which name is 'initializationKeyListener'
     */
    private void setsFieldWithResult() {
        fieldWithResults.setSize(500,100);
        fieldWithResults.setBackground(Color.white);
        fieldWithResults.setColumns(5);
        initializationKeyListener();

    }

    /**
     *  Function initializes MouseListener for coordinatePanel. On click of the method from MouseAdapter class gets the point
     *  of mouse. Later the coordinates will be added as a text to fieldWithResult object, firstly getting the old text from the object.
     */
    private void initializationMouseListener() {
        coordinatePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point pointOfMouseClick = e.getPoint();
                fieldWithResults.setText(fieldWithResults.getText() + "X: " + pointOfMouseClick.x + ", Y: " + pointOfMouseClick.y + " ");
            }
        });
    }

    /**
     * Method initializes a key listener for fieldWithResult component. Whenever the enter key is pressed the test field is cleared out
     */
    private void initializationKeyListener() {
        fieldWithResults.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    fieldWithResults.setText("");
            }
        });
    }

    /**
     * Method enables changing the state of visibility of the Frame
     * @param visibilityOfFrame - argument that passes wanted visibility of main frame
     */
    public void setVisible(boolean visibilityOfFrame) {
        frame.setVisible(visibilityOfFrame);
    }
}
