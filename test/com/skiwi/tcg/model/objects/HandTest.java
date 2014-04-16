
package com.skiwi.tcg.model.objects;

import com.skiwi.tcg.model.cards.Card;
import com.skiwi.tcg.model.cards.MonsterCard;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Frank van Heeswijk
 */
public class HandTest {
    static {
        assertTrue(true);
    }

    @Test
    public void testConstructor() {
        new Hand(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorIAE() {
        new Hand(0);
    }

    @Test
    public void testIsFull() {
        Hand hand = new Hand(2);
        hand.add(createCard());
        assertFalse("hand should not be full", hand.isFull());
        hand.add(createCard());
        assertTrue("hand should be full", hand.isFull());
        hand.play(1);
        assertFalse("hand should not be full anymore", hand.isFull());
    }

    @Test
    public void testAdd() {
        Hand hand = new Hand(1);
        hand.add(createCard());
    }

    @Test(expected = NullPointerException.class)
    public void testAddNPE() {
        Hand hand = new Hand(1);
        hand.add(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddISE() {
        Hand hand = new Hand(1);
        hand.add(createCard());
        hand.add(createCard());
    }
    
    @Test
    public void testGet() {
        Hand hand = new Hand(1);
        Card card = createCard();
        hand.add(card);
        assertEquals("card should be equal", card, hand.get(0));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIOOBE() {
        Hand hand = new Hand(1);
        hand.get(0);
    }

    @Test
    public void testPlay() {
        Hand hand = new Hand(1);
        Card card = createCard();
        hand.add(card);
        assertEquals("card should be equal", card, hand.play(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPlayIOOBE1() {
        Hand hand = new Hand(1);
        hand.play(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPlayIOOBE2() {
        Hand hand = new Hand(1);
        hand.play(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPlayIOOB3() {
        Hand hand = new Hand(1);
        hand.add(createCard());
        hand.play(1);
    }

    @Test
    public void testSwap() {
        Hand hand = new Hand(2);
        Card card = createCard();
        Card card2 = createCard2();
        hand.add(card);
        hand.add(card2);
        assertNotSame("card should be unequal to card2", card, card2);
        hand.swap(0, 1);
        assertEquals("card should be equal", card, hand.play(1));
        assertEquals("card2 should be equal", card2, hand.play(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSwapIOOBE1() {
        Hand hand = new Hand(1);
        hand.add(createCard());
        hand.swap(-1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSwapIOOBE2() {
        Hand hand = new Hand(1);
        hand.add(createCard());
        hand.swap(1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSwapIOOBE3() {
        Hand hand = new Hand(1);
        hand.add(createCard());
        hand.swap(0, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSwapIOOBE4() {
        Hand hand = new Hand(1);
        hand.add(createCard());
        hand.swap(0, 1);
    }

    @Test
    public void testToString1() {
        Hand hand = new Hand(1);
        assertEquals("Hand(1, [])", hand.toString());
    }

    @Test
    public void testToString2() {
        Hand hand = new Hand(2);
        Card card = createCard();
        Card card2 = createCard2();
        assertNotSame("card should be unequal to card2", card, card2);
        hand.add(card);
        hand.add(card2);
        assertEquals("Hand(2, [" + card + ", " + card2 + "])", hand.toString());
    }
    
    @Test
    public void testIterator() {
        Hand hand = new Hand(2);
        Card card = createCard();
        Card card2 = createCard2();
        hand.add(card);
        hand.add(card2);
        Iterator<Card> iterator = hand.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("first element should equal card", card, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("second element should equal card2", card2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    private Card createCard() {
        return new MonsterCard("Test", 10, 100, MonsterModus.OFFENSIVE);
    }

    private Card createCard2() {
        return new MonsterCard("Test2", 15, 150, MonsterModus.HEALING);
    }
}
