package com.realapps.engine.core.scene.ui;

import java.util.HashMap;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.realapps.engine.core.debug.exception.RuntimeException;
import com.realapps.engine.core.scene.GameScene;

public class UIManager {
	@SuppressWarnings("unused")
	private GameScene mScene = null;
	private HashMap<String, UIView> mUIMap = new HashMap<String, UIView>();
	
	public UIManager(GameScene scene) {
		mScene = scene;
	}
	
	public void add(UIView view) {
		if(mUIMap.containsKey(view.getId())) {
			throw new RuntimeException("["+view.getId()+"] is already existed!");
		}
		
		mUIMap.put(view.getId(), view);
	}
	
	public UIView get(String id) {
		return mUIMap.get(id);
	}
	public void remove(String id) {
		UIView view = get(id);
		view.release();
		
		mUIMap.remove(id);
	}
	public void clear() {
		for(String key: mUIMap.keySet()) {
			mUIMap.get(key).release();
		}
		mUIMap.clear();
	}
	
	public void onTouch(MotionEvent event) {
		for(String key: mUIMap.keySet()) {
			mUIMap.get(key).touch(event);
		}
	}
	
	public synchronized void render(Canvas canvas) {
		for(String key: mUIMap.keySet()) {
			UIView view = mUIMap.get(key);
			view.draw(canvas);
		}
	}
}
