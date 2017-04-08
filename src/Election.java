import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;

/**
 * Created by Rakshith on 4/7/2017.
 * Uses singleton pattern for Election class.
 * There could be just one and only instance of Election.
 */
public class Election {

    private Map<String, Candidate> candidates;
    private Map<String,Candidate> votes;
    private boolean isOpen;
    private volatile static Election electionInstance;
    private PollingPlace pollingPlace;

    private Election() {
        isOpen=true;
        candidates = new LinkedHashMap<>();
        votes = new LinkedHashMap<>();
    }

    /**
     * Uses the singleton Pattern to get a unique Instance.
     * Implements the lazy instantiation mechanism to achieve
     * singularity in Election. Handles multithreaded environment
     * issues.
     * @return One and only Election Instance.
     * Creates a new one if already not instantiated.
     */
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

    /**
     * Checks if the opportunity for voting is allowed.
     * @return true if there is still an opportunity to vote.
     */
    public boolean isOpenStill() {
        return isOpen;
    }

    /**
     * Closes the election for further votes.
     */
    public void closeElection(){isOpen=false;}

    /**
     * Reads from the candidate file and passes the
     * to the value to addCandidate() method which uses factory
     * pattern to create a new candidate.
     * @return true when successfully read. False otherwise.
     */
    public boolean readCandidates(){
        try {
            Scanner scanner = new Scanner(new File("candidates.txt")).useDelimiter("\n");
            while (scanner.hasNext()){
                String[] candidateAndParty = scanner.next().split(",");
                addCandidate(candidateAndParty[0],candidateAndParty[1]);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Adds a candidate to the election.
     * Uses FactoryPattern to create a Candidate.
     * Calls createCandidate() in CandidateFactory class that
     * returns a new appropriate candidate.
     * @param candidateName name of the candidate to be added.
     * @param partyName name of party added.
     */
    public  void addCandidate(String candidateName, String partyName){
        Candidate candidate = CandidateFactory.createCandidate(candidateName,partyName);
        candidates.put(candidate.getName(),candidate);
    }

}
