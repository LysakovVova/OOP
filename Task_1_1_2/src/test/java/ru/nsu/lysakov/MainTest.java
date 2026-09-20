package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты основных классов игры.
 */
class MainTest {

    /**
     * Проверка номинала числовой карты.
     */
    @Test
    void testNumberCardNominal() {
        Card card = new NumberCard("7", Suit.HEARTS);

        assertEquals(7, card.getNominal());
    }

    /**
     * Проверка номинала карты с картинкой.
     */
    @Test
    void testPictureCardNominal() {
        Card card = new PictureCard("K", Suit.SPADES);

        assertEquals(10, card.getNominal());
    }

    /**
     * Проверка номинала туза.
     */
    @Test
    void testAceCardNominal() {
        Card card = new AceCard("A", Suit.CLUBS);

        assertEquals(11, card.getNominal());
    }

    /**
     * Проверка закрытой карты.
     */
    @Test
    void testClosedCardNominal() {
        Card card = new NumberCard("8", Suit.DIAMONDS);

        card.hide();

        assertEquals(0, card.getNominal());
    }

    /**
     * Проверка добавления карт игроку.
     */
    @Test
    void testGamblerNominal() {
        Gambler gambler = new Gambler();

        gambler.addCard(new NumberCard("10", Suit.HEARTS));
        gambler.addCard(new NumberCard("7", Suit.SPADES));

        assertEquals(17, gambler.getNominal());
    }

    /**
     * Проверка изменения стоимости туза с 11 на 1.
     */
    @Test
    void testAceBecomesOne() {
        Gambler gambler = new Gambler();

        gambler.addCard(new AceCard("A", Suit.HEARTS));
        gambler.addCard(new NumberCard("9", Suit.SPADES));
        gambler.addCard(new NumberCard("5", Suit.CLUBS));

        assertEquals(15, gambler.getNominal());
    }

    /**
     * Проверка нескольких тузов.
     */
    @Test
    void testMultipleAces() {
        Gambler gambler = new Gambler();

        gambler.addCard(new AceCard("A", Suit.HEARTS));
        gambler.addCard(new AceCard("A", Suit.SPADES));
        gambler.addCard(new NumberCard("9", Suit.CLUBS));

        assertEquals(21, gambler.getNominal());
    }

    /**
     * Проверка очистки руки.
     */
    @Test
    void testGamblerReset() {
        Gambler gambler = new Gambler();

        gambler.addCard(new NumberCard("10", Suit.HEARTS));
        gambler.addCard(new NumberCard("5", Suit.CLUBS));

        gambler.reset();

        assertEquals(0, gambler.getNominal());
    }

    /**
     * Проверка получения карты из колоды.
     */
    @Test
    void testDeckTakeCard() {
        Deck deck = new Deck();

        Card card = deck.takeCard();

        assertNotNull(card);
    }

    /**
     * Проверка символа масти.
     */
    @Test
    void testSuitSymbol() {
        assertEquals("♥", Suit.HEARTS.toSymbol());
        assertEquals("♦", Suit.DIAMONDS.toSymbol());
        assertEquals("♣", Suit.CLUBS.toSymbol());
        assertEquals("♠", Suit.SPADES.toSymbol());
    }

    /**
     * Проверка начального состояния игры.
     */
    @Test
    void testGameStateInitialState() {
        GameStateClass state = new GameStateClass();

        assertEquals(GameState.START_GAME, state.getState());
    }

    /**
     * Проверка изменения состояния игры.
     */
    @Test
    void testGameStateChange() {
        GameStateClass state = new GameStateClass();

        state.setStateGame(GameState.USER_MOVE);

        assertEquals(GameState.USER_MOVE, state.getState());
    }

    /**
     * Проверка сброса состояния игры.
     */
    @Test
    void testGameStateClear() {
        GameStateClass state = new GameStateClass();

        state.setStateGame(GameState.END_GAME);
        state.incScoreUser();
        state.incScoreDiller();

        state.clear();

        assertEquals(GameState.START_GAME, state.getState());
    }

    /**
     * Проверка открытия и закрытия карты.
     */
    @Test
    void testCardHideAndOpen() {
        Card card = new NumberCard("5", Suit.HEARTS);

        card.hide();
        assertEquals(0, card.getNominal());

        card.open();
        assertEquals(5, card.getNominal());
    }

    /**
     * Проверка закрытия последней карты игрока.
     */
    @Test
    void testHideLastCard() {
        Gambler gambler = new Gambler();

        gambler.addCard(new NumberCard("10", Suit.HEARTS));
        gambler.addCard(new NumberCard("5", Suit.CLUBS));

        gambler.hideLastCard();

        assertEquals(10, gambler.getNominal());
    }

    /**
     * Проверка открытия последней карты игрока.
     */
    @Test
    void testOpenLastCard() {
        Gambler gambler = new Gambler();

        gambler.addCard(new NumberCard("10", Suit.HEARTS));
        gambler.addCard(new NumberCard("5", Suit.CLUBS));

        gambler.hideLastCard();
        gambler.openLastCard();

        assertEquals(15, gambler.getNominal());
    }

    /**
     * Проверка руки с перебором без тузов.
     */
    @Test
    void testGamblerBust() {
        Gambler gambler = new Gambler();

        gambler.addCard(new NumberCard("10", Suit.HEARTS));
        gambler.addCard(new PictureCard("K", Suit.SPADES));
        gambler.addCard(new NumberCard("5", Suit.CLUBS));

        assertEquals(25, gambler.getNominal());
    }

    /**
     * Проверка увеличения очков пользователя.
     */
    @Test
    void testIncScoreUser() {
        GameStateClass state = new GameStateClass();

        state.incScoreUser();

        assertEquals(1, state.getScoreUser());
    }

    /**
     * Проверка увеличения очков диллера.
     */
    @Test
    void testIncScoreDiller() {
        GameStateClass state = new GameStateClass();

        state.incScoreDiller();

        assertEquals(1, state.getScoreDiller());
    }
}