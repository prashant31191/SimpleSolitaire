package com.pmeaney.simplesolitaire;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.graphics.Rect;
import android.util.Log;

import com.pmeaney.simplesolitaire.Card.CardNumber;

public class CardStack extends ArrayList<Card>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8728504701794351109L;
	
	private Rect dropArea = new Rect();
	public int index;
	int xLocation;
	boolean init = false;
	
	public enum Type {
		CardStack, SuitStack 
	}
	
	private Type type;
	
	public CardStack(int index, Type type){
		if(type == Type.CardStack){
			this.index = index;
			this.type = type;
			xLocation = Constants.CARD_WIDTH * (index - 1) + Constants.H_PADDING_AMOUNT + (Constants.COLUMN_SEPERATION_AMOUNT * (index -1));
			updateDropArea();
		}
		else{
			this.setDropArea(new Rect(Constants.GAMEBOARD_WIDTH / 80,(Constants.V_PADDING_AMOUNT + Constants.CARD_HEIGHT) * index,(Constants.GAMEBOARD_WIDTH / 80) + Constants.CARD_WIDTH,((Constants.V_PADDING_AMOUNT + Constants.CARD_HEIGHT) * index) + Constants.CARD_HEIGHT));
		}
	}
	
	@Override
	public Card remove(int index){
		Card toReturn = super.remove(index);		
		updateDropArea();
		return toReturn;
	}
	
	@Override
	public boolean add(Card c){
		if(this.init){
			super.add(c);
			c.setDrawableArea(this.dropArea);
			updateDropArea();
			return true;
		}
		if(this.type == Type.CardStack){
			if(this.isEmpty() && c.getNumber() != Card.CardNumber.King)//If its empty and not a king
				return false;
			else if(c.compareSuit(this.get(this.size() -1)) != 1 || c.compareTo(this.get(this.size() -1)) != 1)// If the last card and the current card are incompatible
				return false;
			else{
				super.add(c);			
				c.setDrawableArea(this.dropArea);
				updateDropArea();
				return true;
			}
		}
		else{
			if(this.isEmpty() && c.getNumber() != Card.CardNumber.Ace)//If its empty and not a king
				return false;
			else if(c.compareSuit(this.get(this.size() -1)) != 0 || c.compareTo(this.get(this.size() -1)) != 1)// If the last card and the current card are incompatible
				return false;
			else{
				super.add(c);
				c.setDrawableArea(this.dropArea);
				Log.i("CardStack"," added card to suitstack, dropArea: " + this.dropArea);
				return true;
			}
		}
	}
		
	private void updateDropArea() {
		if(this.type == Type.SuitStack)
			return;
		this.dropArea = new Rect( xLocation ,
			Constants.V_PADDING_AMOUNT * (this.size() + 1),
			Constants.CARD_WIDTH + xLocation,
			(Constants.V_PADDING_AMOUNT * (this.size() + 1)) + Constants.CARD_HEIGHT);		
	}

	@Override
	public boolean addAll(Collection cards){
		if(this.type == Type.SuitStack){//can't add more than one card to the Suit Stack at a time
			return false;
		}
		Card c = (Card)cards.toArray()[0]; //bit of a waste converting the whole thing to an array, but collection doesnt have a get method and cant cast the collection either.
		if(this.isEmpty()){
			if(c.getNumber() == CardNumber.King){
				super.addAll(cards);
				return true;
			}
			else //its empty but the top card in the would-be-added pack is not a king.
				return false;
		}
		else if(c.compareTo(this.getLastCard()) != 1)
			return false;
		else {
			super.addAll(cards);
			updateDropArea();
			return true;
		}
	}
	
	public boolean reAdd(List<Card> cards){
		boolean result =  true;
		for(Card c: cards){
			result = result && super.add(c);			
			c.setDrawableArea(this.dropArea);			
			updateDropArea();
		}
		
		return result;
	}
	
	
	@Override
	public boolean addAll(int index, Collection cards){
		if(this.type == Type.SuitStack){//can't add more than one card to the Suit Stack at a time
			return false;
		}
		Card c = (Card)cards.toArray()[index]; //bit of a waste converting the whole thing to an array, but collection doesnt have a get method and cant cast the collection either.
		if(this.isEmpty()){
			return false;
		}
		else if(c.compareTo(this.getLastCard()) != 1)
			return false;
		else 
		{
			super.addAll(index,cards);
			updateDropArea();
			return true;
		}
	}
	
	private Card getLastCard(){
		if(!this.isEmpty())
			return this.get(this.size() - 1);
		else
		{
			return null;
		}
	}
	
	public Rect getDropArea() {
		return dropArea;
	}
	public void setDropArea(Rect dropArea) {
		this.dropArea = dropArea;
	}

	public Type getType() {
		return type;
	}
	
}
