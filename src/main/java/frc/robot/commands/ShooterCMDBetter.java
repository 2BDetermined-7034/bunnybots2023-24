package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterCMDBetter extends CommandBase {
    public Shooter subsystem;
    public double speed;

    public ShooterCMDBetter(Shooter sub, double speed) {
        subsystem = sub;
        this.speed = speed;
        addRequirements(subsystem);
        SmartDashboard.putNumber("Shooter Speed Falcon", 0.25);
        SmartDashboard.putNumber("Shooter Speed Neo", 0.25);
    }
    @Override

    public void execute() {
        subsystem.activateShooter(SmartDashboard.getNumber("Shooter Speed Falcon", 0));
        subsystem.neoShooter(SmartDashboard.getNumber("Shooter Speed Neo", 0));
    }

    public void end(boolean interrupted) {
        subsystem.activateShooter(0);
        subsystem.neoShooter(0);
    }
}
