package com.realapps.engine.core.animation;

import com.realapps.engine.core.drawable.Drawable;

public class DelayAnimation extends Animator {
	public DelayAnimation(int animationID, int duration) {
		super(animationID);
		setDuration(duration);
	}
	
	@Override
	protected void animation(Drawable drawable, long duration) {
		mDuration -= duration;
		
		if(mDuration <= 0) {
			endAnimation(drawable);
		}
	}
}
