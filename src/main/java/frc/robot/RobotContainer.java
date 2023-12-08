// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.commands.shooter.ShooterCMDFalcon;
import frc.robot.commands.shooter.ShooterCMDNeo;
import frc.robot.subsystems.DriveBaseHenryE;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Indexer;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private Shooter SubShooterSystem = new Shooter();
  private ShooterCMDFalcon FalconShooter = new ShooterCMDFalcon(SubShooterSystem, 0);
  private ShooterCMDNeo NeoShooter = new ShooterCMDNeo(SubShooterSystem, 0);

  private IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  //private SpinIntake intakeCommand = new SpinIntake(intakeSubsystem);

  private Indexer indexerSubsystem = new Indexer();
  private IndexerCommand indexerCommand = new IndexerCommand(indexerSubsystem);

  DriveBaseHenryE driveBase = new DriveBaseHenryE();
  Drive driveCommand = new Drive(driveBase, m_driverController.getLeftY());

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    driveBase.setDefaultCommand(driveCommand);

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
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    //new Trigger(m_driverController.a()).whileTrue(FalconShooter);
    //new Trigger(m_driverController.y()).whileTrue(NeoShooter);
    new Trigger(m_driverController.rightTrigger().whileTrue(new ParallelCommandGroup(FalconShooter, NeoShooter)));
    //new Trigger(m_driverController.y()).whileTrue(new ShooterCMDBetter(SubShooterSystem, 0));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //new Trigger(m_driverController.b()).whileTrue(new AbortNeo(SubShooterSystem, 0));


  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
