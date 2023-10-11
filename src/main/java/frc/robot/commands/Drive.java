package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBaseHenryE;

public class Drive extends CommandBase{
    DriveBaseHenryE driveBase;
    double left;

    public Drive(DriveBaseHenryE driveBase, double left) {
        this.driveBase = driveBase; 
        this.left = left;
    }

    @Override
    public void execute() {
        driveBase.drive(() -> left, () -> 0.0);
    }
    
}
