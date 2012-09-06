package com.realapps.engine.core.drawable.shape;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint.Style;

public class RectShapeDrawable extends ShapeDrawable {
	public static int MODE_FILL = -1;
	public static int MODE_LINE = 1;
	
	public static class RectShapeBuilder extends ShapeBuilder {
		private int mode = MODE_FILL;
		public RectShapeBuilder(Rect rect) {
			this(rect.left, rect.top, rect.right, rect.bottom);
		}
		public RectShapeBuilder(int left, int top, int right, int bottom) {
			addVertex(left, top);
			addVertex(right, bottom);
		}
		
		public RectShapeBuilder setMode(int mode) {
			this.mode = mode;
			return this;
		}
		
		public RectShapeDrawable build(String id) {
			mID = id;
			return new RectShapeDrawable(this);
		}
	}
	
	private int mode = MODE_FILL;
	private RectShapeDrawable(RectShapeBuilder srcBuilder) {
		super(srcBuilder);
		vertices = srcBuilder.vertices;
		mode = srcBuilder.mode;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getMode() {
		return mode;
	}
	
	@Override
	public void draw(Canvas canvas) {
		if(mode == MODE_LINE) {
			mPaint.setStyle(Style.STROKE);
		} else {
			mPaint.setStyle(Style.FILL);
		}
		canvas.drawRect(vertices.get(0).getX(), vertices.get(0).getY(), vertices.get(1).getX(), vertices.get(1).getY(), mPaint);
	}
}
