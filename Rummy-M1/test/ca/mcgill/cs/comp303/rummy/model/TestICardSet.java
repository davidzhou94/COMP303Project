package ca.mcgill.cs.comp303.rummy.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;
import static ca.mcgill.cs.comp303.rummy.testutils.AllCards.*;

public class TestICardSet
{
    @Test
    public void testGroupConstructor1()
    {
        Set<Card> temp = new HashSet<Card>();
        temp.add(C4S);
        temp.add(C4D);
        temp.add(C4H);
        ICardSet lGroup = null;
        try
        {
            lGroup = new Group(temp);
        }
        catch (HandException e)
        {
            fail();
        }
        assertArrayEquals(lGroup.getCards().toArray(), temp.toArray());
    }
    
    @Test
    public void testGroupConstructor2()
    {
        Set<Card> temp = new HashSet<Card>();
        temp.add(C4S);
        temp.add(C3D);
        temp.add(C4H);
        ICardSet lGroup = null;
        try
        {
            lGroup = new Group(temp);
        }
        catch (HandException e)
        {
            assertEquals(e.getMessage(), "The given set of cards is not a Group");
        }
        assertEquals(lGroup, null);
    }
    
    @Test
    public void testGroupConstructor3()
    {
        ICardSet lGroup = null;
        try
        {
            lGroup = new Group(null);
        }
        catch (HandException e)
        {
            assertEquals(e.getMessage(), "The given set of cards was null");
        }
        assertEquals(lGroup, null);
    }

}
