package com.realapps.engine.core.animation;

import com.realapps.engine.core.debug.logger.Debug;
import com.realapps.engine.core.drawable.Drawable;

public abstract class Animator {
	public static interface OnAnimationFinishListener {
		public void onAnimationFinish(String drawableID, int animID);
	}
	
	protected boolean mEnd = false;
	
	protected int mAnimationID = -1;
	public Animator(int animationID) {
		mAnimationID = animationID;
	}
	
	/*
	 * Animation Duration
	 */
	protected long mDuration = 0;
	public void setDuration(int duration) {
		lastRun = System.currentTimeMillis();
		mDuration = duration;
		mEnd = false;
	}
	
	/*
	 * Animation Frame Async
	 */
	protected long lastRun = 0;
	
	protected abstract void animation(Drawable drawable, long duration);

	public boolean animate(Drawable drawable, long nowTime) {
		if(!mEnd) {
			animation(drawable, (nowTime-lastRun));
			lastRun = nowTime;
		}
		return mEnd;
	}
	
	protected void endAnimation(Drawable drawable) {
		if(mAnimationID >= 0)
			Debug.log("Animation",  drawable.getId()+"/"+mAnimationID+" End");
		
		mEnd = true;
		drawable.onAnimationFinish(mAnimationID);
	}
}
