package com.realapps.engine.core.scene.ui;

import java.util.HashMap;

import com.realapps.engine.core.debug.exception.RuntimeException;
import com.realapps.engine.core.scene.GameScene;

public class UIManager {
	@SuppressWarnings("unused")
	private GameScene mScene = null;
	private HashMap<String, UIView> uiList = new HashMap<String, UIView>();
	
	public UIManager(GameScene scene) {
		mScene = scene;
	}
	
	public void addUI(UIView view) {
		if(uiList.containsKey(view.getId())) {
			throw new RuntimeException("["+view.getId()+"] is already existed!");
		}
		
		uiList.put(view.getId(), view);
	}
}
