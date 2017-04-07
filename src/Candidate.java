/**
 * Created by Rakshith on 4/6/2017.
 */
public abstract class Candidate implements Cloneable{

    private String name;
    private boolean isEliminated;
    private Party partyName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEliminated() {
        return isEliminated;
    }

    public void setEliminated(boolean eliminated) {
        isEliminated = eliminated;
    }

    public Party getPartyName() {
        return partyName;
    }

    public void setPartyName(Party partyName) {
        this.partyName = partyName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj!=null && getClass()==obj.getClass()) {
            Candidate candidate = (Candidate) obj;
            return (this.name.equals(candidate.name));
        }
        return false;
    }

    @Override
    public Candidate clone() {
        try{
           Candidate candidateCopy = (Candidate)super.clone();
           candidateCopy.partyName=this.partyName;
           return candidateCopy;
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int hashCode() {
        int a = 31 * name.hashCode();
        a = ((isEliminated) ? 1231 : 1237) * a;
        return a;
    }

    @Override
    public String toString() {
        return "Candidate name is: "+ name + " who is "+
                (isEliminated?" Eliminated. ":" Not Eliminated.");
    }
}
