package com.realapps.engine.core.scene.ui;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.realapps.engine.core.renderer.RenderManager;
import com.realapps.engine.core.scene.GameScene;

public abstract class UIView {
	public static final int CENTER = Integer.MIN_VALUE;
	
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
		update();
	}
	public void hide() {
		mShow = false;
		update();
	}
	public boolean isShow() {
		return mShow;
	}
	
	public void setPosition(int x, int y) {
		if(x == CENTER) {
			x = RenderManager.getManager().getCanvasWidth()/2;
			x -= getWidth()/2;
		}
		if(y == CENTER) {
			y = RenderManager.getManager().getCanvasHeight()/2;
			y -= getHeight()/2;
		}
		
		Log.e("Position", "X: "+x+", Y: "+y);
		
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
		Log.e("touch", "action: "+action);
		Log.e("touceh", "x: "+x+", y: "+y);
		
		if(action == MotionEvent.ACTION_CANCEL) {
			onTouch(action);
		} else {
			if((mX <= x && x <= mX+mWidth) && (mY <= y && y <= mY+mHeight)) {
				onTouch(action);
				if(action == MotionEvent.ACTION_UP)
					mScene.onClick(this);
			} else if(action == MotionEvent.ACTION_MOVE) {
				onTouch(MotionEvent.ACTION_CANCEL);
			}
		}
	}
	
	protected abstract void onTouch(int action);
	public abstract void draw(Canvas canvas);
}
