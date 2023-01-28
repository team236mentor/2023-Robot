// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.NavX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Joystick;

public class DoubleArcadeDrive extends CommandBase {
  private Drive drive;
  private Gripper gripper1;
  private NavX navX;
  private Joystick leftStick, rightStick;
  private double max, L, R;
  private Boolean isDeadzone = Constants.DriveConstants.IS_DEADZONE;
  private Boolean inXDeadzone = Constants.DriveConstants.IN_X_DEADZONE;
  private double kP;
  private double error;
  


  /** Creates a new DoubleArcadeDrive. */
  public DoubleArcadeDrive(Drive drive, Joystick leftStick, Joystick rightStick, Gripper gripper1) {
    this.drive = drive;
    this.gripper1 = gripper1;
    this.navX = navX;
    this.leftStick = leftStick;
    this.rightStick = rightStick;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
    addRequirements(gripper1);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.isDeadzone = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(leftStick.getX()) < 0.15) {
      this.inXDeadzone = true;
    }
    while(inXDeadzone){
      kP = 0.000;
      error = navX.getrate();
    }
    while(!inXDeadzone){
      error = 0;
    }

    if (this.isDeadzone) {
      drive.setRightSpeedWithDeadzone(R);
      drive.setLeftSpeedWithDeadzone(L);
    } else {
      drive.setLeftSpeed(L);
      drive.setRightSpeed(R);
    }

    if (rightStick.getY() <= 0) {
      L = (-rightStick.getY() - kP*error) + leftStick.getX();
      R = (-rightStick.getY() + kP*error) - leftStick.getX();
    } else {
      L = (-rightStick.getY() + kP*error) - leftStick.getX();
      R = (-rightStick.getY() - kP*error) + leftStick.getX();
    }

    max = Math.abs(L);
    if (max < Math.abs(R)) {max = Math.abs(R);}
    if (max > 1) {L /= max; R/= max;}
  
    if (gripper1.getGripperEyeCount() == 1) {gripper1.grab();}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
