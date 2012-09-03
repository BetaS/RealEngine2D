package com.realapps.engine.core.animation.translate;

import com.realapps.engine.core.animation.Animator;
import com.realapps.engine.core.drawable.Drawable;


public class RotateAnimation extends Animator {
	private float mDistDegree = 0.0f;
	private float mSpeed = 0.0f;
	
	public RotateAnimation(int animationID, float distDegree, int duration) {
		super(animationID);
		setDuration(duration);
		
		mDistDegree = distDegree;
		mSpeed = mDistDegree/duration;
	}
	
	@Override
	protected void animation(Drawable drawable, long duration) {
		float degree = drawable.getRotate();
		
		mDuration -= duration;
		if(mDuration < 0) duration += mDuration;
		
		drawable.setRotate(degree+mSpeed);
		if(mDuration <= 0) {
			drawable.setRotate(mDistDegree%360);
			endAnimation(drawable);
		}
	}
}
