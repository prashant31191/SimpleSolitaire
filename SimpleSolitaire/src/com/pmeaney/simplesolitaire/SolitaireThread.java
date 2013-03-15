package com.pmeaney.simplesolitaire;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class SolitaireThread extends Thread{

	private TouchManager mTouchManager = new TouchManager();
	private DrawManager mDrawManager = new DrawManager();
	private LogicManager mLogicManager = new LogicManager();
	private String TAG = this.getClass().getName();
	
	/** Handle to the surface manager object we interact with */
    private SurfaceHolder mSurfaceHolder;
	private Context mContext;
	private Handler mHandler;
    private Game game;
	private boolean mRun;
    
	public SolitaireThread(SurfaceHolder surfaceHolder, Context context, Handler handler){
		mSurfaceHolder = surfaceHolder;
		mHandler = handler;
        mContext = context;
        Resources res = context.getResources();
        game = new Game(3, mContext);
        game.populateGameBoard();
        mDrawManager.loadResources(res);
	}
	
	@Override
	public void run(){
		while(mRun){
			Canvas c = null;
	        try {
	            c = mSurfaceHolder.lockCanvas(null);
	            synchronized (mSurfaceHolder) {
	                mLogicManager.updateLogic(game);
	                mDrawManager.draw(game, c);
	            }
	        } finally {
	            // do this in a finally so that if an exception is thrown
	            // during the above, we don't leave the Surface in an
	            // inconsistent state
	            if (c != null) {
	                mSurfaceHolder.unlockCanvasAndPost(c);
	            }
        	}
		}
	}
	
	public void setSurfaceSize(int width, int height){
		mDrawManager.setGameBoardHeight(width);
		mDrawManager.setGameBoardHeight(height);
	}

	public void doTouch(Point touchedPoint) {
		mTouchManager.doTouch(touchedPoint, game);
		
	}
	
	public void setRunning(boolean run){
		mRun = run;
	}

	public void doTouchUp(Point touchedPoint) {
		mTouchManager.doTouchUp(touchedPoint,game);
		
	}

	public void doMove(Point touchedPoint) {
		mTouchManager.doMove(touchedPoint, game);		
	}

	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
}
