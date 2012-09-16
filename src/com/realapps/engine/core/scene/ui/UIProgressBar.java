package com.realapps.engine.core.scene.ui;

import android.graphics.Canvas;

import com.realapps.engine.core.drawable.ImageDrawable;
import com.realapps.engine.core.scene.GameScene;

public class UIProgressBar extends UIView {
	protected ImageDrawable border;
	protected ImageDrawable contents;
	
	protected int mProgressMax 	= 100;
	protected int mProgress		= 0;
	
	public UIProgressBar(GameScene scene, String id, ImageDrawable drawable_border, ImageDrawable drawable_contents) {
		super(scene, id, drawable_border.getWidth(), drawable_border.getHeight());
	}
	
	public void setMaxProgress(int progress) {
		mProgressMax = progress;
		
		update();
	}
	public void setProgress(int progress) {
		mProgress = progress;
		
		update();
	}
	public int getProgress() {
		return mProgress;
	}
	public float getProgressPercent() {
		return (mProgress/(float)mProgressMax)*100.0f;
	}

	@Override
	public void update() {
		border.setPosition(getX(), getY());
		contents.setPosition(getX(), getY());
		
		contents.setRenderWidth((int)(contents.getWidth()*getProgressPercent()*(mProgress/(float)mProgressMax)));
	}

	@Override
	public void draw(Canvas canvas) {
		border.draw(canvas);
		contents.draw(canvas);
	}

	@Override
	protected void onTouch(int action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		border.release();
		contents.release();
	}
}
