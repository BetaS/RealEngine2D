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
	public void setPadding(int left, int top, int right, int bottom) {
		super.setPadding(left, top, right, bottom);

		updateRect();
	}
	
	@Override
	protected void setSize(int width, int height) {
		super.setSize(width, height);
		
		updateRect();
	}
	
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		
		updateRect();
	}
	
	public void setRenderWidth(int width) {
		mImageRect.right = width;
	}
	
	protected void updateRect() {
		mImageRect = new Rect(getPadding().left, getPadding().top, getWidth()-getPadding().right, getHeight()-getPadding().bottom);
		mRenderRect = new Rect(getX()+getPadding().left, getY()+getPadding().top, getX()+getWidth()-getPadding().right, getY()+getHeight()-getPadding().bottom);
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
