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
     * Implements the double check instantiation mechanism to achieve
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
     * @param pollPlace The Actual polling Place.
     * @param votesList preferences list of votes from the outside.
     */
    public void addDataFromPolls(PollingPlace pollPlace, List<Map<Candidate, Integer>> votesList) {
        votesFromPollingPlaces.put(pollPlace, votesList);
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
        Map<Candidate, Integer> candidateVotesMap=null;
        //go through all the available polling places and get ith preference votes.
        for (PollingPlace currentPollingPlace : votesFromPollingPlaces.keySet())
            // gets the ith vote preference of Map<Candidate, Integer>.
            candidateVotesMap = currentPollingPlace.getPriorityVotes().get(i);
        //Merge 2 maps adding their respective values in keys.
        Utilities.mergeMapsAddingIntegerValues(candidateVotesMap, votes);
        //sort the map.
        votes = Utilities.sortMapByVotesAndName(votes);
    }

    /**
     * Checks if any candidate has votes larger than or equal to 50% +1vote
     * of the total votes, who is actually the winner.
     *
     * @return a winner if found.
     */
    public Map<Candidate, Integer> isMajority() {
        int totalVotes = Utilities.getTotalVotesFromElection();
        Map<Candidate, Integer> winner = new HashMap<>();
        for (Map.Entry<Candidate, Integer> currentVotes : votes.entrySet()) {
            if ((currentVotes.getValue() / (double) totalVotes) * 100 > 50)
                winner.put(currentVotes.getKey(), currentVotes.getValue());
        }
        return winner;
    }

    /**
     * Removes the candidate having least votes from the election by distributing his votes
     * to the next preferences votes.
     * @param i ith preference votes.
     * @return name of the eliminated candidate.
     */
    public String eliminateCandidate(int i) {
        Iterator iterator = votes.values().iterator();
        Candidate candidateToBeEliminated;
        String candidateName = "";
        int least = 0;
        if (iterator.hasNext())
            least = votes.values().iterator().next();
        for (Integer leastVotes : votes.values())
            if (leastVotes < least)
                least = leastVotes;
        for (Map.Entry<Candidate, Integer> currentMap : votes.entrySet()) {
            if (currentMap.getValue() == least) {
                candidateToBeEliminated = currentMap.getKey();
                distributeVotes(candidateToBeEliminated, i);
                candidateName = candidateToBeEliminated.getName();
                candidateToBeEliminated.setEliminated(true);
                votes.remove(candidateToBeEliminated);
                votes = Utilities.sortMapByVotesAndName(votes);
                return candidateName;
            }
        }
        return candidateName;
    }

    /**
     * Get all vote objects from the added poll Places and check for that
     * candidate name.
     *
     * @param candidateToBeEliminated the candidate that has to be removed votes from.
     * @param i                       ith run off = ith preference votes would be extracted.
     */
    private void distributeVotes(Candidate candidateToBeEliminated, int i) {
        Candidate nextCandidate;
        int counter;
        Map<Candidate, Integer> candidateVotesMap = new LinkedHashMap<>();
        List<PollingPlace> addedPlaces = ElectionTextUI.getAddedPollingPlaces();
        for (PollingPlace currentPollingPlace : addedPlaces) {
            for (Vote currentVote : currentPollingPlace.getVotes()) {
                if (candidateToBeEliminated.getName().equals(currentVote.getPreferences().get(i))) {
                    //get the next candidate from the preferences name.
                    nextCandidate = Utilities.
                            getCandidateFromString(currentVote.getPreferences().get(i + 1));
                    //if the "nextCandidate" is eliminated, then get the actual next candidate.
                    for (counter=2;counter<=currentVote.getPreferences().size() &&
                            nextCandidate.isEliminated();counter++)
                        nextCandidate = Utilities.
                                getCandidateFromString(currentVote.getPreferences().
                                        get(i + counter));
                    //If the candidate is eliminated don't merge that candidate, remove instead.
                    votes.keySet().removeIf(Candidate::isEliminated);
                    candidateVotesMap.keySet().removeIf(Candidate::isEliminated);
                    //add the votes for the next candidate in the preference list.
                    candidateVotesMap.put(nextCandidate, 1);
                    //merge the values in candidateVotesMap and votes Maps.
                    Utilities.mergeMapsAddingIntegerValues(candidateVotesMap, votes);
                }
            }
        }
    }
}
