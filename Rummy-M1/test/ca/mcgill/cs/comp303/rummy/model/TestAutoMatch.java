package ca.mcgill.cs.comp303.rummy.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static ca.mcgill.cs.comp303.rummy.testutils.AllCards.*;
import java.util.Set;

public class TestAutoMatch
{
    @Test
    public void test46()
    {
        Hand lHand = new Hand();
        lHand.add( CAS );
        lHand.add( C2S );
        lHand.add( C3S );
        
        lHand.add( C4S );
        lHand.add( C4D );
        lHand.add( C4C );
        
        lHand.add( C2H );
        lHand.add( C3H );
        lHand.add( C4H );
        
        lHand.add( C2D );
        lHand.autoMatch();
        Set<Card> lUnmatched = lHand.getUnmatchedCards();
        assertEquals( 1, lUnmatched.size());
        Set<ICardSet> lMatched = lHand.getMatchedSets();
        assertEquals( 3, lMatched.size());
        assertEquals( 2, lHand.score() );
    }
    
    @Test(timeout=100) 
    public void test47()
    {
        Hand lHand = new Hand();
        lHand.add( C9D );
        lHand.add( CTD );
        lHand.add( CJD );
        lHand.add( CQD );
        lHand.add( CQS );
        lHand.add( CQC );
        lHand.add( CJH );
        lHand.add( CQH );
        lHand.add( CKH );
        lHand.add( CJC );
        lHand.autoMatch();
        Set<Card> lUnmatched = lHand.getUnmatchedCards();
        assertEquals( 1, lUnmatched.size());
        Set<ICardSet> lMatched = lHand.getMatchedSets();
        assertEquals( 3, lMatched.size());
        assertEquals( 10, lHand.score() );
    }
}
