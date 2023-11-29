package frc.robot.subsystems;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;

import static frc.robot.Constants.IndexerConstants.*;

public class Indexer extends SubsystemBase {
    public int ObjectsTaken = 1;
    DigitalInput DIO;
    CANSparkMax Motor;
    DigitalInput DIOb;

    public Indexer() {
        DIO = new DigitalInput(digitalInput);
        DIOb = new DigitalInput(digitalInput2);

        Motor = new CANSparkMax(motorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        Motor.setIdleMode(CANSparkMax.IdleMode.kBrake);

    }

    public void updateAmount() {
        //work in progress
    }
    public void run(double speed) {
        if (ObjectsTaken < 6) {
            ++ObjectsTaken;
            Motor.set(speed);
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