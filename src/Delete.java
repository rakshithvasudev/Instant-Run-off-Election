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
        PollingPlace bothell = new PollingPlace("bothell");
        PollingPlace queen = new PollingPlace("u district");
        try {
           belleVue.readVotes();
           bothell.readVotes();
            queen.readVotes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        belleVue.processVotes();
        bothell.processVotes();
        queen.processVotes();
//        System.out.println(belleVue.getPriorityVotes());
        election.addDataFromPolls(belleVue.getName(),belleVue.getPriorityVotes());
        election.addDataFromPolls(bothell.getName(),bothell.getPriorityVotes());
        election.addDataFromPolls(queen.getName(),queen.getPriorityVotes());


//        election.getCandidates().get("Barack Obama").setEliminated(true);
        election.processVotesAndAssignToCandidates();

        System.out.println(election.getVotes());



    }
}
