package com.realapps.engine.core.scene.ui;

import android.view.MotionEvent;

import com.realapps.engine.core.drawable.ImageDrawable;
import com.realapps.engine.core.scene.GameScene;

public class UIButton extends UIView {
	public static final int BUTTON_DOWN = 0;
	public static final int BUTTON_UP 	= 1;
	
	private ImageDrawable up_img = null;
	private ImageDrawable down_img = null;
	
	public UIButton(GameScene scene, String btn_name, int drawable_off, int drawable_on) {
		this(scene, btn_name, new ImageDrawable.ImageBuilder().setSource(drawable_off).build("btn_"+btn_name+"_up"), new ImageDrawable.ImageBuilder().setSource(drawable_on).build("btn_"+btn_name+"_down"));
	}
	public UIButton(GameScene scene, String btn_name, ImageDrawable off, ImageDrawable on) {
		super(scene, btn_name, off.getWidth(), off.getHeight());
		
		up_img = off;
		down_img = on;
		
		setState(BUTTON_DOWN);
	}

	@Override
	public void update() {
		up_img.setPosition(getX(), getY());
		up_img.setPriority(getPriority());
		
		down_img.setPosition(getX(), getY());
		down_img.setPriority(getPriority());
	}
	@Override
	public void release() {
		mScene.getDrawableManager().remove(up_img.getId());
		mScene.getDrawableManager().remove(down_img.getId());
	}
	
	protected void setState(int state) {
		if(!isShow()) {	
			up_img.hide();
			down_img.hide();
		} else if(state == BUTTON_DOWN) {
			up_img.hide();
			down_img.show();
		} else if(state == BUTTON_UP) {
			up_img.show();
			down_img.hide();
		}
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
