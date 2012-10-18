package com.realapps.engine;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.realapps.engine.core.renderer.RenderManager;
import com.realapps.engine.core.scene.GameScene;
import com.realapps.engine.core.scene.SceneManager;
import com.realapps.engine.core.util.hardware.SystemManager;

public class Game extends Activity {
	private static Activity mApplicationContext = null;
	public static Activity getContext() {
		return mApplicationContext;
	}
	
	
	protected void onCreate(Bundle savedInstanceState, GameScene scene, boolean debug) {
		this.onCreate(savedInstanceState, scene, 480, 800, true, 30, debug);
	}
	
	protected void onCreate(Bundle savedInstanceState, GameScene scene, int fps, boolean debug) {
		this.onCreate(savedInstanceState, scene, 480, 800, true, fps, debug);
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
	protected void onCreate(Bundle savedInstanceState, GameScene scene, int width, int height, boolean clipping, int fps, boolean debug) {
		super.onCreate(savedInstanceState);

		mApplicationContext = this;
		
		SystemManager.getInstance().init(this);
		
		RenderManager renderer = RenderManager.getManager();
		if(debug) renderer.showFPS();
		else renderer.hideFPS();
		
		renderer.setResolution(width, height, clipping);
		
		renderer.setFPS(fps);
	
		SceneManager.getManager().startScene(scene, true, true);
		
		setContentView(renderer);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	
		mApplicationContext = this;
		SceneManager.getManager().resume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		SceneManager.getManager().stop();
	}
	
	@Override
	public void onBackPressed() {
		SceneManager.getManager().onBackPressed();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		event.setLocation(event.getX()/RenderManager.getManager().getXRatio(), event.getY()/RenderManager.getManager().getYRatio());
		
		SceneManager.getManager().onTouchScreen(event);
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}