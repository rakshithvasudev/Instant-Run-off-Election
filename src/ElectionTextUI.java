// Homework 4 (Election)
// Instructor-provided code.
// You SHOULD modify this file to make it interface with your own classes.

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the text user interface (UI) for the election
 * program, allowing the user to view and manage the election and its objects.
 *
 * @author Han
 * @version Spring 2017
 */
public final class ElectionTextUI {
    //After deleting Delete.java class, then initialize in consturctor
    static List<PollingPlace> addedPollingPlaces = new ArrayList<>();
    Election election;
    int i;

    /**
     * Constructs a new text user interface for managing a election.
     */

    public ElectionTextUI() {
        System.out.println("Election Vote Counter");

        // TODO: initialization code can go here
        election = election.getElectionInstance();
        i=0;

        try {
            election.readCandidates();
        } catch (FileNotFoundException e) {
            System.out.println("There is no Candidates.txt file.");
        }

    }

    /**
     * Displays the main menu of choices and prompts the user to enter a choice.
     * Once a valid choice is made, initiates other code to handle that choice.
     */
    public void mainMenu() {
        // main menu
        displayOptions();
        while (true) {
            String choice = ValidInputReader.getValidString(
                    "Main menu, enter your choice:",
                    "^[aAcCrRpPeEqQ?]$").toUpperCase();
            if (choice.equals("A")) {
                addPollingPlace();
            } else if (choice.equals("C")) {
                closeElection();
            } else if (choice.equals("R")) {
                results();
            } else if (choice.equals("P")) {
                perPollingPlaceResults();
            } else if (choice.equals("E")) {
                eliminate();
            } else if (choice.equals("?")) {
                displayOptions();
            } else if (choice.equals("Q")) {
                System.out.println("Goodbye.");
                break;
            }
            System.out.println();
        }
    }

    // Displays the list of key commands the user can use.
    private void displayOptions() {
        System.out.println();
        System.out.println("Main System Menu");
        System.out.println("----------------");
        System.out.println("A)dd polling place");
        System.out.println("C)lose the polls");
        System.out.println("R)esults");
        System.out.println("P)er-polling-place results");
        System.out.println("E)liminate lowest candidate");
        System.out.println("?) display this menu of choices again");
        System.out.println("Q)uit");
        System.out.println();
    }

    // Called when P key is pressed from main menu.
    // Reads data from a new polling place.
    private void addPollingPlace() {

        // when the election is not open,
        if (!election.isOpenStill()) {
            System.out.println("The election is closed.");
            System.out.println("No more polling places may be added.");
            return;
        }

        String pollingPlaceName = ValidInputReader.getValidString("Name of polling place:", "^[a-zA-Z0-9 ]+$");
        PollingPlace pollingPlace = null;
        try {
            pollingPlace = new PollingPlace(pollingPlaceName);
        } catch (IllegalArgumentException e) {
            System.out.println("Name must be > 0 letters.");
        }


        try {
            addedPollingPlaces.add(pollingPlace);
            pollingPlace.readVotes();
            pollingPlace.processVotes();
            // TODO: add polling place's data to election totals
            election.addDataFromPolls(pollingPlace, pollingPlace.getPriorityVotes());
            election.processVotesAndAssignToCandidates(i);
            System.out.println("Added " + pollingPlaceName + ".");
        } catch (Exception e) {
            // when the polling place is not found,
            System.out.println("No such polling place was found.");
        }
    }


    /**
     * Retrives all the added pollingPlaces
     *
     * @return Added pollingPlaces
     */
    public static List<PollingPlace> getAddedPollingPlaces() {
        return addedPollingPlaces;
    }


    // Called when C key is pressed from main menu.
    // Closes the election so that no more voting can take place.
    private void closeElection() {

        if(!election.isOpenStill()) {
            System.out.println("Election is already closed");
            return;
        }
        System.out.println("Closing the election.");
        // TODO: close the election
        election.closeElection();



    }

    // Called when R key is pressed from main menu.
    // Shows the current results of the election.
    private void results() {
        // when the election is not closed,
        if (election.isOpenStill()) {
            System.out.println("The election is still open for votes.");
            System.out.println("You must close the election before viewing results.");
        }
        // when the election is closed,
        if (!election.isOpenStill()) {
            System.out.println("Current election results for all polling places.");
            int totalVotes =  Utilities.getTotalVotesFromElection();

            if(election.getVotes().entrySet().size()==0){
                System.out.println("You have not added any polling place. " +
                        "Sorry! Nothing can be shown now.");
                return;
            }


            // TODO: show the current results
            System.out.println("NAME                          PARTY   VOTES     %");
            for (Map.Entry<Candidate, Integer> currentVote : election.getVotes().entrySet()) {
                System.out.printf("%-30s%-8s%5d%9.1f\n",
                        currentVote.getKey().getName(),
                        currentVote.getKey().getParty(),
                        currentVote.getValue(),
                        (currentVote.getValue() / (double) totalVotes) * 100);
            }
        }
    }

    // Called when R key is pressed from main menu.
    // Shows the current results of the election.
    private void perPollingPlaceResults() {
        // when the election is not closed,
        if (election.isOpenStill()) {
            System.out.println("The election is still open for votes.");
            System.out.println("You must close the election before viewing results.");
            return;
        }
        String pollingPlaceName = ValidInputReader.getValidString("Name of polling place:", "^[a-zA-Z0-9 ]+$");
        PollingPlace requiredPollingPlace=null;
        int totalVotes;
        List<Candidate> allCandidates=null;
        Candidate nextCandidate;
        Map<Candidate,Integer> candidateVotesMap = new LinkedHashMap<>();
        // when the polling place exists,
        for (PollingPlace currentPlace : addedPollingPlaces) {
            if (currentPlace.getName().equals(pollingPlaceName)) {
                System.out.println("Current election results for " + pollingPlaceName + ".");
                // TODO: show the current results for this polling place
                requiredPollingPlace = currentPlace;
                allCandidates = new ArrayList<>(requiredPollingPlace.getPriorityVotes().get(0).keySet());
                break;
            }
        }

        if(requiredPollingPlace!=null){
            for (Candidate currentCandidate: allCandidates){
                for (Vote currentVote: requiredPollingPlace.getVotes()) {
                    if(currentCandidate.isEliminated() &&
                            currentCandidate.getName().equals(currentVote.getPreferences().get(0))) {
                            nextCandidate = Utilities.getCandidateFromString(currentVote.
                                    getPreferences().get(1));
                        for (int counter=2;counter<currentVote.getPreferences().size() &&
                                nextCandidate.isEliminated();counter++)
                            nextCandidate = Utilities.
                                    getCandidateFromString(currentVote.getPreferences().
                                            get(counter));
                        requiredPollingPlace.getPriorityVotes().get(0).entrySet().
                                removeIf(candidateIntegerEntry ->
                                        candidateIntegerEntry.getKey().isEliminated());
                        candidateVotesMap.entrySet().removeIf(candidateIntegerEntry ->
                                candidateIntegerEntry.getKey().isEliminated());
                        candidateVotesMap.put(nextCandidate,1);
                        Utilities.mergeMapsAddingIntegerValues(candidateVotesMap,
                                requiredPollingPlace.getPriorityVotes().get(0));
                    }
                }
            }

            totalVotes = Utilities.getTotalVotesFromPollingPlace(requiredPollingPlace);
            System.out.println("NAME                          PARTY   VOTES     %");
            for (Map.Entry<Candidate, Integer> currentVote : requiredPollingPlace.getPriorityVotes().get(0).entrySet()) {
                if(!currentVote.getKey().isEliminated())
                System.out.printf("%-30s%-8s%5d%9.1f\n",
                        currentVote.getKey().getName(),
                        currentVote.getKey().getParty(),
                        currentVote.getValue(),
                        (currentVote.getValue() / (double) totalVotes) * 100);
            }
        }


        // when the polling place doesn't exist,
        if (requiredPollingPlace==null)
            System.out.println("No such polling place was found.");


    }

    // Called when E key is pressed from main menu.
    // Removes the candidate who has the fewest votes, and reallocates his/her
    // votes to the next available choice for those ballots.
    private void eliminate() {
        // when the election is not closed,
        if(election.isOpenStill()) {
            System.out.println("The election is still open for votes.");
            System.out.println("You must close the election before eliminating candidates.");
            return;
        }

        // when the election already has a winner,
        if(election.isMajority().size()>0) {
            System.out.println("A candidate already has a majority of the votes.");
            System.out.println("You cannot remove any more candidates.");
            return;
        }

        // when we can eliminate a candidate,
        if(election.isMajority().size()<1)
            System.out.println("Eliminating the lowest-ranked candidate.");

        // TODO: eliminate the candidate
        String eliminatedName =  election.eliminateCandidate(i);
        if(eliminatedName.length()<=0) {
            System.out.println("There is nobody to eliminate.");
            return;
        }
        System.out.println("Eliminated " + eliminatedName+".");

    }

    // This helper is just put into the text UI code to mark places where you
    // will need to add or modify this file.  Crashes with a runtime exception.
    private void crash(String message) {
        // Math.random() < 10 will always be true;  so why is it there?
        // I can't just throw because Eclipse will then warn about dead code
        // for any code that occurs after a call to crash().
        // So I wrap the exception throw in an "opaque predicate" to fool it.
        if (Math.random() < 10) {
            throw new RuntimeException("Not yet implemented: " + message);
        }
    }
}
