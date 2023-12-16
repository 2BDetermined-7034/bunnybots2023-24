// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ControllerDrive;
import frc.robot.commands.Limelight.LimelightDrive;
import frc.robot.commands.drivebase.TimedDrive;
import frc.robot.subsystems.*;
import frc.robot.commands.indexer.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.intake.*;
import frc.robot.utils.SubsystemLogging;

import java.io.IOException;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer implements SubsystemLogging
{
    // The robot's subsystems and commands are defined here...

    // Replace with CommandPS4Controller or CommandJoystick if needed
    public final CommandXboxController driverController =
            new CommandXboxController(OperatorConstants.kDriverControllerPort);

    // Subsystems
    public Shooter shooter = new Shooter();
    public Intake intake = new Intake();
    public Indexer indexerSubsystem = new Indexer();
    public SwerveSubsystem swerveSubsystem = SwerveSubsystem.getInstance();
    public LimeLight limelight = new LimeLight();

    // Commands
    private ShooterCMDBest shootCommand = new ShooterCMDBest(shooter, indexerSubsystem, limelight, swerveSubsystem, () -> MathUtil.applyDeadband(driverController.getLeftX(), 0.1) * 10, () -> MathUtil.applyDeadband(driverController.getLeftY(), 0.1) * 10, () -> MathUtil.applyDeadband(driverController.getRightX(), 0.1) * 7);
    //private ShooterCMDPercent shootCommand = new ShooterCMDPercent(shooter, indexerSubsystem, limelight, swerveSubsystem, () -> MathUtil.applyDeadband(driverController.getLeftX(), 0.1) * 10, () -> MathUtil.applyDeadband(driverController.getLeftY(), 0.1) * 10, () -> MathUtil.applyDeadband(driverController.getRightX(), 0.1) * 7);
    public IndexerCommand indexerCommand = new IndexerCommand(indexerSubsystem);
    public ControllerDrive driveCommand = new ControllerDrive(swerveSubsystem, () -> MathUtil.applyDeadband(driverController.getLeftX(), 0.1) * 10, () -> MathUtil.applyDeadband(driverController.getLeftY(), 0.1) * 10, () -> MathUtil.applyDeadband(driverController.getRightX(), 0.1) * 7, false);

    //public ControllerDrive driveCommand = new ControllerDrive(swerveSubsystem, () -> 0, () -> 0, () -> 0, false);


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {


        swerveSubsystem.setDefaultCommand(driveCommand);

        //new Trigger(() -> m_driverController.getRightTriggerAxis() > 0.5).whileTrue(intakeCommand);

        // Configure the trigger bindings
        configureBindings();
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        new Trigger(driverController.a().whileTrue(new RepeatCommand(shootCommand)));
        new Trigger(driverController.b().toggleOnTrue(new LimelightDrive(swerveSubsystem, limelight, () -> MathUtil.applyDeadband(driverController.getLeftX(), 0.1) * 10, () -> MathUtil.applyDeadband(driverController.getLeftY(), 0.1) * 10, () -> MathUtil.applyDeadband(driverController.getRightX(), 0.1) * 7)));
        new Trigger(driverController.a().whileFalse(new InstantCommand(() -> {shooter.setFalconSpeed(0); shooter.setNeoSpeed(0);})));

        new Trigger(() -> driverController.getRightTriggerAxis() > 0.5).whileTrue(new SpinIntake(intake, indexerSubsystem, Constants.Intake.spinMotorSpeed));
        new Trigger(() -> driverController.getLeftTriggerAxis() > 0.5).whileTrue(new SpitIntake(intake, indexerSubsystem, Constants.Intake.spinMotorSpeed));

        new Trigger(driverController.back()).onTrue(swerveSubsystem.runOnce(swerveSubsystem::zeroGyro));
        new Trigger(driverController.start()).onTrue(indexerSubsystem.runOnce(indexerSubsystem::zeroCurrentObjects));

        new Trigger(driverController.povDown().onTrue(intake.runOnce(() -> intake.setGoalPosition(Intake.IntakePosition.LOWERED))));
        new Trigger(driverController.povUp().onTrue(intake.runOnce(() -> intake.setGoalPosition(Intake.IntakePosition.RAISED))));



//        new Trigger(driverController.b()).toggleOnTrue()

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return new SequentialCommandGroup(
                new TimedDrive(swerveSubsystem, () -> 0, () -> -2, () -> 0, false)

        );

    }
}
