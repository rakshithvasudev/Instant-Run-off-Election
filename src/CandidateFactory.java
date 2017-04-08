/**
 * Created by Rakshith on 4/8/2017.
 * Creates a series of Candidates based on their
 * party partyName.
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
