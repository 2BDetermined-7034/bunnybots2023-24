package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

import static frc.robot.Constants.Intake.*;

public class SpinIntake extends CommandBase {
    Intake intake;
    double speed;
    Indexer indexer;

    public SpinIntake(Intake intake, Indexer indexer, double speed) {
        this.intake = intake;
        this.indexer = indexer;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        intake.setGoalPosition(Intake.IntakePosition.LOWERED);
    }

    @Override
    public void execute() {
        intake.setSpinSpeed(speed);
        if(indexer.getCurrentObjects() < Constants.IndexerConstants.maxBalls) {
            indexer.run(0.25);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.setSpinSpeed(0);
        intake.setGoalPosition(Intake.IntakePosition.RAISED);
        indexer.run(0);
    }
}
