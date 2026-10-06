package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lysakov.cards.AceCard;
import ru.nsu.lysakov.cards.Card;
import ru.nsu.lysakov.cards.CardEnum;
import ru.nsu.lysakov.cards.NumberCard;
import ru.nsu.lysakov.cards.PictureCard;
import ru.nsu.lysakov.cards.Suit;
import ru.nsu.lysakov.game.Shoe;
import ru.nsu.lysakov.players.Gambler;
import ru.nsu.lysakov.players.Player;
import ru.nsu.lysakov.ui.Input;

/**
 * Тесты игры Blackjack.
 */
class MainTest {
    private final InputStream oldIn = System.in;

    /**
     * Возвращает стандартный поток ввода после теста.
     */
    @AfterEach
    void restoreInput() {
        System.setIn(oldIn);
    }

    /**
     * Проверка символов всех мастей.
     */
    @Test
    void suitShouldReturnCorrectSymbols() {
        assertEquals("♥", Suit.HEARTS.toSymbol());
        assertEquals("♦", Suit.DIAMONDS.toSymbol());
        assertEquals("♣", Suit.CLUBS.toSymbol());
        assertEquals("♠", Suit.SPADES.toSymbol());
    }

    /**
     * Проверка номинальной стоимости карт.
     */
    @Test
    void cardNominalValueShouldBeCorrect() {
        Card ace = new AceCard(CardEnum.ACE, Suit.HEARTS);
        Card numberCard = new NumberCard(CardEnum.TEN, Suit.SPADES);
        Card kingCard = new PictureCard(CardEnum.NINE, Suit.SPADES);

        assertEquals(11, ace.getNominal());
        assertEquals(10, numberCard.getNominal());
        assertEquals(10, kingCard.getNominal());
    }

    /**
     * Игрок может брать карту, если сумма меньше 21.
     */
    @Test
    void playerCanTakeCardWhenScoreLessThan21() {
        Player player = new Player();

        player.addCard(new NumberCard(CardEnum.TEN, Suit.HEARTS));
        player.addCard(new NumberCard(CardEnum.NINE, Suit.SPADES));

        assertTrue(player.canTakeCard());
    }

    /**
     * Игрок не может брать карту при сумме 21.
     */
    @Test
    void playerCannotTakeCardWhenScoreEquals21() {
        Player player = new Player();

        player.addCard(new NumberCard(CardEnum.TEN, Suit.HEARTS));
        player.addCard(new AceCard(CardEnum.ACE, Suit.SPADES));

        assertFalse(player.canTakeCard());
    }

    /**
     * Игрок не может брать карту при переборе.
     */
    @Test
    void playerCannotTakeCardWhenScoreMoreThan21() {
        Player player = new Player();

        player.addCard(new NumberCard(CardEnum.TEN, Suit.HEARTS));
        player.addCard(new NumberCard(CardEnum.TEN, Suit.SPADES));
        player.addCard(new NumberCard(CardEnum.FIVE, Suit.CLUBS));

        assertFalse(player.canTakeCard());
    }

    /**
     * Проверка получения карты из шула.
     */
    @Test
    void shoeShouldReturnCard() {
        Shoe shoe = new Shoe(1);

        Card card = shoe.takeCard();

        assertNotNull(card);
    }

    /**
     * Проверка работы шула после окончания колоды.
     */
    @Test
    void shoeShouldResetWhenAllCardsUsed() {
        Shoe shoe = new Shoe(1);

        Card card = null;

        for (int i = 0; i < 53; ++i) {
            card = shoe.takeCard();
        }

        assertNotNull(card);
    }

    /**
     * Проверка действия остановки набора карт.
     */
    @Test
    void inputShouldReturnZeroForStopAction() {
        Input input = createInput("0\n");

        assertEquals(0, input.getPlayerAction());
    }

    /**
     * Проверка действия получения карты.
     */
    @Test
    void inputShouldReturnOneForTakeCardAction() {
        Input input = createInput("1\n");

        assertEquals(1, input.getPlayerAction());
    }

    /**
     * Проверка неправильного действия игрока.
     */
    @Test
    void inputShouldReturnMinusOneForWrongPlayerAction() {
        Input input = createInput("5\n");

        assertEquals(-1, input.getPlayerAction());
    }

    /**
     * Проверка минимального допустимого количества колод.
     */
    @Test
    void inputShouldAcceptOneDeck() {
        Input input = createInput("1\n");

        assertEquals(1, input.getInitGameAction());
    }

    /**
     * Проверка максимального допустимого количества колод.
     */
    @Test
    void inputShouldAcceptTenDecks() {
        Input input = createInput("10\n");

        assertEquals(10, input.getInitGameAction());
    }

    /**
     * Проверка слишком маленького количества колод.
     */
    @Test
    void inputShouldRejectZeroDecks() {
        Input input = createInput("0\n");

        assertEquals(-1, input.getInitGameAction());
    }

    /**
     * Проверка слишком большого количества колод.
     */
    @Test
    void inputShouldRejectMoreThanTenDecks() {
        Input input = createInput("11\n");

        assertEquals(-1, input.getInitGameAction());
    }

    /**
     * Проверка ввода текста вместо количества колод.
     */
    @Test
    void inputShouldRejectNonNumberDeckCount() {
        Input input = createInput("abc\n");

        assertEquals(-1, input.getInitGameAction());
    }

    /**
     * Проверка ожидания нажатия Enter.
     */
    @Test
    void waitEnterShouldReadInput() {
        Input input = createInput("\n");

        input.waitEnter();
    }

    /**
     * Создание объекта ввода с заданными данными.
     *
     * @param text данные для стандартного ввода
     * @return объект ввода
     */
    private Input createInput(String text) {
        ByteArrayInputStream stream = new ByteArrayInputStream(
                text.getBytes(StandardCharsets.UTF_8)
        );

        System.setIn(stream);

        return new Input();
    }


    /**
     * Проверка туза как 11.
     */
    @Test
    void aceShouldCountAsEleven() {
        Gambler gambler = new Gambler();

        gambler.addCard(new AceCard(CardEnum.ACE, Suit.HEARTS));
        gambler.addCard(new NumberCard(CardEnum.FIVE, Suit.CLUBS));

        assertEquals(16, gambler.getNominal());
    }

    /**
     * Проверка нескольких тузов.
     */
    @Test
    void multipleAcesShouldBeHandledCorrectly() {
        Gambler gambler = new Gambler();

        gambler.addCard(new AceCard(CardEnum.ACE, Suit.HEARTS));
        gambler.addCard(new AceCard(CardEnum.ACE, Suit.SPADES));
        gambler.addCard(new NumberCard(CardEnum.NINE, Suit.CLUBS));

        assertEquals(21, gambler.getNominal());
    }

    /**
     * Проверка закрытой карты.
     */
    @Test
    void closedCardShouldNotBeCounted() {
        Gambler gambler = new Gambler();

        NumberCard card = new NumberCard(CardEnum.TEN, Suit.HEARTS);
        card.hide();

        gambler.addCard(card);
        gambler.addCard(new NumberCard(CardEnum.FIVE, Suit.CLUBS));

        assertEquals(5, gambler.getNominal());
    }

    /**
     * Проверка очистки руки.
     */
    @Test
    void resetShouldClearHand() {
        Gambler gambler = new Gambler();

        gambler.addCard(new NumberCard(CardEnum.TEN, Suit.HEARTS));
        gambler.addCard(new NumberCard(CardEnum.FIVE, Suit.CLUBS));

        gambler.reset();

        assertEquals(0, gambler.getNominal());
    }

    /**
     * Проверка печати руки.
     */
    @Test
    void printShouldOutputCards() {
        Gambler gambler = new Gambler();

        gambler.addCard(new NumberCard(CardEnum.SEVEN, Suit.HEARTS));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            gambler.print();
        } finally {
            System.setOut(oldOut);
        }

        assertTrue(output.toString().contains("7"));
    }


}