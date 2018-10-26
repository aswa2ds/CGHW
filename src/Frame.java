import javax.swing.*;
import java.awt.*;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.*;
import java.net.URL;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;


public class Frame extends JFrame{
    Dimension dimension = new Dimension(800, 600);
    private int lineBeginX = 0, lineEndX = 0, lineBeginY = 0, lineEndY = 0;
    private int lastX = 0, lastY = 0;
    private int upperLeftX, upperLeftY;
    enum STATUSENUM { DRAWLINE, DRAWCIRCLE, DRAWRECT, DRAWMOUSE};
    private STATUSENUM statusenum;

    private void addButton(int index, String filePath){
        URL url = getClass().getResource(filePath);
        System.out.println(url);
        ImageIcon icon = new ImageIcon(url);
        JButton jButton = new JButton(icon);
        jButton.setBounds(0, 50 * index, 50, 50);
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
                        statusenum = STATUSENUM.DRAWMOUSE;
                        break;
                }
            }
        });
        add(jButton);
    }

    private void addPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setBounds(50, 0, 750, 600);
        jPanel.setBackground(new Color(255, 255, 255));
        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lineBeginX = e.getX();
                lineBeginY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // System.out.println("release panel");

                Graphics graphics = jPanel.getGraphics();
                graphics.setColor(new Color(255, 255, 255));
                graphics.drawLine(lineBeginX, lineBeginY, lineEndX, lineEndY);
                lineEndX = e.getX();
                lineEndY = e.getY();
                graphics.setColor(new Color(0, 0, 0));
                if(statusenum == STATUSENUM.DRAWLINE) {
                    graphics.drawLine(lineBeginX, lineBeginY, lineEndX, lineEndY);
                }
                if(statusenum == STATUSENUM.DRAWCIRCLE) {
                    upperLeftX = min(lineBeginX, lineEndX);
                    upperLeftY = min(lineBeginY, lineEndY);
                    graphics.drawOval(upperLeftX, upperLeftY, abs(lineEndX - lineBeginX), abs(lineBeginY - lineEndY));
                }
                if(statusenum == STATUSENUM.DRAWRECT){
                    upperLeftX = min(lineBeginX, lineEndX);
                    upperLeftY = min(lineBeginY, lineEndY);
                    graphics.drawRect(upperLeftX, upperLeftY, abs(lineEndX - lineBeginX), abs(lineBeginY - lineEndY));
                }
            }
        });
        jPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Graphics graphics = jPanel.getGraphics();
                graphics.setXORMode(Color.white);
                switch(statusenum){
                    case DRAWLINE:
                        graphics.drawLine(lineBeginX, lineBeginY, lineEndX, lineEndY);
                        lineEndX = e.getX();
                        lineEndY = e.getY();
                        graphics.drawLine(lineBeginX, lineBeginY, lineEndX, lineEndY);
                        break;
                    case DRAWRECT:
                        upperLeftX = min(lineBeginX, lineEndX);
                        upperLeftY = min(lineBeginY, lineEndY);
                        graphics.drawRect(upperLeftX, upperLeftY, abs(lineEndX - lineBeginX), abs(lineBeginY - lineEndY));
                        lineEndX = e.getX();
                        lineEndY = e.getY();
                        upperLeftX = min(lineBeginX, lineEndX);
                        upperLeftY = min(lineBeginY, lineEndY);
                        graphics.drawRect(upperLeftX, upperLeftY, abs(lineEndX - lineBeginX), abs(lineBeginY - lineEndY));
                        break;
                    case DRAWCIRCLE:
                        int upperLeftX = min(lineBeginX, lineEndX);
                        int upperLeftY = min(lineBeginY, lineEndY);
                        graphics.drawOval(upperLeftX, upperLeftY, abs(lineEndX - lineBeginX), abs(lineBeginY - lineEndY));
                        lineEndX = e.getX();
                        lineEndY = e.getY();
                        upperLeftX = min(lineBeginX, lineEndX);
                        upperLeftY = min(lineBeginY, lineEndY);
                        graphics.drawOval(upperLeftX, upperLeftY, abs(lineEndX - lineBeginX), abs(lineBeginY - lineEndY));
                        break;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                lineBeginX = e.getX();
                lineBeginY = e.getY();
                lineEndX = e.getX();
                lineEndY = e.getY();
            }
        });
        add(jPanel);
    }

    public Frame(){
        setSize(dimension);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Container container = getContentPane();
        container.setLayout(null);
        addPanel();
        addButton(0, "./pics/line.png");
        addButton(1, "./pics/circle.png");
        addButton(2, "./pics/rectangle.png");
        addButton(3, "./pics/mouse.png");
    }
}