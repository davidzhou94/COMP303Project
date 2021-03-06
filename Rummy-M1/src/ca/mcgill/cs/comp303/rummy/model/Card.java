package ca.mcgill.cs.comp303.rummy.model;

/**
 * An immutable description of a playing card.
 */
public final class Card implements Comparable<Card>
{
    private static final int SUIT_SIZE = 4;
    private static final int RANK_SIZE = 13;
    private static final int MAX_SCORE = 10;
    
    /**
     * Represents the rank of the card.
     */
    public enum Rank
    {
        ACE(1), 
        TWO(2), 
        THREE(3), 
        FOUR(4), 
        FIVE(5), 
        SIX(6), 
        SEVEN(7), 
        EIGHT(8), 
        NINE(9), 
        TEN(10), 
        JACK(11), 
        QUEEN(12), 
        KING(13);
        
        private int aVal;
        Rank(int pVal)
        {
            this.aVal = pVal;
        }
        /**
         * Gives the numerical value for this Rank in the ordering
         * of the Rank enum.
         * @return numerical value for the Rank
         */
        public int getValue() 
        {
            return aVal;
        }
        
        /**
         * Gives the next rank in the ordering of ranks.
         * @return The next rank after this one. If there is none, null is returned.
         */
        public Rank nextRank()
        {
            if ( aVal >= Rank.values().length )
            {
                return null;
            }
            else
            {
                return Rank.values()[aVal];
            }
        }
    }

    /**
     * Represents the suit of the card.
     */
    public enum Suit
    {
        CLUBS(1),
        DIAMONDS(2), 
        HEARTS(3), 
        SPADES(4);
        
        private int aVal;
        Suit(int pVal)
        {
            this.aVal = pVal;
        }
        /**
         * Gives the numerical value for this Suit in the ordering
         * of the Suit enum.
         * @return numerical value for the Suit
         */
        public int getValue()
        {
            return aVal;
        }
    }

    private final Rank aRank;

    private final Suit aSuit;

    /**
     * Create a new card object.
     * 
     * @param pRank
     *            The rank of the card.
     * @param pSuit
     *            The suit of the card.
     */
    public Card(final Rank pRank, final Suit pSuit)
    {
        aRank = pRank;
        aSuit = pSuit;
    }

    /**
     * Obtain the rank of the card.
     * 
     * @return An object representing the rank of the card.
     */
    public Rank getRank()
    {
        return aRank;
    }

    /**
     * Obtain the suit of the card.
     * 
     * @return An object representing the suit of the card
     */
    public Suit getSuit()
    {
        return aSuit;
    }

    /**
     * @see java.lang.Object#toString()
     * @return See above.
     */
    public String toString()
    {
        return aRank + " of " + aSuit;
    }

    /**
     * Compares two cards according to gin rules (ace is low, suits run as Spade, Hearts, Diamonds, Clubs (high to low)).
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * @param pCard
     *            The card to compare to
     * @return Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than pCard
     */
    public int compareTo(final Card pCard)
    {
        int thisCardValue = this.getRank().getValue() * SUIT_SIZE + this.getSuit().getValue();
        int pCardValue = pCard.getRank().getValue() * SUIT_SIZE + pCard.getSuit().getValue();
        return thisCardValue - pCardValue;
    }

    /**
     * Two cards are equal if they have the same suit and rank.
     * 
     * @param pCard
     *            The card to test.
     * @return true if the two cards are equal
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object pCard)
    {
        if (pCard != null)
        {
            return this.hashCode() == pCard.hashCode();
        }
        else
        {
            return false;
        }

    }

    /**
     * The hashcode for a card is the suit*13 + that of the rank (perfect hash).
     * 
     * @return the hashcode
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return (this.getSuit().getValue() - 1) * RANK_SIZE + this.getRank().getValue();
    }
    
    /**
     * The score of this card (rank number unless it is greater than the max score possible).
     * @return the score
     */
    public int getScore()
    {
        if (aRank.getValue() > MAX_SCORE) 
        {
            return MAX_SCORE;
        }
        else
        {
            return aRank.getValue();
        }
    }
}
