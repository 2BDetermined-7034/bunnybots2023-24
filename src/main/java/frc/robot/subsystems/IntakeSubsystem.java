package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import static frc.robot.Constants.Intake.*;

public class IntakeSubsystem extends SubsystemBase {
    CANSparkMax liftMotor;
    CANSparkMax spinMotor;

    RelativeEncoder liftMotorEncoder;
    PIDController liftpid;
    public double homePosition = 0;
    public double activePosition = 90;
    double goalPosition = homePosition;
    public IntakeSubsystem() {
        liftMotor = new CANSparkMax(10, CANSparkMaxLowLevel.MotorType.kBrushless);
        spinMotor = new CANSparkMax(20, CANSparkMaxLowLevel.MotorType.kBrushless);
        //TODO set encoder conversino factor
        liftMotorEncoder = liftMotor.getEncoder();
        liftpid = new PIDController(0,0,0);
        liftMotorEncoder.setPosition(homePosition);
    }

    public void setLiftSpeed(double speed) {
        liftMotor.set(speed);
    }

    public void setSpinSpeed(double speed) {
        spinMotor.set(speed);
    }

    public void periodic(){
        setLiftSpeed(liftpid.calculate(liftMotorEncoder.getPosition(), goalPosition));
    }
    public void setGoalPosition(double goalPosition){
        this.goalPosition = goalPosition;
    }
}
