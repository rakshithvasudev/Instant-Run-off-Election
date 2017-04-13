import java.util.Comparator;
import java.util.Map;

/**
 * Created by Rakshith on 4/10/2017.
 * Compares 2 candidates based on their votes first.
 * Then on their names if their votes are the same.
 * This class breaks the tie in the event that the candidates have
 * same number of votes.
 */
public class CandidateComparator implements Comparator<Map.Entry<Candidate,Integer>> {
    /**
     * Compares 2 candidates by their votes.
     * If their votes are equal, then the compares by their
     * lexicographically text names.
     * @param o1 candidate 1.
     * @param o2 candidate 2.
     * @return >0 or ==0 <0
     */
    @Override
    public int compare(Map.Entry<Candidate, Integer> o1, Map.Entry<Candidate, Integer> o2) {
        if (o1.getValue()!=(int)o2.getValue())
            return o2.getValue()-o1.getValue();
        return o1.getKey().compareTo(o2.getKey());
    }
}
