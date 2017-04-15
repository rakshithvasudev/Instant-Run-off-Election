import java.util.*;

/**
 * Created by Rakshith on 4/8/2017.
 */
public class Utilities{
    static  Election election = Election.getElectionInstance();

    /**
     * Checks if a candidate is WriteIn.
     * @param candidate
     * @return true if the candidate is a write-In.
     */
    public static boolean isWriteInCandidate(Candidate candidate){
       if(candidate.getParty()==Party.NOPARTY)
            return true;
        return false;
    }

    /**
     * Computes the edit distance.
     * Code Taken from https://rosettacode.org/wiki/Levenshtein_distance#Java
     * @param a string that has to be compared to.
     * @param b string that has to be compared.
     * @return
     */
    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j],
                        costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ?
                        nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    /**
     * Gets the current candidate if the edit distance between
     * the passed candidateName and the Registered Candidate is <=3.
     * Creates a New WriteIn Candidate other wise.
     * @param currentCandidateName Name of the candidate whose edit distance must
     *                             be compared with all the registered candidates.
     * @return the Candidate matching the name if edit distance<=3. Creates a new
     * write in candidate otherwise.
     */
    public static Candidate LdistanceToCandidates(String currentCandidateName){
        for (Candidate currentCandidate: election.getCandidates().values()) {
            if(distance(currentCandidateName,currentCandidate.getName())<=3){
                return currentCandidate;
            }
        }
        return new WriteInCandidate(currentCandidateName);
    }


    /**
     * Everything is similar to LdistanceToCandidates(String currentCandidateName).
     * Except, instead of returning a Candidate, this returns the corresponding
     * Name that matches.
     * @param currentCandidateName Candidate name to be checked for Ldistance.
     * @return matched candidate.
     */
    public static String LdistanceToCandidatesName(String currentCandidateName){
        for (Candidate currentCandidate: election.getCandidates().values()) {
            if(distance(currentCandidateName,currentCandidate.getName())<=3){
                return currentCandidate.getName();
            }
        }
        return currentCandidateName;
    }

    /**
     *
     * @param candidateName name of the candidate to be checked.
     * @return Corresponding candidate, if available. Null otherwise.
     */
    public static Candidate getCandidateFromString(String candidateName){
        Candidate candidate = LdistanceToCandidates(candidateName);
        for (Candidate currentCandidate:election.getCandidates().values()) {
            if(currentCandidate.getName().equalsIgnoreCase(candidateName))
                candidate = currentCandidate.clone();
        }
       return candidate;
    }

    /**
     * Gets the total votes count from the election.
     * @return total votes
     */
    public static int getTotalVotesFromElection(){
        int totalVotes = 0;
        for (Integer currentCount : election.getVotes().values())
            totalVotes += currentCount;
        return totalVotes;
    }

    /**
     * Add the key value pairs to the votes, if the key is already added,
     * just update the values, otherwise add newly to the map.
     *
     * If the specified key is not already associated with a value or is
     * associated with null, associates it with the given non-null value.
     * Otherwise, replaces the associated value with the results of the given
     * remapping function, or removes if the result is {null}. This
     * method may be of use when combining multiple mapped values for a key.
     *
     * @param map1 iterates through the elements of map1.
     * @param map2 adds all the values from map1 to map2, maintaining the keys.
     * @throws NullPointerException if the null values are passed.
     * @return true if merging happened.
     *
     */
    public static boolean mergeMapsAddingIntegerValues(Map<Candidate, Integer> map1,Map<Candidate, Integer> map2){
        map1.forEach((Candidate,Integer)->
                map2.merge(Candidate,Integer, java.lang.Integer::sum));
        return true;
    }

    /**
     * This method sorts the map based on the votes and name specs.
     * @param map the required map.
     * @return the sorted map.
     */
    public static Map<Candidate,Integer> sortMapByVotesAndName(Map<Candidate,Integer> map){
        List<Map.Entry<Candidate,Integer>> candidates = new LinkedList<>(map.entrySet());
        Collections.sort(candidates, new CandidateComparator());
        Map<Candidate,Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Candidate,Integer> currentEntry: candidates)
            result.put(currentEntry.getKey(),currentEntry.getValue());
        return result;
    }

    /**
     * Takes all candidates and removes those candidates whose votes are not the same and
     * returns a sorted List that can be used.
     * @param allCandidates all candidates
     * @return
     */
    public static List<Map.Entry<Candidate,Integer>> sortMapForElimination(Set<Map.Entry<Candidate, Integer>> allCandidates) {
        List<Map.Entry<Candidate,Integer>> result = new ArrayList<>(allCandidates);
        List<Integer> dontRemoveIndices = new ArrayList<>();
        for (int i=0;i<result.size()-1;i++){
            if(result.get(i).getValue()==(int)result.get(i+1).getValue()){
                dontRemoveIndices.add(i);
                dontRemoveIndices.add(i+1);
            }
        }

       for (int i=0;i<dontRemoveIndices.size();i++){
            if(i!=dontRemoveIndices.get(i))
                result.remove(i);
        }

        Collections.sort(result, new Comparator<Map.Entry<Candidate, Integer>>() {
            @Override
            public int compare(Map.Entry<Candidate, Integer> o1, Map.Entry<Candidate, Integer> o2) {
                if(o1.getValue()!=(int)o2.getValue())
                    return o1.getValue()-(o2.getValue());
                return o2.getKey().compareTo(o1.getKey());
            }
        });
        return result;
    }
}
