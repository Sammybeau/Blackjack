import java.util.Random;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

public class Deck {
  private final int singleDeck = 52;
  private int numberOfDecks = 6;
  private int numberOfCards = singleDeck * numberOfDecks;
  private int[] deck = new int[numberOfCards];

  public void buildDeck() {
    int count = 0;
    for(int k = 0; k < numberOfDecks; k++) {
      for(int i = 1; i <= singleDeck/4; i++) {
        for(int j = 0; j < 4; j++) {
          deck[count] = i;
          if(deck[count] >= 11 && deck[count] <= 13) {
            deck[count] = 10;
          }
          count++;
        }
      }
    }
  }

  public void shuffleCards(int[] array)
  {
    int index;
    Random random = new Random();
    for (int i = array.length - 1; i > 0; i--)
    {
      index = random.nextInt(i + 1);
      if (index != i)
      {
        array[index] ^= array[i];
        array[i] ^= array[index];
        array[index] ^= array[i];
      }
    }
  }

  public int removeCard(int[] deck, int cardToRemove) {
    for(int i = 0; i < deck.length; i++) {
      if(deck[i] == cardToRemove) {
        for(int j = i; j >= 0; j--) {
          if(j==0) {
            deck[j] = 0;
          } else {
            deck[j] = deck[j-1];
          }
        }
        return cardToRemove;
      }
    }
    return cardToRemove;
  }

  public int dealCard(int[] deck) {
    int temp = 0;
    for(int i = 0; i < deck.length; i++) {
      if(deck[i] != 0){
        temp = deck[i];
        deck[i] = 0;
        return temp;
      }
    }
    return error;
  }

  public void printDeck(int[] deck) {
    for (int i = 0; i < deck.length; i++) {
      System.out.print(deck[i] + ", ");
    }
    System.out.println();
  }

  public void setupDeck() {
    this.buildDeck();

/*    this.removeCard(deck, 10);
    this.removeCard(deck, 10);
    this.removeCard(deck, 10);
    this.removeCard(deck, 10);
    this.removeCard(deck, 1);
    this.removeCard(deck, 1);
    this.removeCard(deck, 1);
    this.removeCard(deck, 1);
    this.removeCard(deck, 10);*/

    this.shuffleCards(deck);
  }

  public int[] getDeck() {
    return deck;
  }

}
