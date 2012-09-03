package com.realapps.engine.core.scene;

import java.util.LinkedList;

import com.realapps.engine.core.renderer.RenderManager;

public class SceneManager {
	private SceneManager() {
	}	
	
	private static final SceneManager mInstance = new SceneManager();
	public static SceneManager getManager() {
		return mInstance;
	}
	public static GameScene getCurrentScene() {
		return mInstance.mSceneStack.getFirst();
	}
	
	private LinkedList<GameScene> mSceneStack = new LinkedList<GameScene>();
	public void startScene(GameScene scene) {
		if(mSceneStack.size() > 0) {
			GameScene currScene = mSceneStack.getFirst();
			currScene.onStop();
		}
		
		mSceneStack.addFirst(scene);
		
		scene.onInit();
		RenderManager.getManager().setDrawableManager(scene.getDrawableManager());
		scene.onStart();
	}
	public void replaceScene(GameScene scene) {
		GameScene currScene = mSceneStack.getFirst();
		mSceneStack.addFirst(scene);
		
		if(currScene != null) {
			currScene.onStop();
			currScene.onDestroy();
			mSceneStack.remove(currScene);
		}

		RenderManager.getManager().setDrawableManager(scene.getDrawableManager());
		scene.onInit();
		scene.onStart();
	}
	public void finishScene() {
		GameScene scene = mSceneStack.removeFirst();
		scene.onStop();
		scene.onDestroy();
		
		scene = mSceneStack.getFirst();
		RenderManager.getManager().setDrawableManager(scene.getDrawableManager());
		scene.onStart();
	}
}
