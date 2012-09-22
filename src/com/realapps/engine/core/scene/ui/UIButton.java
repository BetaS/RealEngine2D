package com.realapps.engine.core.scene.ui;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.realapps.engine.core.drawable.ImageDrawable;
import com.realapps.engine.core.scene.GameScene;

public class UIButton extends UIView {
	public static final int BUTTON_DOWN = 0;
	public static final int BUTTON_UP 	= 1;
	
	private ImageDrawable up_img = null;
	private ImageDrawable down_img = null;
	
	protected int mState = BUTTON_UP;
	
	public UIButton(GameScene scene, String btn_name, int drawable_off, int drawable_on) {
		this(
				scene, 
				btn_name, 
				(ImageDrawable)new ImageDrawable.ImageBuilder().setSource(drawable_off).setUIMode().build("btn_"+btn_name+"_up"), 
				(ImageDrawable)new ImageDrawable.ImageBuilder().setSource(drawable_on).setUIMode().build("btn_"+btn_name+"_down")
		);
	}
	public UIButton(GameScene scene, String btn_name, ImageDrawable off, ImageDrawable on) {
		super(scene, btn_name, off.getWidth(), off.getHeight());
		
		up_img = off;
		down_img = on;

		setState(BUTTON_UP);
	}

	@Override
	public void update() {
		up_img.setPosition(getX(), getY());
		up_img.setPriority(getPriority());
		
		down_img.setPosition(getX(), getY());
		down_img.setPriority(getPriority());
		
		if(!isShow()) {	
			up_img.hide();
			down_img.hide();
		} else if(mState == BUTTON_DOWN) {
			up_img.hide();
			down_img.show();
		} else if(mState == BUTTON_UP) {
			up_img.show();
			down_img.hide();
		}
	}
	@Override
	public void release() {
		up_img.release();
		down_img.release();
	}
	
	@Override
	public void draw(Canvas canvas) {
		up_img.draw(canvas);
		down_img.draw(canvas);
	}
	
	public void setState(int state) {
		mState = state;
		update();
	}
	
	public int getState() {
		return mState;
	}
	
	@Override
	protected void onTouch(int action) {
		if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
			setState(BUTTON_UP);
		} else {
			setState(BUTTON_DOWN);
		}
	}
}
