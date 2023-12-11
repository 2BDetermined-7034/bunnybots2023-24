package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

import static frc.robot.Constants.Intake.*;

public class SpinIntake extends CommandBase {
    Intake intake;
    double speed;

    public SpinIntake(Intake intake, double speed) {
        this.intake = intake;
        this.speed = speed;
    }


    @Override
    public void execute() {
        intake.setSpinSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setSpinSpeed(0);
    }
}
