package com.realapps.engine.core.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SpriteDrawable extends ImageDrawable {
	public static class SpriteBuilder extends ImageBuilder {
		private int mAnimationSliceX 	= 1;
		private int mAnimationSliceY 	= 1;
		private int mAnimationSpeed		= 1;
		
		public SpriteBuilder setSource(Bitmap bitmap) {
			super.setSource(bitmap);
			return this;
		}
		public SpriteBuilder setSource(String local_path) {
			super.setSource(local_path);
			return this;
		}
		public SpriteBuilder setSource(int resourceID) {
			super.setSource(resourceID);
			return this;
		}
		
		public SpriteBuilder setSpriteSlice(int x, int y) {
			mAnimationSliceX = x;
			mAnimationSliceY = y;
			return this;
		}
		
		public SpriteBuilder setSpriteSpeed(int fps) {
			mAnimationSpeed = fps;
			return this;
		}
		
		public SpriteDrawable build(String id) {
			mID = id;
			return new SpriteDrawable(this);
		}
	}
	
	private boolean mAnimationPlay = true;
	
	private int mAnimationSliceX = 0;
	private int mAnimationSliceY = 0;
	
	private int mAnimationSpeed = 0;
	private int mAnimationSpeedCnt = 0;
	
	private int mAnimationIndex = 0;
	
	private int mAnimationStart	= 0;
	private int mAnimationEnd	= 0;
	
	private SpriteDrawable(SpriteBuilder srcBuilder) {
		super(srcBuilder);
		
		mAnimationSliceX 	= srcBuilder.mAnimationSliceX;
		mAnimationSliceY 	= srcBuilder.mAnimationSliceY;
		setAnimationSpeed(srcBuilder.mAnimationSpeed);
		setAnimationRange(0, (mAnimationSliceX*mAnimationSliceY)-1);
		
		// Set Size
		setSize((mBitmap.getWidth()/mAnimationSliceX), (mBitmap.getHeight()/mAnimationSliceY));
	}
	
	public void setAnimationRange(int start_idx, int end_idx) {
		mAnimationStart = start_idx;
		mAnimationEnd	= end_idx;
	}
	public void setAnimationSpeed(int fps) {
		mAnimationSpeed	= fps;
		mAnimationSpeedCnt = mAnimationSpeed;
	}
	
	public int getAnimationIndex() {
		return mAnimationIndex;
	}
	
	public void play() {
		mAnimationPlay = true;
	}
	public void stop() {
		mAnimationPlay = false;
	}
	public boolean isPlay() {
		return mAnimationPlay;
	}
	
	protected void update() {
		if(mShow && mAnimationPlay) {
			if(mAnimationSpeedCnt <= 0) {
	 			if((++mAnimationIndex) > mAnimationEnd) {
					mAnimationIndex = mAnimationStart;
				}
	 			mAnimationSpeedCnt = mAnimationSpeed;
			}
			
			mAnimationSpeedCnt--;
		}
	}
	protected void render(Canvas canvas) {
		if(mBitmap != null && !mBitmap.isRecycled()) {
			int X = mAnimationIndex%mAnimationSliceX;
			int Y = mAnimationIndex/mAnimationSliceX;
		
			canvas.drawBitmap(mBitmap, new Rect(X*getWidth(), Y*getHeight(), (X+1)*getWidth(), (Y+1)*getHeight()), new Rect(getX(), getY(), getX()+getWidth(), getY()+getHeight()), mPaint);
		}
	}
}
