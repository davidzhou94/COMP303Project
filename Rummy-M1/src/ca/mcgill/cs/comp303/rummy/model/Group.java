package ca.mcgill.cs.comp303.rummy.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card.*;

/**
 * Models a group of cards, implementing ICardSet.
 *
 */
public class Group implements ICardSet
{
    private HashSet<Card> aCards;
    
    /** 
     * Constructor that creates a Group.
     * @param pCard A set of cards to include
     */
    public Group(Set<Card> pCard)
    {
        if (pCard != null) {
            Iterator<Card> cardIterator = pCard.iterator();
            Card currentCard = cardIterator.next();
            Rank rankOfFirstCard = currentCard.getRank();
            aCards.add(currentCard);
            for (Iterator<Card> i = cardIterator; i.hasNext(); )
            {
                currentCard = i.next();
                if ( rankOfFirstCard.equals(currentCard.getRank()) )
                {
                    aCards.add(currentCard);
                }
                else
                {
                    throw new HandException("The given set of cards is not a Group");
                }
            }
        }
        else
        {
            throw new HandException("The given set of cards was null");
        }
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
        return true;
    }

    @Override
    public boolean isRun()
    {
        return false;
    }

}
