package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.utils.Lagrange;
import frc.robot.utils.SubsystemLogging;
import frc.robot.utils.Vector2;

public class ShooterCMDBest extends CommandBase implements SubsystemLogging {
    public Shooter shooter;
    public Indexer indexer;
    Lagrange interp = new Lagrange();
    LimeLight limelight;

    public Timer timer = new Timer();

    public ShooterCMDBest(Shooter sub, Indexer indexer) {
        shooter = sub;
        this.indexer = indexer;
        addRequirements(shooter, this.indexer);
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
        shooter.setFalconSpeed(-interp.get(test_power));
        log("Target Power", interp.get(test_power));
        shooter.setNeoSpeed(0);
        // TODO: Tune the spin-up voltage
        if(shooter.getActualFalconSpeed() >= Constants.Shooter.shooterRPSCutoff * Math.abs(interp.get(test_power)) && !timer.hasElapsed(0.2)) {
            shooter.setNeoSpeed(Constants.Shooter.neoSpeed);
            indexer.run(0.3);
            timer.start();
            log(" Falcon Voltage", shooter.getActualFalconSpeed());
            log("Timer", timer.get());
        }
    }

    @Override
    public boolean isFinished() {

        return timer.hasElapsed(1.0);
    }

    public void end(boolean interrupted) {
        timer.stop();
        timer.reset();
        //subsystem.setFalconSpeed(0);
        shooter.setNeoSpeed(0);
        indexer.run(0);
    }
}
