import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakshith on 4/6/2017.
 */
public class Vote {

    private List<String> preferences;

    public Vote(String... args) {
        preferences=new ArrayList<>();
        for (String currentString:args) {
            preferences.add(currentString);
        }

        System.out.println(preferences);
    }
}

