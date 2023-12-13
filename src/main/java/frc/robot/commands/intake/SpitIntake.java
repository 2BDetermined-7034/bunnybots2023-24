package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class SpitIntake extends CommandBase {
    Intake intake;
    double speed;
    Indexer indexer;

    public SpitIntake(Intake intake, Indexer indexer, double speed) {
        this.intake = intake;
        this.indexer = indexer;
        this.speed = speed;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute() {
        intake.setSpinSpeed(-speed);
        indexer.spitOut(0.25);
    }

    @Override
    public void end(boolean interrupted) {
        indexer.zeroCurrentObjects();
    }
}
