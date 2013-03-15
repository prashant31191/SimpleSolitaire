package com.pmeaney.simplesolitaire;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SolitaireActivity extends Activity {

	SolitaireView mView;
	SolitaireThread mThread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.solitaire_layout);
        
        Display display = getWindowManager().getDefaultDisplay();
        Constants.GAMEBOARD_WIDTH = display.getWidth();
        Constants.GAMEBOARD_HEIGHT = display.getHeight();
        
        mView = (SolitaireView) findViewById(R.id.solitaire);
        mThread = mView.getThread();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.solitaire_layout, menu);
        return true;
    }
    
    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mThread.pause(); // pause game when Activity pauses
    }
}
