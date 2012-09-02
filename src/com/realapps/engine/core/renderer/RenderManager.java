package com.realapps.engine.core.renderer;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.realapps.engine.Game;
import com.realapps.engine.core.drawable.Drawable;
import com.realapps.engine.core.drawable.DrawableManager;
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
	
	/*
	 * Render Thread
	 */
	private Thread 	mThread			= null;
	
	/*
	 * Drawable Manager
	 */
	private DrawableManager mDrawableManager = null;
	
	/*
	 *  Frame Per Sec.
	 */
	private String 	mFPSText		= "FPS: 0";
	private Paint	mFPSPaint		= new Paint();
	
	private long 	mDrawLate 		= 1; // �� ms �� �ѹ� ȭ���� �����ϴ���
	private long 	mLastDrawTime 	= 0; 	// ȭ���� ���� ���ŵǾ�����

	private boolean mShowFPS 		= false; 	// FPS�� ȭ�鿡 �׷��ִ���
	private int 	mFrameRate 		= 0;		// ���� ������
	private long 	mLastCheckTime 	= 0;		// ������ ������ ���� �ð�
	
	private int 	mBaseWidth		= 0;
	private int		mBaseHeight		= 0;
	private int		mScreenWidth	= 0;
	private int 	mScreenHeight	= 0;
	
	private float	mWidthRatio		= 0.0f;
	private float	mHeightRatio	= 0.0f;
	
	public void end() {
		mThread.interrupt();
	}
	
	public void setResolution(int width, int height) {
		mBaseWidth = width;
		mBaseHeight = height;
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager window = (WindowManager)Game.getContext().getSystemService(Context.WINDOW_SERVICE);
		window.getDefaultDisplay().getMetrics(displayMetrics);
		mScreenWidth = displayMetrics.widthPixels;
		mScreenHeight = displayMetrics.heightPixels;
		
		mWidthRatio = (float)mScreenWidth/mBaseWidth;
		mHeightRatio = (float)mScreenHeight/mBaseHeight;
		
		Log.e("Screen Ratio", "X: "+mWidthRatio+", Y: "+mHeightRatio);
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
		
		mThread = new Thread(this); // �����带 ������ݴϴ�.
		mThread.setPriority(Thread.MAX_PRIORITY);
		mThread.start(); // �����尡 run�� �����ϵ��� ��ŸƮ��ŵ�ϴ�.
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mThread.interrupt();
	}
	
	public void setDrawableManager(DrawableManager manager) {
		mDrawableManager = manager;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) { // �������� ���ѹݺ������ؼ� while�� �����ݴϴ�.
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
		Canvas canvas = null;
	
		try {
			canvas = getHolder().lockCanvas(null); // �˹ٽ��� �ᱸ�� ���� ������ �ٲܼ� �ֵ��� ����ȭ�մϴ�.
		} finally { // ��� �۾��� ����ģ��������
			if (canvas != null){
				canvas.scale(mWidthRatio, mHeightRatio);
				
				List<Drawable> renderingQueue = mDrawableManager.refresh();
						
				GameScene scene = SceneManager.getCurrentScene();
				
				canvas.drawColor(scene.getBackgroundColor());
				scene.onPreRender();
				for(Drawable drawable: renderingQueue) {
					drawable.draw(canvas);
				}
				scene.onPostRender();
				if(mShowFPS) canvas.drawText(mFPSText, 0, mFPSPaint.getTextSize(), mFPSPaint);

				getHolder().unlockCanvasAndPost(canvas); // �˹ٽ��� ����� Ǭ������ ȭ�鿡 �����մϴ�.
			}
		}
	}
}
