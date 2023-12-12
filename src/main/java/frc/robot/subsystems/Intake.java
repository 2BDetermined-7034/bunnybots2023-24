package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.robot.Constants.Intake.*;

public class Intake extends SubsystemBase {
    CANSparkMax liftMotor;
    CANSparkMax spinMotor1;
    CANSparkMax spinMotor2;

    MotorControllerGroup mGroup;

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

        spinMotor1 = new CANSparkMax(spinMotor1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        spinMotor1.setInverted(false);
        spinMotor1.setIdleMode(CANSparkMax.IdleMode.kBrake);

        spinMotor2 = new CANSparkMax(spinMotor2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        spinMotor2.setInverted(true);
        spinMotor2.setIdleMode(CANSparkMax.IdleMode.kBrake);

        mGroup = new MotorControllerGroup(spinMotor1, spinMotor2);

        //TODO set encoder conversion factor
        liftMotorEncoder = liftMotor.getEncoder();
        liftpid = new PIDController(0,0,0);
        liftMotorEncoder.setPosition(homePosition);
    }

    public void setLiftSpeed(double speed) {
        liftMotor.set(speed);
    }

    public void setSpinSpeed(double speed) {
        mGroup.set(speed);
    }

    public void periodic(){
        //setLiftSpeed(liftpid.calculate(liftMotorEncoder.getPosition(), goalPosition));
        setLiftSpeed(0);
        SmartDashboard.putNumber("lifter encoder", liftMotorEncoder.getPosition());
    }
    public void setGoalPosition(IntakePosition position){
        this.goalPosition = position.getEncoderPosition();
    }
}
