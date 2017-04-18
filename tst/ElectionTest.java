import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * Created by Rakshith on 4/10/2017.
 * Please run these tests individually.
 * The singleton pattern would return an already instantiated election
 * instance if all tests are made to run at once.
 *
 */
public class ElectionTest {

    @Test
    public void constructorTest(){
        //Given (Arrange)
        Election election;
        //When(Action)
        election = Election.getElectionInstance();
        // Then(Assert)
        Assert.assertTrue(election.getVotes().size()==0);
    }

    @Test
    public void constructorTest2(){
        //Given (Arrange)
        Election election;
        //When(Action)
        election = Election.getElectionInstance();
        // Then(Assert)
        Assert.assertTrue(election.getVotes().size()==0);
    }


    /**
     * Checks for correct operation of singleton pattern.
     */
    @Test
    public void singleTonTest(){
        //Given (Arrange)
        Election election, election1;
        //When(Action)
        election = Election.getElectionInstance();
        election1 = Election.getElectionInstance();
        // Then(Assert)
        Assert.assertTrue(election==election1);
    }

    @Test
    public void isOpenTest(){
        //Given (Arrange)
        Election election;
        //When(Action)
        election = Election.getElectionInstance();
        // Then(Assert)
        Assert.assertTrue(election.isOpenStill());
    }

    @Test
    public void closeTest(){
        //Given (Arrange)
        Election election;
        //When(Action)
        election = Election.getElectionInstance();
        election.closeElection();
        // Then(Assert)
        Assert.assertFalse(election.isOpenStill());
    }


    @Test
    public void EliminationTest(){
        //Given (Arrange)
        Election election;
        //When(Action)
        election = Election.getElectionInstance();
        // Then(Assert)
        Assert.assertTrue(election.eliminateCandidate(0).length()==0);
    }

    @Test
    public void readCandidatesTest(){
        //Given (Arrange)
        Election election;
        //When(Action)
        election = Election.getElectionInstance();
        boolean candidates=false;
        try {
            candidates=election.readCandidates();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Then(Assert)
        Assert.assertTrue(candidates);
    }

    @Test
    public void addCandidatesTest(){
        //Given (Arrange)
        Election election;
        //When(Action)
        election = Election.getElectionInstance();
        election.addCandidate("Ronald",Party.CNS.toString());
        // Then(Assert)
        Assert.assertTrue(election.getCandidates().size()>0);
    }

    @Test
    public void getPriorityVotesTest(){
        //Given (Arrange)
        Election election;
        //When(Action)
        election = Election.getElectionInstance();
        election.addCandidate("Ronald",Party.CNS.toString());
        // Then(Assert)
        Assert.assertTrue(election.getCandidates().size()>0);
    }


    @Test
    public void ProcessVotesTest() throws FileNotFoundException {
        //Given (Arrange)
        Election election = Election.getElectionInstance();
        PollingPlace belleVue = new PollingPlace("bellevue");

        //When(Action)
        doRepetitiveTasks(election,belleVue);

        // Then(Assert)
        Assert.assertTrue(election.getVotes().size()>0);
    }


    @Test
    public void isMajorityTest() throws FileNotFoundException {
        //Given (Arrange)
        Election election = Election.getElectionInstance();
        PollingPlace kent = new PollingPlace("kent");
        //When(Action)
        doRepetitiveTasks(election,kent);
        // Then(Assert)
        Assert.assertTrue(election.isMajority().size()>=0);
    }


    @Test
    public void eliminateCandidateTest() throws FileNotFoundException {
        //Given (Arrange)
        Election election = Election.getElectionInstance();
        PollingPlace bellevue = new PollingPlace("bellevue");
        //When(Action)
        doRepetitiveTasks(election,bellevue);
        //first run off
        election.eliminateCandidate(0);
        //second run off
        election.eliminateCandidate(0);
        
        // Then(Assert)
        Assert.assertTrue(election.isMajority().size()>0);
    }



    /**
     * Helper that provides the infrastructure code to perform common tasks such as
     * reading votes from polling place to be added, reading candidates from election
     * @param election singleton ElectionInstance.
     * @param pollingPlace name of the polling place to be added.
     */
    private static void doRepetitiveTasks(Election election, PollingPlace pollingPlace ){
        try {
            pollingPlace.readVotes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            election.readCandidates();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pollingPlace.processVotes();
        election.addDataFromPolls(pollingPlace,pollingPlace.getPriorityVotes());
        election.processVotesAndAssignToCandidates(0);
    }



}
