package com.realapps.engine.core.animation.translate;

import com.realapps.engine.core.animation.Animator;
import com.realapps.engine.core.drawable.Drawable;


public class TranslateAnimation extends Animator {
	private int mDistX = 0;
	private int mDistY = 0;
	
	private float floatX = 0;
	private float floatY = 0;
	
	public TranslateAnimation(int animationID, int distX, int distY, int duration) {
		super(animationID);
		setDuration(duration);
		
		mDistX 	= distX;
		mDistY 	= distY;
	}
	
	@Override
	protected void animation(Drawable drawable, long duration) {
		int x = drawable.getX();
		int y = drawable.getY();
		
		float dx = (mDistX*((float)duration/mDuration));
		float dy = (mDistY*((float)duration/mDuration));
		
		mDuration -= duration;
		if(mDuration < 0) duration += mDuration;
		
		if(Math.abs(mDistX) < Math.abs(dx)) dx = mDistX;
		if(Math.abs(mDistY) < Math.abs(dy)) dy = mDistY;
		
		mDistX -= dx; mDistY -= dy;
		
		floatX += dx-Math.floor(dx); floatX -= Math.floor(floatX);
		floatY += dy-Math.floor(dy); floatY -= Math.floor(floatY);
		
		x += Math.floor(dx)+Math.floor(floatX);
		y += Math.floor(dy)+Math.floor(floatY);
		
		drawable.setPosition(x, y);
		if(mDuration <= 0) {
			drawable.setPosition(x+mDistX, y+mDistY);
			endAnimation(drawable);
		}
	}
}
