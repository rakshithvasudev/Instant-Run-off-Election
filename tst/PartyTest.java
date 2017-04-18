import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Rakshith on 4/10/2017.
 */
public class PartyTest {

    @Test
    public void usageTest(){

        //Given (Arrange)
        Party party1;
        //When(Action)
        party1=Party.TEA;

        // Then(Assert)
        Assert.assertTrue(Party.valueOf("TEA").equals(party1));
    }

    @Test
    public void usageTest2(){

        //Given (Arrange)
        Party party1;
        //When(Action)
        party1=Party.UKK;

        // Then(Assert)
        Assert.assertTrue(Party.valueOf("UKK").equals(party1));
    }


}
