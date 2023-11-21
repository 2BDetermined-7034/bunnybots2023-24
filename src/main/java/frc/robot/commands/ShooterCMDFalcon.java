package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterCMDFalcon extends CommandBase {
    public Shooter subsystem;
    public double speed;

    public ShooterCMDFalcon(Shooter sub, double speed) {
        subsystem = sub;
        this.speed = speed;
        //addRequirements(subsystem);
        SmartDashboard.putNumber("Shooter Speed Falcon", 0);
    }
    @Override

    public void execute() {
        subsystem.activateShooter(SmartDashboard.getNumber("Shooter Speed Falcon", 0));
    }

    public void end(boolean interrupted) {
        subsystem.activateShooter(0);
    }
}
