import java.util.ArrayList;

public class User {

  private ArrayList<Integer> dealerHand = new ArrayList<>();
  private ArrayList<Integer> playerHand = new ArrayList<>();
  private ArrayList<Integer> playerHand2 = new ArrayList<>();
  private boolean doubleOnSoftAllowed = false;
  private boolean handIsSoft = false;
  private boolean userHasSplit = false;
  private boolean userHasDoubledDown = false;

  private int betSize = 1;
  private int betSize2 = 0;

  public void dealCard(int card, String hand) {
    if (hand == "player") {
      playerHand.add(card);
    } else if (hand == "dealer") {
      dealerHand.add(card);
    } else if (hand == "player2") {
      playerHand2.add(card);
    }
  }

  public int getHandValue(ArrayList<Integer> hand) {
    int handValue = 0;
    for(int i = 0; i < hand.size(); i++) {
      handValue = handValue + hand.get(i);
    }
    return handValue;
  }

  public boolean shouldSplit() {
    boolean split = false;
    //Does the player have 2 matching cards?
    if (playerHand.get(0).equals(playerHand.get(1))) {
      //Cases for As and 8s
      if (playerHand.get(0).equals(1) || playerHand.get(0).equals(8)) {
        split = true;
      } //Cases for 9s
      else if (!(dealerHand.get(0).equals(7) || dealerHand.get(0).equals(10) || dealerHand.get(0).equals(1)) && playerHand.get(0).equals(9)) {
        split = true;
      } //Cases for 7s
      else if (dealerHand.get(0) <= 7 && playerHand.get(0).equals(7)) {
        split = true;
      } //Cases for 6s
      else if ((dealerHand.get(0) <= 6 && dealerHand.get(0) >= 3) && playerHand.get(0).equals(6)) {
        split = true;
      } //Cases for 3s and 2s
      else if ((dealerHand.get(0) <= 7 && dealerHand.get(0) >= 4) && (playerHand.get(0).equals(3) || playerHand.get(0).equals(2))) {
        split = true;
      }
    }
    return split;
  }

  public boolean shouldDoubleDown() {
    boolean doubleDown = false;
    //Cases for value of 9
    if (!this.handIsSoft) {
      if (this.getHandValue(playerHand) == 9 && (dealerHand.get(0) <= 6 && dealerHand.get(0) >= 3)) {
        doubleDown = true;
      } //Cases for value of 10
      else if (this.getHandValue(playerHand) == 10 && (dealerHand.get(0) <= 9 && dealerHand.get(0) >= 2)) {
        doubleDown = true;
      } //Cases for value of 11
      else if (this.getHandValue(playerHand) == 11) {
        doubleDown = true;
      }
    }
    //Double on soft allowed
    if (this.doubleOnSoftAllowed && this.handIsSoft) {
      //Cases for dealer having a 6 or 5
      if ((this.getHandValue(playerHand) >= 3 && this.getHandValue(playerHand) <= 7) && (dealerHand.get(0) <= 6 && dealerHand.get(0) >= 5)) {
        doubleDown = true;
      } //Cases for dealer having a 4
      else if ((this.getHandValue(playerHand) >= 5 && this.getHandValue(playerHand) <= 7) && dealerHand.get(0).equals(4)) {
        doubleDown = true;
      } //Case for dealer having a 3
      else if (this.getHandValue(playerHand) == 7 && dealerHand.get(0).equals(3)) {
        doubleDown = true;
      }
    }
    return doubleDown;
  }

  public boolean shouldHit(ArrayList<Integer> playerHand) {
    boolean userShouldHit = false;
    this.setWhetherHandIsSoft(playerHand);
    //User cannot hit if they have doubled down
    if (!userHasDoubledDown) {
      //In the case of a hard hand
      if (!handIsSoft) {
        if (this.playHardHand(playerHand)) {
          userShouldHit = true;
        }
      } //In the case of a soft hand
      else {
        if (this.playSoftHand(playerHand)) {
          userShouldHit = true;
        }
      }
    }
    return userShouldHit;
  }

  public boolean dealerShouldHit() {
    boolean dealerShouldHit = false;
    this.setWhetherHandIsSoft(dealerHand);
    //In the case of a hard hand
    if (!handIsSoft) {
      if (getHandValue(dealerHand) <= 16) {
        dealerShouldHit = true;
      }
    } //In the case of a soft hand
    else {
      if (getHandValue(dealerHand) <= 6 || (getHandValue(dealerHand) >= 12 && getHandValue(dealerHand) <= 16)) {
        dealerShouldHit = true;
      }
    }
    return dealerShouldHit;
  }

  private boolean playHardHand(ArrayList<Integer> playerHand) {
    boolean userShouldHit = false;
    //Cases for user having 4-11
    if (getHandValue(playerHand) >= 4 && getHandValue(playerHand) <= 11) {
      userShouldHit = true;
    } //Cases for user having 12
    else if ((getHandValue(playerHand) == 12) && ((dealerHand.get(0) <= 3 && dealerHand.get(0) >= 1) || (dealerHand.get(0) <= 10 && dealerHand.get(0) >= 7))) {
      userShouldHit = true;
    } //Cases for user having 13-16
    else if ((getHandValue(playerHand) <= 16 && getHandValue(playerHand) >= 13) && ((dealerHand.get(0) <= 10 && dealerHand.get(0) >= 7) || dealerHand.get(0).equals(1))) {
      userShouldHit = true;
    } //Cases for user having 17+
    else {
      userShouldHit = false;
    }
    return userShouldHit;
  }

  private boolean playSoftHand(ArrayList<Integer> playerHand) {
    boolean userShouldHit = false;
    //Cases for user having A2-A6
    if (getHandValue(playerHand) >= 3 && getHandValue(playerHand) <= 7) {
      userShouldHit = true;
    } //Cases for user having A7
    else if ((getHandValue(playerHand) == 8) && (dealerHand.get(0).equals(10) || dealerHand.get(0).equals(1))) {
      userShouldHit = true;
    } //Cases for hard 12
    else if ((getHandValue(playerHand) == 12) && ((dealerHand.get(0) <= 3 && dealerHand.get(0) >= 1) || (dealerHand.get(0) <= 10 && dealerHand.get(0) >= 7))) {
      userShouldHit = true;
    } //Cases for hard 13-16
    else if ((getHandValue(playerHand) <= 16 && getHandValue(playerHand) >= 13) && ((dealerHand.get(0) <= 10 && dealerHand.get(0) >= 7) || dealerHand.get(0).equals(1))) {
      userShouldHit = true;
    }
    //Cases for user having A8-A10 or hard 17+
    else {
      userShouldHit = false;
    }
    return userShouldHit;
  }

  public void setWhetherHandIsSoft(ArrayList<Integer> playerHand) {
    handIsSoft = false;
    for (int i = 0; i < playerHand.size(); i++) {
      if (playerHand.get(i).equals(1)) {
        handIsSoft = true;
      }
    }
  }

  public boolean getWhetherFinalHandIsSoft(ArrayList<Integer> playerHand) {
    for (int i = 0; i < playerHand.size(); i++) {
      if (playerHand.get(i).equals(1)) {
        if (getHandValue(playerHand) <= 11) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean userHasBlackjack(ArrayList<Integer> playerHand) {
    if (getHandValue(playerHand) == 11 && playerHand.size() == 2) {
      return true;
    } else {
      return false;
    }
  }

  public void clearHand(ArrayList<Integer> playerHand) {
    playerHand.clear();
  }














  public ArrayList<Integer> getDealerHand() {
    return dealerHand;
  }

  public void setDealerHand(ArrayList<Integer> dealerHand) {
    this.dealerHand = dealerHand;
  }

  public ArrayList<Integer> getPlayerHand() {
    return playerHand;
  }

  public void setPlayerHand(ArrayList<Integer> playerHand) {
    this.playerHand = playerHand;
  }

  public ArrayList<Integer> getPlayerHand2() {
    return playerHand2;
  }

  public void setPlayerHand2(ArrayList<Integer> playerHand2) {
    this.playerHand2 = playerHand2;
  }

  public boolean isDoubleOnSoftAllowed() {
    return doubleOnSoftAllowed;
  }

  public void setDoubleOnSoftAllowed(boolean doubleOnSoftAllowed) {
    this.doubleOnSoftAllowed = doubleOnSoftAllowed;
  }

  public boolean getUserHasSplit() {
    return userHasSplit;
  }

  public void setUserHasSplit(boolean userHasSplit) {
    this.userHasSplit = userHasSplit;
  }

  public int getBetSize() {
    return betSize;
  }

  public void setBetSize(int betSize) {
    this.betSize = betSize;
  }

  public int getBetSize2() {
    return betSize2;
  }

  public void setBetSize2(int betSize2) {
    this.betSize2 = betSize2;
  }

  public boolean getUserHasDoubledDown() {
    return userHasDoubledDown;
  }

  public void setUserHasDoubledDown(boolean userHasDoubledDown) {
    this.userHasDoubledDown = userHasDoubledDown;
  }


}
