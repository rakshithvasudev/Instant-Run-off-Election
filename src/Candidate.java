/**
 * Created by Rakshith on 4/6/2017.
 * This class is responsible for having common
 * characteristics for candidates. This is made abstract
 * in order to allow this to be used by the CandidateFactory class.
 * Implements cloneable and comparable to allow cloning and comparing
 * mechanisms to be done.
 */
public abstract class Candidate implements Cloneable, Comparable {

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

    /**
     * compares if the current object is same as the
     * passed object. Looks for the name and party specifically.
     *
     * @param obj object to be compared.
     * @return true if the objects are the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            Candidate candidate = (Candidate) obj;
            return (this.name.equals(candidate.name) && this.party.equals(candidate.getParty()));
        }
        return false;
    }

    @Override
    public Candidate clone() {
        try {
            Candidate candidateCopy = (Candidate) super.clone();
            candidateCopy.party = this.party;
            return candidateCopy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int hashCode() {
        int a = 31 * name.hashCode();
        a = party.hashCode() * a;
        return a;
    }

    @Override
    public String toString() {
        return name;
    }


    /**
     * Compares by name.
     *
     * @param o other Object
     * @return >1 if this candidate has an
     * alphabetically larger name than compared name.
     * or 0 if they're equal.
     * or <1 if this is smaller than other.
     * Ex: Raphal > Obama (R>O).
     */
    @Override
    public int compareTo(Object o) {
        Candidate other = (Candidate) o;
        return this.name.compareTo(other.name);
    }
}
