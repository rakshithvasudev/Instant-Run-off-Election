import org.junit.Assert;
import org.junit.Test;

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
}
