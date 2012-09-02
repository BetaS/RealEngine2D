package com.realapps.engine.core.scene;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;

import com.realapps.engine.core.drawable.Drawable;
import com.realapps.engine.core.drawable.DrawableManager;
import com.realapps.engine.core.drawable.ImageDrawable;
import com.realapps.engine.core.drawable.SpriteDrawable;
import com.realapps.engine.core.drawable.TextDrawable;
import com.realapps.engine.core.scene.ui.UIManager;

public abstract class GameScene {
	private DrawableManager mDrawableManager 	= new DrawableManager(this);
	private UIManager		mUIManager			= new UIManager(this);
	
	public abstract void onInit();
	public void onDestroy() {
		mDrawableManager.clear();
	}
	
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
	
	/*
	 * Background
	 */
	private int mBGColor = 0;
	public void setBackgroundColor(int r, int g, int b) {
		mBGColor = Color.argb(255, r, g, b);
	}
	public int getBackgroundColor() {
		return mBGColor;
	}
	
	/*
	 * Resource Load
	 */
	public Drawable loadText(String id, String text, int size, int priority, int x, int y) {
		return new TextDrawable.TextBuilder().setText(text).setSize(size).setPriority(priority).setPosition(x, y).build(id);
	}
	
	public Drawable loadImage(String id, Bitmap source, int priority, int x, int y) {
		return new ImageDrawable.ImageBuilder().setSource(source).setPriority(priority).setPosition(x, y).build(id);
	}
	public Drawable loadImage(String id, String source, int priority, int x, int y) {
		return new ImageDrawable.ImageBuilder().setSource(source).setPriority(priority).setPosition(x, y).build(id);
	}
	public Drawable loadImage(String id, int source, int priority, int x, int y) {
		return new ImageDrawable.ImageBuilder().setSource(source).setPriority(priority).setPosition(x, y).build(id);
	}
	
	public Drawable loadSprite(String id, Bitmap source, int priority, int x, int y, int sliceX, int sliceY) {
		return new SpriteDrawable.SpriteBuilder().setSource(source).setSpriteSlice(sliceX, sliceY).setPriority(priority).setPosition(x, y).build(id);
	}
	public Drawable loadSprite(String id, String source, int priority, int x, int y, int sliceX, int sliceY) {
		return new SpriteDrawable.SpriteBuilder().setSource(source).setSpriteSlice(sliceX, sliceY).setPriority(priority).setPosition(x, y).build(id);
	}
	public Drawable loadSprite(String id, int source, int priority, int x, int y, int sliceX, int sliceY) {
		return new SpriteDrawable.SpriteBuilder().setSource(source).setSpriteSlice(sliceX, sliceY).setPriority(priority).setPosition(x, y).build(id);
	}
	
	/*
	 * Resource Manage
	 */
	public Drawable findDrawableById(String id) {
		return mDrawableManager.get(id);
	}
	
	/*
	 * Manager
	 */
	public DrawableManager getDrawableManager() {
		return mDrawableManager;
	}
	public UIManager getUiManager() {
		return mUIManager;
	}
	
	/*
	 * Timer
	 */
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
}
