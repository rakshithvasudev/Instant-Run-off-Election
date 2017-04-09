/**
 * Created by Rakshith on 4/6/2017.
 * A registered candidate has a party name.
 * Uses Abstraction.
 */
public class RegisteredCandidate extends Candidate {

    public RegisteredCandidate(String name,Party party) {
        setName(name);
        setParty(party);
    }
}
