/**
 * Created by Rakshith on 4/8/2017.
 */
public class Utilities {

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


}
