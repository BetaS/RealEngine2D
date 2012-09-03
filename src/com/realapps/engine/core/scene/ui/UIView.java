package com.realapps.engine.core.scene.ui;

import com.realapps.engine.core.scene.GameScene;

import android.util.Log;
import android.view.MotionEvent;

public abstract class UIView {
	private String mID = "";
	
	protected GameScene mScene = null;

	private boolean mShow = true;
	private int mPriority = 0;
	
	private int mX = 0;
	private int mY = 0;
	
	private int mWidth = 0;
	private int mHeight = 0;
	
	public UIView(GameScene scene, String id, int width, int height) {
		mScene = scene;
		mID = id;
		setSize(width, height);
	}
	
	public abstract void update();
	public abstract void release();
	
	/*
	 * Status
	 */
	public String getId() {
		return mID;
	}

	public void setPriority(int priority) {
		mPriority = priority;
		
		update();
	}
	public int getPriority() {
		return mPriority;
	}
	
	public void show() {
		mShow = true;
	}
	public void hide() {
		mShow = false;
	}
	public boolean isShow() {
		return mShow;
	}
	
	public void setPosition(int x, int y) {
		mX = x; 
		mY = y;

		update();
	}
	public int getX() {
		return mX;
	}
	public int getY() {
		return mY;
	}
	
	public void setSize(int width, int height) {
		mWidth = width;
		mHeight = height;
		
		update();
	}
	public int getWidth() {
		return mWidth;
	}
	public int getHeight() {
		return mHeight;
	}
	
	/*
	 * Touch Event
	 */
	public void touch(MotionEvent event) {
		touch(event.getAction()%260, (int)event.getX(event.getAction()/260), (int)event.getY(event.getAction()/260));
	}
	public void touch(int action, int x, int y) {
		Log.e("Touched", action+", x="+x+", y="+y);
		
		if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
			onTouch(action);
		} else {
			if((mX <= x && x <= mX+mWidth) && (mY <= y && y <= mY+mHeight)) {
				onTouch(action);
			} else if(action == MotionEvent.ACTION_MOVE) {
				onTouch(MotionEvent.ACTION_CANCEL);
			}
		}
	}
	
	protected abstract void onTouch(int action);
}
