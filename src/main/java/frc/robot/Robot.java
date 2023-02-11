// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private XboxController m_leftStick;
  private XboxController m_rightStick;

  private static final int leftleadDeviceID = 1; 
  private static final int leftfollowDeviceID = 2;
  private static final int rightleadDeviceID = 3;
  private static final int rightfollowDeviceID = 4;

  private CANSparkMax m_leftleadMotor;
  private CANSparkMax m_rightleadMotor;
  private CANSparkMax m_leftfollowMotor;
  private CANSparkMax m_rightfollowMotor;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_leftleadMotor = new CANSparkMax(leftleadDeviceID, MotorType.kBrushless);
    m_leftfollowMotor = new CANSparkMax(leftfollowDeviceID, MotorType.kBrushless);
    m_rightleadMotor = new CANSparkMax(rightleadDeviceID, MotorType.kBrushless);
    m_rightfollowMotor = new CANSparkMax(rightfollowDeviceID, MotorType.kBrushless);
    m_leftleadMotor.restoreFactoryDefaults();
    m_leftfollowMotor.restoreFactoryDefaults();
    m_rightleadMotor.restoreFactoryDefaults();
    m_rightfollowMotor.restoreFactoryDefaults();

    m_myRobot = new DifferentialDrive(m_leftleadMotor, m_rightleadMotor);

    m_leftStick = new XboxController(0);
    m_rightStick = new XboxController(1);

    m_leftfollowMotor.follow(m_leftleadMotor);
    m_rightfollowMotor.follow(m_rightleadMotor);
  }


  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
   
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_myRobot.tankDrive(m_leftStick.getLeftY(), m_rightStick.getLeftY());
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
