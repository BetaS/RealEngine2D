package com.realapps.engine.core.animation.translate;

import com.realapps.engine.core.animation.Animator;
import com.realapps.engine.core.drawable.Drawable;


public class AccelerationAnimation extends Animator {
	private double mAngle = 0;
	private double mForce = 0;
	
	private float floatX = 0;
	private float floatY = 0;
	
	public AccelerationAnimation(int animationID, double force, int degree) {
		super(animationID);
		setDuration(0);
		
		mForce 	= force;
		mAngle = Math.toRadians(degree);
	}
	
	@Override
	protected void animation(Drawable drawable, long duration) {
		int x = drawable.getX();
		int y = drawable.getY();
		
		float dx = (float)(mForce*Math.cos(mAngle));
		float dy = (float)(mForce*Math.sin(mAngle));
		
		mDuration -= duration;
		if(mDuration < 0) duration += mDuration;
		
		mForce *= 0.8;
		
		floatX += dx-Math.floor(dx)-Math.floor(floatX);
		floatY += dy-Math.floor(dy)-Math.floor(floatY);
		
		x += Math.floor(dx)+Math.floor(floatX);
		y += Math.floor(dy)+Math.floor(floatY);
		if(Math.floor(dx)+Math.floor(floatX) <= 0 && Math.floor(dy)+Math.floor(floatY) <= 0) {
			endAnimation(drawable);
		}
		
		drawable.setPosition(x, y);
	}
}
