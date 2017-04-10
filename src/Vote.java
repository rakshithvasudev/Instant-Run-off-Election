import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rakshith on 4/6/2017.
 * The constructor for Votes takes in multiple
 * arguments because it'd check for the number of
 * candidates on candidates.txt and see if people
 * voted for candidates in priorities.i.e
 * if candidatesCount == or > count of voter's preferences
 * then it's a legal vote.
 *
 * Ex: if there are 5 candidates in candidates.txt
 * and the number of preferences from the voter is 5,
 * then it's a valid vote. Not valid if
 * the preferences is less than the registered candidates count.
 *
 */
public class Vote {

    private List<String> preferences;

    /**
     * Ignore the write-In Candidates for preferences match.
     * Because of write In candidates, the preferences count
     * may be larger than the registered candidates. But
     * cannot be lesser than registered candidates.
     *
     * @param args array of candidate preferences.
     * @throws IllegalVoteCastException when preferences count
     *  is lesser than than the registered candidates count.
     */
    public Vote(String... args) {
        if(args.length<Election.getElectionInstance().getCandidates().size())
            throw new IllegalVoteCastException("Preferences can't be " +
                    "less than registered candidates count");
        preferences = new ArrayList<>();
        preferences.addAll(Arrays.asList(args));
    }

    /**
     * returns the order of preferences of the vote.
     * @return List of preferences.
     */
    public List<String> getPreferences() {
        return preferences;
    }

    /**
     * Prints the vote object.
     * @return String Version of the preferences.
     */
    @Override
    public String toString() {
        return getPreferences().toString();
    }
}