package com.realapps.engine.core.util.hardware;

import android.content.Context;
import android.os.Vibrator;

public class SystemManager {
	private Vibrator mVibratorService = null;
	private String mFilePath = "";
	
	private SystemManager() {
	}
	
	private static SystemManager mInstance = new SystemManager();
	public static SystemManager getInstance() {
		return mInstance;
	}
	
	public void init(Context context) {
		mVibratorService = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		mFilePath = context.getFilesDir().getAbsolutePath();
	}
	
	public void vibrate(int time) {
		mVibratorService.vibrate(time);
	}
	
	public String getFilePath() {
		return mFilePath;
	}
}
