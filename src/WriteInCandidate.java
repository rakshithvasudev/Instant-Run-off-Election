/**
 * Created by Rakshith on 4/6/2017.
 * Uses abstraction from the Candidate Class.
 * The Party is always "NOPARTY" for a writeInCandidate.
 */
public class WriteInCandidate extends Candidate {

    public WriteInCandidate(String name) {
        setName(name);
        setParty(Party.NOPARTY);
        setEliminated(false);
    }

}
