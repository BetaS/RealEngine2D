package com.realapps.engine.core.drawable;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.realapps.engine.core.util.display.Point;

public class ShapeDrawable extends Drawable {
	public static class ShapeBuilder extends Builder {
		protected ArrayList<Point> vertices = new ArrayList<Point>();
		protected int color = 0x000000;
		protected int border = 0;
		
		public ShapeBuilder addVertex(int x, int y) {
			vertices.add(new Point(x, y));
			return this;
		}
		
		public ShapeBuilder setColor(int color) {
			this.color = color;
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
	
	private ArrayList<Point> vertices = new ArrayList<Point>();
	private int border = 0;
	private int color = 0x000000;
	
	private ShapeDrawable(ShapeBuilder srcBuilder) {
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
}
