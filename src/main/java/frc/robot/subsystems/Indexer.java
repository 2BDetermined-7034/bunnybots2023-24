package frc.robot.subsystems;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;

public class Indexer extends SubsystemBase {
    public int ObjectsTaken = 0;
    DigitalInput DIO;
    CANSparkMax Motor;
    DigitalInput DIS;

    public Indexer() {
        DIO = new DigitalInput(1);
        DIS = new DigitalInput(2);

        Motor = new CANSparkMax(5,CANSparkMaxLowLevel.MotorType.kBrushless);
        Motor.setIdleMode(CANSparkMax.IdleMode.kBrake);

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