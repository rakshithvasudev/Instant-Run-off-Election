/**
 * This is a custom Exception built and it's
 * used when there is an Illegal Vote.
 * Mostly used when the preferences count is less than
 * the size of the registered candidates.
 * Created by Rakshith on 4/8/2017.
 */
public class IllegalVoteCastException extends RuntimeException{

    public IllegalVoteCastException(String exceptionMessage){
        super(exceptionMessage);
    }

}
