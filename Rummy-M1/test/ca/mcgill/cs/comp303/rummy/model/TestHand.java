package ca.mcgill.cs.comp303.rummy.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import static ca.mcgill.cs.comp303.rummy.testutils.AllCards.*;
import java.util.*;


public class TestHand
{
    private Hand testHand1 = new Hand();
    private Hand testHand2 = new Hand();
    private Set<Card> testRunSet2 = new HashSet<Card>();
    private Set<Card> testGroupSet2 = new HashSet<Card>();
    
    @Before
    public void init()
    {
        testHand1.add(C7C);
        testHand1.add(C8C);
        testHand1.add(C9C);
        testHand1.add(CAC);
        testHand1.add(CAD);
        testHand1.add(CAS);
        testHand1.add(C2S);
        testHand1.add(C3S);
        testHand1.add(C4S);
        testHand1.add(C5S);
        
        testHand2.add(CAC);
        testHand2.add(CAD);
        testHand2.add(CAS);
        testHand2.add(C5D);
        testHand2.add(C6D);
        testHand2.add(C7D);
        testHand2.add(C8D);
        testHand2.add(C9D);
        testHand2.add(CTD);
        testHand2.add(CJD);
        
        testRunSet2.add(C6D);
        testRunSet2.add(C5D);
        testRunSet2.add(C7D);
        testRunSet2.add(C8D);
        testRunSet2.add(CTD);
        testRunSet2.add(CJD);
        testRunSet2.add(C9D);
        
        testGroupSet2.add(CAC);
        testGroupSet2.add(CAD);
        testGroupSet2.add(CAS);
    }
    
    @Test
    public void testSize()
    {
        Hand lHand = testHand1;
        assertEquals(lHand.size(), 10);
        lHand = new Hand();
        assertEquals(lHand.size(), 0);
    }
    
    @Test
    public void testAdd1()
    {
        Hand lHand = new Hand();
        assertEquals(lHand.size(), 0);
        lHand.add(CAC);
        assertEquals(lHand.size(), 1);
    }
    
    @Test
    public void testAdd2()
    {
        Hand lHand = new Hand();
        lHand.add(CAC);
        try
        {
            lHand.add(CAC);
        }
        catch (HandException e)
        {
            assertEquals(e.getMessage(), "Hand already contains card");
            lHand = null;
        }
        assertEquals(lHand, null);
    }
    
    @Test
    public void testAdd3()
    {
        Hand lHand = testHand1;
        try
        {
            lHand.add(C3C);
        }
        catch (HandException e)
        {
            assertEquals(e.getMessage(), "Hand is complete");
            lHand = null;
        }
        assertEquals(lHand, null);
    }
    
    @Test
    public void testAdd4()
    {
        Hand lHand = testHand2;
        
        
    }
    
    @Test
    public void testContains()
    {
        Hand lHand = testHand1;
        assertFalse(lHand.contains(CTC));
        assertTrue(lHand.contains(C7C));
    }
    
    @Test
    public void testClear()
    {
        Hand lHand = testHand1;
        lHand.clear();
        assertEquals(lHand.size(), 0);
    }
    
    @Test
    public void testIsComplete()
    {
        Hand lHand = new Hand();
        assertFalse(lHand.isComplete());
        lHand = testHand1;
        assertTrue(lHand.isComplete());
    }
    
    @Test
    public void testRemove1()
    {
        Hand lHand = testHand1;
        lHand.remove(CAD);
        assertEquals(lHand.size(), 9);
        assertFalse(lHand.contains(CAD));
    }
    
    @Test
    public void testRemove2()
    {
        Hand lHand = testHand2;
        lHand.createGroup(testGroupSet2);
        lHand.createRun(testRunSet2);
        lHand.remove(CAD);
        assertEquals(lHand.size(), 9);
        assertTrue(lHand.contains(CAC));
        assertTrue(lHand.contains(CAS));
        assertFalse(lHand.contains(CAD));
        lHand.remove(C7D);
        assertEquals(lHand.size(), 8);
        assertTrue(lHand.contains(C5D));
        assertTrue(lHand.contains(C6D));
        assertFalse(lHand.contains(C7D));
        assertTrue(lHand.contains(C8D));
        assertTrue(lHand.contains(C9D));
        assertTrue(lHand.contains(CTD));
        assertTrue(lHand.contains(CJD));
        assertEquals(lHand.getMatchedSets().size(), 0);
    }
    
    @Test
    public void testScore1()
    {
        Hand lHand = new Hand();
        assertEquals(lHand.score(), 0);
    }
    
    @Test
    public void testScore2()
    {
        Hand lHand = new Hand();
        lHand.add(C7C);
        lHand.add(C8C);
        lHand.add(C9C);
        lHand.add(CKC);
        lHand.add(CQD);
        lHand.add(CAS);
        lHand.add(C2S);
        lHand.add(C3S);
        lHand.add(C4S);
        assertEquals(lHand.score(), 54);
    }
    
    @Test
    public void testCreateGroup1()
    {
        Hand lHand = new Hand();
        lHand.add(CTC);
        lHand.add(CTD);
        lHand.add(CTS);
        Set<Card> temp = new HashSet<Card>();
        temp.add(CTC);
        temp.add(CTD);
        temp.add(CTS);
        
        lHand.createGroup(temp);
        
        Set<Card> unmatched = lHand.getUnmatchedCards();
        Set<ICardSet> matched = lHand.getMatchedSets();
        
        assertEquals(unmatched.size(), 0);
        assertEquals(matched.size(), 1);
        
        for (Card c : temp)
        {
            assertTrue(((Group)matched.toArray()[0]).contains(c));
        }
        
        for (Card c : ((Group)matched.toArray()[0]).getCards() )
        {
            assertTrue(temp.contains(c));
        }
    }
    
    @Test
    public void testCreateRun1()
    {
        Hand lHand = new Hand();
        lHand.add(C7C);
        lHand.add(C8C);
        lHand.add(C9C);
        lHand.add(CTC);
        lHand.add(CJC);
        Set<Card> temp = new HashSet<Card>();
        temp.add(C7C);
        temp.add(C8C);
        temp.add(C9C);
        temp.add(CTC);
        temp.add(CJC);
        lHand.createRun(temp);
        
        Set<Card> unmatched = lHand.getUnmatchedCards();
        Set<ICardSet> matched = lHand.getMatchedSets();
        
        assertEquals(unmatched.size(), 0);
        assertEquals(matched.size(), 1);
        
        for (Card c : temp)
        {
            assertTrue(((Run)matched.toArray()[0]).contains(c));
        }
        
        for (Card c : ((Run)matched.toArray()[0]).getCards() )
        {
            assertTrue(temp.contains(c));
        }
    }
}
