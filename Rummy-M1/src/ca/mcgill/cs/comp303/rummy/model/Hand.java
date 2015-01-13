package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe. The hand is a set: adding the same card twice will not add duplicates of the
 * card.
 * 
 * size() > 0
 * size() <= HAND_SIZE
 */
public class Hand
{
    private static final int HAND_SIZE = 10;
    private static final int MAX_GROUP_SIZE = 10;
    private static final int MIN_GROUP_SIZE = 0;

    private HashSet<Card> aUnmatchedCards;
    private HashSet<Card> aMatchedCards;
    private ArrayList<Run> aRuns;
    private ArrayList<Group> aGroups;

    /**
     * Creates a new, empty hand.
     */
    public Hand()
    {
        aUnmatchedCards = new HashSet<Card>(HAND_SIZE);
        aMatchedCards = new HashSet<Card>(HAND_SIZE);
        aRuns = new ArrayList<Run>();
        aGroups = new ArrayList<Group>();
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
            ArrayList<ICardSet> lAllCardSets = new ArrayList<ICardSet>();
            lAllCardSets.addAll(aGroups);
            lAllCardSets.addAll(aRuns);
            for (ICardSet i : lAllCardSets)
            {
                if (i.contains(pCard)) 
                {
                    for(Card c : i.getCards())
                    {
                        aUnmatchedCards.add(c);
                        aMatchedCards.remove(c);
                    }
                    if (aRuns.contains(i))
                    {
                        aRuns.remove(i);
                    }
                    else
                    {
                        aGroups.remove(i);
                    }
                }
            }
            aUnmatchedCards.remove(pCard);
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
        aGroups.clear();
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
        return aUnmatchedCards.size() + aMatchedCards.size();
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
        int score = 0;
        for (Card card : aUnmatchedCards)
        {
            score += card.getScore();
        }
        return score;
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
        for (Card card : pCards)
        {
            if (!aUnmatchedCards.contains(card)) 
            {
                throw new HandException("A card in the group was not an unmatched card in the hand");
            }
        }
        aGroups.add(new Group(pCards));
        for (Card card : pCards)
        {
            aUnmatchedCards.remove(card);
            aMatchedCards.add(card);
        }
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
        for (Card card : pCards)
        {
            if (!aUnmatchedCards.contains(card))
            {
                throw new HandException("A card in the group was not an unmatched card in the hand");
            }
        }
        aRuns.add(new Run(pCards));
        for (Card card : pCards)
        {
            aUnmatchedCards.remove(card);
            aMatchedCards.add(card);
        }
    }

    /**
     * Calculates the matching of cards into groups and runs that results in the lowest amount of points for all cards in the hand.
     */
    public void autoMatch()
    {
        ArrayList<Card> allCards = new ArrayList<Card>(aUnmatchedCards);
        allCards.addAll(aMatchedCards);
        
        CardSets bestMatching = autoMatch(new ArrayList<Group>(), new ArrayList<Run>(), allCards, 0);
        
        aRuns = new ArrayList<Run>();
        for (Run run : bestMatching.getMatchedRuns())
        {
            aRuns.add(run);
            for (Card card : run.getCards())
            {
                aMatchedCards.add(card);
            }
        }

        aGroups = new ArrayList<Group>();
        for (Group group : bestMatching.getMatchedGroups())
        {
            aGroups.add(group);
            for (Card card : group.getCards())
            {
                aMatchedCards.add(card);
            }
        }

        aUnmatchedCards = new HashSet<Card>();
        for (Card card : bestMatching.getUnusedCards())
        {
            aUnmatchedCards.add(card);
        }
    }

    @SuppressWarnings("unchecked")
    private CardSets autoMatch(ArrayList<Group> pGroups, ArrayList<Run> pRuns, ArrayList<Card> pCardsToMatch, int pScore)
    {
        if (pCardsToMatch.size()<=0)
        {
            return new CardSets(pScore, pCardsToMatch, pGroups, pRuns);
        }
        
        int bestMatchingScore = pScore;
        CardSets bestMatchingSet = new CardSets(pScore, pCardsToMatch, pGroups, pRuns);
        ArrayList<Card> unmatchedCards = (ArrayList<Card>) pCardsToMatch.clone();
        
        // Sort unmatched cards
        Collections.sort(unmatchedCards, new Comparator<Card>()
        {
            public int compare(Card pFirstCard, Card pSecondCard)
            {
                return pFirstCard.compareTo(pSecondCard);
            }
        });
        
        // Run matching
        int indexFirstCardInRun = 0;
        int scoreOfRun = 0;
        
        for (int i = 0; i < unmatchedCards.size(); ++i)
        {
            // Check whether the run has been broken
            boolean suitDifferentFromPrevious = !(unmatchedCards.get(i - 1).getSuit() == unmatchedCards.get(i).getSuit());
            boolean rankNotOneAbovePrevious = !(unmatchedCards.get(i - 1).getRank() == unmatchedCards.get(i).getRank().nextRank());
            if ( (i<=0) || suitDifferentFromPrevious || rankNotOneAbovePrevious )
            {
                indexFirstCardInRun = i;
                scoreOfRun = 0;
            }

            scoreOfRun += unmatchedCards.get(i).getScore();
            int streakLength = i - indexFirstCardInRun + 1;

            if (streakLength >= 3)
            {
                ArrayList<Run> allRuns = new ArrayList<Run>(pRuns);
                Run newFoundRun = new Run(new HashSet<Card>( unmatchedCards.subList(indexFirstCardInRun, i + 1) ));
                allRuns.add(newFoundRun);

                ArrayList<Card> newFree = new ArrayList<Card>();
                newFree.addAll(unmatchedCards.subList(0, indexFirstCardInRun));
                newFree.addAll(unmatchedCards.subList(i + 1, unmatchedCards.size()));
                
                CardSets trial = autoMatch(pGroups, allRuns, newFree, pScore + scoreOfRun);
                
                if (trial.getScore() >= bestMatchingScore)
                {
                    bestMatchingScore = trial.getScore();
                    bestMatchingSet = trial;
                }
            }
        }
        
        // Sort unmatched cards
        unmatchedCards = (ArrayList<Card>) pCardsToMatch.clone();
        Collections.sort(unmatchedCards, new Comparator<Card>()
        {
            public int compare(Card pFirstCard, Card pSecondCard)
            {
                return pFirstCard.compareTo(pSecondCard);
            }
        });
        
        // Group matching
        int indexFirstCardInGroup = 0;
        int scoreOfGroup = 0;
        
        for (int i = 0; i < unmatchedCards.size(); ++i)
        {
            boolean rankDifferentFromPrevious = !(unmatchedCards.get(i - 1).getRank() == unmatchedCards.get(i).getRank());
            if ( (i<=0) || rankDifferentFromPrevious )
            {
                indexFirstCardInGroup = i;
                scoreOfGroup = 0;
            }
            scoreOfGroup += unmatchedCards.get(i).getScore();
            int streakLength = i - indexFirstCardInGroup + 1;

            if (streakLength > MAX_GROUP_SIZE)
            {
                streakLength = 0;
                scoreOfGroup = unmatchedCards.get(i).getScore();
            }
            
            if (streakLength >= MIN_GROUP_SIZE && streakLength <= MAX_GROUP_SIZE)
            {
                ArrayList<Group> allGroups = new ArrayList<Group>(pGroups);
                Group newFoundGroup = new Group(new HashSet<Card>( unmatchedCards.subList(indexFirstCardInGroup, i + 1) ));
                allGroups.add(newFoundGroup);
                
                ArrayList<Card> newFreeCards = new ArrayList<Card>();
                newFreeCards.addAll(unmatchedCards.subList(0, indexFirstCardInGroup));
                newFreeCards.addAll(unmatchedCards.subList(i + 1, unmatchedCards.size()));
                
                CardSets trial = autoMatch(allGroups, pRuns, newFreeCards, pScore + scoreOfGroup);
                
                if (trial.getScore() >= bestMatchingScore)
                {
                    bestMatchingScore = trial.getScore();
                    bestMatchingSet = trial;
                }
            }
        }
        
        return bestMatchingSet;
    }
}
