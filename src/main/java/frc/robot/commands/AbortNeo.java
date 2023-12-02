package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AbortNeo extends CommandBase {
    public Shooter subsystem;
    public double speed;

    public AbortNeo(Shooter sub, double speed) {
        subsystem = sub;
        this.speed = speed;
        addRequirements(subsystem);
        SmartDashboard.putNumber("Shooter Speed Neo", 0);
    }
    @Override

    public void execute() {
        subsystem.neoShooter(0);
    }
}
