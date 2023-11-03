// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Shooter extends SubsystemBase {
    /** Creates a new ExampleSubsystem. */
    public CANSparkMax neo;
    public WPI_TalonSRX m1;
    public WPI_TalonSRX m2;
    public MotorControllerGroup mgroup;

    public Shooter() {
        neo = new CANSparkMax(0,CANSparkMaxLowLevel.MotorType.kBrushless);
        m1 = new WPI_TalonSRX(1);
        m2 = new WPI_TalonSRX(2);

        mgroup = new MotorControllerGroup(m2,m1);
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
    public void neoShooter(double speed) {
        neo.set(speed);
    }

    public void activateShooter(double speed) {
        mgroup.set(speed);
    }
    
}
