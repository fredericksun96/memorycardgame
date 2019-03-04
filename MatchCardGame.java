package hw2;

import java.util.Random;

public class MatchCardGame{
	public final int game_size; //the game size
	private static char[] cards_arr; //an array which will keep letters
	private static int[] num_arr;
	private static int[] last_card = new int[2]; //will be the last card played. Indexed at 0
	private static int turn_no;

	//constructor. Construct num_arr as an array of the size of the game, indexed at 1
	//cards_arr is gonna be a bunch of chars, 4 of each kind
	//last_card garbage
	public MatchCardGame(int n) {
		turn_no = 0;
		game_size = n;
		int letter_count = 0;	//this will be the number used later for making the array
		char letter = 'a'; 		//letter to be put into cards_arr
		letter--;				//subtract to get code to work later
		cards_arr = new char[game_size];
		num_arr = new int[n];


		for (int i = 0; i < game_size; i++){
			num_arr[i] = i + 1;
			if (i % 4 == 0)	//every four indices, go up one letter
				letter++;

			cards_arr[i] = letter;
		}
	}

	//sigh
	public String boardToString(){
		int nRows = game_size/4;	//number of rows
		int nCols = 14;				//will be 14 cols: format is:	
									//'a1 a2 a3 a4  \n' etc

		char[][] board = new char[nRows][nCols]; //2d array to hold char array

		for (int i = 0; i < nRows; i++){
			int count = 1;	//count for the nth element in the row
			for (int j = 0; j < nCols; j++){
				if (j == 0) 
					board[i][j] = (char) (i + 'a');		//make the first char 'a'
				else if(j % 3 == 1){
					board[i][j] = (char) (count + '0');
					count++;
				}
				else if (j % 3 == 2)
					board[i][j] = ' ';
				else if (j % 3 == 0)
					board[i][j] = (char) (i + 'a');

			}
			board[i][12] = ' ';
			board[i][13] = '\n';	//to next line
		}

		//Some code that will check if there's any cards flipped. this will access num_arr above
		for (int i = 0; i < game_size; i++){
			if (num_arr[i] == 0){	//if num_arr[i] is 0, then it's print the char
				int m_row = i/4;	//determines which row of the change
				int m_col = i%4; 	//determines the column
				m_col *= 3;  
				board[m_row][m_col] = '[';
				board[m_row][m_col + 1] = cards_arr[i];
				board[m_row][m_col + 2] = ']';
			}						
		}

		int nChars = nRows * nCols;
		//to change to 1d char array to change to string later
		char[] m_board = new char[nChars];
		int a_count = 0;
		for (int i = 0; i < nRows; i++){
			for (int j = 0; j < nCols; j++){
				m_board[a_count] = board[i][j];
				a_count++;
			}
		}
		String s_board = new String(m_board);
		return s_board;
	}

	//flip a card if the card isn't already flipped. if it is flipped, return false; if not flip the card and return true
	public boolean flip(int i){
		if (num_arr[i] != 0) {
			num_arr[i] = 0;
			last_card[turn_no%2] = i; 
			turn_no++;
			return true;
		} else
		return false;
	}


	//only to be called after two flips. Checks if the two cards are a match
	public boolean wasMatch(){
		return cards_arr[last_card[0]] == cards_arr[last_card[1]];
	}

	//return the char of the previous flip
	public char previousFlipIdentity() {
		if (turn_no % 2 == 1)
			return cards_arr[last_card[0]];
		else
			return cards_arr[last_card[1]];
	}

	//only to be called if wasMatch() is false. Sets the idex of num_arr back to the proper index instead of 0
	public void flipMismatch(){
		if (!wasMatch()){
			num_arr[last_card[0]] = last_card[0] + 1;
			num_arr[last_card[1]] = last_card[1] + 1;
		}
	}

	//returns if all cards have been matched
	public boolean gameOver(){
		for (int i = 0; i < num_arr.length; i++)
			if (num_arr[i] != 0)
				return false;
		return true;
	}

	//returns number of flips so far
	public int getFlips(){
		return turn_no;
	}

	//shuffle cards using Fisher-Yates
	public void shuffleCards() {
		Random ran = new Random();
		for (int i = 0; i < num_arr.length; i++){
			int j = ran.nextInt(i+1);
			char temp = cards_arr[i];
			cards_arr[i] = cards_arr[j];
			cards_arr[j] = temp;
		}
	}

public static void main(String[] args){
	MatchCardGame g1 = new MatchCardGame(16);
	System.out.println("Game size is " + g1.game_size);
	System.out.println(g1.cards_arr);

	for (int i = 0; i < g1.game_size; i++){
			System.out.print(g1.num_arr[i]);

	}
	System.out.print("\n");
	g1.flip(3);
	g1.flip(2);
	//String fish = Character.toString(cards_arr[3]);
	//System.out.println(fish);
	System.out.println(g1.boardToString());
	System.out.println(g1.wasMatch());
	g1.flipMismatch();
	System.out.println(g1.boardToString());
	g1.flip(7);
	g1.flip(1);
	System.out.println(g1.boardToString());

	System.out.println(g1.previousFlipIdentity());


	g1.flip(5);
	System.out.println(g1.boardToString());

	System.out.println(g1.previousFlipIdentity());


	System.out.println(g1.wasMatch());
	g1.flipMismatch();
	System.out.println(g1.boardToString());
	g1.shuffleCards();
	for (int i = 0; i < num_arr.length; i++)
		num_arr[i] = 0;

		System.out.println(g1.boardToString());

	System.out.println(g1.gameOver());
}

}