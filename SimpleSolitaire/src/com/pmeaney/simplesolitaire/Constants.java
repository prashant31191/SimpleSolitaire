package com.pmeaney.simplesolitaire;

public class Constants {
	public static int GAMEBOARD_WIDTH = 800;
	public static int GAMEBOARD_HEIGHT = 480;
	/**
	 * how much in from the top and sides of the screen the gameboard starts. Divides into the gameboard width and height to give the pixel value;
	 */
	public static int H_PADDING_AMOUNT = 85;	
	public static int V_PADDING_AMOUNT = 25;	
	public static int COLUMN_SEPERATION_AMOUNT = 10;
	public static int CARD_WIDTH = 80;
	public static int CARD_HEIGHT = 128;
	
	/*TODO:
	1. Call draw on CardStack instead of card, so I can check if size is 0 and draw the empty
	placeholder instead. **Cards should only be revealed on a successful drop.
	2. Card hidden/revealed boolean + logic 
	
	3. Architecture: LogicManager? accessing the Game from TouchManager?
	
	4. Card collectors (Ace 2 3 etc.) use Cardstack, list, or new extension?
	*/
}
