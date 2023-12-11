package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ShooterCMDBest extends CommandBase {
    public Shooter subsystem;

    public ShooterCMDBest(Shooter sub) {
        subsystem = sub;
        addRequirements(subsystem);
    }
    @Override

    public void execute() {
        subsystem.setFalconSpeed(Constants.Shooter.falconSpeed);
        subsystem.setNeoSpeed(Constants.Shooter.neoSpeed);
    }

    public void end(boolean interrupted) {
        subsystem.setFalconSpeed(0);
        subsystem.setNeoSpeed(0);
    }
}
