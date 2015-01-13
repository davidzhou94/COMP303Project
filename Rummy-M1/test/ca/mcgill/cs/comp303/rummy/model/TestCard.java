package ca.mcgill.cs.comp303.rummy.model;

import org.junit.Test;
import static org.junit.Assert.*;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;
import static ca.mcgill.cs.comp303.rummy.testutils.AllCards.*;

public class TestCard
{
    @Test
    public void testGettersAndSetters()
    {
        assertEquals( CAH.getRank(), Rank.ACE );
        assertEquals( CKS.getSuit(), Suit.SPADES );
    }
    
    @Test
    public void testRankEnum()
    {
        assertEquals( Rank.valueOf("KING"), Rank.KING );
        assertEquals( Rank.values()[0], Rank.ACE );
        
        assertEquals( CAC.getRank().getValue(), 1 );
        assertEquals( CAS.getRank().nextRank(), Card.Rank.TWO);
        assertEquals( CKD.getRank().nextRank(), null );
        assertEquals( CKH.getRank().getValue(), 13 );
    }
    
    @Test
    public void testSuitEnum()
    {
        assertEquals( Suit.valueOf("SPADES"), Suit.SPADES );
        assertEquals( Suit.values()[0], Suit.CLUBS );
        
        assertEquals( C2S.getSuit().getValue(), 4 );
        assertEquals( C2H.getSuit().getValue(), 3 );
        assertEquals( C2D.getSuit().getValue(), 2 );
        assertEquals( C2C.getSuit().getValue(), 1 );
    }
    
    @Test
    public void testConstructor()
    {
        Card lCard = new Card( Rank.THREE, Suit.SPADES );
        assertEquals( lCard, C3S );
    }
    
    @Test
    public void testCompareTo()
    {
        int compare = C4S.compareTo(C3S);
        assertEquals( (Math.abs(compare)/compare), 1 );
        compare = CAC.compareTo(CKS);
        assertEquals( (Math.abs(compare)/compare), -1 );
        compare = C3D.compareTo(C3H);
        assertEquals( (Math.abs(compare)/compare), -1 );
        compare = C4C.compareTo(C4C);
        assertEquals( compare, 0 );
    }
    
    @Test
    public void testEquals()
    {
        Card lCard = new Card( Rank.FIVE, Suit.CLUBS );
        assertTrue( lCard.equals(C5C));
        assertFalse( lCard.equals(null) );
        assertTrue( C5C.equals(lCard));
        assertTrue( C4S.equals(C4S) );
        assertFalse( C4S.equals(C5S) );
    }
    
    @Test
    public void testGetScore()
    {
        assertEquals( CAH.getScore(), 1);
        assertEquals( C7S.getScore(), 7);
        assertEquals( CTC.getScore(), 10);
        assertEquals( CQD.getScore(), 10);
    }
    
    @Test
    public void testHashCode()
    {
        assertEquals( C4S.hashCode(), 43 );
    }
    
    @Test
    public void testToString()
    {
        assertEquals( CAD.toString(), "ACE of DIAMONDS");
        assertEquals( CTS.toString(), "TEN of SPADES");
    }
}
