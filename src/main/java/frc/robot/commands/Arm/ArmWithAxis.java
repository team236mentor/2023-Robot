// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;
import frc.robot.Constants.ControllerConstants;

public class ArmWithAxis extends CommandBase {
  /** Creates a new ArmWithAxis. */
  private Arm arm;
  private Joystick controller;
  private double speed;

  public ArmWithAxis(Arm arm, Joystick controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.arm = arm;
    this.controller = controller;
    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    arm.setArmSpeed(-controller.getRawAxis(ControllerConstants.LogitechF310.AxesController.LEFT_Y));
    speed = -controller.getRawAxis(ControllerConstants.LogitechF310.AxesController.LEFT_Y);
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.armStop();
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
