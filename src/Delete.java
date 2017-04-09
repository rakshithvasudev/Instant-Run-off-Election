/**
 * Created by Rakshith on 4/6/2017.
 */
public class Delete {

    public static void main(String[] args) {
        Election election = Election.getElectionInstance();
        election.readCandidates();

        PollingPlace belleVue = new PollingPlace("belleVue");
        System.out.println(belleVue.readVotes());
        belleVue.processVotes();
        System.out.println(belleVue.getPriorityVotes());



    }
}
