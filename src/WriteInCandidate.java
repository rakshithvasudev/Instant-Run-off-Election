/**
 * Created by Rakshith on 4/6/2017.
 * Uses abstraction from the Candidate Class.
 * The Party is always null for a writeInCandidate.
 */
public class WriteInCandidate extends Candidate {

    public WriteInCandidate(String name) {
        setName(name);
        setPartyName(null);
    }
}
