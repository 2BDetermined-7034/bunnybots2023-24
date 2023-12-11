package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

/**
 * This was an idea we made long ago with the shooter's <i>NEO Motor</i>, because the motor during
 * testing was very annoying, it kept stopping over and over but only working at specific times.
 * <p>
 * I'm just doing these paragraphs for info.
 *
 */
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
        subsystem.setNeoSpeed(0);
    }
}
