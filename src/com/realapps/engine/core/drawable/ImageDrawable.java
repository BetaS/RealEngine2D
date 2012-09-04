package com.realapps.engine.core.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.realapps.engine.core.resource.BitmapManager;

public class ImageDrawable extends Drawable {
	public static class ImageBuilder extends Builder {
		protected Bitmap mBitmap 		= null;
		
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
		
		public ImageDrawable build(String id) {
			mID = id;
			return new ImageDrawable(this);
		}
	}
	
	protected Bitmap mBitmap = null;
	protected Rect mRenderRect = null;
	protected Rect mImageRect = null;
	
	protected ImageDrawable(ImageBuilder srcBuilder) {
		super(srcBuilder);
		
		if(srcBuilder.mScale == 1)
			mBitmap = srcBuilder.mBitmap;
		else 
			mBitmap = Bitmap.createScaledBitmap(srcBuilder.mBitmap, (int)(srcBuilder.mBitmap.getWidth()*srcBuilder.mScale), (int)(srcBuilder.mBitmap.getHeight()*srcBuilder.mScale), true);	
		
		// Set Size
		setSize(mBitmap.getWidth(), mBitmap.getHeight());
	}
	
	public Rect getBounds() {
		return mRenderRect;
	}
	
	@Override
	protected void setSize(int width, int height) {
		super.setSize(width, height);
	
		mImageRect = new Rect(0, 0, getWidth(), getHeight());
		mRenderRect = new Rect(getX(), getY(), getX()+getWidth(), getY()+getHeight());
	}
	
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);

		mRenderRect = new Rect(getX(), getY(), getX()+getWidth(), getY()+getHeight());
	}
	
	protected void update() { }
	protected void render(Canvas canvas) {
		if(mBitmap != null && !mBitmap.isRecycled()) {
			canvas.drawBitmap(mBitmap, mImageRect, mRenderRect, mPaint);
		}
	}
	
	@Override
	public void release() {
		mBitmap = null;
	}
}
