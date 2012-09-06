package com.realapps.engine.core.drawable.shape;

import java.util.ArrayList;

import android.graphics.Canvas;

import com.realapps.engine.core.drawable.Drawable;
import com.realapps.engine.core.util.display.Point;

public class ShapeDrawable extends Drawable {
	public static class ShapeBuilder extends Builder {
		protected ArrayList<Point> vertices = new ArrayList<Point>();
		protected int border = 0;
		
		public ShapeBuilder addVertex(int x, int y) {
			vertices.add(new Point(x, y));
			return this;
		}
		
		public ShapeDrawable build(String id) {
			mID = id;
			return new ShapeDrawable(this);
		}
	}
	
	protected ArrayList<Point> vertices = new ArrayList<Point>();
	
	protected ShapeDrawable(ShapeBuilder srcBuilder) {
		super(srcBuilder);
		vertices = srcBuilder.vertices;
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void render(Canvas canvas) {
		Point last_pt = null;
		for(Point pt: vertices) {
			if(last_pt != null) {
				canvas.drawLine(last_pt.getX(), last_pt.getY(), pt.getX(), pt.getY(), mPaint);
			}
			last_pt = pt;
		}
	}
	
	@Override
	public void release() {
		
	}
}
