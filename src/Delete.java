import java.io.FileNotFoundException;

/**
 * Created by Rakshith on 4/6/2017.
 */
public class Delete {

    public static void main(String[] args) {
        Election election = Election.getElectionInstance();
        try {
            election.readCandidates();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PollingPlace belleVue = new PollingPlace("belleVue");
        try {
            System.out.println(belleVue.readVotes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        belleVue.processVotes();
//        System.out.println(belleVue.getPriorityVotes());
        election.addDataFromPolls(belleVue.getName(),belleVue.getPriorityVotes());

        election.processVotesAndAssignToCandidates();
        System.out.println(election.getVotes());


    }
}
