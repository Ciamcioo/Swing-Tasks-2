import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_UP;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    private int actionType = 6;
    private Shape currentShape ;
    private ArrayList<Shape> shapeList = new ArrayList<>();
    private boolean drawMode = false, selectMode = false;
    private Point2D startPoint, endPoint;

    public DrawPanel(int width, int height) {
        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createLineBorder(Color.black, 4, false));
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyboardListener();
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        if (actionType >= 0 && actionType <= 4) {
            determineShapeToDraw();
            graphics.draw(currentShape);
        }
    }
    private void determineShapeToDraw() {
        switch (actionType) {
            case ButtonConst.RECTANGLE -> currentShape = new Rectangle2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
            case ButtonConst.CIRCLE -> currentShape = new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getX() - startPoint.getX());
            case ButtonConst.LINE -> {}
            case ButtonConst.ELLIPSE -> currentShape = new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
            case ButtonConst.SELECT -> {

            }
            default ->  currentShape = null;
        }
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
        else if (isSelectMode())
            selectComponent();

    }
    private void selectComponent() {
        for (Shape shape : shapeList)
            if(shape.contains(startPoint))
                currentShape = shape;
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
            actionType = ButtonConst.LINE;
            currentShape = new Line2D.Double(startPoint, e.getPoint());
            startPoint = e.getPoint();
            paintComponent(getGraphics());
        }
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

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int shape) {
        this.actionType = shape;
    }

    public boolean isSelectMode() {
        return selectMode;
    }

    public void setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
    }

    private void addKeyboardListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case VK_UP -> System.out.println("no spoko");
                }
            }
        });
    }
}
