package com.realapps.engine.core.drawable;

import android.graphics.Canvas;

public class TextDrawable extends Drawable {
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_HCENTER = 2;
	public static final int ALIGN_RIGHT = 4;
	public static final int ALIGN_TOP = 8;
	public static final int ALIGN_VCENTER = 16;
	public static final int ALIGN_BOTTOM = 32;
	
	public static class TextBuilder extends Builder {
		private String mContent = "";
		private int mSize = 9;
		private int mAlign = ALIGN_LEFT|ALIGN_TOP;

		public TextBuilder setText(String content) {
			mContent = content;
			return this;
		}
		public TextBuilder setSize(int size) {
			mSize = size;
			return this;
		}
		public TextBuilder setAlign(int align) {
			mAlign = align;
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
		
		setSize(srcBuilder.mSize);
		setText(srcBuilder.mContent);
		
		if((srcBuilder.mAlign|ALIGN_HCENTER) == ALIGN_HCENTER) {
			setOffsetX(getWidth()/2);
		} else if((srcBuilder.mAlign|ALIGN_RIGHT) == ALIGN_RIGHT) {
			setOffsetX(getWidth());
		}
		
		if((srcBuilder.mAlign|ALIGN_VCENTER) == ALIGN_VCENTER) {
			setOffsetY(getHeight()/2);
		} else if((srcBuilder.mAlign|ALIGN_BOTTOM) == ALIGN_BOTTOM) {
			setOffsetY(getHeight());
		}
	}
	
	public void setSize(int size) {
		mPaint.setTextSize(size);
		setSize((int)mPaint.measureText(mContent), (int)mPaint.getTextSize()*mContent.split("\n").length);
	}
	public void setText(String content) {
		mContent = content;
		setSize((int)mPaint.measureText(mContent), (int)mPaint.getTextSize()*mContent.split("\n").length);
	}
	
	protected void update() {
		
	}
	protected void render(Canvas canvas) {
		int cnt = 0;
		for(String line: mContent.split("\n")) {
			canvas.drawText(line, getX()+mOffsetX, getY()+mPaint.getTextSize()+mOffsetY+(cnt*mPaint.getTextSize()), mPaint);
			cnt++;
		}
	}

	@Override
	public void release() {
		mPaint = null;
	}
}
