import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;

/**
 * Created by Rakshith on 4/7/2017.
 */
public class Election {

    private Map<String, Candidate> candidates;
    private Map<String,Candidate> votes;
    private boolean isOpen;
    private volatile static Election electionInstance;

    private Election() {
        isOpen=true;
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

    public boolean isOpenStill() {
        return isOpen;
    }

    /**
     * Reads from the candidate file and passes the
     * to the value to candidate adder.
     * @return true when successfully read. False otherwise.
     */
    public boolean readCandidates(){
        try {
            Scanner scanner = new Scanner(new File("candidates.txt")).useDelimiter("\n");
            while (scanner.hasNext()){
                String[] candidateAndParty = scanner.next().split(",");
                System.out.println(Arrays.toString(candidateAndParty));
                addCandidate(candidateAndParty[0],candidateAndParty[1]);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void addCandidate(String candidateName, String partyName){

        //candidates.put(name,new )

    }
}
