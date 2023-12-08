package frc.robot.utils;

import frc.robot.Constants;

public class VectorAngle {
    private double deadZone;
    private double previousAngle = Constants.VectorConstants.lastAngle;
    public VectorAngle() {
        this.deadZone = Constants.VectorConstants.unaliveZone;
    }
    public VectorAngle(double deadZone) {
        this.deadZone = deadZone;
    }

    public double getAngle(Vector2 vector) {
        if (vector.magnitude() > deadZone) {
            previousAngle = Math.atan2(vector.y, vector.x);
        }

        return previousAngle;
    }
    public void setDeadZone(double deadZone) {
        this.deadZone = deadZone;
    }
}
