// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Gripper.Grab;
import frc.robot.commands.Gripper.ReleasePiece;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class GrabScoreFlatGround extends SequentialCommandGroup {
  /** Creates a new GrabPieceandScore. */
  public GrabScoreFlatGround(Drive _drive, Gripper _gripper, Arm _arm, Turret _turret) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new AutoPIDDrive(_drive, 225),
      new Grab(_gripper),
      new AutoPIDDrive(_drive, -225),
      //new TargetLimelight or something that turns the turret automatically
      new ReleasePiece(_gripper)
    );
  }
}
