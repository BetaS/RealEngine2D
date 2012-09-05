package com.realapps.engine.core.scene;

import java.util.LinkedList;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.realapps.engine.Game;
import com.realapps.engine.core.renderer.RenderManager;

public class SceneManager {
	public static abstract class SceneChanger {
		protected GameScene mNext = null;
		private float mSpeed = 0;
		private float mAlpha = 0;
		
		public SceneChanger(GameScene next, float speed) {
			mNext = next;
			mSpeed = speed;
			
			if(mSpeed > 0)
				mAlpha = 0;
			else
				mAlpha = 255;
		}
		
		public abstract void onSceneChange();
		
		public boolean render(Canvas canvas) {
			canvas.drawARGB((int)mAlpha, 0, 0, 0);
			mAlpha += mSpeed;
			
			if(mAlpha > 255 && mSpeed > 0) {
				onSceneChange();
				return false;
			} else if(mAlpha < 0 && mSpeed < 0) {
				onSceneChange();
				return false;
			}
			return true;
		}
	}

	private LinkedList<GameScene> mSceneStack = new LinkedList<GameScene>();
	private SceneManager() {}
	
	private static final SceneManager mInstance = new SceneManager();
	public static SceneManager getManager() {
		return mInstance;
	}
	public static GameScene getCurrentScene() {
		if(mInstance.mSceneStack.size() > 0) {
			return mInstance.mSceneStack.getFirst();
		}
		return null;
	}
	
	public void onTouchScreen(MotionEvent event) {
		if(mSceneStack.size() > 0 && !RenderManager.getManager().isStop()) {
			getCurrentScene().onTouch(event);
		}
	}
	
	public void onBackPressed() {
		if(mSceneStack.size() > 0 && !RenderManager.getManager().isStop()) {
			getCurrentScene().onBackPressed();
		}
	}
	
	public void resume() {
		RenderManager.getManager().start();
		if(getCurrentScene() != null)
			getCurrentScene().onResume();
	}
	public void stop() {
		RenderManager.getManager().stop();
		if(getCurrentScene() != null)
			getCurrentScene().onStop();
	}
	
	public void startScene(GameScene scene, final boolean stop_anim, final boolean start_anim) {
		if(mSceneStack.size() > 0) {
			stop();
			if(stop_anim) {
				getCurrentScene().setSceneChanger(new SceneChanger(scene, 10) {
					@Override
					public void onSceneChange() {
						start(mNext, start_anim);
					}
				});
				return;
			}
		}
		start(scene, start_anim);
	}
	protected void replaceScene(GameScene scene, final boolean stop_anim, final boolean start_anim) {
		if(mSceneStack.size() > 0) {
			stop();
			if(stop_anim) {
				getCurrentScene().setSceneChanger(new SceneChanger(scene, 10) {
					@Override
					public void onSceneChange() {
						finish(false);
						start(mNext, start_anim);
					}
				});
				return;
			}
		}
		finish(false);
		start(scene, start_anim);
	}
	protected void finishScene(boolean anim) {
		if(!RenderManager.getManager().isStop() && mSceneStack.size() > 0) {
			stop();
			if(anim) {
				getCurrentScene().setSceneChanger(new SceneChanger(null, 10) {
					@Override
					public void onSceneChange() {
						finish(true);
					}
				});
			} else {
				finish(true);
			}
		}
	}
	
	private void start(GameScene scene, boolean anim) {
		mSceneStack.addFirst(scene);

		RenderManager.getManager().setDrawableManager(scene.getDrawableManager());
		scene.onInit();
		
		if(anim) {
			scene.setSceneChanger(new SceneChanger(scene, -10) {
				@Override
				public void onSceneChange() {
					mNext.onStart();
					resume();
				}
			});
		} else {
			scene.onStart();
			resume();
		}
	}
	private void finish(boolean end) {
		GameScene scene = mSceneStack.removeFirst();
		RenderManager.getManager().stop();
		scene.onStop();
		scene.onDestroy();
		
		if(mSceneStack.size() > 0) {
			scene = mSceneStack.getFirst();
			RenderManager.getManager().setDrawableManager(scene.getDrawableManager());
			resume();
		} else if(end) {
			Game.getContext().finish();
		}
	}
}
