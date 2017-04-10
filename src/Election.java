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
    private Map<Candidate, Integer> votes;
    private boolean isOpen;
    private volatile static Election electionInstance;
    private Map<PollingPlace, List<Map<Candidate, Integer>>> votesFromPollingPlaces;


    private Election() {
        isOpen = true;
        candidates = new LinkedHashMap<>();
        votes = new LinkedHashMap<>();
        votesFromPollingPlaces = new LinkedHashMap<>();
    }

    /**
     * Uses the singleton Pattern to get a unique Instance.
     * Implements the lazy instantiation mechanism to achieve
     * singularity in Election. Handles multithreaded environment
     * issues.
     *
     * @return One and only Election Instance.
     * Creates a new one if already not instantiated.
     */
    public static Election getElectionInstance() {
        if (electionInstance == null) {
            synchronized (Election.class) {
                if (electionInstance == null) {
                    electionInstance = new Election();
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
     *
     * @return true if there is still an opportunity to vote.
     */
    public boolean isOpenStill() {
        return isOpen;
    }

    /**
     * Closes the election for further votes.
     */
    public void closeElection() {
        isOpen = false;
    }

    /**
     * Reads from the candidate file and passes the
     * to the value to addCandidate() method which uses factory
     * pattern to create a new candidate.
     *
     * @return true when successfully read. False otherwise.
     */
    public boolean readCandidates() throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File("candidates.txt")).useDelimiter("\n");
            while (scanner.hasNext()) {
                String[] candidateAndParty = scanner.next().split(",");
                addCandidate(candidateAndParty[0], candidateAndParty[1]);
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
     *
     * @param candidateName name of the candidate to be added.
     * @param partyName     name of party added.
     */
    public void addCandidate(String candidateName, String partyName) {
        Candidate candidate = CandidateFactory.createCandidate(candidateName, partyName);
        candidates.put(candidate.getName(), candidate);
    }

    /**
     * Adds data from outside pollPlaces to the main election.
     *
     * @param pollPlace Actual the polling Place.
     * @param votesList preferences list of votes from the outside.
     */
    public void addDataFromPolls(PollingPlace pollPlace, List<Map<Candidate, Integer>> votesList) {
        votesFromPollingPlaces.put(pollPlace, votesList);
    }

    /**
     * As long as there are candidates, update votes
     * from different pollingPlaces to Candidate.
     * Based on the run off, the parameter would be assigned.
     */
    public void processVotesAndAssignToCandidates() {
        //for (int i=0;i<candidates.size();i++)
        processVotesAndAssignToCandidates(0);
    }

    /**
     * Go through every single pollPlace and add the
     * ith preference votes to the votes<Candidate, Integer> Map.
     * if i=0, gets the first preference votes -> No run off yet.
     * if i=1, gets the second preference and so fourth. -> First run off and so on.
     *
     * @throws IllegalArgumentException if i> size of votesFromPollingPlaces.
     */
    public void processVotesAndAssignToCandidates(int i) {
        Arguments.ensureAtMost(i, votesFromPollingPlaces.size());
        Map<Candidate, Integer> candidateVotesMap;
        //go through all the available polling places and get ith preference votes.
        for (PollingPlace currentPollingPlace : votesFromPollingPlaces.keySet()) {
            // gets the ith vote preference of Map<Candidate, Integer>.
            candidateVotesMap = currentPollingPlace.getPriorityVotes().get(i);
            //add the key value pairs to the votes, if the key is already added,
            // just update the values, otherwise add newly.
             candidateVotesMap.forEach((Candidate,Integer)->votes.merge(Candidate,Integer, java.lang.Integer::sum));
          }
    }

    /**
     * Get all vote objects from the added poll Places and check for that
     * candidate name.
     *
     * @param iterCandidate the candidate that has to be removed votes from.
     * @param i             ith run off = ith preference votes would be extracted.
     */
    private void distributeVotes(Candidate iterCandidate, int i,
                                 Map<Candidate, Integer> candidateVotesMap) {
        Candidate nextCandidate;
//        List<PollingPlace> addedPlacesList = new ArrayList<>(votesFromPollingPlaces.keySet())
        List<PollingPlace> addedPlaces = ElectionTextUI.getAddedPollingPlaces();
        for (PollingPlace currentPollingPlace : addedPlaces) {
            for (Vote currentVote : currentPollingPlace.getVotes()) {
                if (iterCandidate.getName().equals(currentVote.getPreferences().get(i))) {
                    //fix any indexOutOfBounds exception that might occur.
                    if (i + 1 > currentVote.getPreferences().size())
                        i = i - 1;
                    //get the next candidate from the preferences name.
                    nextCandidate = Utilities.
                            getCandidateFromString(currentVote.getPreferences().get(i + 1));
                    //add the votes for the next candidate in the preference list.
                    if (votes.containsKey(nextCandidate))
                        votes.put(nextCandidate,
                                candidateVotesMap.get(nextCandidate) + candidateVotesMap.get(iterCandidate));
                    if (!votes.containsKey(nextCandidate))
                        votes.put(nextCandidate,
                                1);
                }
            }
        }
    }
}
