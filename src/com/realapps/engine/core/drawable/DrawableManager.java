package com.realapps.engine.core.drawable;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.realapps.engine.core.scene.GameScene;

public class DrawableManager {
	private static boolean sync = false;
	
	private GameScene mScene = null;
	public DrawableManager(GameScene scene) {
		mScene = scene;
	}
	
	public static void sync() {sync = true;}
	
	private List<Drawable> mRenderingQueue = new LinkedList<Drawable>();
	public synchronized List<Drawable> refresh() {
		if(sync) {
			mRenderingQueue = new LinkedList<Drawable>();
			for(String key: mDrawableMap.keySet()) {
				mRenderingQueue.add(mDrawableMap.get(key));
			}
	
			Collections.sort(mRenderingQueue, new Comparator<Drawable>() {
				@Override
				public int compare(Drawable lhs, Drawable rhs) {
					return lhs.getPriority()-rhs.getPriority();
				}
			});
			sync = false;
		}
		
		return mRenderingQueue;
	}

	private HashMap<String, Drawable> mDrawableMap = new HashMap<String, Drawable>();
	public void putDrawable(String key, Drawable drawable) {
		mDrawableMap.put(key, drawable);
		sync();
	}
	public Drawable getDrawable(String id) {
		return mDrawableMap.get(id);
	}
	public void removeDrawable(String id) {
		mDrawableMap.remove(id);
		sync();
	}
	public void clearDrawable() {
		mDrawableMap.clear();
		sync();
	}
}