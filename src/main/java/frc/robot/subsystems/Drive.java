// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.DriveConstants;


public class Drive extends SubsystemBase {
  public CANSparkMax leftFront, leftRear, rightFront, rightRear;
  //private RelativeEncoder leftEncoder, rightEncoder;
  //private Encoder leftEncoder, rightEncoder;
  private RelativeEncoder leftEncoder;
  private Encoder rightEncoder;
  public AHRS navX;
  private XboxController xboxController;
  private boolean isDeadzone;
  private DoubleSolenoid transmission;

  /** Creates a new ExampleSubsystem. */
  public Drive() {
    leftFront = new CANSparkMax(Constants.MotorControllers.ID_LEFT_FRONT, MotorType.kBrushless);
    leftRear = new CANSparkMax(Constants.MotorControllers.ID_LEFT_REAR, MotorType.kBrushless);
    rightFront = new CANSparkMax(Constants.MotorControllers.ID_RIGHT_FRONT, MotorType.kBrushless);
    rightRear = new CANSparkMax(Constants.MotorControllers.ID_RIGHT_REAR, MotorType.kBrushless);

    leftFront.restoreFactoryDefaults();
    rightFront.restoreFactoryDefaults();

    leftFront.setInverted(false); //testbed = true, 2022 = false
    rightFront.setInverted(true); // testbed = false, 2022 = true

    leftRear.follow(leftFront);
    rightRear.follow(rightFront);

    leftEncoder = leftFront.getEncoder();
   //rightEncoder = rightFront.getEncoder();
   //leftEncoder = new Encoder(DriveConstants.DIO_LDRIVE_ENC_A, DriveConstants.DIO_LDRIVE_ENC_B);
    rightEncoder = new Encoder(DriveConstants.DIO_RDRIVE_ENC_A, DriveConstants.DIO_RDIRVE_ENC_B);

   navX = new AHRS();
   xboxController = new XboxController(Constants.ControllerConstants.USB_DRIVECONTROLLER);
   transmission = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, DriveConstants.SOL_LOW_GEAR, DriveConstants.SOL_HI_GEAR);
  isDeadzone = Constants.DriveConstants.IS_DEADZONE;

  }
  public void highGear() {
    transmission.set(Value.kReverse);
  }
  public void lowGear() {
    transmission.set(Value.kForward);
  }
  public boolean inLowGear() {
    if (transmission.get() == Value.kForward) {
    return true;  
    } else {
      return false;
    }
  }

  public double getRoll() {
    return (navX.getRoll());
  }

  public boolean isLevel(){
    if (Math.abs(navX.getRoll()) < 5) {
    return true;
    } else {
      return false;
  }
     }


  public void setLeftSpeed(double speed) {
    leftFront.set(speed);
  }

  public void setRightSpeed(double speed) {
    rightFront.set(speed);
  }

  public void setBothSpeeds(double speed) {
    rightFront.set(speed);
    leftFront.set(speed);
  }
  public void setTurnSpeeds(double speed) {
    leftFront.set(speed);
    rightFront.set(-speed);
  }
  public void setLeftSpeedWithDeadzone(double speed) {
    double leftSpeed = speed;
    if(leftSpeed < DriveConstants.LEFT_DEADZONE && leftSpeed > -DriveConstants.LEFT_DEADZONE) {
      leftSpeed = 0;
    } 
    setLeftSpeed(leftSpeed);
  }

  public void setRightSpeedWithDeadzone(double speed) {
    double rightSpeed = speed;
    if(rightSpeed < DriveConstants.RIGHT_DEADZONE && rightSpeed > -DriveConstants.RIGHT_DEADZONE) {
      rightSpeed = 0;
    } 
    setRightSpeed(rightSpeed);
  }
 /* public void setArcadeSpeed() {
    double max, L, R, kPgyro, error;

    /*if (Math.abs(leftStick.getX()) <= 0.15) {
      kPgyro = 0.03;
      error = navX.getRate();
    } else {
      kPgyro = 0;
      error = 0;
    }

    if (rightStick.getY() <= 0) {
      L = (-rightStick.getY() - (kPgyro*error)) + leftStick.getX();
      R = (-rightStick.getY() + (kPgyro*error)) - leftStick.getX();
    } else {
      L = (-rightStick.getY() + (kPgyro*error)) - leftStick.getX();
      R = (-rightStick.getY() - (kPgyro*error)) + leftStick.getX();
    }


    //Adjust the Y speed so the robot drives straight (no angle change) when X (turn) input is low 
    if (Math.abs(xboxController.getLeftX()) <= 0.15) {
      kPgyro = 0.02;  //need to tune this for each robot  
      error = navX.getRate();
    } else {
      kPgyro = 0;
      error = 0;
    }

    if (xboxController.getRightY() <=0) {            

      //change all signs before getLeftX for 2022 robot 
      L = (xboxController.getRightY() + (kPgyro*error)) + xboxController.getLeftX();
      R = (xboxController.getRightY() - (kPgyro*error)) - xboxController.getLeftX();
    } else {
      L = (xboxController.getRightY() - (kPgyro*error)) - xboxController.getLeftX();
      R = (xboxController.getRightY() + (kPgyro*error)) + xboxController.getLeftX();
    }

    max = Math.abs(L);
    if (max < Math.abs(R)) {max = Math.abs(R);}
    if (max > 1) {L /= max; R/= max;}

    setRightSpeedWithDeadzone(R);
    setLeftSpeedWithDeadzone(L);

  }*/

  public double getLeftSpeed() {
   return leftEncoder.getVelocity();
   //return leftEncoder.getRate();
  }
  public double getRightSpeed() {
    //return rightEncoder.getVelocity();
    return rightEncoder.getRate();
  }
  public double getLeftEncoder(){
 return leftEncoder.getPosition();
// return leftEncoder.get()/128; //revs from encoder ticks
  }
  public double getRightEncoder() {
    //return rightEncoder.getPosition();
    return rightEncoder.get()/128;
  }
  public double getLeftDistance() {
    return getLeftEncoder() * DriveConstants.REV_TO_IN_K;
  }
  public double getRightDistance() {
    return getRightEncoder() * DriveConstants.REV_TO_IN_K;
  }
  public double getAvgDistance() {
    return (getLeftDistance() + getRightDistance())/2 ;
  }
  public void resetLeftEncoder() {
  leftEncoder.setPosition(0);
    //leftEncoder.reset();
  }
  public void resetRightEncoder() {
    //rightEncoder.setPosition(0);
    rightEncoder.reset();
  }

  public void stop() {
    leftFront.stopMotor();
    rightFront.stopMotor();
  }

 public double getGyroRate(){
  return navX.getRate();
  }

  @Override
  public void periodic() {
    //SmartDashboard.getBoolean("In Low Gear?", inLowGear());
    SmartDashboard.putNumber("left enc", getLeftEncoder());
    SmartDashboard.putNumber("right enc", getRightEncoder());
    SmartDashboard.putNumber("rightDis", getRightDistance());
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
