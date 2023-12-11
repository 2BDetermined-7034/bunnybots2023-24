package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class RaiseIntake extends CommandBase {
    Intake intake;
    Intake.IntakePosition position;

    public RaiseIntake(Intake intake, Intake.IntakePosition position) {
        this.intake = intake;
        this.position = position;
    }


    @Override
    public void execute() {
        //intake.setLiftSpeed(SmartDashboard.getNumber("Lift Speed", 0));
        //intake.setSpinSpeed(SmartDashboard.getNumber("Spin Speed", 0));

        intake.setGoalPosition(position);
    }
}
