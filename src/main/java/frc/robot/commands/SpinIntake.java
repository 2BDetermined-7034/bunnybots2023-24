package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

import static frc.robot.Constants.Intake.*;

public class SpinIntake extends CommandBase {
    IntakeSubsystem intake;
    boolean IntakeActive;

    public SpinIntake(IntakeSubsystem intake, boolean ActiveIntake) {
        this.intake = intake;
        IntakeActive = ActiveIntake;

                    }
    }


    @Override
    public void execute() {
        //intake.setLiftSpeed(SmartDashboard.getNumber("Lift Speed", 0));
        //intake.setSpinSpeed(SmartDashboard.getNumber("Spin Speed", 0));

        if (IntakeActive){
            intake.setGoalPosition(intake.activePosition);

        } else {
            intake.setGoalPosition(intake.homePosition);
        }
    }
}
