package Utils;

import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.StrictMath.max;

public class PaintShape {
    public static void paintPoint(Graphics graphics, Point point){
        paintPoint(graphics, point.x, point.y);
    }

    public static void paintPoint(Graphics graphics, int x, int y){
        graphics.fillOval(x, y, 2, 2);
    }

    private static void DDALine(Graphics graphics, int x1, int y1, int x2, int y2){
        double dx, dy, e, x, y;
        dx = x2 - x1;
        dy = y2 - y1;
        e = max(abs(dx), abs(dy));
        dx /= e;
        dy /= e;
        x = x1;
        y = y1;
        for(int i = 0; i <= (int)e; i++){
            paintPoint(graphics, (int)(x+0.5), (int)(y+0.5));
            x += dx;
            y += dy;
        }
    }

    public static void paintLine(Graphics graphics, Point pointStart, Point pointEnd){
        DDALine(graphics, pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
    }
}
