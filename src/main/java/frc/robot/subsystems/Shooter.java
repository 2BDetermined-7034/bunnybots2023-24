// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.Constants;
import frc.robot.utils.SubsystemLogging;

import static frc.robot.Constants.Shooter.shooterRPSCutoff;

/**
 * A subsystem, which is specifically for the shooter.
 * <p> This code is for the shooter build, which is the reason why the motors run.
 *
 */
public class Shooter extends SubsystemBase implements SubsystemLogging {
    public CANSparkMax neo;
    public WPI_TalonFX talon1;
    public WPI_TalonFX talon2;
    public MotorControllerGroup mgroup;
    private double neoSpeed;
    private double falconSpeed;

    /**Shooter function;
     * contains all the things the shooter build (and even the code, obviously) needs.
     * @Objects [CANSparkMax Neo, CANSparkMax Falcon]
     * @Commands [ShooterCMDNeo.java, ShooterCMDFalcon.java] Located in the <i>commands</i> folder.
     */
    public Shooter() {
        neo = new CANSparkMax(Constants.Shooter.neoID, CANSparkMaxLowLevel.MotorType.kBrushless);
        neo.setIdleMode(CANSparkMax.IdleMode.kCoast);
        neo.setInverted(false);

        talon1 = new WPI_TalonFX(Constants.Shooter.Motor1ID);
        talon1.setNeutralMode(NeutralMode.Brake);
        talon1.setInverted(false);

        talon2 = new WPI_TalonFX(Constants.Shooter.Motor2ID);
        talon2.setNeutralMode(NeutralMode.Brake);
        talon2.setInverted(true);

        neoSpeed = 0;
        falconSpeed = 0;
        mgroup = new MotorControllerGroup(talon1, talon2);

        SmartDashboard.setDefaultNumber("ShooterSpeed", 0);

        talon1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    }
    
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //neo.set(neoSpeed);
        //mgroup.set(falconSpeed);
        mgroup.set(falconSpeed);
        log("Falcon Speed", getActualFalconSpeed());
        log("Falcon Cuttoff", shooterRPSCutoff * Math.abs(SmartDashboard.getNumber("ShooterSpeed", -1)));

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
    public void setNeoSpeed(double speed) {
        neo.set(speed);
    }

    public void setFalconSpeed(double speed) {
        falconSpeed = speed;
    }

    /**
     *
     * @return Talon's angular velocity, in rotations per second
     */
    public double getActualFalconSpeed(){
        // TODO: Ensure motorOutputVoltage() returns actual and not expected velocity.
        return Math.abs((talon1.getSelectedSensorVelocity() / 4096.0) * 10.0);
    }
}
