// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.Constants;

/**
 * A subsystem, which is specifically for the shooter.
 * <p> This code is for the shooter build, which is the reason why the motors run.
 *
 */
public class Shooter extends SubsystemBase {
    public CANSparkMax neo;
    public WPI_TalonSRX talon1;
    public WPI_TalonSRX talon2;
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

        talon1 = new WPI_TalonSRX(Constants.Shooter.Motor1ID);
        talon1.setNeutralMode(NeutralMode.Brake);
        talon1.setInverted(false);

        talon2 = new WPI_TalonSRX(Constants.Shooter.Motor2ID);
        talon2.setNeutralMode(NeutralMode.Brake);
        talon2.setInverted(true);

        neoSpeed = 0;
        falconSpeed = 0;
        mgroup = new MotorControllerGroup(talon1, talon2);

        SmartDashboard.setDefaultNumber("ShooterSpeed", 0);

    }
    
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //neo.set(neoSpeed);
        //mgroup.set(falconSpeed);
        mgroup.set(SmartDashboard.getNumber("ShooterSpeed", 0));
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
    public double getActualFalconVoltage(){
        // TODO: Ensure motorOutputVoltage() returns actual and not expected velocity.
        return talon1.getMotorOutputVoltage();
    }
}
