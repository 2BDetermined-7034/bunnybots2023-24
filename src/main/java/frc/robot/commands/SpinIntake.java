package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

import static frc.robot.Constants.Intake.*;

public class SpinIntake extends CommandBase {
    IntakeSubsystem intake;
    public SpinIntake(IntakeSubsystem intake) {
        this.intake = intake;
    }

    @Override
    public void execute() {
        intake.setLiftSpeed(liftMotorSpeed);
        intake.setSpinSpeed(spinMotorSpeed);
    }
}
