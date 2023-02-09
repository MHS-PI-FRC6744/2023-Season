// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.




import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
 
  private final WPI_VictorSPX m_arm = new WPI_VictorSPX(5);
  private final WPI_VictorSPX m_leftDrive = new WPI_VictorSPX(1);
  private final WPI_VictorSPX m_rightDrive = new WPI_VictorSPX(2);
  private final WPI_VictorSPX m_leftFollow = new WPI_VictorSPX(3);
  private final WPI_VictorSPX m_rightFollow = new WPI_VictorSPX(4);
  

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDrive,m_rightDrive);
  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();
  
  private final MotorControllerGroup m_left = new MotorControllerGroup(m_leftDrive, m_leftFollow);
  private final MotorControllerGroup m_right = new MotorControllerGroup(m_rightDrive, m_rightFollow);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.

    

    m_right.setInverted(true);
    m_left.setInverted(false);

  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  
  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    //m_robotDrive.arcadeDrive(-m_controller.getLeftY(), -m_controller.getRightX());
    m_robotDrive.tankDrive(-m_controller.getLeftY(), -m_controller.getRightX());

    if (m_controller.getRightBumperPressed()) {
     
     m_arm.set(0.5);
 
  }   else if (m_controller.getLeftBumper()){
    
    m_arm.set(-0.5);

      


  }

  }


  /** This function is called once each time the robot enters test mode. */
@Override
  public void testInit() {

    
  }

  /** This function is called periodically during test mode. */
   
@Override
  public void testPeriodic() {

  }
}