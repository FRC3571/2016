package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.utilities.XboxController;

import edu.wpi.first.wpilibj.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends RobotMap {
	
	private static final double ControllerDeadzone = 0.25;
	
	public static XboxController driver = new XboxController(DriverUSB.DriverController, ControllerDeadzone);
	public static XboxController operator = new XboxController(DriverUSB.OperatorController, ControllerDeadzone);
	
	public static RobotDrive drive = new RobotDrive(PWM.LeftFrontDriveMotor,PWM.LeftRearDriveMotor,PWM.RightFrontDriveMotor,PWM.RightRearDriveMotor);
	
	public static Talon arm = new Talon(PWM.ArmMotor);
	public static Talon ballIntake = new Talon(PWM.BallIntakeMotor);
	public static Talon lift = new Talon(PWM.LiftMotor);
	//New objects go here
	
	/**Calls all Refresh methods**/
	public static void refreshAll(){
		driver.refresh();
		operator.refresh();
		//All refresh calls for the objects above go in here
	}
}

