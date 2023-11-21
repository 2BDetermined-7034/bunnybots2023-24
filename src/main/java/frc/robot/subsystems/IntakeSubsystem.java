package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import static frc.robot.Constants.Intake.*;

public class IntakeSubsystem extends SubsystemBase {
    CANSparkMax liftMotor;
    CANSparkMax spinMotor;

    public IntakeSubsystem() {
        //liftMotor = new CANSparkMax(10, CANSparkMaxLowLevel.MotorType.kBrushless);
        //spinMotor = new CANSparkMax(20, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setLiftSpeed(double speed) {
        liftMotor.set(speed);
    }

    public void setSpinSpeed(double speed) {
        spinMotor.set(speed);
    }
}
