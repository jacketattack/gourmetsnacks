package GourmetSnacks;

import game.*;
import game.round.LandGrantRound;
import junit.framework.TestCase;
import game.Session;
import game.LocalSession;
import game.Map;


import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.matchers.JUnitMatchers;

import java.util.ArrayList;


/**
 *
 */
public class BuyPropertyTest extends TestCase
{
    LandGrantRound round = new LandGrantRound();
    Session session = new LocalSession();
    ArrayList<String> ids = session.createPlayers(2);

    String id1 = ids.get(0);
    String id2 = ids.get(1);



    @Test
    public void testNoMoney()
    {

        session.setMap(new Map(true));
        session.setPlayerType(id1, PlayerType.FLAPPER);
        session.setPlayerType(id2, PlayerType.HUMAN);
        round.setSession(session);
        round.init();

        System.out.println(id2);

        //Player 1

        round.click(0,0,true);
        round.click(0,Plot.SIZE*1+1,true);
        round.click(Plot.SIZE + 1 , 0 , true);
        round.click(Plot.SIZE+1, Plot.SIZE+1,true);
        System.out.println("current id is person 2" + session.getCurrentPlayerId().equals(id2));
        System.out.println(session.getPlayerMoney(id2));
        round.click(Plot.SIZE*2 + 1, Plot.SIZE*2 + 1,true );
        round.click(Plot.SIZE*3 + 1, Plot.SIZE*1 + 1,true );
        round.click(Plot.SIZE*3 + 1, 1,true );
        round.click(Plot.SIZE*3 + 1, Plot.SIZE*3 + 1,true );

        round.click(Plot.SIZE*4+1,1,true);


        boolean bought = session.isPlotOwnedByPlayer(id2,session.getPlot(4,0));
        assertEquals(bought ,false);

    }
}
