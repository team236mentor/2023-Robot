// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;
import frc.robot.subsystems.Drive;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalanceGyro extends CommandBase {
  static final double kOffBalanceAngleThresholdDegrees = 10;
  static final double kOonBalanceAngleThresholdDegrees  = 5;
  private AHRS navx;
 private XboxController controller;
 private boolean autoBalanceXMode;
  private boolean autoBalanceYMode;
  private Drive drive;

  /** Creates a new AutoBalanceGyro. */
  public AutoBalanceGyro(Drive drive, XboxController controller) {
    navx = new AHRS();
    this.drive = drive;
    addRequirements(drive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      double xAxisRate = controller.getLeftX();
      double yAxisRate = controller.getRightY();
      double pitchAngleDegrees = navx.getPitch();
      double rollAngleDegrees = navx.getRoll();
      
      if ( !autoBalanceXMode && 
           (Math.abs(pitchAngleDegrees) >= 
            Math.abs(kOffBalanceAngleThresholdDegrees))) {
          autoBalanceXMode = true;
      }
      else if (autoBalanceXMode && 
                (Math.abs(pitchAngleDegrees) <= 
                 Math.abs(kOonBalanceAngleThresholdDegrees))) {
          autoBalanceXMode = false;
      }
      if ( !autoBalanceYMode && 
           (Math.abs(rollAngleDegrees) >= 
            Math.abs(kOffBalanceAngleThresholdDegrees))) {
          autoBalanceYMode = true;
      }
      else if ( autoBalanceYMode && 
                (Math.abs(rollAngleDegrees) <= 
                 Math.abs(kOonBalanceAngleThresholdDegrees))) {
          autoBalanceYMode = false;
      }
      // Control drive system automatically, 
      // driving in reverse direction of pitch/roll angle,
      // with a magnitude based upon the angle
      
      if (autoBalanceXMode) {
          double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
          xAxisRate = Math.sin(pitchAngleRadians) * -1;
      }
      if (autoBalanceYMode) {
          double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
          yAxisRate = Math.sin(rollAngleRadians) * -1;
      }

      drive.setBothSpeeds(yAxisRate);
      Timer.delay(0.005);
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}