package com.realapps.engine.core.debug.logger;

import com.realapps.engine.Game;

import android.view.Gravity;
import android.widget.Toast;

public class Debug {
	public static void log(String type, String message) {
		
	}
	public static void toast(String message, boolean center) {
		Toast toast = Toast.makeText(Game.getContext(), message, Toast.LENGTH_SHORT);
		if(center)
			toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
