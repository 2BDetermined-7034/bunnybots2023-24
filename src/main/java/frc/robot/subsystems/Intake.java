package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.utils.SubsystemLogging;

import static frc.robot.Constants.Intake.*;

public class Intake extends SubsystemBase implements SubsystemLogging {
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
        spinMotor1.setIdleMode(CANSparkMax.IdleMode.kCoast);

        spinMotor2 = new CANSparkMax(spinMotor2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        spinMotor2.setInverted(true);
        spinMotor2.setIdleMode(CANSparkMax.IdleMode.kCoast);

        mGroup = new MotorControllerGroup(spinMotor1, spinMotor2);

        //TODO set encoder conversion factor
        liftMotorEncoder = liftMotor.getEncoder();
        liftpid = new PIDController(0.4,0,0);
        liftpid.setTolerance(positionTolerance);
        liftMotorEncoder.setPosition(homePosition);
    }

    public void setLiftSpeed(double speed) {
        liftMotor.set(speed);
    }

    public void setSpinSpeed(double speed) {
        mGroup.set(speed);
        // spinMotor1.set(speed);
        // spinMotor2.set(speed);
    }

    public void periodic(){
         setLiftSpeed(liftpid.calculate(liftMotorEncoder.getPosition(), goalPosition));
         //setLiftSpeed(0);
         logger();
    }
    public void setGoalPosition(IntakePosition position){
        this.goalPosition = position.getEncoderPosition();
    }

    public void logger() {
        log("intake position", liftMotorEncoder.getPosition());
        log("intake setpoint", goalPosition);
    }
}
