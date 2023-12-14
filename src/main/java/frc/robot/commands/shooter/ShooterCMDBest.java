package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;

public class ShooterCMDBest extends CommandBase {
    public Shooter subsystem;
    public Indexer indexer;

    public ShooterCMDBest(Shooter sub, Indexer indexer) {
        subsystem = sub;
        this.indexer = indexer;
        addRequirements(subsystem, this.indexer);
    }
    @Override

    public void execute() {
        subsystem.setFalconSpeed(Constants.Shooter.falconSpeed);
        // TODO: Tune the spin-up voltage

            subsystem.setNeoSpeed(Constants.Shooter.neoSpeed);
            indexer.run(0.3);
    }

    public void end(boolean interrupted) {
        subsystem.setFalconSpeed(0);
        subsystem.setNeoSpeed(0);
        indexer.run(0);
    }
}
