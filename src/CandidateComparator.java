import java.util.Comparator;

/**
 * Created by Rakshith on 4/10/2017.
 * Compares 2 candidates based on their votes first.
 * Then on their names if their votes are the same.
 * This class breaks the tie in the event that the candidates have
 * same number of votes.
 */
public class CandidateComparator implements Comparator<Candidate> {
    Election election = Election.getElectionInstance();

    /**
     * Compares 2 candidates by their votes.
     * If their votes are equal, then the compares by their
     * lexicographically text names.
     * @param o1 candidate 1.
     * @param o2 candidate 2.
     * @return >0 or ==0 <0
     */
    @Override
    public int compare(Candidate o1, Candidate o2) {
    int candidate1Votes = election.getVotes().get(o1);
    int candidate2Votes = election.getVotes().get(o2);
        if(candidate1Votes!=candidate2Votes)
            return candidate1Votes-candidate2Votes;
        
        return o1.compareTo(o2);
    }
}
