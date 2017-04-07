import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Rakshith on 4/7/2017.
 */
public class Election {

    private Map<String, Candidate> candidates;
    private Map<String,Candidate> votes;
    private boolean isOpen;
    private volatile static Election electionInstance;

    private Election() {
        candidates = new LinkedHashMap<>();
        votes = new LinkedHashMap<>();
    }

    public static Election getElectionInstance() {
        if(electionInstance==null){
            synchronized (Election.class){
                if(electionInstance==null){
                    electionInstance=new Election();
                    new Election();
                }
            }
        }
        return electionInstance;
    }


    public Map<String, Candidate> getCandidates() {
        return candidates;
    }

    public Map<String, Candidate> getVotes() {
        return votes;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
