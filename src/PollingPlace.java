import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakshith on 4/8/2017.
 */
public class PollingPlace {
    private String name;
    private List<Vote> votes;

    public PollingPlace(String name) {
        if(name.length()<=0)
            throw new IllegalArgumentException("Enter a proper polling place");
        this.name = name;
        votes=new ArrayList<>();
    }

    /**
     * Reads the appropriate votes from the ballot and
     * creates a new Vote by calling addVote method.
     * @return true when successfully read.
     */
    public  boolean readVotes(){
        //Get the correct file name from the argument passed.
        String actualFileName = "ballots-"+name.toLowerCase()+".txt";

        try {
            Scanner scanner = new Scanner(new File(actualFileName)).useDelimiter("\n");
            while (scanner.hasNext()){
                String[] preferences = scanner.next().split(",");
                addVote(preferences);
            }
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Sorry! No such File found.");
        }
        return false;
    }

    /**
     * Adds a new Vote from the list of preferences.
     * @param preferences array of preferred candidates
     *                    from highest to lowest preferred.
     */
    public void addVote(String[] preferences) {
        votes.add(new Vote(preferences));
    }

    public void displayVotes(){
        for (Vote currentVote: votes)
            System.out.println(currentVote);
    }

    public String getName() {
        return name;
    }
}
