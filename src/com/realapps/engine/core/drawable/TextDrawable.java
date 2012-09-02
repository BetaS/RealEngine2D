package com.realapps.engine.core.drawable;

import android.graphics.Canvas;

public class TextDrawable extends Drawable {
	public static class TextBuilder extends Builder {
		private String mContent = "";
		private int mSize = 9;

		public TextBuilder setText(String content) {
			mContent = content;
			return this;
		}
		public TextBuilder setSize(int size) {
			mSize = size;
			return this;
		}
				
		public TextDrawable build(String id) {
			mID = id;
			return new TextDrawable(this);
		}
	}

	private String mContent = "";
	private TextDrawable(TextBuilder srcBuilder) {
		super(srcBuilder);
		
		mContent = srcBuilder.mContent;
		
		mPaint.setTextSize(srcBuilder.mSize);
		
		// Set Size
		setSize(mPaint.getTextWidths(mContent, new float[mContent.length()]), (int)mPaint.getTextSize());
	}
	
	public void setSize(int size) {
		mPaint.setTextSize(size);
		setSize(mPaint.getTextWidths(mContent, new float[mContent.length()]), (int)mPaint.getTextSize());
	}
	public void setText(String content) {
		mContent = content;
		setSize(mPaint.getTextWidths(mContent, new float[mContent.length()]), (int)mPaint.getTextSize());
	}
	
	protected void update() {
		
	}
	protected void render(Canvas canvas) {
		canvas.drawText(mContent, getX(), getY()+getHeight(), mPaint);
	}

	@Override
	public void release() {
		mPaint = null;
	}
}
