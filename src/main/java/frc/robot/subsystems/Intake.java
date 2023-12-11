package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Intake.*;

public class Intake extends SubsystemBase {
    CANSparkMax liftMotor;
    CANSparkMax spinMotor;

    RelativeEncoder liftMotorEncoder;
    PIDController liftpid;
    double goalPosition = homePosition;

    public enum IntakePosition {
        LOWERED(activePosition),
        RAISED(homePosition);

        private final double encoderPosition;
        IntakePosition(double encoderPosition) {
            this.encoderPosition = encoderPosition;
        }

        public double getEncoderPosition() {
            return encoderPosition;
        }
    }
    public Intake() {
        liftMotor = new CANSparkMax(liftMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        spinMotor = new CANSparkMax(spinMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        //TODO set encoder conversion factor
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
    public void setGoalPosition(IntakePosition position){
        this.goalPosition = position.getEncoderPosition();
    }
}
