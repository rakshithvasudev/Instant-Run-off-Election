import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Rakshith on 4/7/2017.
 */
public class Election {

    private Map<String, Candidate> candidates;
    private Map<String,Candidate> votes;
    private boolean isOpen;
    public static Election electionInstance;

    private Election() {
        candidates = new LinkedHashMap<>();
        votes = new LinkedHashMap<>();
    }

    public static Election getElectionInstance() {
        if(electionInstance==null){
            synchronized (Election.class){
                if(electionInstance==null){
                    electionInstance=new Election();
                }
            }
        }
        return electionInstance;
    }





}
