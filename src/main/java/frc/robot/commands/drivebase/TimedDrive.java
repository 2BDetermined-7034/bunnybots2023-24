package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.ControllerDrive;
import frc.robot.subsystems.SwerveSubsystem;

import java.util.function.DoubleSupplier;

public class TimedDrive extends ControllerDrive {

    Timer timer = new Timer();

    public TimedDrive(SwerveSubsystem swerveSubsystem, DoubleSupplier x, DoubleSupplier y, DoubleSupplier rawAxis, boolean isOpenLoop) {
        super(swerveSubsystem, x, y, rawAxis, isOpenLoop);
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(2.5);
    }

    @Override
    public void end(boolean interrupted) {
        swerveSubsystem.stop();

    }
}
