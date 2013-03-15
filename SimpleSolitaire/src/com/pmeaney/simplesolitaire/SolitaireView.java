package com.pmeaney.simplesolitaire;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class SolitaireView extends SurfaceView implements SurfaceHolder.Callback {
	private String TAG = this.getClass().getName();
	SolitaireThread thread;
	public SolitaireThread getThread() {
		return thread;
	}

	TextView mStatusText;
	public SolitaireView(Context context, AttributeSet attrs) {
		super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new SolitaireThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                mStatusText.setVisibility(m.getData().getInt("viz"));
                mStatusText.setText(m.getData().getString("text"));
            }
        });

        setFocusable(true); // make sure we get key events
	}

	/* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        thread.setSurfaceSize(width, height);
    }

    /*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created    	
    	thread.setRunning(true);
        thread.start();
    }

    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	Point touchedPoint = new Point((int)event.getX(), (int)event.getY());
    	if(event.getAction() == MotionEvent.ACTION_MOVE){	    	
	    	thread.doMove(touchedPoint);	    	
    	}
    	else if(event.getAction() == MotionEvent.ACTION_DOWN){
    		thread.doTouch(touchedPoint);	    
    	}
    	else if(event.getAction() == MotionEvent.ACTION_UP)
    	{    		
    		thread.doTouchUp(touchedPoint);
    	}
    	return true;
    }


}
