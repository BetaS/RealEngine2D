package com.realapps.engine.core.scene;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.realapps.engine.core.drawable.Drawable;
import com.realapps.engine.core.drawable.DrawableManager;
import com.realapps.engine.core.drawable.ImageDrawable;
import com.realapps.engine.core.drawable.SpriteDrawable;
import com.realapps.engine.core.drawable.TextDrawable;
import com.realapps.engine.core.scene.SceneManager.SceneChanger;
import com.realapps.engine.core.scene.ui.UIButton;
import com.realapps.engine.core.scene.ui.UIManager;
import com.realapps.engine.core.scene.ui.UISwitchButton;
import com.realapps.engine.core.scene.ui.UIView;
import com.realapps.engine.core.util.physics.PhysicsManager;

public abstract class GameScene {
	private PhysicsManager	mPhysicsManager		= new PhysicsManager();
	private DrawableManager mDrawableManager 	= new DrawableManager(this);
	private UIManager		mUIManager			= new UIManager(this);
	
	private SceneChanger		mSceneChanger 	= null;
	
	public abstract void onInit(); // 엑티비티 세팅
	public void onDestroy() {
		mUIManager.clear();
		mDrawableManager.clear();
	}
	
	public void onStart() {} // 엑티비티 세팅이 끝난이후
	
	public void onResume() {} // 장면이 다시 실행될때
	public void onStop() {} // 장면이 잠시 중단될때
	
	public abstract void onPreRender(); // 연산처리등을 할때
	public final void onPostRender(Canvas canvas) {
		mUIManager.render(canvas);
		
		if(mSceneChanger != null) {
			if(!mSceneChanger.render(canvas))
				mSceneChanger = null;
		}
	}
	
	public final void onUpdateTimer() {
		long now = System.currentTimeMillis();
		
		int size = mTimerList.size();
		for(int i=0; i<size; i++) {
			Timer timer = mTimerList.get(i);
			if((now-timer.mLastRun) >= timer.mTime) {
				onTimer(timer.mIdx);
				if(timer.mRepeat > 0) {
					timer.mRepeat--;
					if(timer.mRepeat <= 0) {
						mTimerList.remove(i);
					}
				}
				
				timer.mLastRun = now;
			}
		}
	}
	
	public void onBackPressed() {
		finish(false);
	}
	public void onTouch(MotionEvent event) {
		mUIManager.onTouch(event);
	}
	public void onClick(UIView view) {}
	public void onTimer(int timer_idx) {}
	
	public void onAnimationFinish(String drawableID, int animID) {}
	
	public void startScene(GameScene scene) {
		startScene(scene, true, true);
	}
	public final void startScene(GameScene scene, boolean anim, boolean new_anim) {
		SceneManager.getManager().startScene(scene, anim, new_anim);
	}
	
	public final void replaceScene(GameScene scene) {
		replaceScene(scene, true, true);
	}
	public void replaceScene(GameScene scene, boolean anim, boolean new_anim) {
		SceneManager.getManager().replaceScene(scene, anim, new_anim);
	}
	
	public final void finish(boolean anim) {
		SceneManager.getManager().finishScene(anim);
	}
	protected final void setSceneChanger(SceneChanger changer) {
		mSceneChanger = changer;
	}
	
	/*
	 * Background
	 */
	private int mBGColor = 0;
	public void setBackgroundColor(int r, int g, int b) {
		mBGColor = Color.argb(255, r, g, b);
	}
	public int getBackgroundColor() {
		return mBGColor;
	}
	
	/*
	 * Resource Load
	 */
	public Drawable loadText(String id, String text, int size, int priority, int x, int y) {
		return new TextDrawable.TextBuilder().setText(text).setSize(size).setPriority(priority).setPosition(x, y).build(id);
	}
	
	public Drawable loadImage(String id, Bitmap source, int priority, int x, int y) {
		return new ImageDrawable.ImageBuilder().setSource(source).setPriority(priority).setPosition(x, y).build(id);
	}
	public Drawable loadImage(String id, String source, int priority, int x, int y) {
		return new ImageDrawable.ImageBuilder().setSource(source).setPriority(priority).setPosition(x, y).build(id);
	}
	public Drawable loadImage(String id, int source, int priority, int x, int y) {
		return new ImageDrawable.ImageBuilder().setSource(source).setPriority(priority).setPosition(x, y).build(id);
	}
	
	public Drawable loadSprite(String id, Bitmap source, int priority, int x, int y, int sliceX, int sliceY) {
		return new SpriteDrawable.SpriteBuilder().setSource(source).setSpriteSlice(sliceX, sliceY).setPriority(priority).setPosition(x, y).build(id);
	}
	public Drawable loadSprite(String id, String source, int priority, int x, int y, int sliceX, int sliceY) {
		return new SpriteDrawable.SpriteBuilder().setSource(source).setSpriteSlice(sliceX, sliceY).setPriority(priority).setPosition(x, y).build(id);
	}
	public Drawable loadSprite(String id, int source, int priority, int x, int y, int sliceX, int sliceY) {
		return new SpriteDrawable.SpriteBuilder().setSource(source).setSpriteSlice(sliceX, sliceY).setPriority(priority).setPosition(x, y).build(id);
	}
	
	/*
	 * UI Load
	 */
	public UIButton loadButton(String name, int off, int on, int x, int y) {
		UIButton button = new UIButton(this, name, off, on);
		button.setPosition(x, y);
		mUIManager.add(button);
		return button;
	}
	public UISwitchButton loadSwitchButton(String name, int off, int on, int x, int y) {
		UISwitchButton button = new UISwitchButton(this, name, off, on);
		button.setPosition(x, y);
		mUIManager.add(button);
		return button;
	}
	
	/*
	 * Resource Manage
	 */
	public final Drawable findDrawableById(String id) {
		return mDrawableManager.get(id);
	}
	
	/*
	 * Manager
	 */
	public final PhysicsManager getPhysicsManager() {
		return mPhysicsManager;
	}
	public final DrawableManager getDrawableManager() {
		return mDrawableManager;
	}
	public final UIManager getUiManager() {
		return mUIManager;
	}
	
	/*
	 * Timer
	 */
	private static class Timer {
		public int mIdx;
		public int mTime;
		public int mRepeat;
		
		public long mLastRun;
		
		public Timer(int idx, int time, int repeat) {
			mIdx = idx; mTime = time; mRepeat = repeat;
			mLastRun = System.currentTimeMillis();
		}
	}
	private ArrayList<Timer> mTimerList = new ArrayList<Timer>();
	public final void setTimer(int timer_idx, int time, int repeat) {
		mTimerList.add(new Timer(timer_idx, time, repeat));
	}
}
