package frc.robot.utils;

import edu.wpi.first.math.geometry.Translation2d;

import java.util.ArrayList;

public class RationalInterp {
    public RationalInterp() {}

    /*
    PASS POINTS IN ASCENDING X COORDINATES OR ELSE!!!
     */
    public RationalInterp(ArrayList<Translation2d> points) {
        this.points = points;
    }

    double get(double x) {
        double result = 0.0;

        for (int i = 0; i < points.size() - 1; ++i) {
            if (x < points.get(i + 1).getX()) {
                Vector2 a = new Vector2(points.get(i));
                Vector2 b = new Vector2(points.get(i + 1));

                a.y = 1.0 / a.y;
                b.y = 1.0 / b.y;

                result = x - a.x;
                result *= (b.y - a.y) / (b.x - a.x);
                result += a.y;

                result = 1.0 / result;

                break;
            }
        }

        return result;
    }

    public ArrayList<Translation2d> points;
}
