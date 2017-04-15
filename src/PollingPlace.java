import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Rakshith on 4/8/2017.
 */
public final class PollingPlace {
    private String name;
    private List<Vote> votes;
    private List<Map<Candidate,Integer>> priorityVotes;

    public PollingPlace(String name) {
        Arguments.ensureNotNullOrEmpty(name);
        this.name = name;
        votes=new ArrayList<>();
        priorityVotes =new ArrayList<>();
    }

    /**
     * Reads the appropriate votes from the ballot and
     * creates a new Vote by calling addVote method.
     * sets the name of the pollingPlace to the according to the
     * value entered by the user during creation.
     * Also handles any small mistakes in the spelling during vote
     * cast according to the spec that there could be utmost 3 edit
     * distances.
     *Also converts any empty spaces, to "-".
     *
     * @return true when successfully read.
     */
    public  boolean readVotes() throws FileNotFoundException {
        //Get the correct file name from the argument passed.
        String actualFileName = "ballots-"+name.toLowerCase().
                replaceAll("\\s","-")+".txt";
        try {
            Scanner scanner = new Scanner(new File(actualFileName)).useDelimiter("\n");
            while (scanner.hasNext()){
                String[] preferences = scanner.next().split(",");
                List<String> LdistanceComputedNames = new ArrayList<>();
                for (String votersCandidateName: preferences) {
                    LdistanceComputedNames.add(Utilities.LdistanceToCandidatesName(votersCandidateName));
                }
                addVote(LdistanceComputedNames.toArray(new String[LdistanceComputedNames.size()]));
            }
            return true;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Couldn't find that file");
        }
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
        priorityVotes = Utilities.sortMapByVotesAndName(priorityVotes);
    }


    /**
     * Adds a new Vote from the list of preferences.
     * @param preferences array of preferred candidates
     *                    from highest to lowest preferred.
     */
    public void addVote(String[] preferences) {
        votes.add(new Vote(preferences));
    }

    /**
     * Displays all the votes in the current polling Place.
     */
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

    public List<Vote> getVotes() {
        return votes;
    }

    /**
     * Just check for name equality
     * @param obj object to be compared.
     * @return true if the names match.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj!=null && getClass()== obj.getClass()) {
            PollingPlace PollingObj  =(PollingPlace)obj;
            return name.equals(PollingObj.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int a = 19;
        a = name.hashCode()*a;
        a = votes.hashCode()*a;
        a = priorityVotes.hashCode()*votes.hashCode()*17*a;
        return a;
    }


}
