package com.realapps.engine.core.drawable.shape;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;

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
		
		public ShapeBuilder setBorder(int border) {
			this.border = border;
			return this;
		}
		
		public ShapeDrawable build(String id) {
			mID = id;
			return new ShapeDrawable(this);
		}
	}
	
	protected ArrayList<Point> vertices = new ArrayList<Point>();
	protected int border = 0;
	protected int color = 0x000000;
	
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
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(border);
		
		Point last_pt = null;
		for(Point pt: vertices) {
			if(last_pt != null) {
				canvas.drawLine(last_pt.getX(), last_pt.getY(), pt.getX(), pt.getY(), paint);
			}
			last_pt = pt;
		}
	}
	
	@Override
	public void release() {
		
	}
}
