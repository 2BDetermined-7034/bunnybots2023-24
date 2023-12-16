package frc.robot.commands.shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.utils.Lagrange;
import frc.robot.utils.LagrangeQuadratic;
import frc.robot.utils.SubsystemLogging;
import frc.robot.utils.Vector2;

import java.util.function.DoubleSupplier;

public class ShooterCMDBest extends CommandBase implements SubsystemLogging {
    public Shooter subsystem;
    public Indexer indexer;
    Lagrange interp = new Lagrange();
    LimeLight limelight;
    Timer timer;
    PIDController pid;
    SwerveSubsystem swerveSubsystem;
    double output;
    boolean opposite;
    DoubleSupplier x,y, rot;

    public ShooterCMDBest(Shooter sub, Indexer indexer, LimeLight limeLight, SwerveSubsystem swerveSubsystem, DoubleSupplier x, DoubleSupplier y, DoubleSupplier rot) {
        subsystem = sub;
        this.limelight = limeLight;
        this.swerveSubsystem = swerveSubsystem;
        this.indexer = indexer;
        this.x = x;
        this.y = y;
        this.rot = rot;
        addRequirements(subsystem, this.indexer, swerveSubsystem);
        interp.vertices = new Vector2[3];

        interp.vertices[0] = new Vector2(5.0, 0.7);
        interp.vertices[1] = new Vector2(15.0, 0.57);
        interp.vertices[2] = new Vector2(20.0, 0.6);

        limelight = new LimeLight();

        timer = new Timer();
        SmartDashboard.putNumber("Target Power", 0);
    }

    @Override
    public void initialize(){
        timer.reset();
    }

    @Override
    public void execute() {
        //subsystem.setFalconSpeed(Constants.Shooter.falconSpeed);
        //SmartDashboard.getNumber("ShooterSpeed", 0)
        double distance = limelight.getTargetDistance();
        double targetPower = interp.get(distance / 12.0);
        double cutoff = 25.0 * targetPower + 5;

        //double targetPower = SmartDashboard.getNumber("Target Power", 0.5);

        subsystem.setFalconTargetVelocity(cutoff);

        log("Distance", distance);
        log("Target Power", targetPower);
        log("Spinup Target", cutoff);

        if(limelight.isTargetAvailable()) {

            double tx = limelight.getTargetOffsetX();
            pid = new PIDController(0.3, 000.00, 0.00000);
            pid.setTolerance(5);

            //Second term is for keeping the robot ahead of the target to account for shooter delay
//            pid.setSetpoint(0 + swerveSubsystem.getChassisSpeeds().omegaRadiansPerSecond * 0.001);
            pid.setSetpoint(0);

            double currentAngle = swerveSubsystem.getAngle().getDegrees();

            output = -pid.calculate(tx);

            double targetVert = limelight.getVert();
            double targetHor = limelight.getHor();
            double aspectRatio = limelight.calculateAspectRatio(targetHor, targetVert);

            opposite = oppositeTeam(aspectRatio, false);


            log("PID output", output);

            if(opposite) {
                /* Yippee target is found and it's not friendly fire */
                swerveSubsystem.drive(new Translation2d(y.getAsDouble(), x.getAsDouble()), output, true, false);

                if(subsystem.getActualFalconSpeed() >= cutoff - 0.5 && !timer.hasElapsed(0.1)) {
                    timer.start();
                    subsystem.setNeoSpeed(Constants.Shooter.neoSpeed);
                    indexer.run(0.3);
                } else {
                    indexer.run(0);
                    subsystem.setNeoSpeed(0);
                    if (timer.hasElapsed(1)) {

                        timer.stop();
                        timer.reset();
                    }
                }
                }
            } else {
                /* manual control if no target */
                swerveSubsystem.drive(new Translation2d(y.getAsDouble(), x.getAsDouble()), rot.getAsDouble(), true, false);
            }

        }




    public void end(boolean interrupted) {
        subsystem.setFalconSpeed(0);
        subsystem.setNeoSpeed(0);
        indexer.run(0);
        timer.stop();
        timer.reset();
    }


    private static boolean oppositeTeam(double aspectRatio, boolean override) {
        boolean target = false;

        if(override) {
            target = true;
            return target;
        }

        switch(DriverStation.getAlliance()) {
            case Red:
                // target blue
                // target = aspectRatio > 3.5;
                target = true;
                break;

            case Blue:
                // target red
                // target = aspectRatio < 3.4;
                target = true;
                break;

        }
        return target;
    }
}
