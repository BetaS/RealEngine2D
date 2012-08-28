package com.realapps.engine.core.animation.group;

import com.realapps.engine.core.animation.Animator;
import com.realapps.engine.core.drawable.Drawable;

public class AnimationGroup extends Animator {
	protected Animator[] mAnimations = null;

	public AnimationGroup(int animationID, Animator... animations) {
		super(animationID);
		
		mAnimations = animations;
	}
	public AnimationGroup(int animationID, int duration, Animator... animations) {
		this(animationID, animations);

		for(Animator animation: mAnimations) {
			animation.setDuration(duration);
		}
	}

	@Override
	public boolean animate(Drawable drawable, long nowTime) {
		if(!mEnd) {
			boolean runed = false;
			
			for(Animator animation: mAnimations) {
				if(!animation.animate(drawable, nowTime)) {
					runed = true;
				}
			}

			lastRun = nowTime;
			
			if(!runed) {
				mAnimations = null;
				endAnimation(drawable);
			}
		}
		return mEnd;
	}
	
	@Override
	protected void animation(Drawable drawable, long duration) {	
	}
}
