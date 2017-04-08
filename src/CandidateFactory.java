/**
 * Created by Rakshith on 4/8/2017.
 * This is a Factory pattern for the candidate that
 * creates a new Candidate based on their
 * partyName.
 */
public class CandidateFactory {
    public static Candidate createCandidate(String candidateName,String partyName){
        Candidate candidate = null;

        if(partyName.length()>0)
            candidate=new RegisteredCandidate(candidateName,Party.valueOf(partyName));

        if(partyName.length()==0)
            candidate=new WriteInCandidate(candidateName);

        return candidate;
    }
}
