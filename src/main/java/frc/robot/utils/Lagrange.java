package frc.robot.utils;

public class Lagrange {
    public Vector2[] vertices;
    public Lagrange() {}
    public double get(double x) {
        double result = 0.0;
        for (int i = 0; i < vertices.length; ++i) {
            double y = vertices[i].y;
            for (int j = 0; j < vertices.length; ++j) {
                if (j == i) continue;

                y *= (x - vertices[j].x) / (vertices[i].x - vertices[j].x);
            }

            result += y;
        }

        return result;
    }
}
