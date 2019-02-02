public class Main {

    public static void main(String[] args) {
        Deck deck = new Deck();
        User user = new User();
        int playerFinalValue = 0;
        int playerFinalValue2 = 0;
        int dealerFinalValue = 0;
        double numberOfWins = 0;
        int numberOfHands = 0;
        user.setDoubleOnSoftAllowed(true);

        for (int i = 0; i < 20000; i++) {
            //System.out.println("Here is our original deck with the selected cards removed:");
            deck.setupDeck();

            user.dealCard(deck.dealCard(deck.getDeck()), "dealer");

            user.dealCard(deck.dealCard(deck.getDeck()), "player");
            user.dealCard(deck.dealCard(deck.getDeck()), "player");

        /*user.dealCard(1, "dealer");
        user.dealCard(8, "player");
        user.dealCard(8, "player");*/

            System.out.println();
            System.out.println("Dealer's initial hand: " + user.getDealerHand());
            System.out.println("User's initial hand: " + user.getPlayerHand());
            //System.out.println();
            //System.out.println("Here is our deck after our deal: ");
            //deck.printDeck(deck.getDeck());

            user.setWhetherHandIsSoft(user.getPlayerHand());

            //determine if the player should split
            if (user.shouldSplit()) {
                user.getPlayerHand2().add(user.getPlayerHand().get(1));
                user.getPlayerHand().remove(1);
                user.dealCard(deck.dealCard(deck.getDeck()), "player");
                user.dealCard(deck.dealCard(deck.getDeck()), "player2");
                System.out.println("The player has chosen to split");
                System.out.println("The player's first hand: " + user.getPlayerHand());
                System.out.println("The player's second hand: " + user.getPlayerHand2());
                //System.out.println("The remaining deck: ");
                //deck.printDeck(deck.getDeck());
                user.setUserHasSplit(true);
                user.setBetSize2(1);

                while (user.shouldHit(user.getPlayerHand())) {
                    System.out.println();
                    user.dealCard(deck.dealCard(deck.getDeck()), "player");
                    System.out.println("The player has split, and has been dealt a new card. His first hand is now: " + user.getPlayerHand());
                }
                while (user.shouldHit(user.getPlayerHand2())) {
                    System.out.println();
                    user.dealCard(deck.dealCard(deck.getDeck()), "player2");
                    System.out.println("The player has split, and has been dealt a new card. His second hand is now: " + user.getPlayerHand2());
                }
            }
            //determine if the player should double down
            else if (user.shouldDoubleDown()) {
                user.setBetSize(2);
                user.dealCard(deck.dealCard(deck.getDeck()), "player");
                System.out.println();
                System.out.println("The player had doubled down and his hand is now: " + user.getPlayerHand());
                user.setUserHasDoubledDown(true);
            }
            //determine if the user should hit
            while (user.shouldHit(user.getPlayerHand())) {
                System.out.println();
                user.dealCard(deck.dealCard(deck.getDeck()), "player");
                System.out
                  .println("The player has not doubled down or split, and has been dealt a new card. His hand is now: " + user.getPlayerHand());
            }

            //dealer's turn
            user.dealCard(deck.dealCard(deck.getDeck()), "dealer");
            System.out.println();
            System.out.println("The dealer's hand is: " + user.getDealerHand());
            while (user.dealerShouldHit()) {
                System.out.println();
                user.dealCard(deck.dealCard(deck.getDeck()), "dealer");
                System.out.println("The dealer has taken another card. His hand is now: " + user.getDealerHand());
            }

            //TODO need to add when dealer has a BJ and user does not

            //determine who wins!
            playerFinalValue = user.getHandValue(user.getPlayerHand());
            playerFinalValue2 = user.getHandValue(user.getPlayerHand2());
            dealerFinalValue = user.getHandValue(user.getDealerHand());

            //if any of the hands are soft
            if (user.getWhetherFinalHandIsSoft(user.getPlayerHand())) {
                playerFinalValue = playerFinalValue + 10;
            }
            if (user.getWhetherFinalHandIsSoft(user.getPlayerHand2())) {
                playerFinalValue2 = playerFinalValue2 + 10;
            }
            if (user.getWhetherFinalHandIsSoft(user.getDealerHand())) {
                dealerFinalValue = dealerFinalValue + 10;
            }
            //if the dealer got a blackjack and the player does not have one
            if (user.userHasBlackjack(user.getDealerHand()) && !user.userHasBlackjack(user.getPlayerHand())) {
                System.out.println("Dealer got a blackjack, player does not, and we will skip the rest!");
            }
            //if the user has split
            else if (user.getUserHasSplit()) {
                if ((playerFinalValue > dealerFinalValue) && playerFinalValue <= 21) {
                    numberOfWins++;
                }
                if ((playerFinalValue2 > dealerFinalValue) && playerFinalValue2 <= 21) {
                    numberOfWins++;
                }
                if (dealerFinalValue > 21 && playerFinalValue <= 21) {
                    numberOfWins++;
                }
                if (dealerFinalValue > 21 && playerFinalValue2 <= 21) {
                    numberOfWins++;
                }
                if (((playerFinalValue == dealerFinalValue) && playerFinalValue <= 21) || ((playerFinalValue2 == dealerFinalValue)
                  && playerFinalValue2 <= 21)) {
                    numberOfHands--;
                }
            }
            //if the user has doubled down
            else if (user.getUserHasDoubledDown()) {
                if ((playerFinalValue > dealerFinalValue) && playerFinalValue <= 21) {
                    numberOfWins = numberOfWins + 2;
                } else if (dealerFinalValue > 21 && playerFinalValue <= 21) {
                    numberOfWins = numberOfWins + 2;
                } else if (playerFinalValue == dealerFinalValue) {
                    numberOfHands--;
                } else {
                    numberOfWins = numberOfWins - 2;
                }
            }
            //if the user has a blackjack or if both user and dealer have a blackjack
            else if (user.userHasBlackjack(user.getPlayerHand())) {
                if (!user.userHasBlackjack(user.getDealerHand())) {
                    numberOfWins = numberOfWins + 1.5;
                }
            }
            //if player has a higher total than the dealer
            else if ((playerFinalValue > dealerFinalValue) && playerFinalValue <= 21) {
                numberOfWins++;
            }
            //if player has the same total as the dealer
            else if (playerFinalValue == dealerFinalValue) {
                numberOfHands--;
            }
            //if the dealer busts
            else if (dealerFinalValue > 21 && playerFinalValue <= 21) {
                numberOfWins++;
            }

            numberOfHands++;

            System.out.println();
            System.out.println("The final hand of the user is: " + user.getPlayerHand());
            if (user.getUserHasSplit()) {
                System.out.println("The final second hand of the user is: " + user.getPlayerHand2());
            }
            System.out.println("The final hand of the dealer is: " + user.getDealerHand());
            System.out.println();
            System.out.println("The number of hands the user has won: " + numberOfWins);
            System.out.println("The number of hands that have been played (minus hands that have been drawn): " + numberOfHands);
            System.out.println();
            System.out.println(
              "______________________________________________________________________________________________________________________________________________________________________");
            System.out.println();

            //reset
            user.clearHand(user.getPlayerHand());
            user.clearHand(user.getPlayerHand2());
            user.clearHand(user.getDealerHand());
            user.setUserHasDoubledDown(false);
            user.setUserHasSplit(false);
        }
        System.out.println(numberOfWins/numberOfHands);
    }



}