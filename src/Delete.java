import java.io.FileNotFoundException;
import java.io.InterruptedIOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Rakshith on 4/6/2017.
 */
public class Delete {

    public static void main(String[] args) {
        Election election = Election.getElectionInstance();
        int i=0;

        try {
            election.readCandidates();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PollingPlace belleVue = new PollingPlace("bellevue");
        ElectionTextUI.addedPollingPlaces.add(belleVue);
//        PollingPlace bothell = new PollingPlace("bothell");
//        ElectionTextUI.addedPollingPlaces.add(bothell);
//        PollingPlace queen = new PollingPlace("queen anne");
//        ElectionTextUI.addedPollingPlaces.add(queen);
        try {
           belleVue.readVotes();
//            bothell.readVotes();
//            queen.readVotes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        belleVue.processVotes();
//        bothell.processVotes();
//        queen.processVotes();
//        System.out.println(belleVue.getPriorityVotes());
        election.addDataFromPolls(belleVue,belleVue.getPriorityVotes());
//        election.addDataFromPolls(bothell,bothell.getPriorityVotes());
//        election.addDataFromPolls(queen,queen.getPriorityVotes());



        election.processVotesAndAssignToCandidates(i);
        System.out.println(election.getVotes());
        election.eliminateCandidate(i);
        System.out.println(election.getVotes());
        election.eliminateCandidate(i);
        System.out.println(election.getVotes());


    }
}
