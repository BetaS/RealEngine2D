package com.realapps.engine.core.animation.translate;

import com.realapps.engine.core.animation.Animator;
import com.realapps.engine.core.drawable.Drawable;


public class PositionAnimation extends Animator {
	private int mToX = 0;
	private int mToY = 0;
	
	private float floatX = 0;
	private float floatY = 0;
	
	public PositionAnimation(int animationID, int toX, int toY, int duration) {
		super(animationID);
		setDuration(duration);
		
		mToX 	= toX;
		mToY 	= toY;
	}
	
	@Override
	protected void animation(Drawable drawable, long duration) {
		int x = drawable.getX();
		int y = drawable.getY();
		
		float dx = ((mToX-x)*((float)duration/mDuration));
		float dy = ((mToY-y)*((float)duration/mDuration));
		
		mDuration -= duration;
		if(mDuration < 0) duration += mDuration;	
		
		floatX += dx-Math.floor(dx); floatX -= Math.floor(floatX);
		floatY += dy-Math.floor(dy); floatY -= Math.floor(floatY);
		
		x += Math.floor(dx)+Math.floor(floatX);
		y += Math.floor(dy)+Math.floor(floatY);
		
		drawable.setPosition(x, y);
		if(mDuration <= 0) {
			drawable.setPosition(mToX, mToY);
			endAnimation(drawable);
		}
	}
}
