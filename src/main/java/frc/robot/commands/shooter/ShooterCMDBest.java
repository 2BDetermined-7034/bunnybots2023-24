package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.utils.Lagrange;
import frc.robot.utils.LagrangeQuadratic;
import frc.robot.utils.SubsystemLogging;
import frc.robot.utils.Vector2;

public class ShooterCMDBest extends CommandBase implements SubsystemLogging {
    public Shooter subsystem;
    public Indexer indexer;
    Lagrange interp = new Lagrange();
    LimeLight limelight;

    public ShooterCMDBest(Shooter sub, Indexer indexer) {
        subsystem = sub;
        this.indexer = indexer;
        addRequirements(subsystem, this.indexer);
        interp.vertices = new Vector2[3];

        interp.vertices[0] = new Vector2(5.0, 0.7);
        interp.vertices[1] = new Vector2(15.0, 0.57);
        interp.vertices[2] = new Vector2(20.0, 0.6);

        limelight = new LimeLight();
    }
    @Override

    public void execute() {
        //subsystem.setFalconSpeed(Constants.Shooter.falconSpeed);
        //SmartDashboard.getNumber("ShooterSpeed", 0)
        double test_power = 10.0;
        subsystem.setFalconSpeed(-interp.get(test_power));
        log("Target Power", interp.get(test_power));
        // TODO: Tune the spin-up voltage
        if(subsystem.getActualFalconSpeed() >= Constants.Shooter.shooterRPSCutoff * Math.abs(interp.get(test_power))) {
            subsystem.setNeoSpeed(Constants.Shooter.neoSpeed);
            indexer.run(0.3);
            log(" Falcon Voltage", subsystem.getActualFalconSpeed());
        }
    }

    public void end(boolean interrupted) {
        subsystem.setFalconSpeed(0);
        subsystem.setNeoSpeed(0);
        indexer.run(0);
    }
}
