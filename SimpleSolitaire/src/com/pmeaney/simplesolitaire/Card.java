package com.pmeaney.simplesolitaire;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Card implements Comparable<Card>{
	
	private String TAG = this.getClass().getName();
	
	public enum Suit{
		Heart,
		Spade,
		Diamond,
		Club
	};
	public enum CardNumber{
		Ace,
		Two,
		Three,
		Four,
		Five,
		Six,
		Seven,
		Eight,
		Nine,
		Ten,
		Jack,
		Queen,
		King
	};
	
	private Suit suit;
	private CardNumber cardNumber;
	private Rect drawableArea;
	private Drawable sprite;
	private boolean isFaceDown = true;

	public Card(CardNumber number, Suit suit, Context context, boolean isFaceDown){
		this.suit = suit;
		this.cardNumber = number;
		//TODO: set sprite here
		this.setSprite(context.getResources().getDrawable(R.drawable.testcard));
	}

	public Suit getSuit() {
		return suit;
	}

	public CardNumber getNumber() {
		return cardNumber;
	}

	public Rect getDrawableArea() {
		return drawableArea;
	}

	public void setDrawableArea(Rect drawableArea) {
		this.drawableArea = drawableArea;
	}

	public Drawable getSprite() {
		return sprite;
	}

	public void setSprite(Drawable sprite) {
		this.sprite = sprite;
	}
	
	public boolean isFaceDown() {
		return isFaceDown;
	}

	public void setFaceDown(boolean isFaceDown) {
		this.isFaceDown = isFaceDown;
	}

	public int compareTo(Card theCard) {	
		return this.cardNumber.compareTo(theCard.cardNumber);
	}

	public int compareSuit(Card theCard){
		if((this.suit == Suit.Diamond || this.suit == Suit.Heart) && (theCard.suit == Suit.Club || theCard.suit == Suit.Spade)){
			return 1;
		}
		else if((this.suit == Suit.Club|| this.suit == Suit.Spade) && (theCard.suit == Suit.Heart || theCard.suit == Suit.Diamond)){
			return 1;
		}
		else if(this.suit == theCard.suit){
			return 0;
		}
		else
			return -1;
	}

	@Override
	public String toString() {
		return  suit + "\n" + cardNumber;
	}
	
	
}
