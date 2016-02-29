package org.usfirst.frc.team3571.robot;

public class Teleop extends OI {
	
	private static double driveY=0, driveX=0;
	
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
		driveX = driver.LeftStick.X;
		driveY = driver.LeftStick.Y;
		
		if(driver.Buttons.A.current)driveY = -driveY;//Useful for driving in reverse with minimal training
		
		if(!driver.Buttons.LeftStick.current){
			//Trades is a little bit of speed for nicer cornering
			//Pressing the left stick or the button underneath disables this
			driveX *= 0.8;
			driveY *= 0.8;
		}
		
		if(driver.Buttons.B.current){//A stop button
			drive.stopMotor();
		}
		else{
			drive.arcadeDrive(driveY,driveX);
		}
		
		arm.set(driver.RightStick.Y * 0.75);
		
		//Sets intake speed
		ballIntake.set(driver.Triggers.Combined);
		
		//Moves the lift mechanism
		lift.set(operator.LeftStick.Y);
		
	}
}
