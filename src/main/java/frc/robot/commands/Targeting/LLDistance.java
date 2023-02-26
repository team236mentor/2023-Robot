// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Targeting;
import frc.robot.subsystems.Drive;
//import frc.robot.subsystems.Limelight;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.lang.Math;

//import org.apache.commons.io.comparator.SizeFileComparator;

//import com.fasterxml.jackson.databind.deser.ValueInstantiator.Gettable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class LLDistance extends CommandBase {
    //tV = 1 if there are any targets found, =0 if not
    //ty = vertical offset from crosshair to target -20.5 to +20.5 degrees
    //h1 = distance from floor to center of Limelight lens
    //h2 = distance from floor to center of target
    //a1 = angle between floor (horizontal) and camera's centerline (camera mount angle, how far rotated from vertical?)
    //a2 = getTy (angle between camera's centerline and line extending from center of camera to center of target)
    //d = Distance to target (want 14" or 16" distance in order to be in front of Grid)
    //tan(a1 +a2)  = (h2-h1)/dx;
  private double kY = 0.00785; //0.00725;
  private Drive drive1;
  private double h1 = 21.5; //inches, same unit as d  9.5 for testbot; 21.5 for 2023 bot
  private double h2 = 18; // inches, same unit as d
  private double a1 = 0.436; //25 degrees  //0.261799 15 degrees for testbot - 0.29667 or 17 deg for 2022 bot
  private double d = 40; // 12 inches away? idk how far we want to be
  private double errorY = 0;
 // private Limelight limelight;
  
  /** Creates a new LLAngle. */
  public LLDistance(Drive passed_drive1) {
    this.drive1 = passed_drive1;
    addRequirements(this.drive1);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double disY= NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
   
    if(tv==1){
         double a2 = disY*Math.PI/180;
         double dx = -(h2-h1) / Math.tan(a1+a2);
         errorY = d - dx;
         double distanceAdjust = kY * errorY;
       drive1.setLeftSpeed(distanceAdjust);
       drive1.setRightSpeed(distanceAdjust);
      SmartDashboard.putNumber("dx", dx);  
      SmartDashboard.putNumber("ErrorY", errorY);
      SmartDashboard.putNumber("distanceAdjust", distanceAdjust);
   } else{
   SmartDashboard.putNumber("No Target", tv);
   }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive1.stop();
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
   // NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
