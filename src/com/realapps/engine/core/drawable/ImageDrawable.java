package com.realapps.engine.core.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.realapps.engine.core.resource.BitmapManager;

public class ImageDrawable extends Drawable {
	public static class ImageBuilder extends Builder {
		protected Bitmap mBitmap 		= null;
		
		private int mAnimationSliceX 	= 1;
		private int mAnimationSliceY 	= 1;
		
		private float mScale = 1.0f;
		
		public ImageBuilder setSource(Bitmap bitmap) {
			mBitmap = bitmap;
			return this;
		}
		public ImageBuilder setSource(String local_path) {
			mBitmap = BitmapManager.getManager().getImage(local_path);
			return this;
		}
		public ImageBuilder setSource(int resourceID) {
			mBitmap = BitmapManager.getManager().getImage(resourceID);
			return this;
		}
		public ImageBuilder setScale(float scale) {
			mScale = scale;
			return this;
		}
		public ImageBuilder setSpriteSlice(int x, int y) {
			mAnimationSliceX = x;
			mAnimationSliceY = y;
			return this;
		}
		
		public ImageDrawable build(String id) {
			mID = id;
			return new ImageDrawable(this);
		}
	}
	
	private Bitmap mBitmap = null;
	
	private boolean mAnimationPlay = false;
	
	private int mAnimationSliceX = 0;
	private int mAnimationSliceY = 0;
	
	private int mAnimationIndex = 0;
	
	private ImageDrawable(ImageBuilder srcBuilder) {
		super(srcBuilder);
		
		if(srcBuilder.mScale == 1)
			mBitmap 			= srcBuilder.mBitmap;
		else 
			mBitmap = Bitmap.createScaledBitmap(srcBuilder.mBitmap, (int)(srcBuilder.mBitmap.getWidth()*srcBuilder.mScale), (int)(srcBuilder.mBitmap.getHeight()*srcBuilder.mScale), true);	
		
		mAnimationSliceX 	= srcBuilder.mAnimationSliceX;
		mAnimationSliceY 	= srcBuilder.mAnimationSliceY;
		
		// Set Size
		setSize((mBitmap.getWidth()/mAnimationSliceX), (mBitmap.getHeight()/mAnimationSliceY));
	}
	
	protected void update() {
		if(mAnimationPlay) {
			if((++mAnimationIndex) >= (mAnimationSliceX*mAnimationSliceY)) {
				mAnimationIndex = 0;
			}
		}
	}
	protected void render(Canvas canvas) {
		if(mBitmap != null && !mBitmap.isRecycled()) {
			int X = mAnimationIndex%mAnimationSliceX;
			int Y = mAnimationIndex/mAnimationSliceX;
		
			canvas.drawBitmap(mBitmap, new Rect(X*getWidth(), Y*getHeight(), (X+1)*getWidth(), (Y+1)*getHeight()), new Rect(getX(), getY(), getX()+getWidth(), getY()+getHeight()), mPaint);
		}
	}
	
	public void delete() {
		mBitmap.recycle();
		mBitmap = null;
	}
}
