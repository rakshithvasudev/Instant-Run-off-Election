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
//        PollingPlace bothell = new PollingPlace("bothell");
        try {
           belleVue.readVotes();
//           bothell.readVotes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        belleVue.processVotes();
//        bothell.processVotes();
//        System.out.println(belleVue.getPriorityVotes());
        election.addDataFromPolls(belleVue.getName(),belleVue.getPriorityVotes());
//        election.addDataFromPolls(bothell.getName(),bothell.getPriorityVotes());

        election.processVotesAndAssignToCandidates();
        System.out.println(election.getVotes());



    }
}
