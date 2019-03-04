package hw2; 

import java.util.Random;

public class PlayCard {
  public static void main(String[] args) {
    //set up reader to take inputs
    java.util.Scanner reader = new java.util.Scanner (System.in);
    
    int n = 32; //game size
        /*

    MatchCardGame g1 = new MatchCardGame(n);
    g1.shuffleCards();
    
    while(!g1.gameOver()) {
      //print board status
      System.out.println(g1.boardToString());
      
      //ask for a card to flip until we get a valid one
      System.out.println("Which card to play?");
      while(!g1.flip(reader.nextInt())) {}
      
      //print board status
      System.out.println(g1.boardToString());
      
      //ask for a card to flip until we get a valid one
      while(!g1.flip(reader.nextInt())) {}
      
      //say whether the 2 cards were a match
      if(g1.wasMatch()) {
        System.out.println("Was a match!");
      } else {
        //print board to show mismatched cards
        System.out.println(g1.boardToString());		
        System.out.println("Was not a match.");
        //flip back the mismatched cards
        g1.flipMismatch();
      }
    }
    
    //Report the score
    System.out.println("The game took " + g1.getFlips() + " flips.");
    */
    //Using the AIs

    int count;
    MatchCardGame g2 = new MatchCardGame(32);
    g2.shuffleCards();
    count = playRandom(g2);
    System.out.println("The bad AI took " + count + " flips.");
    MatchCardGame g3 = new MatchCardGame(n);
    g3.shuffleCards();
    count = playGood(g3);
    System.out.println("The good AI took " + count + " flips.");

        MatchCardGame g4 = new MatchCardGame(n);
    g4.shuffleCards();
    count = playGood(g4);
    System.out.println("The good AI took " + count + " flips.");

    
    System.out.println(goodMC(4));
    //Using MCs
    int N = 1000;
    System.out.println("The bad AI took " + randomMC(N) + " flips on average.");
    System.out.println("The good AI took " + goodMC(N) + " flips on average.");
  }



  //random AI
  public static int playRandom(MatchCardGame g) {

  Random ran = new Random();

  while(!g.gameOver()) {


  //this will flip cards randomly until a card is successfully flipped. all cards are possible
    //flip the random card
  while(!g.flip(ran.nextInt(g.game_size))){}

  //System.out.println(g.boardToString()); //for testing

  while(!g.flip(ran.nextInt(g.game_size))){}

  if(!g.wasMatch())
  g.flipMismatch();

  }
  return g.getFlips();
  }


public static int playGood(MatchCardGame g) {
  char[] card_memory = new char[g.game_size];
  //so right now, card_memory is a collection of garbage values
  int[] n = new int[g.game_size]; //n is the game size
  for (int i = 0; i < g.game_size; i++){
    n[i] = i;
    //so n is {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,...}
    card_memory[i] = '0';
    //have card_memory be a character array of '0'
  }
  Random ran = new Random();

  while (!g.gameOver()){

    //checks to see if cards_arr has any matches or memories.
    //in card_memory, '0' is an unknown card. '1' will be a matched card

   // System.out.println(g.boardToString()); //for testing
    boolean knownCards = false;
    boolean knownPair = false;

    //if we know any characters and they're not matched, we then match them
    for (int i = 0; i < g.game_size; i++) {
      if (Character.isLetter(card_memory[i]))
        knownCards = true;
    }

    //see if there's a known pair in memory
    int[] pair = new int[2];
    if (knownCards) {
      for (int i = 0; i < g.game_size; i++){
        for (int j = i + 1; j < g.game_size; j++) {
          if (Character.isLetter(card_memory[i]) && card_memory[i] == card_memory[j]) {
            knownPair = true;
            pair[0] = i; pair[1] = j;
          }
        }
      }
    }

    //if no known cards/pairs
    if (!knownCards) {
      //flip two cards randomly 
      int flip_card = ran.nextInt(g.game_size);
      while(!g.flip(flip_card))
       flip_card = ran.nextInt(g.game_size);
      //store the flipped card into memory

      int flipped1 = flip_card;

      card_memory[flip_card] = g.previousFlipIdentity();

      flip_card = ran.nextInt(g.game_size);
      while(!g.flip(flip_card))
        flip_card = ran.nextInt(g.game_size);
    card_memory[flip_card] = g.previousFlipIdentity();

          //check if mismatch or no
    if(!g.wasMatch()) {
     g.flipMismatch();
  } else {
      card_memory[flip_card] = '1'; card_memory[flipped1] = '1';
   }


  } else if (knownCards && !knownPair) {
    //if there's knownCards but no knownPair
    //randomly flip one card
 //   System.out.println("Known cards but no pair!"); //for testings
      int flip_card = ran.nextInt(g.game_size);
      while (card_memory[flip_card] != '0') {
        flip_card = ran.nextInt(g.game_size);
      }
      g.flip(flip_card);
      //while(!g.flip(flip_card)) MAYBE BRING THIS BACK??
//       flip_card = ran.nextInt(g.game_size);

           int flipped1 = flip_card;

      //store the flipped card into memory
      card_memory[flip_card] = g.previousFlipIdentity();   


        //for testing
   // for (int i = 0; i < g.game_size; i++)
//System.out.print(card_memory[i]);
  //  System.out.print("\n");

      //see if the first flipped card is the same as any other
      for (int i = 0; i < g.game_size; i++) {
        for (int j = i + 1; j < g.game_size; j++) {
          if (Character.isLetter(card_memory[i]) && card_memory[i] == card_memory[j]) {
            knownPair = true;
            pair[0] = i; pair[1] = j;
            break;
          }
        }
      }

     // System.out.println(g.boardToString());




      //if there's a known pair and the card flipped was the one in memory
      if (knownPair && !g.flip(pair[0])) {
        g.flip(pair[1]);
        card_memory[pair[0]] = '1'; card_memory[pair[1]] = '1';
      //  System.out.println("We found one!");
      } else if (knownPair && !g.flip(pair[1])){
        g.flip(pair[0]);
        card_memory[pair[0]] = '1'; card_memory[pair[1]] = '1';
        //System.out.println("We found one!");
      }
      else { 
       // System.out.println("Time to guess!");
       //randomly flip one card

        flip_card = ran.nextInt(g.game_size);
      while(!g.flip(flip_card)) {
       flip_card = ran.nextInt(g.game_size);
      // System.out.println(flip_card);
       if (g.flip(flip_card))
        break;
     }
      //store the flipped card into memory
      card_memory[flip_card] = g.previousFlipIdentity();   

        if(!g.wasMatch()) {
           g.flipMismatch();
        } else {
        card_memory[flip_card] = '1'; card_memory[flipped1]  = '1';
        }


    }

  } else if (knownPair) {
    //if there's a known pair
      g.flip(pair[0]);
      g.flip(pair[1]);
      card_memory[pair[0]] = '1'; card_memory[pair[1]] = '1';
    }



  //for testing
 //   for (int i = 0; i < g.game_size; i++)
   //   System.out.print(card_memory[i]);
   // System.out.print("\n");


  }
  return g.getFlips();
}

//play random N times and then output the average turns
public static double randomMC(int N) {

  int sum = 0;
  for (int i = 0; i < N; i++) {
    MatchCardGame g = new MatchCardGame(32);
    g.shuffleCards();

    sum += playRandom(g);
}
  return sum/N;

}

//play good mc
public static double goodMC(int N) {
  int sum = 0;
  for (int i = 0; i < N; i++){
    MatchCardGame g = new MatchCardGame(32);

    g.shuffleCards();
    sum += playGood(g);
     }

return sum/N;
}
}