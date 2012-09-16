package com.realapps.engine.core.util.hardware;

import android.content.Context;
import android.os.Vibrator;

public class HardwareManager {
	private Vibrator mVibratorService = null;
	
	private HardwareManager() {
	}
	
	private static HardwareManager mInstance = new HardwareManager();
	public static HardwareManager getInstance() {
		return mInstance;
	}
	
	public void init(Context context) {
		mVibratorService = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	public void vibrate(int time) {
		mVibratorService.vibrate(time);
	}
}
