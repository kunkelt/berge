package expert.kunkel.berge.dao;


public class Tourabschnitt implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5545127832949683992L;
    
    private Tourentag ttag;
    private int sequenz;
    private Punkt vonPunkt;
    private Punkt nachPunkt;

    public int getSequenz() {
        return sequenz;
    }

    public void setSequenz(int sequenz) {
        this.sequenz = sequenz;
    }


    public Punkt getNachPunkt() {
        return nachPunkt;
    }

    public void setNachPunkt(Punkt nachPunkt) {
        this.nachPunkt = nachPunkt;
    }

    public Tourentag getTtag() {
        return ttag;
    }

    public void setTtag(Tourentag ttag) {
        this.ttag = ttag;
    }

    public Punkt getVonPunkt() {
        return vonPunkt;
    }

    public void setVonPunkt(Punkt vonPunkt) {
        this.vonPunkt = vonPunkt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tourabschnitt other = (Tourabschnitt) obj;
        if (this.ttag != other.ttag && (this.ttag == null || !this.ttag.equals(other.ttag))) {
            return false;
        }
        if (this.sequenz != other.sequenz) {
            return false;
        }
        if (this.vonPunkt != other.vonPunkt && (this.vonPunkt == null || !this.vonPunkt.equals(other.vonPunkt))) {
            return false;
        }
        if (this.nachPunkt != other.nachPunkt && (this.nachPunkt == null || !this.nachPunkt.equals(other.nachPunkt))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.ttag != null ? this.ttag.hashCode() : 0);
        hash = 53 * hash + this.sequenz;
        hash = 53 * hash + (this.vonPunkt != null ? this.vonPunkt.hashCode() : 0);
        hash = 53 * hash + (this.nachPunkt != null ? this.nachPunkt.hashCode() : 0);

        return hash;
    }

    
    @Override
    public String toString() {
        return ttag.getTour().getId() + ";" + ttag.getTag() + ";" + sequenz;
    }
}
