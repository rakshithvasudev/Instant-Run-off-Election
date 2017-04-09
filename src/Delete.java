/**
 * Created by Rakshith on 4/6/2017.
 */
public class Delete {

    public static void main(String[] args) {
        Election election = Election.getElectionInstance();
        election.readCandidates();

        PollingPlace pollingPlace = new PollingPlace("belleVue");
        System.out.println(pollingPlace.readVotes());
        pollingPlace.displayVotes();
        pollingPlace.processVotes();
        System.out.println(pollingPlace.getProcessedVotesCount());


    }
}
