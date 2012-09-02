package com.realapps.engine.core.scene.ui;

import java.util.HashMap;

import com.realapps.engine.core.debug.exception.RuntimeException;
import com.realapps.engine.core.scene.GameScene;

public class UIManager {
	private GameScene mScene = null;
	private HashMap<String, UIView> view_list = new HashMap<String, UIView>();
	
	public UIManager(GameScene scene) {
		mScene = scene;
	}
	
	public void addUI(UIView view) {
		if(view_list.containsKey(view.getId())) {
			throw new RuntimeException("["+view.getId()+"] is already existed!");
		}
		
		view_list.put(view.getId(), view);
	}
}
