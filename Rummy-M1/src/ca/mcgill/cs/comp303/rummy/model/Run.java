package ca.mcgill.cs.comp303.rummy.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Models a group of cards, implementing ICardSet.
 *
 */
public class Run implements ICardSet
{
    private HashSet<Card> aCards;
    
    /** 
     * Constructor that creates a Group.
     * @param pCard A set of cards to include
     */
    public Run(Set<Card> pCard)
    {
        
    }
    
    @Override
    public Iterator<Card> iterator()
    {
        return aCards.iterator();
    }

    @Override
    public boolean contains(Card pCard)
    {
        return aCards.contains(pCard);
    }

    @Override
    public int size()
    {
        return aCards.size();
    }

    @Override
    public boolean isGroup()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRun()
    {
        // TODO Auto-generated method stub
        return false;
    }

}
