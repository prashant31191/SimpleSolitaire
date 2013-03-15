package com.pmeaney.simplesolitaire;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class TouchManager {

	private String TAG = this.getClass().getName();
		
	public void doTouch(Point touch, Game game){
		
		if(checkColumn(game.column1, touch,game))
			return;
		else if(checkColumn(game.column2, touch,game))
			return;
		else if(checkColumn(game.column3, touch,game))
			return;
		else if(checkColumn(game.column4, touch,game))
			return;
		else if(checkColumn(game.column5, touch,game))
			return;
		else if(checkColumn(game.column6, touch,game))
			return;
		else if(checkColumn(game.column7, touch,game))
			return;		
	}

	
	public boolean checkColumn(CardStack theColumn, Point touchPoint, Game game){
		boolean foundIt = false;
		for(int i = theColumn.size() -1; i >= 0; i--){
			Card cardToTest = theColumn.get(i);
			if(cardToTest.getDrawableArea().contains(touchPoint.x, touchPoint.y)){
				if(cardToTest.isFaceDown()){
					return true; // indicate we have handled the touch event
				}
				List<Card> newFloatingHand = new ArrayList<Card>();
				
				for(int j= i; j < theColumn.size(); j++){
					Card card = theColumn.remove(i);
					card.setDrawableArea(new Rect( touchPoint.x ,
							touchPoint.y +((j-i)*Constants.V_PADDING_AMOUNT),
							touchPoint.x + Constants.CARD_WIDTH ,
							touchPoint.y +((j-i)*Constants.V_PADDING_AMOUNT) + Constants.CARD_HEIGHT)
							);
					newFloatingHand.add(card);
				}
				setFloatingHand(game,newFloatingHand);
				game.previousColumn = theColumn;
				foundIt = true;
				break;
			}
		}
		return foundIt;		
	}


	private void setFloatingHand(Game game,List<Card> newFloatingHand) {
		game.floatingHand = newFloatingHand;		
	}


	public void doMove(Point touchedPoint, Game game) {
		for(int i = 0; i < game.floatingHand.size(); i++){
			Card card = game.floatingHand.get(i);
			card.setDrawableArea(new Rect(touchedPoint.x ,
					touchedPoint.y +((i)*Constants.V_PADDING_AMOUNT),
					touchedPoint.x + Constants.CARD_WIDTH ,
					touchedPoint.y +((i)*Constants.V_PADDING_AMOUNT) + Constants.CARD_HEIGHT)
					);
		}
		
	}


	public void doTouchUp(Point touchedPoint, Game game) {
		if(game.floatingHand.size() < 1) //TODO: Tap logic here?
			return;
		
		boolean addSuccess = false;
		if(game.floatingHand.size() > 1){			
			if(game.column1.getDropArea().contains(touchedPoint.x,touchedPoint.y)){
				addSuccess = true;
				if(game.column1.addAll(game.floatingHand)){
					game.floatingHand.clear();
				}
			}
			else if(game.column2.getDropArea().contains(touchedPoint.x,touchedPoint.y)){
				addSuccess = true;
				if(game.column2.addAll(game.floatingHand)){
					game.floatingHand.clear();
				}
			}
			else if(game.column3.getDropArea().contains(touchedPoint.x,touchedPoint.y)){
				addSuccess = true;
				if(game.column3.addAll(game.floatingHand)){
					game.floatingHand.clear();
				}
			}
			else if(game.column4.getDropArea().contains(touchedPoint.x,touchedPoint.y)){
				addSuccess = true;
				if(game.column4.addAll(game.floatingHand)){
					game.floatingHand.clear();
				}
			}
			else if(game.column5.getDropArea().contains(touchedPoint.x,touchedPoint.y)){
				addSuccess = true;
				if(game.column5.addAll(game.floatingHand)){
					game.floatingHand.clear();
				}
			}
			else if(game.column6.getDropArea().contains(touchedPoint.x,touchedPoint.y)){
				addSuccess = true;
				if(game.column6.addAll(game.floatingHand)){
					game.floatingHand.clear();
				}
			}
			else if(game.column7.getDropArea().contains(touchedPoint.x,touchedPoint.y)){
				addSuccess = true;
				if(game.column7.addAll(game.floatingHand)){
					game.floatingHand.clear();
				}
			}
		}
		else{
			if(game.stack1.getDropArea().contains(touchedPoint.x,touchedPoint.y) && game.floatingHand.size() == 1){
				addSuccess = true;
				if(game.stack1.add(game.floatingHand.get(0))){
					game.floatingHand.clear();
				}
			}
			else if(game.stack2.getDropArea().contains(touchedPoint.x,touchedPoint.y) && game.floatingHand.size() == 1){
				addSuccess = true;
				if(game.stack2.add(game.floatingHand.get(0))){
					game.floatingHand.clear();
				}
			}
			else if(game.stack3.getDropArea().contains(touchedPoint.x,touchedPoint.y) && game.floatingHand.size() == 1){
				addSuccess = true;
				if(game.stack3.add(game.floatingHand.get(0))){
					game.floatingHand.clear();
				}
			}
			else if(game.stack4.getDropArea().contains(touchedPoint.x,touchedPoint.y) && game.floatingHand.size() == 1){
				addSuccess = true;
				if(game.stack4.add(game.floatingHand.get(0))){
					game.floatingHand.clear();
				}
			}
		}
		//TODO: Logic for tapping the Deck.
		if(addSuccess){
			try{
				if(!game.previousColumn.isEmpty())
					game.previousColumn.get(game.previousColumn.size() - 1).setFaceDown(false);
			}catch(Exception e){
				Log.i(TAG,"exception caught: " + e + " for cardstack index:" + game.previousColumn.index);
			}

		}
		else{
			//If we've gotten this far, return the floating hand to the previous column
			Log.i(TAG,"previousColumn is:" + game.previousColumn.index);
			game.previousColumn.reAdd(game.floatingHand);
			game.floatingHand.clear();

		}
	}
}
