import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Rakshith on 4/8/2017.
 */
public class PollingPlace {
    private String name;
    private List<Vote> votes;
    private List<Map<Candidate,Integer>> priorityVotes;

    public PollingPlace(String name) {
        if(name.length()<=0)
            throw new IllegalArgumentException("Enter a proper polling place");
        this.name = name;
        votes=new ArrayList<>();
        priorityVotes =new ArrayList<>();
    }

    /**
     * Reads the appropriate votes from the ballot and
     * creates a new Vote by calling addVote method.
     * @return true when successfully read.
     */
    public  boolean readVotes(){
        //Get the correct file name from the argument passed.
        String actualFileName = "ballots-"+name.toLowerCase()+".txt";

        try {
            Scanner scanner = new Scanner(new File(actualFileName)).useDelimiter("\n");
            while (scanner.hasNext()){
                String[] preferences = scanner.next().split(",");
                addVote(preferences);
            }
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Sorry! No such File found.");
        }
        return false;
    }

    /**
     * Process the votes by checking the first to n preferences.
     * Adds all the processed n preference elements in the ArrayList
     * referred to as priorityVotes that contains  a list of
     * Maps of {Candidate->Votes} pairs.
     */
    public void processVotes(){
     for (int i=0;i<Election.getElectionInstance().getCandidates().size();i++)
          processPreferenceVotes(i);
    }

    /**
     * Processing here means, to collect votes that match to
     * the candidate. A first preference vote is that vote
     * which the voters would have selected as their preferred candidate.
     * EX: If this were the votes [Ron Paul,Barack Obama,John McCain,Ralph Nader],
     * [John McCain,Ron Paul,Barack Obama,Ralph Nader] then the first preference
     * vote would be Ron Paul, John McCain.
     * If i=0, it'd process first preference votes.
     * If i=1, it'd process second preference votes and so on until
     * all the candidates are over.
     * Uses the Iterator Pattern to iterate through the votes.
     * The put method put will replace the value of an existing
     * key and will create it if doesn't exist.
     * @param i the value of the preferences in the votes.
     */
    private void processPreferenceVotes(int i) {
        Map<Candidate,Integer> processedVotesCount = new LinkedHashMap<>() ;
        int voteCounter=0;
        Iterator iterator = Election.getElectionInstance().getCandidates().values().iterator();
        while (iterator.hasNext()) {
            Candidate nextCandidate = (Candidate)iterator.next();
            for (Vote currentVote : votes)
                if (currentVote.getPreferences().get(i).equals(nextCandidate.getName()))
                    voteCounter++;
            processedVotesCount.put(nextCandidate,voteCounter);
            voteCounter=0;
        }
        priorityVotes.add(processedVotesCount);
    }


    /**
     * Adds a new Vote from the list of preferences.
     * @param preferences array of preferred candidates
     *                    from highest to lowest preferred.
     */
    public void addVote(String[] preferences) {
        votes.add(Vote.getInstance(preferences));
    }

    public void displayVotes(){
        for (Vote currentVote: votes)
            System.out.println(currentVote);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name :" + name + " VotesCount :" + votes.size();
    }

    public List<Map<Candidate, Integer>> getPriorityVotes() {
        return priorityVotes;
    }
}
