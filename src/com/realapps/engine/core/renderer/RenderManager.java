package com.realapps.engine.core.renderer;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.realapps.engine.Game;
import com.realapps.engine.core.drawable.Drawable;
import com.realapps.engine.core.scene.GameScene;
import com.realapps.engine.core.scene.SceneManager;

public class RenderManager extends SurfaceView implements Callback, Runnable {
	private RenderManager() {
		super(Game.getContext());
		
		getHolder().addCallback(this);
	}	
	
	private static final RenderManager mInstance = new RenderManager();
	public static RenderManager getManager() {
		return mInstance;
	}
	
	public static Drawable getDrawable(String id) {
		return mInstance.mDrawableMap.get(id);
	}
	public static void removeDrawable(String id) {
		mInstance.mDrawableMap.remove(id);
	}
	public static void clearDrawable() {
		mInstance.mDrawableMap.clear();
		mInstance.sync();
	}
	
	/*
	 * Render Thread
	 */
	private Thread 	mThread			= null;
	
	/*
	 *  Frame Per Sec.
	 */
	private String 	mFPSText		= "FPS: 0";
	private Paint	mFPSPaint		= new Paint();
	
	private long 	mDrawLate 		= 1; // 몇 ms 에 한번 화면을 갱신하는지
	private long 	mLastDrawTime 	= 0; 	// 화면이 언제 갱신되었는지

	private boolean mShowFPS 		= false; 	// FPS를 화면에 그려주는지
	private int 	mFrameRate 		= 0;		// 현재 프레임
	private long 	mLastCheckTime 	= 0;		// 마지막 프레임 갱신 시간
	
	public void end() {
		mThread.interrupt();
	}
	
	public void showFPS() {
		mShowFPS = true;
	}
	public void hideFPS() {
		mShowFPS = false;
	}
	public void setFPS(int fps) {
		if(fps > 0) {
			mDrawLate = (long)(1E9/fps);
		} else {
			mDrawLate = 0;
		}
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mFPSPaint.setColor(0xFF000000);
		mFPSPaint.setTextSize(20);
		mFPSPaint.setAntiAlias(true);
		mFPSPaint.setDither(true);
		
		mThread = new Thread(this); // 쓰레드를 만들어줍니다.
		mThread.setPriority(Thread.MAX_PRIORITY);
		mThread.start(); // 쓰레드가 run을 실행하도록 스타트시킵니다.
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mThread.interrupt();
	}

	private HashMap<String, Drawable> mDrawableMap = new HashMap<String, Drawable>();
	public void putDrawable(String key, Drawable drawable) {
		mDrawableMap.put(key, drawable);
		sync();
	}
	
	private boolean isSync = false;
	public synchronized void sync() {
		isSync = true;
	}
	
	private LinkedList<Drawable> mRenderingQueue = new LinkedList<Drawable>();
	private synchronized void synchronize() {
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
		isSync = false;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) { // 쓰레드의 무한반복을위해서 while을 돌려줍니다.
			long nowTime = System.nanoTime();
			
			if((nowTime-mLastDrawTime) >= mDrawLate) {
				if(mShowFPS == true) {
					mFrameRate++;
					
					if((nowTime-mLastCheckTime) >= 1E9) {
						mFPSText = "FPS: "+mFrameRate;
						
						mLastCheckTime = nowTime;
						mFrameRate = 0;
					}
				}

				render();
				mLastDrawTime = nowTime;
			}
		}
		System.exit(0);
	}
	
	public synchronized void render() {
		if(isSync) synchronize();
		
		Canvas canvas = null;
	
		try {
			canvas = getHolder().lockCanvas(null); // 켄바스를 잠구어 안의 내용을 바꿀수 있도록 동기화합니다.
		} finally { // 모든 작업을 끝마친다음에는
			if (canvas != null){
				GameScene scene = SceneManager.getCurrentScene();
				
				canvas.drawColor(scene.getBackgroundColor());
				scene.onPreRender();
				for(Drawable drawable: mRenderingQueue) {
					drawable.draw(canvas);
				}
				scene.onPostRender();
				if(mShowFPS) canvas.drawText(mFPSText, 0, mFPSPaint.getTextSize(), mFPSPaint);
				
				getHolder().unlockCanvasAndPost(canvas); // 켄바스의 잠금을 푼다음에 화면에 개시합니다.
			}
		}
	}
}
