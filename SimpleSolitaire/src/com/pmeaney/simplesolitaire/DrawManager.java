package com.pmeaney.simplesolitaire;


import java.util.List;
import java.util.Random;

import com.pmeaney.simplesolitaire.CardStack.Type;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class DrawManager {

	//defaults
	private int gameBoardWidth;
	private int gameBoardHeight;
	private Bitmap mBackgroundImage;
	private Drawable mEmptyColumn;
	private Drawable mCardBack;
	private Drawable mAceEmpty;
	private boolean debugDraw = false;
	
	public void setGameBoardWidth(int gameBoardWidth) {
		this.gameBoardWidth = gameBoardWidth;
	}

	public void setGameBoardHeight(int gameBoardHeight) {
		this.gameBoardHeight = gameBoardHeight;
	}

	public DrawManager(){
		//set Defaults
		gameBoardWidth = Constants.GAMEBOARD_WIDTH;
		gameBoardHeight = Constants.GAMEBOARD_HEIGHT;
	}

	public void loadResources(Resources res){
		mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.background);
		mCardBack = res.getDrawable(R.drawable.cardback);
		mEmptyColumn = res.getDrawable(R.drawable.empty);
		mAceEmpty = res.getDrawable(R.drawable.aceempty);
	}
	
	
	public void draw(Game theGame, Canvas canvas){
		// Draw the background image. Operations on the Canvas accumulate
        // so this is like clearing the screen.
        canvas.drawBitmap(mBackgroundImage, 0, 0, null);
        
        drawCardStack(theGame.column1,canvas);
        drawCardStack(theGame.column2,canvas);
        drawCardStack(theGame.column3,canvas);
        drawCardStack(theGame.column4,canvas);
        drawCardStack(theGame.column5,canvas);
        drawCardStack(theGame.column6,canvas);
        drawCardStack(theGame.column7,canvas);
        drawFloatingHand(theGame.floatingHand,canvas);
        drawSuitStack(theGame.stack1, canvas);
        drawSuitStack(theGame.stack2, canvas);
        drawSuitStack(theGame.stack3, canvas);
        drawSuitStack(theGame.stack4, canvas);
	}
	
	private void drawFloatingHand(List<Card> floatingHand, Canvas canvas) {
		if(floatingHand.isEmpty()){
			return;
		}
		else{
			for(int i = 0; i< floatingHand.size(); i++){
				Card theCard = floatingHand.get(i);
				Drawable image = theCard.getSprite();
				image.setBounds(theCard.getDrawableArea());
				image.draw(canvas);
			}
		}
		
	}

	private void drawSuitStack(CardStack stack, Canvas canvas) {
		if(stack.isEmpty()){
			mAceEmpty.setBounds((stack).getDropArea());
			mAceEmpty.draw(canvas);
		}
		else{
			Card theCard = stack.get(stack.size() - 1);
			Drawable image = theCard.getSprite();
			image.setBounds(theCard.getDrawableArea());
			image.draw(canvas);
		}
	}

	public void drawCardStack(List<Card> column, Canvas canvas){
		if(column.isEmpty())	{			
				CardStack c = (CardStack) column;
				mEmptyColumn.setBounds(c.getDropArea());
				mEmptyColumn.draw(canvas);				
		}				
		else{
			for(int i = 0; i< column.size(); i++){
				Card theCard = column.get(i);
				if(debugDraw == true){
					Paint paint = new Paint();
					paint.setColor( new Random().nextInt());
					canvas.drawRect(theCard.getDrawableArea(), paint);
				}
				else{			
					if(!theCard.isFaceDown()){
						Drawable image = theCard.getSprite();
						Paint paint = new Paint();
						paint.setColor(Color.BLACK);
						paint.setTextSize(24);
						Rect area = theCard.getDrawableArea();
						image.setBounds(area);
						image.draw(canvas);
						canvas.drawText(theCard.toString(),area.left+ 8,area.top + Constants.CARD_HEIGHT/2 ,paint);
					}
					else{
						mCardBack.setBounds(theCard.getDrawableArea());
						mCardBack.draw(canvas);
					}
				}
			}
		}
	}
}
