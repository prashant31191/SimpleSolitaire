package com.pmeaney.simplesolitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.pmeaney.simplesolitaire.Card.CardNumber;
import com.pmeaney.simplesolitaire.Card.Suit;
import com.pmeaney.simplesolitaire.CardStack.Type;

public class Game {

	private String TAG = this.getClass().getName();
	
	CardStack column1 = new CardStack(1,Type.CardStack);
	CardStack column2 = new CardStack(2,Type.CardStack);
	CardStack column3 = new CardStack(3,Type.CardStack);
	CardStack column4 = new CardStack(4,Type.CardStack);
	CardStack column5 = new CardStack(5,Type.CardStack);
	CardStack column6 = new CardStack(6,Type.CardStack);
	CardStack column7 = new CardStack(7,Type.CardStack);
	List<Card> hand;
	List<Card> spares = new ArrayList<Card>();
	
	CardStack stack1 = new CardStack(0,Type.SuitStack);
	CardStack stack2 = new CardStack(1,Type.SuitStack);
	CardStack stack3 = new CardStack(2,Type.SuitStack);
	CardStack stack4 = new CardStack(3,Type.SuitStack);
	List<Card> Deck = new ArrayList<Card>();
	
	List<Card> floatingHand = new ArrayList<Card>();
	CardStack previousColumn;
	
	
	public Game(int handSize,Context context){
		generateDeck(context);
		Drawable d = context.getResources().getDrawable(R.drawable.testcard);
		Constants.CARD_WIDTH = d.getIntrinsicWidth();
		Constants.CARD_HEIGHT =  d.getIntrinsicHeight();
		//Collections.shuffle(Deck, new Random());
		hand = new ArrayList<Card>(handSize);
	}
	
	public Game(int handSize,long seed,Context context){
		generateDeck(context);
		Collections.shuffle(Deck, new Random(seed));
		hand = new ArrayList<Card>(handSize);
	}
	

	private void generateDeck(Context context){
		for(Suit suit : Suit.values()){
			for(CardNumber no : CardNumber.values()){
				Card card = new Card(no,suit, context, true);				
				Deck.add(card);				
			}
		}
	}
	
	public void populateGameBoard(){
		
		populateColumn(column1);
		populateColumn(column2);
		populateColumn(column3);
		populateColumn(column4);
		populateColumn(column5);
		populateColumn(column6);
		populateColumn(column7);
		for(int i=0; i< Deck.size();i++)
		{
			Card theCard = Deck.remove(0);
			spares.add(theCard);	 
		}
		
	}
	
	private void populateColumn(CardStack theColumn){
		theColumn.init = true;
		for(int i =0; i< theColumn.index; i++){
			Card theCard = Deck.remove(0);			
			theColumn.add(theCard);
		}
		theColumn.get(theColumn.size() - 1).setFaceDown(false);
		theColumn.init = false;
	}
	
	
}
