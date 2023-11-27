import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    private int wantedShape = 6;

    private Shape currentShape ;
    private ArrayList<Shape> shapeList = new ArrayList<>();
    private boolean drawMode = false;

    private Point2D startPoint, endPoint;

    public DrawPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createLineBorder(Color.black, 4, false));
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
        setVisible(true);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        if (wantedShape >= 0 && wantedShape <= 5) {
            determineShapeToDraw();
            graphics.draw(currentShape);
        }
    }

    private void determineShapeToDraw() {
        switch (wantedShape) {
            case ButtonConst.RECTANGLE -> currentShape = new Rectangle2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
            case ButtonConst.CIRCLE -> currentShape = new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getX() - startPoint.getX());
            case ButtonConst.ELLIPSE -> currentShape = new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
            default ->  currentShape = null;
        }
        if (currentShape != null)
            shapeList.add(currentShape);

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endPoint = e.getPoint();
        if (isDrawMode())
            paintComponent(getGraphics());
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

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    public void setCurrentShape(Shape currentShape) {
        this.currentShape = currentShape;
    }

    public ArrayList<Shape> getShapeList() {
        return shapeList;
    }

    public void setShapeList(ArrayList<Shape> shapeList) {
        this.shapeList = shapeList;
    }

    public boolean isDrawMode() {
        return drawMode;
    }

    public void setDrawMode(boolean drawMode) {
        this.drawMode = drawMode;
    }

    public int getWantedShape() {
        return wantedShape;
    }

    public void setWantedShape(int shape) {
        this.wantedShape = shape;
    }
}
