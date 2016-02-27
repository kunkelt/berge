/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expert.kunkel.berge.dao;

/**
 *
 * @author thorsten
 */
public class Verlag {

    private int id;
    private String name;
    private String anschrift;
    private String telefon;
    private String bezugsquelle;

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getBezugsquelle() {
        return bezugsquelle;
    }

    public void setBezugsquelle(String bezugsquelle) {
        this.bezugsquelle = bezugsquelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Verlag other = (Verlag) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.anschrift == null) ? (other.anschrift != null) : !this.anschrift.equals(other.anschrift)) {
            return false;
        }
        if ((this.telefon == null) ? (other.telefon != null) : !this.telefon.equals(other.telefon)) {
            return false;
        }
        if ((this.bezugsquelle == null) ? (other.bezugsquelle != null) : !this.bezugsquelle.equals(other.bezugsquelle)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.anschrift != null ? this.anschrift.hashCode() : 0);
        hash = 89 * hash + (this.telefon != null ? this.telefon.hashCode() : 0);
        hash = 89 * hash + (this.bezugsquelle != null ? this.bezugsquelle.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
