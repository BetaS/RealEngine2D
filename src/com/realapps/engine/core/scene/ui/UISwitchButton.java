package com.realapps.engine.core.scene.ui;

import com.realapps.engine.core.scene.GameScene;

public class UISwitchButton extends UIButton {
	public UISwitchButton(GameScene scene, String btn_name, int drawable_off, int drawable_on) {
		super(scene, btn_name, drawable_off, drawable_on);
	}
	
	@Override
	public void touch(int action, int x, int y) {
		if((getX() <= x && x <= getX()+getWidth()) && (getY() <= y && y <= getY()+getHeight())) {
			onTouch(action);
			mScene.onClick(this);
		}
	}
}
