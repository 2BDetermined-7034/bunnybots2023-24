package frc.robot.subsystems;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;

import static frc.robot.Constants.IndexerConstants.*;

public class Indexer extends SubsystemBase {
    public int ObjectsTaken = 0;
    DigitalInput entrance;
    CANSparkMax belt_motor;
    DigitalInput exit;
    boolean buffer = false;
    boolean buffer2 = false;
    public Indexer() {
        entrance = new DigitalInput(digitalInput);
        exit = new DigitalInput(digitalInput2);

        belt_motor = new CANSparkMax(motorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        belt_motor.setIdleMode(CANSparkMax.IdleMode.kBrake);

    }
    /**Indexer function:<p>
     * Updates the current amount of the objects taken.
     * This will change the amount of objects taken due to the signals from the {@link DigitalInput} sensors.
     *
     */
    public void updateAmount() {
        if (buffer && !entrance.get()){
            ++ObjectsTaken;
            buffer = false;
        }
        if (!buffer && entrance.get()) {
            buffer = true;
        }
        buffer = false;
        if(!exit.get() && buffer2){
            --ObjectsTaken;
            buffer2 = false;
        }
        if(exit.get() && !buffer2){
            buffer2 = true;
        }
    }

    public void run(double speed) {
        if (ObjectsTaken < 6) {
            ++ObjectsTaken;
            belt_motor.set(speed);
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run;
    }
    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}