package Utils;

import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.StrictMath.max;

public class PaintShape {
    private static Graphics graphics;

    public static void setGraphics(Graphics g){
        graphics = g;
    }

    public static void paintPoint(Point point){
        paintPoint(point.x, point.y);
    }

    public static void paintPoint(int x, int y){
        graphics.fillRect(x, y, 2, 2);
    }

    private static void DDALine(int x1, int y1, int x2, int y2){
        double dx, dy, e, x, y;
        dx = x2 - x1;
        dy = y2 - y1;
        e = max(abs(dx), abs(dy))*5;
        dx /= e;
        dy /= e;
        x = x1;
        y = y1;
        for(int i = 0; i <= (int)e; i++){
            paintPoint((int)(x+0.5), (int)(y+0.5));
            x += dx;
            y += dy;
        }
    }

    public static void paintLine(Point pointStart, Point pointEnd){
        DDALine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
    }

    private static void paintAllPointOnCircle(Point midPoint, int dx, int dy){
        int x = midPoint.x;
        int y = midPoint.y;
        paintPoint(x+dx, y+dy);
        paintPoint(x+dx, y-dy);
        paintPoint(x-dx, y+dy);
        paintPoint(x-dx, y-dy);
        paintPoint(x+dy, y+dx);
        paintPoint(x+dy, y-dx);
        paintPoint(x-dy, y+dx);
        paintPoint(x-dy, y-dx);
    }

    private static void midPointCircle(Point midpoint, int R){
        int x, y, dx, dy, d;
        x = 0;
        y = R;
        d = 1 - R;
        dx = 3;
        dy = 5-R-R;
        paintAllPointOnCircle(midpoint, x, y);
        while(x < y){
            if(d < 0){
                d += dx;
                dx += 2;
                dy += 2;
                x++;
            }
            else{
                d += dy;
                dx += 2;
                dy += 4;
                x++;
                y--;
            }
            paintAllPointOnCircle(midpoint, x, y);
        }
    }

    public static void paintCircle(Point pointStart, Point pointEnd){
        int dx = (pointEnd.x - pointStart.x)/2;
        int dy = (pointEnd.y - pointStart.y)/2;
        int R = (int)pow((double)(dx * dx + dy * dy), 0.5);
        Point pointCircle = new Point((pointStart.x + pointEnd.x)/2, (pointStart.y + pointEnd.y)/2);
        midPointCircle(pointCircle, R);
    }

    public static void paintRect(Point pointStart, Point pointEnd){
        Point point1 = new Point(pointStart.x, pointEnd.y);
        Point point2 = new Point(pointEnd.x, pointStart. y);
        paintLine(pointStart, point1);
        paintLine(pointStart, point2);
        paintLine(point1, pointEnd);
        paintLine(point2, pointEnd);
    }

    private static void paintAllPointOnOval(Point pointOval, int dx, int dy){
        int x = pointOval.x;
        int y = pointOval.y;
        paintPoint(x + dx, y + dy);
        paintPoint(x + dx, y - dy);
        paintPoint(x - dx, y + dy);
        paintPoint(x - dx, y - dy);
    }

    public static void midPointOval(Point pointOval, int a, int b){
        double sqa = a*a;
        double sqb = b*b;
        double d = sqb + sqa*(-b+0.25);
        int x = 0;
        int y = b;
        paintAllPointOnOval(pointOval, x, y);
        while(sqb * (x + 1) < sqa * (y - 0.5)){
            if(d < 0){
                d += sqb * (2 * x + 3);
            }
            else{
                d += (sqb * (2 * x + 3) + sqa * (-2 * y + 2));
                y--;
            }
            x++;
            paintAllPointOnOval(pointOval, x, y);
        }
        d = (b * (x + 0.5)) * 2 + (a * (y - 1)) * 2 - (a * b) * 2;
        while(y > 0){
            if(d < 0){
                d += sqb * (2 * x + 2) + sqa * (-2 * y + 3);
                x++;
            }
            else{
                d += sqa *(-2 * y + 3);
            }
            y--;
            paintAllPointOnOval(pointOval, x, y);
        }
    }

    public static void paintOval(Point pointStart, Point pointEnd){
        int a = abs(pointEnd.x - pointStart.x)/2;
        int b = abs(pointEnd.y - pointStart.y)/2;
        Point pointOval = new Point((pointStart.x + pointEnd.x)/2, (pointStart.y + pointEnd.y)/2);
        midPointOval(pointOval, a, b);
    }
}
