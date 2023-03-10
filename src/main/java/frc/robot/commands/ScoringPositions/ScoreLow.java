// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ScoringPositions;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.PivotConstants;
import frc.robot.commands.Arm.ArmPID;
import frc.robot.commands.Gripper.ReleasePiece;
import frc.robot.commands.Pivot.PivotPID;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Pivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreLow extends SequentialCommandGroup {

  
  /** Creates a new ScoreMiddleLevel. */
  public ScoreLow(Arm _lowScore, Gripper _gripScore, Pivot _pvtLow) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    Gripper gripScore = _gripScore;
    CommandBase temp;

    addCommands(
        //new GrabReleaseToggle(gripScore1),
      new PivotPID(_pvtLow, PivotConstants.PVT_ENC_LOW_SCORE).withTimeout(1),
      new ArmPID(_lowScore, 0)  
      
      // several attempts using lambda to use the subsystem toggle method 
       // CommandBase toggleGripper1 = runOnce(() -> gripScore.toggle(), _gripScore);)
       // Command toggleGripper2 = runOnce( () -> { this.gripScore.toggle(); }, _gripScore),
       // temp = runOnce(() -> gripScore.toggle(),_gripScore)
    );
      
  }
}
