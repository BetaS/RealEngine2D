package com.realapps.engine.core.scene.ui;

import android.util.Log;
import android.view.MotionEvent;

import com.realapps.engine.core.drawable.ImageDrawable;

public abstract class UIView {
	protected String mID = "";
	protected ImageDrawable[] mImages = null;
	
	protected abstract void touched(int action);
	
	public UIView(String id, int width, int height) {
		mID = id;
		setSize(width, height);
	}
	
	public String getId() {
		return mID;
	}
	
	public void touch(MotionEvent event) {
		touch(event.getAction()%260, (int)event.getX(event.getAction()/260), (int)event.getY(event.getAction()/260));
	}
	public void touch(int action, int x, int y) {
		Log.e("Touched", action+", x="+x+", y="+y);
		
		if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
			touched(action);
		} else {
			if((mX <= x && x <= mX+mWidth) && (mY <= y && y <= mY+mHeight)) {
				touched(action);
			} else if(action == MotionEvent.ACTION_MOVE) {
				touched(MotionEvent.ACTION_CANCEL);
			}
		}
	}
	
	/*
	 * UI Showing
	 */
	protected boolean mShow = true;
	public void show() {
		mShow = true;
	}
	public void hide() {
		mShow = false;
	}
	public boolean isShow() {
		return mShow;
	}
	
	/*
	 * UI Scale
	 */
	protected int mWidth = 0;
	protected int mHeight = 0;
	public void setSize(int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	public int getWidth() {
		return mWidth;
	}
	public int getHeight() {
		return mHeight;
	}
	
	/*
	 * UI Position
	 */
	protected int mX = 0;
	protected int mY = 0;
	public void setPosition(int x, int y) {
		mX = x; 
		mY = y;

		for(ImageDrawable image: mImages) {
			image.setPosition(x, y);
		}
	}
	public int getX() {
		return mX;
	}
	public int getY() {
		return mY;
	}
	
	/*
	 * Draw Priority
	 */
	protected short mPriority = 0;
	public void setPriority(int priority) {
		mPriority = (short)priority;
		for(ImageDrawable image: mImages) {
			image.setPriority(priority);
		}
	}
	public int getPriority() {
		return mPriority;
	}
}
