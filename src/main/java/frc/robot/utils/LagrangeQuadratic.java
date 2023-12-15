package frc.robot.utils;

public class LagrangeQuadratic {
    public Vector2[] vertices;
    public LagrangeQuadratic() {
        vertices = new Vector2[3];
    }
    double get(double x) {
        double result = 0.0;
        for (int i = 0; i < 3; ++i) {
            double y = vertices[i].y;
            for (int j = 0; j < 3; ++j) {
                if (j == i) continue;

                y *= (x - vertices[j].x) / (vertices[i].x - vertices[j].x);
            }

            result += y;
        }

        return result;
    }
}
