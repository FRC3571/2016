package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.utilities.XboxController;

import edu.wpi.first.wpilibj.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends RobotMap {
	
	private static final double controllerDeadzone = 0.25;
	
	public static XboxController driver = new XboxController(DriverUSB.driverController, controllerDeadzone);
	public static XboxController operator = new XboxController(DriverUSB.operatorController, controllerDeadzone);
	
	public static RobotDrive drive = new RobotDrive(PWM.leftFrontDriveMotor,PWM.leftRearDriveMotor,PWM.rightFrontDriveMotor,PWM.rightRearDriveMotor);
	
	public static Talon arm = new Talon(PWM.armMotor);
	public static Talon ballIntake = new Talon(PWM.ballIntakeMotor);
	public static Talon lift = new Talon(PWM.liftMotor);
	//New objects go here
	
	/**Calls all Refresh methods**/
	public static void refreshAll(){
		driver.refresh();
		operator.refresh();
		//All refresh calls for the objects above go in here
	}
}

