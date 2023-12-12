package frc.robot.commands.shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.DoubleSolenoidSim;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.utils.Distance;

import java.util.function.DoubleSupplier;

public class ShooterLimelight extends CommandBase {
    public Shooter subsystem;
    public Indexer indexer;
    public SwerveSubsystem swerveSubsystem;
    public LimeLight limeLight;

    public DoubleSupplier x, y, rot;
    public PIDController pid;
    public double output;
    public boolean opposite;

    public ShooterLimelight(SwerveSubsystem swerveSubsystem, Shooter sub, Indexer indexer, LimeLight limeLight, DoubleSupplier x, DoubleSupplier y, DoubleSupplier rot) {
        this.swerveSubsystem = swerveSubsystem;
        this.limeLight = limeLight;
        subsystem = sub;
        this.indexer = indexer;

        this.x = x;
        this.y = y;
        this.rot = rot;

        addRequirements(subsystem, this.indexer);
    }
    @Override

    public void execute() {

        if(limeLight.isTargetAvailable()) {

            double tx = limeLight.getTargetOffsetX();
            pid = new PIDController(0.02, 000.00, 0.00000);
            pid.setTolerance(5);

            //Second term is for keeping the robot ahead of the target to account for shooter delay
            pid.setSetpoint(0 + swerveSubsystem.getChassisSpeeds().omegaRadiansPerSecond * 0.001);
            double currentAngle = swerveSubsystem.getAngle().getDegrees();

            output = -pid.calculate(tx);

            double targetVert = limeLight.getVert();
            double targetHor = limeLight.getHor();
            double aspectRatio = limeLight.calculateAspectRatio(targetHor, targetVert);

            opposite = oppositeTeam(aspectRatio, false);
        }
        double distance = limeLight.getTargetDistance();
        double eq = Math.abs( ((Math.pow(distance - 1, 3)) / 2) + 2 );

        if(opposite && limeLight.isTargetAvailable()) {
            /* Yippee target is found and it's not friendly fire */
            swerveSubsystem.drive(new Translation2d(y.getAsDouble(), x.getAsDouble()), output, true, true);

            subsystem.setFalconSpeed(Constants.Shooter.falconSpeed);
            // TODO: Tune the spin-up voltage
            if(subsystem.getActualFalconVoltage() >= 3){
                subsystem.setNeoSpeed(Constants.Shooter.neoSpeed);
                indexer.run(1);
            }
        } else {
            /* manual control if no target */
            swerveSubsystem.drive(new Translation2d(y.getAsDouble(), x.getAsDouble()), rot.getAsDouble(), true, true);
        }
    }

    public void end(boolean interrupted) {
        subsystem.setFalconSpeed(0);
        subsystem.setNeoSpeed(0);
    }

    /**
     *
     * @param aspectRatio
     * @param override
     * @return
     */
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
