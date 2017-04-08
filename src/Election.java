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

    public static boolean readCandidates(){
        try {
            Scanner scanner = new Scanner(new File("candidates.txt")).useDelimiter(",|\n");
            int counter=0;
                while (scanner.hasNext()){
                    if(counter%2==0)
                        System.out.println("Candidate: "+scanner.next());
                    if(counter%2!=0)
                        System.out.println("Party: "+scanner.next());
                    counter++;
                }

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
