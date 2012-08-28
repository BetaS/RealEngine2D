package com.realapps.engine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;

import com.realapps.engine.core.renderer.RenderManager;
import com.realapps.engine.core.scene.GameScene;
import com.realapps.engine.core.scene.SceneManager;

public class Game extends Activity {
	private static Context mApplicationContext = null;
	public static Context getContext() {
		return mApplicationContext;
	}
	
	
	protected void onCreate(Bundle savedInstanceState, GameScene scene, boolean debug) {
		this.onCreate(savedInstanceState, scene, 480, 800, 30, debug);
	}
	
	protected void onCreate(Bundle savedInstanceState, GameScene scene, int fps, boolean debug) {
		this.onCreate(savedInstanceState, scene, 480, 800, fps, debug);
	}
	
	/**
	 * 
	 * @param savedInstanceState
	 * @param scene 시작 화면 설정
	 * @param width 기준 화면 너비 설정
	 * @param height 기준 화면 높이 설정
	 * @param fps 최대 FPS 설정 (0 = 무한)
	 * @param debug 디버그 모드 설정(FPS / Logging)
	 */
	protected void onCreate(Bundle savedInstanceState, GameScene scene, int width, int height, int fps, boolean debug) {
		super.onCreate(savedInstanceState);

		mApplicationContext = (Context)this;
		
		RenderManager renderer = RenderManager.getManager();
		if(debug) renderer.showFPS();
		else renderer.hideFPS();
		
		renderer.setResolution(width, height);
		
		renderer.setFPS(fps);
	
		SceneManager.getManager().startScene(scene);
		
		setContentView(renderer);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	
		mApplicationContext = (Context)this;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		RenderManager.getManager().end();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		SceneManager.getCurrentScene().onTouchScreen(event);
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}