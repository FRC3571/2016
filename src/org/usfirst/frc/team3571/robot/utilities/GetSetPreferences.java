package org.usfirst.frc.team3571.robot.utilities;

import edu.wpi.first.wpilibj.Preferences;

/**
 * This class solves the issue of Preferences not saving the default value on a get when  the key does not exist
 * @author Tomas
 */
public class GetSetPreferences{
	static Preferences pref = Preferences.getInstance();
	public static int Int(String key,int dafaultVal){
		if(pref.containsKey(key))return pref.getInt(key, dafaultVal);
		else pref.putInt(key, dafaultVal);
		return dafaultVal;
	}
	public static double Double(String key,double dafaultVal){
		if(pref.containsKey(key))return pref.getDouble(key, dafaultVal);
		else pref.putDouble(key, dafaultVal);
		return dafaultVal;
	}
	public static String String(String key,String dafaultVal){
		if(pref.containsKey(key))return pref.getString(key, dafaultVal);
		else pref.putString(key, dafaultVal);
		return dafaultVal;
	}
}
