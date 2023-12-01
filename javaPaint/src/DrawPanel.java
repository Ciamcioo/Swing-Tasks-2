import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private int figureType = 3;
    private Shape currentShape;
    private Rectangle selectionBox;
    private final ArrayList<Shape> shapeList = new ArrayList<>(), selectedShapes = new ArrayList<>();
    private boolean drawMode = false, selectMode = false;
    private Point2D startPoint, endPoint;

    /**
     * Constructor of DrawPanel class. As an arguments we pass width and height of frame.
     * @param width - width of the drawPanel
     * @param height - height of the drawPanel
     */
    public DrawPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black, 4, false));
        setFocusable(true);
        addFocusListener();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyboardListener();
        setVisible(true);
    }

    /**
     * Method sets up focusListener for drawPanel. After it regains focus it repaints the drawPanel.
     */
    private void addFocusListener() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }
        });
    }

    /**
     * Method which is override from the JPanel class. It supposes to draw shape that are stored in shapeList variable.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        for (Shape shape : shapeList)
            graphics.draw(shape);

        if (selectionBox != null) {
            graphics.setColor(new Color(0, 0, 255, 50));
            graphics.fill(selectionBox);
            graphics.setColor(Color.BLUE);
            graphics.draw(selectionBox);
        }
    }

    /**
     * Method determines what kind of shape we want to draw
     */
    private void determineShapeToDraw() {
        switch (figureType) {
            case ButtonConst.RECTANGLE -> currentShape = new Rectangle2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
            case ButtonConst.CIRCLE -> currentShape = new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getX() - startPoint.getX());
            case ButtonConst.LINE -> currentShape = new Line2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            case ButtonConst.ELLIPSE -> currentShape = new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
            default ->  currentShape = null;
        }
        shapeList.add(currentShape);
    }

    /**
     *  Method sets as currentShape a shape  which was selected by a user with mouse click.
     */
    private void selectShape(int x, int y) {
        for (Shape shape : shapeList) {
            if (shape.getBounds2D().contains(x,y)) {
                currentShape = shape;
                return;
            }
        }
        currentShape = null;
    }

    /**
     * Method is checks if selectedShapes is empty.
     * Determines vector of move for selectedShapes.
     * Invokes move method for every shape in selectedShapes.
     */
    private void moveShapesInTheBox() {
        if (selectedShapes.isEmpty())
            return;
        int deltaX = (int) (endPoint.getX() - startPoint.getX());
        int deltaY = (int) (endPoint.getY() - startPoint.getY());
        for (Shape shape : selectedShapes) {
            moveShape(shape, deltaX, deltaY);
        }
    }

    /**
     * Method moves shapes based on instance of the figure. It moves the shape accordingly to the deltaX and deltaY which is basically a vector of the move.
     * @param shape - pointer to the shape which should be moved
     * @param deltaX - shift of the shape in axis of X's
     * @param deltaY - shift of the shape in axis of Y's
     */
    private void moveShape(Shape shape, int deltaX, int deltaY) {
        if (shape instanceof RectangularShape recShape)
            recShape.setFrame(recShape.getX() + deltaX, recShape.getY() + deltaY, recShape.getWidth(), recShape.getHeight());
        else if (shape instanceof Line2D line)
            line.setLine(line.getX1() + deltaX, line.getY1() + deltaY, line.getX2() + deltaX, line.getY2() + deltaY);
        else  {
            Rectangle boundOfEllipse = currentShape.getBounds();
            currentShape = new Ellipse2D.Double(boundOfEllipse.getX() + deltaX, boundOfEllipse.getY() + deltaY, boundOfEllipse.getWidth(), boundOfEllipse.getHeight());
        }

    }

//----------------- KEYBOARD LISTENERS --------------

    /**
     * Wrapping method that will initialize keyListener for drawPanel. It invokes the handlingOfKeys(KeyEvent e) method that implements
     * logic for keyListener
     */
    private void addKeyboardListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                super.keyPressed(keyEvent);
                if (isSelectMode())
                    handlingOfKeys(keyEvent);
                repaint();
            }
        });
    }

    /**
     * Method that handel button press by user.
     * @param e - event of button being pressed
     */
    private void  handlingOfKeys(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_UP -> moveShape(currentShape,0,-5);
            case VK_DOWN -> moveShape(currentShape,0, 5);
            case VK_RIGHT -> moveShape(currentShape, 5,0);
            case VK_LEFT -> moveShape(currentShape,-5, 0);
            default -> System.out.println("Unsupported key was pressed");
        }
    }

//----------------- MOUSE LISTENERS -------------------

    /**
     * Listener for mouse click. If selectMode is true we check if the point of the click is inside any of the shapes.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (isSelectMode()) {
            selectShape(e.getX(), e.getY());
            requestFocusInWindow();
        }
    }

    /**
     * Method prints to the console information about the fact that mouse button is pressed.
     * On this event also we take to point of the mouse press as starting point for possible future shape.
     * If the select mode is on it starts to create rectangle of selection box.
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse button is pressed");
        startPoint = e.getPoint();
        if (isSelectMode()) {
            selectionBox = new Rectangle((Point) startPoint);
            selectedShapes.clear();
        }
    }

    /**
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released");
        endPoint = e.getPoint();
        if (isDrawMode()) {
            determineShapeToDraw();
        }
        else if (isSelectMode()) {
            moveShapesInTheBox();
            selectionBox = null;
            selectedShapes.clear();
        }
        repaint();
    }

    /**
     * Whenever e event happens to the console it's printed that we have entered DrawPanel.
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Entered DrawPanel");
    }

    /**
     * Whenever e event happens to the console it's printed that we have left DrawPanel.
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Exited DrawPanel");
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    /**
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isSelectMode()) {
            Point endPoint = e.getPoint();
            selectionBox.setBounds(
                    (int) Math.min(startPoint.getX(), endPoint.getX()),
                    (int) Math.min(startPoint.getY(), endPoint.getY()),
                    (int) Math.abs(endPoint.getX() - startPoint.getX()),
                    (int) Math.abs(endPoint.getY() - startPoint.getY())
            );
            selectedShapes.clear();
            for (Shape shape : shapeList) {
                if (selectionBox.intersects(shape.getBounds()))
                    selectedShapes.add(shape);
            }
        }
        else if (!isDrawMode() && e.getPoint().getX() < this.getWidth()) {
            endPoint = e.getPoint();
            figureType = ButtonConst.LINE;
            determineShapeToDraw();
            startPoint = endPoint;
        }
        repaint();
    }

    /**
     * Method is implementing the behavior whenever position of mouse roll is changed if the selectMode is true.
     * Changed position of mouse roll influence the size of the shape that is selected
     * @param e the event to be processed
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        if (isSelectMode()) {
            if (currentShape instanceof RectangularShape recShape)
                recShape.setFrame(recShape.getX(), recShape.getY(), recShape.getWidth() + rotation, recShape.getHeight() + rotation);
            else  {
                Rectangle boundOfEllipse = currentShape.getBounds();
                currentShape = new Ellipse2D.Double(boundOfEllipse.getX(), boundOfEllipse.getY(), boundOfEllipse.getWidth() + rotation, boundOfEllipse.getHeight() + rotation);
            }
        }
        repaint();
    }
//----------------- GETTERS AND SETTERS -------------

    public boolean isDrawMode() {
        return drawMode;
    }

    public void setDrawMode(boolean drawMode) {
        this.drawMode = drawMode;
    }

    public void setFigureType(int shape) {
        this.figureType = shape;
    }

    public boolean isSelectMode() {
        return selectMode;
    }

    /**
     * Method is changing the state of selectMode. If the selectMode is true then it send a request for focus in drawPanel
     */
    public void setSelectMode() {
        this.selectMode = !selectMode;
        if (isSelectMode())
            requestFocusInWindow();
        repaint();
    }
//-------------------------------------------------
}


