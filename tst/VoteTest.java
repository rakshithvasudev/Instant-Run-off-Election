import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Rakshith on 4/10/2017.
 */
public class VoteTest {

    @Test
    public void constructorTest(){
        //Given (Arrange)
        String[] preferences ={"Ronald","Hermy", "Henry"};
        Vote vote;
        //When(Action)
        vote = new Vote(preferences);

        // Then(Assert)
        Assert.assertTrue(vote.getPreferences().size()>=0);
    }


    @Test
    public void constructorTest2(){
        //Given (Arrange)
        String[] preferences ={"Ronald","Henry", "Jermieh","Entre","Vulaccan"};
        Vote vote;
        //When(Action)
        vote = new Vote(preferences);

        // Then(Assert)
        Assert.assertTrue(vote.getPreferences().size()>=0);
    }


    @Test
    public void getPreferencesTest(){
        //Given (Arrange)
        String[] preferences ={"Entry"};
        Vote vote;
        //When(Action)
        vote = new Vote(preferences);

        // Then(Assert)
        Assert.assertTrue(vote.getPreferences().size()>=0);
    }

    @Test(expected = IllegalVoteCastException.class)
    public void addVoteTest(){
        //Given (Arrange)
        Election election = Election.getElectionInstance();
        String[] preferences ={};
        Vote vote;
        //When(Action)
        election.addCandidate("Jeffery","DEM");
        vote = new Vote(preferences);

        // Then(Assert)
        Assert.assertTrue(vote.getPreferences().size()>=0);
    }

    @Test
    public void toStringTest(){
        //Given (Arrange)
        Election election = Election.getElectionInstance();
        String[] preferences ={"Seems","Very","Awesome"};
        Vote vote;
        //When(Action)
        election.addCandidate("Jeffery","DEM");
        vote = new Vote(preferences);

        // Then(Assert)
        Assert.assertEquals(Arrays.asList(preferences).toString(),vote.toString());
    }



}
