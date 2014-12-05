package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;

/**
 * This utility class represents a matching of the cards in a hand.
 * @author David
 *
 */
public class CardSets
{
    private int aScore;
    private ArrayList<Card> aUnusedCards;
    private ArrayList<Group> aMatchedGroups;
    private ArrayList<Run> aMatchedRuns;
    
    /**
     * Constructor for CardSets.
     * @param pScore Score to beat.
     * @param pUnusedCards List of unused cards.
     * @param pMatchedGroups List of matched groups.
     * @param pMatchedRuns List of matched runs.
     */
    public CardSets(int pScore, ArrayList<Card> pUnusedCards, ArrayList<Group> pMatchedGroups, ArrayList<Run> pMatchedRuns)
    {
        aScore = pScore;
        aUnusedCards = pUnusedCards;
        aMatchedGroups = pMatchedGroups;
        aMatchedRuns = pMatchedRuns;
    }
    
    /**
     * @return the score.
     */
    public int getScore()
    {
        return aScore;
    }
    
    /**
     * @return the list of unused cards.
     */
    public ArrayList<Card> getUnusedCards()
    {
        return aUnusedCards;
    }
    
    /**
     * @return the list of groups.
     */
    public ArrayList<Group> getMatchedGroups()
    {
        return aMatchedGroups;
    }
    
    /**
     * @return the list of runs.
     */
    public ArrayList<Run> getMatchedRuns()
    {
        return aMatchedRuns;
    }

}