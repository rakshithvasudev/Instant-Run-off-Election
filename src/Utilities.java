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
        Candidate candidate = null;
        for (Candidate currentCandidate:election.getCandidates().values()) {
            if(currentCandidate.getName().equalsIgnoreCase(candidateName))
                candidate = currentCandidate.clone();
        }
       return candidate;
    }


    public static int getTotalVotesFromElection(){
        int totalVotes = 0;
        for (Integer currentCount : election.getVotes().values())
            totalVotes += currentCount;
        return totalVotes;
    }


}
