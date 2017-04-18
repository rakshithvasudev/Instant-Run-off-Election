import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * Created by Rakshith on 4/10/2017.
 */
public class PollingPlaceTest {
    @Test
    public void constructorTest(){
        //Given (Arrange)
        PollingPlace pollingPlace;
        //When(Action)
        pollingPlace = new PollingPlace("bellevue");
        // Then(Assert)
        try {
            pollingPlace.readVotes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(pollingPlace.getVotes().size()>0);
    }

    @Test
    public void constructorTest2(){
        //Given (Arrange)
        PollingPlace pollingPlace;
        //When(Action)
        pollingPlace = new PollingPlace("u-district");
        // Then(Assert)
        Assert.assertTrue(pollingPlace.getName().length()>0);
    }


    @Test
    public void readVotesTest(){
        //Given (Arrange)
        PollingPlace pollingPlace;
        //When(Action)
        pollingPlace = new PollingPlace("u-district");
        // Then(Assert)
        try {
            pollingPlace.readVotes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(pollingPlace.getVotes().size()>0);
    }


    @Test
    public void processPreferenceVotesTest(){
        //Given (Arrange)
        PollingPlace pollingPlace;
        Election election = Election.getElectionInstance();
        //When(Action)
        pollingPlace = new PollingPlace("u-district");

        doRepeatitiveTasks(pollingPlace,election);

        // Then(Assert)
        Assert.assertTrue(pollingPlace.getPriorityVotes().size()>0);
    }



    @Test
    public void DisplayVotesTest(){
        //Given (Arrange)
        PollingPlace pollingPlace;
        Election election = Election.getElectionInstance();
        //When(Action)
        pollingPlace = new PollingPlace("queen-anne");
        doRepeatitiveTasks(pollingPlace,election);
        pollingPlace.displayVotes();

        // Then(Assert)
        Assert.assertTrue(pollingPlace.getVotes().size()>0);
    }



    @Test
    public void getNameTest(){
        //Given (Arrange)
        PollingPlace pollingPlace;
        //When(Action)
        pollingPlace = new PollingPlace("queen-anne");

        // Then(Assert)
        Assert.assertEquals("queen-anne",pollingPlace.getName());
    }


    @Test
    public void getVotesTest(){
        //Given (Arrange)
        Election election = Election.getElectionInstance();
        PollingPlace pollingPlace;
        //When(Action)
        pollingPlace = new PollingPlace("queen-anne");
        doRepeatitiveTasks(pollingPlace,election);
        // Then(Assert)
        Assert.assertTrue(pollingPlace.getVotes().size()>0);
    }


    @Test
    public void equalsTest(){
        //Given (Arrange)
        PollingPlace pollingPlace1, pollingPlace2;
        //When(Action)
        pollingPlace1=new PollingPlace("queen-anne");
        pollingPlace2 = pollingPlace1.clone();

        // Then(Assert)
        Assert.assertTrue(pollingPlace1.equals(pollingPlace2));
    }



    @Test
    public void equalsTest2(){
        //Given (Arrange)
        PollingPlace pollingPlace1, pollingPlace2;
        //When(Action)
        pollingPlace1=  new PollingPlace("queen-anne");
        pollingPlace2 = pollingPlace1.clone();

        // Then(Assert)
        Assert.assertFalse(pollingPlace1 == (pollingPlace2));
    }


    @Test
    public void cloneTest(){
        //Given (Arrange)
        PollingPlace pollingPlace1, pollingPlace2;
        //When(Action)
        pollingPlace1=new PollingPlace("redmond");
        pollingPlace2 = pollingPlace1.clone();

        // Then(Assert)
        Assert.assertTrue(pollingPlace1.equals(pollingPlace2));
    }


    @Test
    public void toStringTest(){
        //Given (Arrange)
        PollingPlace pollingPlace;
        //When(Action)
        pollingPlace = new PollingPlace("queen-anne");

        // Then(Assert)
        Assert.assertEquals("Name :" + pollingPlace.getName() + " VotesCount :" +
                        pollingPlace.getVotes().size(),
                pollingPlace.toString());
    }


    @Test
    public void hashCodeTest(){
        //Given (Arrange)
        PollingPlace pollingPlace, pollingPlace1;
        //When(Action)
        pollingPlace = new PollingPlace("queen-anne");
        pollingPlace1= pollingPlace.clone();

        // Then(Assert)
        Assert.assertTrue(pollingPlace.hashCode()==pollingPlace1.hashCode());
    }






    /**
     * Helper that provides the infrastructure code to perform common tasks such as
     * reading votes from polling place to be added, reading candidates from election
     * @param election singleton ElectionInstance.
     * @param pollingPlace name of the polling place to be added.
     *
     */
    private void doRepeatitiveTasks(PollingPlace pollingPlace, Election election) {
        try {
            pollingPlace.readVotes();
            election.readCandidates();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pollingPlace.processVotes();
    }





}
