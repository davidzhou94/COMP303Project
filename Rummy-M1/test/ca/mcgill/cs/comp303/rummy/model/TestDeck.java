package ca.mcgill.cs.comp303.rummy.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class TestDeck
{
    @Test
    public void testConstructor()
    {
        Deck lDeck = new Deck();
        assertEquals(lDeck.size(), 52);
    }
    @Test
    public void testDraw1()
    {
        Deck lDeck = new Deck();
        
        assertEquals(lDeck.draw().getClass(), Card.class);
    }
    
    
    @Test
    public void testDraw2()
    {
        Deck lDeck = new Deck();
        Card lCard = lDeck.draw();
        
        while (lDeck.size() > 0)
        {
            assertFalse(lCard.equals(lDeck.draw()));
        }
    }
    
    @Test
    public void testDraw3()
    {
        Deck lDeck = new Deck();
        while (lDeck.size() > 0)
        {
            lDeck.draw();
        }
        
        try
        {
            lDeck.draw();
        }
        catch (Exception e)
        {
//            assertEquals(e.getClass(), AssertionError.class);
            lDeck = null;
        }
        assertEquals(lDeck, null);
    }
    
    @Test
    public void testSize()
    {
        Deck lDeck = new Deck();
        assertEquals(lDeck.size(), 52);
        lDeck.draw();
        assertEquals(lDeck.size(), 51);
        for (int i = 50 ; i > 0; i--)
        {
            lDeck.draw();
            assertEquals(lDeck.size(), i);
        }
    }
    
    @Test
    public void testShuffle()
    {
        Deck lDeck = new Deck();
        lDeck.draw();
        lDeck.draw();
        lDeck.draw();
        lDeck.shuffle();
        assertEquals(lDeck.size(), 52);
    }
}
