// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class Shooter {
    public static final int neoID = 1;
    public static final int Motor1ID = 1;
    public static final int Motor2ID = 2;
    public static final double falconSpeed = -0.5;
    public static final double neoSpeed = 0.25;
    public static final double shooterRPSCutoffScalar = 85;
  }
  public static class Intake {
    public static final int liftMotorID = 2;
    public static final int spinMotor1ID = 13;
    public static final int spinMotor2ID = 14;

    public static final double spinMotorSpeed = -0.20;

    //TODO encoder positions for intake
    public static final double homePosition = 0;
    public static final double activePosition = -0.87;
    public static final double positionTolerance = 0.1;

  }
public static class VectorConstants{
    public static double lastAngle = 0.0;
    public static double unaliveZone = .75;
}
  public static class IndexerConstants {
    public static final int frontMotorID = 3;
    public static final int backMotorID = 8;
    public static final int digitalInputFront = 0;
    public static final int digitalInputBack = 1;
    public static final int maxBalls = 5;
  }

  public static class Vision {
    public static final double limeLightMountAngleDegrees = 0;
    public static final double goalHeighInches = 53.75;
    public static final double limeligtLensHeighInches = 0;

    public static final double limeLightHorizontalFOV = 59.6;
    public static final double limeLightVerticalFOV = 49.7;
    public static final double distanceScale = 1.0;
  }
}
