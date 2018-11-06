import Utils.PaintShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Frame extends JFrame {
    private Dimension dimensionOfScreen = Toolkit.getDefaultToolkit().getScreenSize();
    private Dimension dimensionOfWindow = new Dimension((int)(dimensionOfScreen.getWidth()*2/3), (int)(dimensionOfScreen.getHeight()*3/4));
    private int buttonSideLength = (int)(dimensionOfWindow.getHeight()/10);
    private int panelWidth = (int)(dimensionOfWindow.getWidth() - buttonSideLength);

    enum STATUSENUM { DRAWLINE, DRAWCIRCLE, DRAWRECT, DRAWPENCIL, DRAWPOINT, DRAWOVAL, WAIT};
    private STATUSENUM statusenum = STATUSENUM.WAIT;

    Point pointStart;
    Point pointEnd;
    Point pointLast;

    private void addButton(int index, String filePath){
        URL url = getClass().getResource(filePath);
        System.out.println(url);
        ImageIcon icon = new ImageIcon(url);
        JButton jButton = new JButton(icon);
        jButton.setBounds(0, buttonSideLength* index, buttonSideLength-1, buttonSideLength-1);
        jButton.addMouseListener(new MouseAdapter() {      //adpter
            @Override
            public void mouseClicked(MouseEvent e) {
                switch(index) {
                    case 0:
                        statusenum = STATUSENUM.DRAWLINE;
                        break;
                    case 1:
                        statusenum = STATUSENUM.DRAWCIRCLE;
                        break;
                    case 2:
                        statusenum = STATUSENUM.DRAWRECT;
                        break;
                    case 3:
                        statusenum = STATUSENUM.DRAWPENCIL;
                        break;
                    case 4:
                        statusenum = STATUSENUM.DRAWPOINT;
                        break;
                    case 5:
                        statusenum = STATUSENUM.DRAWOVAL;
                        break;
                }
            }
        });
        add(jButton);
    }

    private void addPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setBounds(buttonSideLength, 0, panelWidth, (int)dimensionOfWindow.getHeight());
        jPanel.setBackground(Color.white);
        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                super.mouseEntered(e);
                Cursor cursor;
                switch(statusenum){
                    case WAIT:
                        cursor = new Cursor(Cursor.HAND_CURSOR);
                        break;
                    default:
                        cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
                        break;
                }
                JPanel panel = (JPanel)e.getSource();
                panel.setCursor(cursor);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                pointStart = new Point(e.getX(), e.getY());
                pointLast = pointStart;
            }
            @Override
            public void mouseReleased(MouseEvent e){
                super.mouseReleased(e);
                pointEnd = new Point(e.getX(), e.getY());
                Graphics graphics = ((JPanel)e.getSource()).getGraphics();
                PaintShape.setGraphics(graphics);
                switch(statusenum){
                    case DRAWPOINT:
                        PaintShape.paintPoint(pointEnd);
                        break;
                    case DRAWLINE:
                        PaintShape.paintLine(pointStart, pointEnd);
                        break;
                    case DRAWCIRCLE:
                        PaintShape.paintCircle(pointStart, pointEnd);
                        break;
                    case DRAWRECT:
                        PaintShape.paintRect(pointStart, pointEnd);
                        break;
                    case DRAWOVAL:
                        PaintShape.paintOval(pointStart, pointEnd);
                }
            }
        });
        jPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
            @Override
            public void mouseDragged(MouseEvent e){
                super.mouseDragged(e);
                Graphics graphics = ((JPanel)e.getSource()).getGraphics();
                //graphics.setXORMode(Color.white);
                PaintShape.setGraphics(graphics);
                //if(statusenum != STATUSENUM.WAIT)
                  //  PaintShape.paintLine(pointStart, pointLast);
                pointEnd = new Point(e.getX(), e.getY());
                switch (statusenum){
                    case DRAWPENCIL:
                        PaintShape.paintLine(pointLast, pointEnd);
                        break;
                    /*case DRAWLINE:
                        PaintShape.paintLine(pointStart, pointEnd);
                        break;
                    case DRAWCIRCLE:
                        PaintShape.paintCircle(pointStart, pointEnd);
                        break;*/
                }
                pointLast = pointEnd;
            }
        });
        add(jPanel);
    }

    Frame(){
        setTitle("mySketchPed");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(dimensionOfWindow);
        setLocationRelativeTo(null);
        Container container = getContentPane();
        container.setLayout(null);

        addButton(0, "./pics/line.png");
        addButton(1, "./pics/circle.png");
        addButton(2, "./pics/rectangle.png");
        addButton(3, "./pics/pencil.png");
        addButton(4, "./pics/point.png");
        addButton(5, "./pics/oval.png");
        addPanel();
    }
}