package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
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
        if (pCard != null) 
        {
            // determine the lowest ranked card
            Card lowestCard = pCard.iterator().next();
            for (Iterator<Card> i = pCard.iterator(); i.hasNext(); )
            {
                Card currentCard = i.next();
                if (currentCard.compareTo(lowestCard) < 0)
                {
                    lowestCard = currentCard;
                }
            }
            
            aCards.add(lowestCard);
            for (int i = 1; i <= pCard.size() - 1; i++)
            {
                boolean nextRankUpExists = false;
                for (Iterator<Card> j = pCard.iterator(); j.hasNext(); )
                {
                    Card currentCard = j.next();
                    if (currentCard.getRank().getValue() == lowestCard.getRank().getValue() + i)
                    {
                        if (currentCard.getSuit().equals(lowestCard.getSuit())) 
                        {
                            nextRankUpExists = true;
                            aCards.add(currentCard);
                        }
                        break;
                    }
                }
                if (!nextRankUpExists)
                {
                    throw new HandException("The given set of cards is not a Run");
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
        return false;
    }

    @Override
    public boolean isRun()
    {
        return true;
    }

    @Override
    public ArrayList<Card> getCards()
    {
        return new ArrayList<Card>(aCards);
    }
}
