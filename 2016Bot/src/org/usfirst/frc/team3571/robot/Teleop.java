package org.usfirst.frc.team3571.robot;

public class Teleop extends OI {
	
	/**
	 * Runs once
	 */
	public static void initial(){
		//The code here runs only once when teleop starts
	}
	/**
	 * Runs at a maximum of 50Hz during Teleop
	 */
	public static void periodic(){
		
		drive.arcadeDrive(driver.LeftStick.Y,driver.LeftStick.X);
		
		arm.set(driver.RightStick.Y * 0.75);
		
		ballIntake.set(driver.Triggers.Combined);
		
		lift.set(operator.LeftStick.Y);
		
	}
}
