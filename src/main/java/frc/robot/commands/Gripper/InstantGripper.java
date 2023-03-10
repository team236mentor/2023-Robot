// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Gripper;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Gripper;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class InstantGripper extends InstantCommand {
  private Gripper gripper;
  private boolean toggle;

  public InstantGripper(Gripper _gripper) { 
    this.gripper=_gripper;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(gripper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    toggle = false;
    
   if (gripper.isGripping()) {
    gripper.release();
    gripper.resetGripperEyeCount();
    toggle = true;
    } else if (!gripper.isGripping()) {
    gripper.grab();
    toggle = true;
   }

  }
}
