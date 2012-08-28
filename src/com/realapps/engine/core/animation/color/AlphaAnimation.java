package com.realapps.engine.core.animation.color;

import com.realapps.engine.core.animation.Animator;
import com.realapps.engine.core.drawable.Drawable;


public class AlphaAnimation extends Animator {
	private int mAlpha = 0;
	
	private float floatTmp = 0;
	
	public AlphaAnimation(int animationID, int alpha, int duration) {
		super(animationID);
		setDuration(duration);
		
		mAlpha 	= alpha;
	}
	
	@Override
	protected void animation(Drawable drawable, long duration) {
		int a = drawable.getAlpha();
		
		float da = ((mAlpha-a)*((float)duration/mDuration));
		
		mDuration -= duration;
		if(mDuration < 0) duration += mDuration;	
		
		floatTmp += da-Math.floor(da); floatTmp -= Math.floor(floatTmp);
		
		a += Math.floor(da)+Math.floor(floatTmp);
		
		drawable.setAlpha(a);
		if(mDuration <= 0) {
			drawable.setAlpha(mAlpha);
			endAnimation(drawable);
		}
	}
}
