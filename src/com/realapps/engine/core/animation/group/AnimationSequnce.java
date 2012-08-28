package com.realapps.engine.core.animation.group;

import com.realapps.engine.core.animation.Animator;
import com.realapps.engine.core.drawable.Drawable;

public class AnimationSequnce extends AnimationGroup {
	public AnimationSequnce(int animationID, Animator[] animations) {
		super(animationID, animations);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void animation(Drawable drawable, long duration) {
		boolean runed = false;
		
		for(Animator animation: mAnimations) {
			if(!runed && animation.animate(drawable, duration)) {
				runed = true;
			}
		}
		if(!runed) {
			mAnimations = null;
			endAnimation(drawable);
		}
	}
}
