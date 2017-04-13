/**
 * Created by Rakshith on 4/6/2017.
 */
public abstract class Candidate implements Cloneable, Comparable{

    private String name;
    private boolean isEliminated;
    private Party party;

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

    public Party getParty() {
        return party;
    }

    public void setParty(Party partyName) {
        this.party = partyName;
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
           candidateCopy.party=this.party;
           return candidateCopy;
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int hashCode() {
        int a = 31 * name.hashCode();
        a=party.hashCode()*a;
//        a = ((isEliminated) ? 1231 : 1237) * a;
        return a;
    }

    @Override
    public String toString() {
//        return "Candidate name is: "+ name + " who is "+
//                (isEliminated?" Eliminated. ":" Not Eliminated.");
    return name;
    }


    /**
     * Compares by name.
     * @param o other Object
     * @return >1 if this candidate has an
     * alphabetically larger name than compared name.
     * or 0 if they're equal.
     * or <1 if this is smaller than other.
     * Ex: Raphal > Obama (R>O).
     */
    @Override
    public int compareTo(Object o) {
        Candidate other = (Candidate)o;
        return this.name.compareTo(other.name);
    }
}
