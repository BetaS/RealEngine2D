package com.realapps.engine.core.drawable.shape;

import android.graphics.Canvas;

public class CircleShapeDrawable extends ShapeDrawable {
	public static class CircleShapeBuilder extends ShapeBuilder {
		protected int radius = 0;
		
		public CircleShapeBuilder(int x, int y, int radius) {
			addVertex(x, y);
			this.radius = radius;
		}
		
		public CircleShapeDrawable build(String id) {
			mID = id;
			return new CircleShapeDrawable(this);
		}
	}
	
	protected int radius = 0;
	private CircleShapeDrawable(CircleShapeBuilder srcBuilder) {
		super(srcBuilder);
		vertices = srcBuilder.vertices;
		radius = srcBuilder.radius;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(vertices.get(0).getX(), vertices.get(0).getY(), radius, mPaint);
	}
}
