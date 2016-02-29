package org.usfirst.frc.team3571.robot.utilities;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *Handles Input from the Xbox Controller connected to the driver station.<br/>
 *Designed to have separate objects for the Buttons, Axis, and the Dpad so that anyone can make a local reference of either of them
 *@author TomasR
 */
public class XboxController {
    DriverStation dStation;
    int port=0, buttonState=0, buttonCount=0;
    Button[] buttons=new Button[10];
    short lRumble=0, rRumble=0;
    
    public Axis LeftStick=new Axis(0,0), RightStick=new Axis(0,0);
    public triggers Triggers=new triggers(0,0);
    public ButtonRemap Buttons;
    public POV DPad = new POV();
    
    /**
     * Sets up the controller
     * @param port Controller port
     */
    public XboxController(int port) {
    	this(port, 0, 0);
    }
    /**
     * Sets up the controller with dead zones
     * @param port Controller port
     * @param leftDeadZone The magnitude of the dead zone on the left stick
     * @param rightDeadZone The magnitude of the dead zone on the right stick
     */
    public XboxController(int port,double leftDeadZone, double rightDeadZone){
    	setDeadZones(leftDeadZone,rightDeadZone);
    	dStation = DriverStation.getInstance();
    	this.port = port;
        for(int ii = 0; ii  < 10; ii++){
        	buttons[ii]=new Button();
        }
        refresh();
        Buttons = new ButtonRemap();
        buttonCount = dStation.getStickButtonCount(port);
    }
    /**
     * Sets up the controller with dead zones
     * @param port Controller port
     * @param deadZone The magnitude of the dead zone on the each stick
     */
    public XboxController(int port, double deadZone){
    	this(port, deadZone, deadZone);
    }
    
    /**
     * Sends values to the rumble motors in the controller
     * @param type either left or right
     * @param value (0 to 1) rumble intensity value
     */
    public void vibrate(RumbleType type,float value){
        if (value < 0)
            value = 0;
        else if (value > 1)
            value = 1;
        if (type == RumbleType.left || type == RumbleType.combined)
        	lRumble = (short)(value*65535);
        if(type == RumbleType.right || type == RumbleType.combined)
        	rRumble = (short)(value*65535);
        FRCNetworkCommunicationsLibrary.HALSetJoystickOutputs((byte)port, 0, lRumble, rRumble);
    }
    
    /**
     * Returns the state of a specific button
     * @param i The button number starting with 1
     * @return True if the button is pressed else False
     * @throws Exception if the button does not exist
     */
    public boolean getRawButton(int i) throws Exception{
    	i-=1;
    	if (i >= 0 && i < buttonCount)return (buttonState & (1 << i))!=0;
    	throw new Exception(String.format("Button %d does not exist", i));
    }
    /**
     * Returns the state of a specific axis
     * @param i Axis number starting with 0
     * @return Returns a double between -1 and 1
     */
    public double getRawAxis(int i){
    	return dStation.getStickAxis(port, i);
    }
    /**
     * @return The number of buttons
     */
    public int getButtonCount(){
    	return buttonCount;
    }
    
    /**
     * Reacquires the values for all inputs
     */
    public void refresh(){
        getDpad();
    	getLeftStick();
    	getRightStick();
    	getTrigger();
    	getButtons();
    }
    /**
     * Sets the dead zones of the two sticks<br/>
     * Set either to 0 to turn the dead zone off
     * @param leftStick The magnitude of the dead zone in the LeftStick
     * @param rightStick The magnitude of the dead zone in the RightStick
     */
    public void setDeadZones(double leftStick, double rightStick){
    	LeftStick.deadZone = leftStick * leftStick;
    	RightStick.deadZone = rightStick * rightStick;
    }
    /**
     * Calculates the magnitude of on of the sticks
     * @param stick The side of the stick
     * @return The magnitude of the stick
     */
    public double getMagnitude(Sides stick){
    	if(stick==Sides.left)return Math.sqrt(LeftStick.X*LeftStick.X+LeftStick.Y*LeftStick.Y);
    	else return Math.sqrt(RightStick.X*RightStick.X+RightStick.Y*RightStick.Y);
    }
    
    void getDpad(){
    	DPad.set(dStation.getStickPOV(port, 0));
    }
    void getLeftStick(){
    	LeftStick.X = dStation.getStickAxis(port, 0);
    	LeftStick.Y = dStation.getStickAxis(port, 1);
    	LeftStick.applyDeadzone();
    }
    void getRightStick(){
    	RightStick.X = dStation.getStickAxis(port, 4);
    	RightStick.Y = dStation.getStickAxis(port, 5);
    	RightStick.applyDeadzone();
    }
    void getTrigger(){
        Triggers.Left = dStation.getStickAxis(port, 2);
        Triggers.Right = dStation.getStickAxis(port, 3);
        Triggers.combine();
    }
    void getButtons(){
    	buttonState = dStation.getStickButtons(port);
    	for(int i=0;i<10;i++){
    		buttons[i].set((buttonState & (1 << i)) !=0 );
    	}
    }
    
    public class triggers{
    	/**(0 to 1) value for the individual trigger**/
    	public double Right, Left;
    	/**(0 to 1) combined value equivalent to Right - Left**/
    	public double Combined;
    	public triggers(double r, double l){
    		Right=r;
    		Left=l;
    		combine();
    	}
    	void combine(){
    		Combined = Right - Left;
    	}
    }
    
    public class POV{
    	public boolean Up=false, Down=false, Left = false, Right=false;
    	/**Returns -1 if the Direction Pad is not pressed else it returns a compass orientation starting with up being 0**/
    	public int degrees=-1;
    	void set(int degree){
    		Up=(degree==315 || degree==0 || degree==45);
    		Down=(degree<=225 && degree>=135);
    		Left=(degree<=315 && degree>=225);
    		Right=(degree<=135 && degree>=45);
    		degrees=degree;
    	}
    }
    
    public class Axis{
        public double X, Y;
        double deadZone = 0;
        public Axis(double x,double y){
            X = x;
            Y = y;
        }
        void applyDeadzone(){
        	if(deadZone!=0){
        		if(X * X + Y * Y < deadZone){
        			X = 0;
        			Y = 0;
        		}
        	}
        }
    }
    public static class Button{
        public boolean current=false , last=false,changedDown=false,changedUp=false;
        private Trigger button;
        /**
         * Runs your command automatically<br/>
         * Acts when pressed<br/>
         * Should only be called once when setting the command<br/>
         * Requires <u>Scheduler.getInstance().run()</u>
         * @param command your custom command
         * @param state A selection of when to run the command
         */
        public void runCommand(Command command,CommandState state){
        	if(button==null)button = new Trigger(){
    			@Override
    			public boolean get() {
    				return current ;
    			}
        	};
        	switch(state){
        	case whenPressed:
            	button.whenActive(command);
        		break;
        	case toggle:
        		button.toggleWhenActive(command);
        		break;
        	case whilePressed:
        		button.whileActive(command);
        		break;
        	case whileNotPressed:
        		button.whenInactive(command);
        		button.cancelWhenActive(command);
        		break;
        	case cancel:
        		button.cancelWhenActive(command);
        		break;
        	}
        }
        void set(boolean current){
        	last=this.current;
        	this.current=current;
        	changedDown=!last && this.current;
        	changedUp=last && !this.current;
        }
    }
    public class ButtonRemap{
        public Button A = buttons[0];
        public Button B = buttons[1];
        public Button X = buttons[2];
        public Button Y = buttons[3];
        /**Left Bumper**/
        public Button LB = buttons[4];
        /**Right Bumper**/
        public Button RB = buttons[5];
        public Button Back = buttons[6];
        public Button Start = buttons[7];
        public Button LeftStick = buttons[8];
        public Button RightStick = buttons[9];
    	
    }
    public enum CommandState{
    	/**Runs the command every time it the button is pressed<br/>Does not cancel**/
    	whenPressed,
    	/**Runs the command on every first press<br/>Cancels the command on every second press**/
    	toggle,
    	/**Runs the command while the button is pressed**/
    	whilePressed,
    	/**Runs the command while the button is not pressed**/
    	whileNotPressed,
    	/**Cancels the command once the button is pressed**/
    	cancel;
    }
    public enum Sides{
    	left, right
    }
    public enum RumbleType{
    	left, right, combined;
    }
}
