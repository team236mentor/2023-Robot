// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.DriveConstants;


public class Drive extends SubsystemBase {

  public CANSparkMax leftFront, leftRear, rightFront, rightRear;
  private RelativeEncoder leftEncoder, rightEncoder;
  public AHRS navX;

  /** Creates a new ExampleSubsystem. */
  public Drive() {
    leftFront = new CANSparkMax(Constants.MotorControllers.ID_LEFT_FRONT, MotorType.kBrushless);
    leftRear = new CANSparkMax(Constants.MotorControllers.ID_LEFT_REAR, MotorType.kBrushless);
    rightFront = new CANSparkMax(Constants.MotorControllers.ID_RIGHT_FRONT, MotorType.kBrushless);
    rightRear = new CANSparkMax(Constants.MotorControllers.ID_RIGHT_REAR, MotorType.kBrushless);

    leftFront.restoreFactoryDefaults();
    rightFront.restoreFactoryDefaults();

    leftFront.setInverted(false);
    rightFront.setInverted(true);

    leftRear.follow(leftFront, false);
    rightRear.follow(rightFront, false);

    leftEncoder = leftFront.getEncoder();
    rightEncoder = rightFront.getEncoder();

   navX = new AHRS();

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

  public double getLeftSpeed() {
   return leftEncoder.getVelocity();
  }
  public double getRightSpeed() {
    return rightEncoder.getVelocity();
  }
  public double getLeftEncoder(){
  return leftEncoder.getPosition();
  }
  public double getRightEncoder() {
    return rightEncoder.getPosition();
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
  }
  public void resetRightEncoder() {
    rightEncoder.setPosition(0);
  }

  public void stop(double speed) {
    setLeftSpeed(0);
    setRightSpeed(0);
  }

 public double getGyroRate(){
  return navX.getRate();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
