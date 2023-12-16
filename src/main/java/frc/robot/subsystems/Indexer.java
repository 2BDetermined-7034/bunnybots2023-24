package frc.robot.subsystems;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;
import frc.robot.utils.SubsystemLogging;

import static frc.robot.Constants.IndexerConstants.*;

public class Indexer extends SubsystemBase implements SubsystemLogging {
    public int objectsTaken = 0;
    DigitalInput entrance;
    DigitalInput exit;
    CANSparkMax frontMotor;
    CANSparkMax backMotor;
    boolean buffer = false;
    boolean buffer2 = false;

    public Indexer() {
        entrance = new DigitalInput(digitalInputFront);
        exit = new DigitalInput(digitalInputBack);

        frontMotor = new CANSparkMax(frontMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        frontMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        frontMotor.setInverted(true);

        backMotor = new CANSparkMax(backMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        backMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backMotor.setInverted(true);
    }
    /**Indexer function:<p>
     * Updates the (theoretical) amount of balls in the
     * This will change the amount of objects taken due to the signals from the {@link DigitalInput} sensors.
     */
    public void updateAmount() {
        if (buffer && !entrance.get()){
            ++objectsTaken;
            buffer = false;
        }
        if (!buffer && entrance.get()) {
            buffer = true;
        }
        if(buffer2 && !exit.get()){
            --objectsTaken;
            buffer2 = false;
        }
        if(!buffer2 && exit.get()){
            buffer2 = true;
        }
    }

    public void run(double speed) {
        if (objectsTaken < Constants.IndexerConstants.maxBalls) {
            /* ++ObjectsTaken; */
            frontMotor.set(speed);
            backMotor.set(speed);
        } else {
            frontMotor.set(-speed);
        }
    }

    public void runArse(double speed){
        backMotor.set(speed);
    }

    public void spitOut(double speed){
        backMotor.set(-speed);
        frontMotor.set(-speed);
    }

    public int getCurrentObjects(){
        return objectsTaken;
    }

    public void setCurrentObjects(int objectsTaken){
        objectsTaken = MathUtil.clamp(objectsTaken, 0, 5);
    }

    public void zeroCurrentObjects(){
        objectsTaken = 0;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run;
        updateAmount();
        logger();
    }
    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void logger() {
        log("num balls", objectsTaken);
        log("Sensor Bot", entrance.get());
        log("Sensor Top", exit.get());
    }
}