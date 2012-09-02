package com.realapps.engine.core.scene;

import java.util.ArrayList;

import com.realapps.engine.core.drawable.DrawableManager;
import com.realapps.engine.core.scene.ui.UIManager;

import android.graphics.Color;
import android.view.MotionEvent;

public abstract class GameScene {
	private DrawableManager mDrawableManager 	= new DrawableManager(this);
	private UIManager		mUIManager			= new UIManager(this);
	
	public abstract void onInit();
	public abstract void onDestroy();
	
	public void onStart() {}
	public void onStop() {}
	
	public abstract void onPreRender();
	public void onPostRender() {
		long now = System.currentTimeMillis();
		
		int size = mTimerList.size();
		for(int i=0; i<size; i++) {
			Timer timer = mTimerList.get(i);
			if((now-timer.mLastRun) >= timer.mTime) {
				onTimer(timer.mIdx);
				if(timer.mRepeat > 0) {
					timer.mRepeat--;
					if(timer.mRepeat <= 0) {
						mTimerList.remove(i);
					}
				}
				
				timer.mLastRun = now;
			}
		}
	}
	
	public abstract void onTouchScreen(MotionEvent event);
	public abstract void onTimer(int timer_idx);
	
	public void onAnimationFinish(String drawableID, int animID) {}
	
	public DrawableManager getDrawableManager() {
		return mDrawableManager;
	}
	public UIManager getUiManager() {
		return mUIManager;
	}
	
	private static class Timer {
		public int mIdx;
		public int mTime;
		public int mRepeat;
		
		public long mLastRun;
		
		public Timer(int idx, int time, int repeat) {
			mIdx = idx; mTime = time; mRepeat = repeat;
			mLastRun = System.currentTimeMillis();
		}
	}
	private ArrayList<Timer> mTimerList = new ArrayList<Timer>();
	public void setTimer(int timer_idx, int time, int repeat) {
		mTimerList.add(new Timer(timer_idx, time, repeat));
	}
	
	private int mBGColor = 0;
	public void setBackgroundColor(int r, int g, int b) {
		mBGColor = Color.argb(255, r, g, b);
	}
	public int getBackgroundColor() {
		return mBGColor;
	}
}
