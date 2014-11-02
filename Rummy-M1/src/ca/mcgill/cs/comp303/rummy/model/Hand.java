package ca.mcgill.cs.comp303.rummy.model;

import java.util.*;

/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe. The hand is a set: adding the same card twice will not add duplicates of the
 * card.
 * 
 * @inv size() > 0
 * @inv size() <= HAND_SIZE
 */
public class Hand
{
    private static final int HAND_SIZE = 10;

    private static final int AUTOMATCH_MAX_SET_SIZE = 10;

    private static final int AUTOMATCH_MIN_SET_SIZE = 0;

    private HashSet<Card> aUnmatchedCards;
    private HashSet<Card> aMatchedCards;
    private HashSet<Run> aRuns;
    private HashSet<Group> aGroups;

    /**
     * Creates a new, empty hand.
     */
    public Hand()
    {
        aUnmatchedCards = new HashSet<Card>(HAND_SIZE);
        aMatchedCards = new HashSet<Card>(HAND_SIZE);
        aRuns = new HashSet<Run>();
        aGroups = new HashSet<Group>();
    }

    /**
     * Adds pCard to the list of unmatched cards. If the card is already in the hand, it is not added.
     * 
     * @param pCard
     *            The card to add.
     * @throws HandException
     *             if the hand is complete.
     * @throws HandException
     *             if the card is already in the hand.
     * @pre pCard != null
     */
    public void add(Card pCard)
    {
        if (aUnmatchedCards.size() + aMatchedCards.size() >= HAND_SIZE)
        {
            throw new HandException("Hand is complete");
        }
        else if (aUnmatchedCards.contains(pCard) || aMatchedCards.contains(pCard))
        {
            throw new HandException("Hand already contains card");
        }
        else
        {
            aUnmatchedCards.add(pCard);
        }
    }

    /**
     * Remove pCard from the hand and break any matched set that the card is part of. Does nothing if pCard is not in the hand.
     * 
     * @param pCard
     *            The card to remove.
     * @pre pCard != null
     */
    public void remove(Card pCard)
    {
        if (aUnmatchedCards.contains(pCard))
        {
            aUnmatchedCards.remove(pCard);
        }
        else if (aMatchedCards.contains(pCard))
        {
            aMatchedCards.remove(pCard);
        }
    }

    /**
     * @return True if the hand is complete.
     */
    public boolean isComplete()
    {
        return aUnmatchedCards.size() + aMatchedCards.size() >= HAND_SIZE;
    }

    /**
     * Removes all the cards from the hand.
     */
    public void clear()
    {
        aUnmatchedCards.clear();
        aMatchedCards.clear();
        aRuns.clear();
    }

    /**
     * @return A copy of the set of matched sets
     */

    public Set<ICardSet> getMatchedSets()
    {
        Set<ICardSet> sets = new HashSet<ICardSet>();
        for (Run cards : aRuns)
        {
            sets.add(cards);
        }
        for (Group cards : aGroups)
        {
            sets.add(cards);
        }
        return sets;
    }

    /**
     * @return A copy of the set of unmatched cards.
     */
    public Set<Card> getUnmatchedCards()
    {
        @SuppressWarnings("unchecked")
        Set<Card> clone = (Set<Card>) aUnmatchedCards.clone();
        return clone;
    }

    /**
     * @return The number of cards in the hand.
     */
    public int size()
    {
        return aUnmatchedCards.size() + aUnmatchedCards.size();
    }

    /**
     * Determines if pCard is already in the hand, either as an unmatched card or as part of a set.
     * 
     * @param pCard
     *            The card to check.
     * @return true if the card is already in the hand.
     * @pre pCard != null
     */
    public boolean contains(Card pCard)
    {
        return aUnmatchedCards.contains(pCard) || aMatchedCards.contains(pCard);
    }

    /**
     * @return The total point value of the unmatched cards in this hand.
     */
    public int score()
    {
        return Integer.MAX_VALUE; // TODO
    }

    /**
     * Creates a group of cards of the same rank.
     * 
     * @param pCards
     *            The cards to groups
     * @pre pCards != null
     * @throws HandException
     *             If the cards in pCard are not all unmatched cards of the hand or if the group is not a valid group.
     */
    public void createGroup(Set<Card> pCards)
    {
        // TODO
    }

    /**
     * Creates a run of cards of the same suit.
     * 
     * @param pCards
     *            The cards to group in a run
     * @pre pCards != null
     * @throws HandException
     *             If the cards in pCard are not all unmatched cards of the hand or if the group is not a valid group.
     */
    public void createRun(Set<Card> pCards)
    {
        // TODO
    }

    private class matchingDescriptor
    {
        public int score;
        public ArrayList<Card> free;
        public ArrayList<ArrayList<Card>> sets;
        public ArrayList<ArrayList<Card>> runs;

        public matchingDescriptor(int sc, ArrayList<Card> fr, ArrayList<ArrayList<Card>> se, ArrayList<ArrayList<Card>> ru)
        {
            score = sc;
            free = fr;
            sets = se;
            runs = ru;
        }
    }

    private Card.Rank nextRank(Card c)
    {
        int index = c.getRank().ordinal() + 1;
        if (index >= Card.Rank.values().length || index < 0)
        {
            return null;
        }
        else
        {
            return Card.Rank.values()[c.getRank().ordinal() + 1];
        }
    }

    private <E> ArrayList<E> reversedArrayListFromList(List<E> pList)
    {
        ArrayList<E> result = new ArrayList<E>();
        for (int i = pList.size() - 1; i >= 0; --i)
        {
            result.add(pList.get(i));
        }
        return result;
    }

    /**
     * Calculates the matching of cards into groups and runs that results in the lowest amount of points for unmatched cards.
     */
    public void autoMatch()
    {
        ArrayList<Card> unmatchedList = new ArrayList<Card>();

        for (Card c : aUnmatchedCards)
        {
            unmatchedList.add(c);
        }

        matchingDescriptor bestMatching = autoMatchIter(new ArrayList<ArrayList<Card>>(), new ArrayList<ArrayList<Card>>(), unmatchedList, 0);
        /*
         * Implement the match
         */
        aRuns = new HashSet<Run>();
        for (ArrayList<Card> seq : bestMatching.runs)
        {
            aRuns.add( new Run(new HashSet<Card>(seq)) );
            for (Card card : seq)
            {
                aMatchedCards.add(card);
            }
        }

        aGroups = new HashSet<Group>();
        for (ArrayList<Card> seq : bestMatching.sets)
        {
            aGroups.add( new Group(new HashSet<Card>(seq)) );
            for (Card card : seq)
            {
                aMatchedCards.add(card);
            }
        }

        aUnmatchedCards = new HashSet<Card>();
        for (Card c : bestMatching.free)
        {
            aUnmatchedCards.add(c);
        }
    }

    @SuppressWarnings("unchecked")
    private matchingDescriptor autoMatchIter(ArrayList<ArrayList<Card>> pSets, ArrayList<ArrayList<Card>> pRuns, ArrayList<Card> pUnmatchedCards,
            int pScore)
    {
        ArrayList<Card> l;
        int maxScore;
        matchingDescriptor maxKey;
        int streakStart;
        int streakScore;

        if (pUnmatchedCards.size() == 0)
        {
            return new matchingDescriptor(pScore, pUnmatchedCards, pSets, pRuns);
        }

        maxScore = pScore;
        maxKey = new matchingDescriptor(pScore, pUnmatchedCards, pSets, pRuns);

        /*
         * Sequence matching
         */
        l = (ArrayList<Card>) (pUnmatchedCards.clone());
        
        Collections.sort(l, new Comparator<Card>()
        {
            private int sortKey(Card pCard)
            {
                return (Card.Rank.values().length * 2) * pCard.getSuit().ordinal() + pCard.getRank().ordinal();
            }

            public int compare(Card pFirstCard, Card pSecondCard)
            {
                return sortKey(pSecondCard) - sortKey(pFirstCard);
            }
        });

        streakStart = 0;
        streakScore = 0;

        for (int i = 0; i < l.size(); ++i)
        {
            if (!(i > 0 && l.get(i - 1).getSuit() == l.get(i).getSuit() && nextRank(l.get(i)) == l.get(i - 1).getRank()))
            {

                streakStart = i;
                streakScore = 0;
            }

            streakScore += l.get(i).getScore();
            int streakLength = i - streakStart + 1;

            if (streakLength >= 3)
            {
                ArrayList<ArrayList<Card>> newRuns = new ArrayList<ArrayList<Card>>();
                newRuns.addAll(pRuns);
                newRuns.add(reversedArrayListFromList(l.subList(streakStart, i + 1)));

                ArrayList<Card> newFree = new ArrayList<Card>();
                newFree.addAll(l.subList(0, streakStart));
                newFree.addAll(l.subList(i + 1, l.size()));

                matchingDescriptor trial = autoMatchIter(pSets, newRuns, newFree, pScore + streakScore);

                /*
                 * >= because a larger sequence with the same score is preferable
                 */
                if (trial.score >= maxScore)
                {
                    maxScore = trial.score;
                    maxKey = trial;
                }
            }
        }

        /*
         * Set matching
         */
        l = (ArrayList<Card>) (pUnmatchedCards.clone());
        Collections.sort(l, new Comparator<Card>()
        {
            private int sortKey(Card pCard)
            {
                return (Card.Rank.values().length * 2) * pCard.getSuit().ordinal() + pCard.getRank().ordinal();
            }

            public int compare(Card pFirstCard, Card pSecondCard)
            {
                return sortKey(pSecondCard) - sortKey(pFirstCard);
            }
        });

        streakStart = 0;
        streakScore = 0;

        for (int i = 0; i < l.size(); ++i)
        {
            if (!(i > 0 && l.get(i - 1).getRank() == l.get(i).getRank()))
            {

                streakStart = i;
                streakScore = 0;
            }

            streakScore += l.get(i).getScore();
            int streakLength = i - streakStart + 1;

            if (streakLength > AUTOMATCH_MAX_SET_SIZE)
            {
                streakLength = 0;
                streakScore = l.get(i).getScore();
            }

            if (streakLength >= AUTOMATCH_MIN_SET_SIZE && streakLength <= AUTOMATCH_MAX_SET_SIZE)
            {
                ArrayList<ArrayList<Card>> newSets = new ArrayList<ArrayList<Card>>();
                newSets.addAll(pSets);
                newSets.add(reversedArrayListFromList(l.subList(streakStart, i + 1)));

                ArrayList<Card> newFree = new ArrayList<Card>();
                newFree.addAll(l.subList(0, streakStart));
                newFree.addAll(l.subList(i + 1, l.size()));

                matchingDescriptor trial = autoMatchIter(newSets, pRuns, newFree, pScore + streakScore);

                /*
                 * >= because a larger set with the same score is preferable
                 */
                if (trial.score >= maxScore)
                {
                    maxScore = trial.score;
                    maxKey = trial;
                }
            }
        }

        return maxKey;
    }
}
