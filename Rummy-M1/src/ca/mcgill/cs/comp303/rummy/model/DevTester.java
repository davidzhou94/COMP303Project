package ca.mcgill.cs.comp303.rummy.model;

import java.util.HashSet;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import static ca.mcgill.cs.comp303.rummy.testutils.AllCards.*;

public class DevTester
{
    public static void main( String[] args )
    {
        Hand lHand = new Hand();
        lHand.add(CAC);
        lHand.add(CAD);
        lHand.add(CAS);
        Set<Card> temp = new HashSet<Card>();
        temp.add(CAC);
        temp.add(CAD);
        temp.add(CAS);
        lHand.createGroup(temp);
        lHand.remove(CAD);
        for (Card c : lHand.getUnmatchedCards())
        {
            System.out.println(c.toString());
        }
        System.out.println(lHand.size()); 
    }

}
