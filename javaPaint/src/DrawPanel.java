import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    private int figureType = 3;
    private Shape currentShape ;
    private final ArrayList<Shape> shapeList = new ArrayList<>();
    private boolean drawMode = false, selectMode = false;
    private Point2D startPoint, endPoint;

    /**
     * Constructor of DrawPanel class. As an arguments we pass width and height of drawPanel.
     * @param width - width of the drawPanel
     * @param height - height of the drawPanel
     */
    public DrawPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black, 4, false));
        setFocusable(true);

        addFocListener();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyboardListener();

        setVisible(true);
    }

    private void addFocListener() {
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

    private void moveShape(int deltaX, int deltaY) {
        if (currentShape instanceof RectangularShape recShape) {
            recShape.setFrame(recShape.getX() + deltaX, recShape.getY() + deltaY, recShape.getWidth(), recShape.getHeight());
        }
        else if (currentShape instanceof Line2D)  {
            Line2D line = (Line2D) currentShape;
            line.setLine(line.getX1() + deltaX, line.getY1() + deltaY, line.getX2() + deltaX, line.getY2() + deltaY);
        }
        else  {
            Rectangle boundOfEllipse = currentShape.getBounds();
            currentShape = new Ellipse2D.Double(boundOfEllipse.getX() + deltaX, boundOfEllipse.getY() + deltaY, boundOfEllipse.getWidth(), boundOfEllipse.getHeight());
        }

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (isSelectMode()) {
            selectShape(e.getX(), e.getY());
            requestFocusInWindow();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endPoint = e.getPoint();
        if (isDrawMode()) {
            determineShapeToDraw();
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isDrawMode() && e.getPoint().getX() < this.getWidth()) {
            endPoint = e.getPoint();
            figureType = ButtonConst.LINE;
            determineShapeToDraw();
            repaint();
            startPoint = endPoint;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

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

    /**
     * Wrapping method that will initialize keyListener for drawPanel
     */
    private void addKeyboardListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                super.keyPressed(keyEvent);
                if (selectMode)
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
            case VK_UP -> moveShape(0,-5);
            case VK_DOWN -> moveShape(0, 5);
            case VK_RIGHT -> moveShape(5,0);
            case VK_LEFT -> moveShape(-5, 0);
            default -> System.out.println("Unsupported key was pressed");
        }
    }
}


