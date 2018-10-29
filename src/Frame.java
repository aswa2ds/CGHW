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

    enum STATUSENUM { DRAWLINE, DRAWCIRCLE, DRAWRECT, DRAWMOUSE};
    private STATUSENUM statusenum;

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
                        statusenum = STATUSENUM.DRAWMOUSE;
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
        addButton(3, "./pics/mouse.png");
        addPanel();
    }
}