package com.realapps.engine.core.drawable.ui;

import android.view.MotionEvent;

import com.realapps.engine.core.drawable.ImageDrawable;

public class UIButton extends UIView {
	public static final int BUTTON_DOWN = 0;
	public static final int BUTTON_UP 	= 1;
	
	public UIButton(String btn_name, int drawable_off, int drawable_on) {
		this(new ImageDrawable.ImageBuilder().setSource(drawable_off).build("btn_"+btn_name+"_up"), new ImageDrawable.ImageBuilder().setSource(drawable_on).build("btn_"+btn_name+"_down"));
	}
	public UIButton(ImageDrawable off, ImageDrawable on) {
		super(off.getWidth(), off.getHeight());
		
		mImages = new ImageDrawable[2];
		
		mImages[0] = off;
		mImages[1] = on;
		
		setState(BUTTON_DOWN);
	}

	public void setState(int state) {
		if(!mShow) {	
			mImages[0].hide();
			mImages[1].hide();
		} else if(state == BUTTON_DOWN) {	
			mImages[0].hide();
			mImages[1].show();
		} else if(state == BUTTON_UP) {
			mImages[0].show();
			mImages[1].hide();
		}
	}
	
	@Override
	protected void touched(int action) {
		if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
			setState(BUTTON_UP);
		} else {
			setState(BUTTON_DOWN);
		}
	}
	
}
