import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Rakshith on 4/10/2017.
 */
public class CandidateTest {

    @Test
    public void CandidateConstructorTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new RegisteredCandidate("Jason",Party.ARR);
        //Then(Assert)
        Assert.assertEquals("Jason",candidate.getName());
    }

    @Test
    public void CandidateConstructorTest2(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new WriteInCandidate("Jason");
        //Then(Assert)
        Assert.assertEquals("Jason",candidate.getName());
    }

    @Test
    public void getNameTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new WriteInCandidate("Winne");
        //Then(Assert)
        Assert.assertEquals("Winne",candidate.getName());
    }

    @Test
    public void setNameTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new WriteInCandidate("Winne");
        candidate.setName("Pooh");
        //Then(Assert)
        Assert.assertEquals("Pooh",candidate.getName());
    }

    @Test
    public void isEliminatedTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new WriteInCandidate("Winne");
        candidate.setEliminated(false);
        //Then(Assert)
        Assert.assertTrue(!candidate.isEliminated());
    }

    @Test
    public void isEliminatedTest2(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new WriteInCandidate("Richard Bandler");
        candidate.setEliminated(true);
        //Then(Assert)
        Assert.assertTrue(candidate.isEliminated());
    }

    @Test
    public void getPartyTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new RegisteredCandidate("Richard Bandler",Party.REP);
         //Then(Assert)
        Assert.assertEquals(candidate.getParty(),Party.REP);
    }

    @Test
    public void getPartyTest2(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new RegisteredCandidate("Dennis Richie",Party.GRN);
        //Then(Assert)
        Assert.assertEquals(candidate.getParty(),Party.GRN);
    }

    @Test
    public void setPartyTest(){
        //Given (Arrange)
        Candidate candidate;
        //When(Action)
        candidate = new RegisteredCandidate("James Gosling",Party.LIB);
        //Then(Assert)
        Assert.assertEquals(candidate.getParty(),Party.LIB);
    }

    @Test
    public void equalsTest(){
        //Given (Arrange)
        Candidate candidate1, candidate2;
        //When(Action)
        candidate1 = new RegisteredCandidate("James Gosling",Party.LIB);
        candidate2 = new RegisteredCandidate("Denis Richie",Party.CNS);
        // Then(Assert)
        Assert.assertFalse(candidate1.equals(candidate2));
    }

    @Test
    public void equalsTest2(){
        //Given (Arrange)
        Candidate candidate1, candidate2;
        //When(Action)
        candidate1 = new RegisteredCandidate("James Gosling",Party.LIB);
        candidate2 = new RegisteredCandidate("James Gosling",Party.CNS);
        // Then(Assert)
        Assert.assertFalse(candidate1.equals(candidate2));
    }

    @Test
    public void equalsTest3(){
        //Given (Arrange)
        Candidate candidate1, candidate2;
        //When(Action)
        candidate1 = new RegisteredCandidate("James Gosling",Party.LIB);
        candidate2 = new RegisteredCandidate("James Gosling",Party.LIB);
        // Then(Assert)
        Assert.assertTrue(candidate1.equals(candidate2));
    }




}
