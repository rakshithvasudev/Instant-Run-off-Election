import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Created by Rakshith on 4/7/2017.
 * Uses singleton pattern for Election class.
 * There could be just one and only instance of Election.
 */
public class Election {

    private Map<String, Candidate> candidates;
    private Map<Candidate,Integer> votes;
    private boolean isOpen;
    private volatile static Election electionInstance;
    private Map<String,List<Map<Candidate,Integer>>> votesFromPollingPlaces;


    private Election() {
        isOpen=true;
        candidates = new LinkedHashMap<>();
        votes = new LinkedHashMap<>();
        votesFromPollingPlaces= new LinkedHashMap<>();
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

    public Map<Candidate, Integer> getVotes() {
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
    public boolean readCandidates() throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File("candidates.txt")).useDelimiter("\n");
            while (scanner.hasNext()){
                String[] candidateAndParty = scanner.next().split(",");
                addCandidate(candidateAndParty[0],candidateAndParty[1]);
            }
            return true;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No Candidates file.");
        }

    }

    /**
     * Adds a candidate to the election.
     * Uses FactoryPattern to create a Candidate.
     * Calls createCandidate() in CandidateFactory class that
     * returns a new appropriate candidate.
     * @param candidateName name of the candidate to be added.
     * @param partyName name of party added.
     */
    public void addCandidate(String candidateName, String partyName){
        Candidate candidate = CandidateFactory.createCandidate(candidateName,partyName);
        candidates.put(candidate.getName(),candidate);
    }

    /**
     * Adds data from outside pollPlaces to the main election.
     * @param nameOfPollPlace name of the polling Place.
     * @param votesList preferences list of votes from the outside.
     */
    public void addDataFromPolls(String nameOfPollPlace, List<Map<Candidate,Integer>> votesList){
        votesFromPollingPlaces.put(nameOfPollPlace,votesList);
    }

    /**
     * As long as there are candidates, update votes
     * from different pollingPlaces to Candidate.
     * Based on the run off, the parameter would be assigned.
     */
    public void processVotesAndAssignToCandidates(){
        //for (int i=0;i<candidates.size();i++)
            processVotesAndAssignToCandidates(0);
    }

    /**
     * Go through every single pollPlace and add the
     * ith preference votes to the votes<Candidate, Integer> Map.
     * if i=0, gets the first preference votes.
     * if i=1, gets the second preference and so fourth.
     * @throws IllegalArgumentException if i> size of votesFromPollingPlaces.
     */
    public void processVotesAndAssignToCandidates(int i){
        Arguments.ensureAtMost(i,votesFromPollingPlaces.size());
        Iterator votesFromPollIterator = votesFromPollingPlaces.values().iterator();
        Map<Candidate,Integer> candidateVotesMap;
        List<Map<Candidate,Integer>> candidateVotes;
        // As long as there are elements in the votesFromPollingPlaces, check and assign values.
        while (votesFromPollIterator.hasNext()){
            candidateVotes = (List<Map<Candidate,Integer>>)votesFromPollIterator.next();
            //gets the ith preference votes.
            candidateVotesMap=candidateVotes.get(i);
            for (Candidate iterCandidate: candidateVotesMap.keySet()) {
                if(!iterCandidate.isEliminated()){
                       //if there is a candidate already in the votes Map, then just update the votes.
                       if(votes.containsKey(iterCandidate))
                         votes.put(iterCandidate,
                                 votes.get(iterCandidate)+candidateVotesMap.get(iterCandidate));
                        else if(!votes.containsKey(iterCandidate))
                            votes.put(iterCandidate,candidateVotesMap.get(iterCandidate));
                }
                if(iterCandidate.isEliminated()){
                    distributeVotes(iterCandidate,i);
                    votes.remove(iterCandidate);
                }
            }
        }
    }

    /**
     * Get all vote objects from the added poll Places and check.
     * @param iterCandidate
     * @param i
     */
    private void distributeVotes(Candidate iterCandidate, int i) {
        String nextCandidateName;
        List<PollingPlace> addedPlaces = ElectionTextUI.getAddedPollingPlaces();
        Map<Candidate,Integer> candidateVotes = new LinkedHashMap<>(votes);
        for (PollingPlace currentPollingPlace: addedPlaces ) {
            for (Vote currentVote: currentPollingPlace.getVotes()){
                if(iterCandidate.getName().equals(currentVote.getPreferences().get(i))){
                    //fix any indexOutOfBounds exception that might occur.
                    if(i+1>currentVote.getPreferences().size())
                        i=i-1;
                    nextCandidateName=currentVote.getPreferences().get(i+1);


                }
            }
        }
    }
}
