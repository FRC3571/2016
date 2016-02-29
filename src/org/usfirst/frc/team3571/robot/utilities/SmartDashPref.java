package org.usfirst.frc.team3571.robot.utilities;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;
import edu.wpi.first.wpilibj.Preferences;

/**
 * This class updates the values on the SmartDashboard from either the Preferences or the default value if the first fails
 * @author Tomas
 *
 */
public class SmartDashPref {
	static Preferences pref = Preferences.getInstance();
	static Vector<sType> vec= new Vector<sType>();
	
	public static int addInt(String key, int defaultVal){
		if (!pref.containsKey(key)) {
    		pref.putInt(key, defaultVal);
    		SmartDashboard.putNumber(key, defaultVal);
    	}
		else SmartDashboard.putNumber(key, defaultVal = pref.getInt(key, defaultVal));
		vec.add(new sType(key,'i'));
		return defaultVal;
	}
	
	public static double addDouble(String key, double defaultVal){
		if (!pref.containsKey(key)) {
    		pref.putDouble(key, defaultVal);
    		SmartDashboard.putNumber(key, defaultVal);
    	}
		else SmartDashboard.putNumber(key, defaultVal = pref.getDouble(key, defaultVal));
		vec.add(new sType(key,'d'));
		return defaultVal;
	}
	
	public static String addString(String key, String defaultVal){
		if (!pref.containsKey(key)) {
    		pref.putString(key, defaultVal);
    		SmartDashboard.putString(key, defaultVal);
    	}
		else SmartDashboard.putString(key, defaultVal = pref.getString(key, defaultVal));
		vec.add(new sType(key,'s'));
		return defaultVal;
	}
	
	/**
	 * Updates all added values from SmartDashBoard to Preferences
	 */
	public static void updateAll(){
		for(sType s : vec){
			switch(s.type){
			case 'i':
				pref.putInt(s.key, (int)SmartDashboard.getNumber(s.key));
				break;
			case 'd':
				pref.putDouble(s.key, SmartDashboard.getNumber(s.key));
				break;
			case 's':
				pref.putString(s.key, SmartDashboard.getString(s.key));
				break;
			}
		}
	}
	private static class sType{
		public String key;
		public char type;
		public sType(String key, char type){
			this.key=key;
			this.type=type;
		}
	}
}
