package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBaseHenryE extends SubsystemBase {
    //public WPI_TalonSRX talonLeft1, talonLeft2, talonRight1, talonRight2;
    public CANSparkMax m_left1, m_left2, m_right1, m_right2;
    
    MotorControllerGroup lefControllerGroup, righControllerGroup;
    private DifferentialDrive drive;
        public DriveBaseHenryE(){
            /*
            talonLeft1 = new WPI_TalonSRX(0);
            talonLeft2 = new WPI_TalonSRX(1);
       
            talonRight1 = new WPI_TalonSRX(2);
            talonRight2 = new WPI_TalonSRX(3);
            
            /* 
            lefControllerGroup = new MotorControllerGroup(talonLeft1, talonLeft2);
            righControllerGroup = new MotorControllerGroup(talonRight1, talonRight2);

            drive = new DifferentialDrive(lefControllerGroup, righControllerGroup);
            */

            m_left1 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
            m_left2 = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
            m_right1 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
            m_right2 = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);

            MotorControllerGroup groupLeft = new MotorControllerGroup(m_left1, m_left2);
            MotorControllerGroup groupRight = new MotorControllerGroup(m_right1, m_right2);
            
            drive = new DifferentialDrive(groupLeft, groupRight);
            
        }


        

    public void drive(DoubleSupplier speed, DoubleSupplier rotation){
        drive.arcadeDrive(speed.getAsDouble(), rotation.getAsDouble(), false);
    }

}
