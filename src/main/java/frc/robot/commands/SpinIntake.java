package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

import static frc.robot.Constants.Intake.*;

public class SpinIntake extends CommandBase {
    Intake intake;

    public SpinIntake(Intake intake, Intake.IntakePosition position) {
        this.intake = intake;
    }


    @Override
    public void execute() {
        intake.setSpinSpeed(spinMotorSpeed);

    }

    @Override
    public void end(boolean interrupted) {
        intake.setSpinSpeed(0);
    }
}
