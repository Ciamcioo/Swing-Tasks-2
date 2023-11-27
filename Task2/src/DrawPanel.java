import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener {
    private Shape currentShape = null;
    private final ArrayList<Shape> listOfRectangles = new ArrayList<>();
    private boolean isDrawFocus = false, isMousePressed = false, isSelectFocus = false;
    private Point startPoint;

    public DrawPanel(int width, int height) {
        setSize(width, height);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black, 4, false));
        addMouseMotionListener(this);
        addMouseListener(this);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (currentShape == null)
            return;
        Graphics2D graphics = (Graphics2D) g;
        if (currentShape instanceof Rectangle2D)
            graphics.setColor(Color.blue);
        else
            graphics.setColor(Color.red);
        graphics.draw(currentShape);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!isDrawFocus || isMousePressed)
            return;
        currentShape = new Line2D.Double(startPoint, e.getPoint());
        startPoint = e.getPoint();
        paintComponent(getGraphics());
        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isDrawFocus)
            isSelectFocus = isClickInSelectedArea(e);
        if (!isSelectFocus)
            isDrawFocus = !isDrawFocus;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
        if (!isSelectFocus) {
            double width = e.getX() - startPoint.x;
            double height = e.getY() - startPoint.y;
            currentShape = new Rectangle2D.Double(startPoint.x, startPoint.y, width, height);
            listOfRectangles.add(currentShape);
            paintComponent(getGraphics());
        }
        else {
            moveSelectedShape(e.getPoint());

        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true;
        startPoint = e.getPoint();
    }

    /**
     * Function is checking if click is in the selected are of rectangle.
     * If the statement is true, it assigns it to current shape.
     * @param e - even of mouse click
     * @return true if the click was inside rectangle, false if not
     */
    public boolean isClickInSelectedArea(MouseEvent e) {
        startPoint = e.getPoint();
        for (Shape shape : listOfRectangles)
            if (shape.contains(e.getPoint())) {
                currentShape = shape;
                return true;
            }
        return false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
    private void moveSelectedShape(Point2D endPoint) {
        Rectangle2D rectangle = (Rectangle2D) currentShape;
        rectangle.setRect(endPoint.getX(), endPoint.getY(), rectangle.getWidth(), rectangle.getHeight());

    }
}
