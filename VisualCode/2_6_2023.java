// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 * 
 */
public class Robot extends TimedRobot {
  private static double kDt = 0.02;

  private final XboxController m_XboxController = new XboxController(0);
  private final Encoder m_Encoder = new Encoder(1,2);
  //private final MotorControllerGroup m_motor = new PWMSparkMax(1)

  // Create a PID controller whose setpoint's change is subject to  maximum 
  // velocity and acceleration constraints.
  private final TrapezoidProfile.Constraints m_constraints =
    new TrapezoidProfile.Constraints(1.75, 0.75);
    private final ProfiledPIDController m_Controller =
      new ProfiledPIDController(1.3, 0.0, 0.7, m_constraints, kDt);

  

 

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
// Right Motors-
  private final WPI_VictorSPX rightMotor1 = new WPI_VictorSPX(1);
  private final WPI_VictorSPX rightMotor2 = new WPI_VictorSPX(2);

// Parentheses contain the PWM port
//intake Motors
  private final WPI_TalonSRX armMotor = new WPI_TalonSRX(5);

//Left Motors
  private final WPI_VictorSPX leftMotor1 = new WPI_VictorSPX(3);
  private final WPI_VictorSPX leftMotor2 = new WPI_VictorSPX(4);

//Right Motors speed controller
 private final MotorControllerGroup rightSpeedGroup = new MotorControllerGroup(rightMotor1, rightMotor2);


//Left Motors speed controller 
 private final MotorControllerGroup leftSpeedGroup = new MotorControllerGroup (leftMotor1, leftMotor2);

//drivetrain
 private final DifferentialDrive drivetrain = new DifferentialDrive(rightSpeedGroup, leftSpeedGroup);


// Joystick USB has to be plugged into the USB port 0 on the laptop

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  // new
  private double startTime;

  @Override
  public void robotInit() {

    rightMotor1.setInverted(true);
    rightMotor2.setInverted(true); 
    leftMotor2.setInverted(true); 





    m_Encoder.setDistancePerPulse(1.0 / 360.0 * 2.0 * Math.PI * 1.5);
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }


  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
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
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    
    startTime = Timer.getFPGATimestamp();
  }
  //end of new shit

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
      // new stuff
      double time = Timer.getFPGATimestamp();

      if (time - startTime< 3) {
      leftMotor1.set(0.6);
      leftMotor2.set(0.6);
      rightMotor1.set(-0.6);
      rightMotor2.set(-0.6);
    } else{
      leftMotor1.set(0);
      leftMotor2.set(0);
      rightMotor1.set(0);
      rightMotor2.set(0);
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    drivetrain.tankDrive(-m_XboxController.getLeftY(), -m_XboxController.getRightY());

    /**if(stick.getRawButton(1)){
      gate.setPosition(50);
    }
    else{
      gate.setPosition(0);
    } */
    if(m_XboxController.getLeftBumper()){
      armMotor.set(-0.5);

    }else{
      armMotor.set(0);
    }

    if(m_XboxController.getRightBumper()){
      armMotor.set( 0.5);
    }else{
      armMotor.set(0);
    }

   // if (m_Controller.getRightBumper()) {
     // m_Controller.setGoal(5);
    //} 
    //else if (m_Controller.getRawButtonPressed(3)) {
     // m_Controller.setGoal(0);
    //}


    
    // Run controller and update motor output
    armMotor.set(m_Controller.calculate(m_Encoder.getDistance()));
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
}
