package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ru.nsu.lysakov.cards.Card;
import ru.nsu.lysakov.cards.Suit;
import ru.nsu.lysakov.game.BlackjackRules;
import ru.nsu.lysakov.game.GameScore;
import ru.nsu.lysakov.game.RoundResult;
import ru.nsu.lysakov.game.Shoe;
import ru.nsu.lysakov.players.Dealer;
import ru.nsu.lysakov.players.Player;

/**
 * Тесты основных классов игры.
 */
class MainTest {

    @Test
    void playerNominalCountsAceAsElevenOrOne() {
        Player player = new Player();

        Card ace = new Card("A", Suit.HEARTS);
        Card nine = new Card("9", Suit.DIAMONDS);
        player.addCard(ace);
        player.addCard(nine);
        assertEquals(20, player.getNominal());

        Card nineTwo = new Card("9", Suit.CLUBS);
        player.addCard(nineTwo);
        assertEquals(19, player.getNominal());
    }

    @Test
    void hiddenCardsAreIgnoredInScore() {
        Player player = new Player();

        Card ten = new Card("10", Suit.HEARTS);
        Card ace = new Card("A", Suit.SPADES);
        player.addCard(ten);
        player.addCard(ace);

        assertEquals(21, player.getNominal());

        ten.hide();
        assertEquals(11, player.getNominal());
    }

    @Test
    void dealerNeedsCardOnlyBelowSeventeen() {
        Dealer dealer = new Dealer();

        dealer.addCard(new Card("10", Suit.HEARTS));
        dealer.addCard(new Card("7", Suit.CLUBS));
        assertFalse(dealer.needCard());

        dealer.reset();
        dealer.addCard(new Card("10", Suit.DIAMONDS));
        dealer.addCard(new Card("5", Suit.SPADES));
        assertTrue(dealer.needCard());
    }

    @Test
    void blackjackRulesChooseCorrectWinner() {
        BlackjackRules rules = new BlackjackRules();

        Player player = new Player();
        Dealer dealer = new Dealer();

        player.addCard(new Card("K", Suit.HEARTS));
        player.addCard(new Card("8", Suit.CLUBS));
        dealer.addCard(new Card("10", Suit.DIAMONDS));
        dealer.addCard(new Card("7", Suit.SPADES));
        assertEquals(RoundResult.PLAYER_WIN, rules.getResult(player, dealer));

        player = new Player();
        dealer = new Dealer();
        player.addCard(new Card("10", Suit.HEARTS));
        player.addCard(new Card("9", Suit.CLUBS));
        player.addCard(new Card("5", Suit.SPADES));
        dealer.addCard(new Card("10", Suit.DIAMONDS));
        dealer.addCard(new Card("9", Suit.HEARTS));
        assertEquals(RoundResult.DEALER_WIN, rules.getResult(player, dealer));

        player = new Player();
        dealer = new Dealer();
        player.addCard(new Card("10", Suit.HEARTS));
        player.addCard(new Card("7", Suit.CLUBS));
        dealer.addCard(new Card("10", Suit.DIAMONDS));
        dealer.addCard(new Card("7", Suit.SPADES));
        assertEquals(RoundResult.DRAW, rules.getResult(player, dealer));
    }

    @Test
    void shoeRebuildsDeckAfterExhaustion() {
        Shoe shoe = new Shoe(1);

        for (int i = 0; i < 100; i++) {
            assertNotNull(shoe.takeCard());
        }
    }

    @Test
    void scoreTracksWinsForPlayerAndDealer() {
        GameScore score = new GameScore();

        score.playerWin();
        score.playerWin();
        score.dealerWin();

        assertEquals(2, score.getPlayerScore());
        assertEquals(1, score.getDealerScore());
    }
}