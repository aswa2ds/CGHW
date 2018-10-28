import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private Dimension dimensionOfScreen = Toolkit.getDefaultToolkit().getScreenSize();
    Frame(){
        setTitle("mySketchPed");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize((int)dimensionOfScreen.getWidth()/2, (int)dimensionOfScreen.getHeight()/2);
        setLocationRelativeTo(null);
    }
}