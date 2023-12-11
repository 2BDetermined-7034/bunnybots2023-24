// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.indexer.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.intake.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController driverController =
            new CommandXboxController(OperatorConstants.kDriverControllerPort);

    private Shooter shooter = new Shooter();
    private ShooterCMDBest shootCommand = new ShooterCMDBest(shooter);

    private Intake intake = new Intake();
    //private SpinIntake intakeCommand = new SpinIntake(intakeSubsystem);

    private Indexer indexerSubsystem = new Indexer();
    private IndexerCommand indexerCommand = new IndexerCommand(indexerSubsystem);


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

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
        new Trigger(driverController.rightTrigger().whileTrue(shootCommand));

        new Trigger(() -> driverController.getRightTriggerAxis() > 0.5).whileTrue(new SpinIntake(intake, Constants.Intake.spinMotorSpeed));
        new Trigger(() -> driverController.getLeftTriggerAxis() > 0.5).whileTrue(new SpinIntake(intake, - Constants.Intake.spinMotorSpeed));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return null;
    }
}
