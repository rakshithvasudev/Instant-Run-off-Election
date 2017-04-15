import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;

/**
 * Created by Rakshith on 4/10/2017.
 */
public class CandidateFactoryTest {

    @Test
    public void ConstructorTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Ronald","");
        //Then(Assertion)
        Assert.assertEquals("Constructor Test Failed!",candidate.getName(),"Ronald");
    }

    @Test
    public void PartyAssignTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Ronald","");
        //Then(Assertion)
        Assert.assertEquals("PartyAssign Test Failed!",candidate.getParty(),Party.NOPARTY);
    }

    @Test
    public void PartyAssignTest2(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Ronald","DEM");
        //Then(Assertion)
        Assert.assertEquals("PartyAssign Test 2 Failed!",candidate.getParty(),Party.DEM);

    }


    @Test
    public void PartyAssignTest3(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Barack Obama","");
        //Then(Assertion)
        Assert.assertEquals("PartyAssign Test 3 Failed!",candidate.getParty(),Party.NOPARTY);
    }



    @Test
    public void PartyAssignFactoryTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Barack Obama","");
        //Then(Assertion)
        Assert.assertEquals("PartyAssignFactory Test Failed!",candidate.getParty(),Party.NOPARTY);
    }


    @Test
    public void PartyAssignInstanceFactoryTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Barack Obama","");
        //Then(Assertion)
        Assert.assertTrue(candidate instanceof WriteInCandidate);
    }


    @Test
    public void PartyAssignInstanceFactoryTest2(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Micheal Gorbechav","LIB");
        //Then(Assertion)
        Assert.assertTrue(candidate instanceof RegisteredCandidate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void PartyAssignExceptionTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Rickky Fallony","RSA");
        //Then(Assertion)
        Assert.assertTrue(candidate instanceof RegisteredCandidate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void PartyAssignExceptionTest2(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Rickky Fallony","NONPARTY");
        //Then(Assertion)
        Assert.assertTrue(candidate instanceof WriteInCandidate);
    }

    @Test
    public void PartyAssignExceptionTest3(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = CandidateFactory.createCandidate("Fallony Rocker","REP");
        //Then(Assertion)
        Assert.assertFalse (candidate instanceof WriteInCandidate);
    }


}
