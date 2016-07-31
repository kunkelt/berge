package expert.kunkel.berge.dao;

import com.vividsolutions.jts.geom.Polygon;

import expert.kunkel.berge.util.StringUtils;

public class Karte implements java.io.Serializable {

    private static final long serialVersionUID = -6557092497742148417L;
    public enum Kartentyp {Analog, Digital};
    private int id;
    private String blattnummer;
    private String titel;
    private String untertitel;
    private Verlag verlag;
    private Polygon extent;
    private String massstab;
    private String isbn;
    private Kartentyp kartentyp;
    private int ausgabejahr;

    public int getAusgabejahr() {
        return ausgabejahr;
    }

    public void setAusgabejahr(int ausgabejahr) {
        this.ausgabejahr = ausgabejahr;
    }

    public String getBlattnummer() {
        return blattnummer;
    }

    public void setBlattnummer(String blattnummer) {
        this.blattnummer = blattnummer;
    }

    public Polygon getExtent() {
        return extent;
    }

    public void setExtent(Polygon extent) {
        this.extent = extent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAusgabejahrAsString() {
        if (ausgabejahr == 0) {
            return null;
        }
        return Integer.toString(ausgabejahr);
    }

    public void setAusgabejahr(String s) {
        if (s == null || s.equals("")) {
            this.ausgabejahr = 0;
        }
        else {
            this.ausgabejahr = Integer.parseInt(s);
        }
    }

    public Kartentyp getKartentyp() {
        return kartentyp;
    }

    public void setKartentyp(Kartentyp kartentyp) {
        this.kartentyp = kartentyp;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getUntertitel() {
        return untertitel;
    }

    public void setUntertitel(String untertitel) {
        this.untertitel = untertitel;
    }

    public Verlag getVerlag() {
        return verlag;
    }

    public void setVerlag(Verlag verlag) {
        this.verlag = verlag;
    }

    @Override
    public String toString() {
        String result = "" + titel;
        if (untertitel != null) {
            result += ", " + untertitel;
        }
        if (blattnummer != null) {
            result += "; Blatt " + blattnummer + " ";
        }
        if (massstab != null) {
            result += "; Maßstab " + massstab;
        }
        return result;
    }

    public String getMassstab() {
        return massstab;
    }

    public void setMassstab(String massstab) {
        this.massstab = massstab;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Karte other = (Karte) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.blattnummer == null) ? (other.blattnummer != null) : !this.blattnummer.equals(other.blattnummer)) {
            return false;
        }
        if ((this.titel == null) ? (other.titel != null) : !this.titel.equals(other.titel)) {
            return false;
        }
        if ((this.untertitel == null) ? (other.untertitel != null) : !this.untertitel.equals(other.untertitel)) {
            return false;
        }
        if (this.verlag != other.verlag && (this.verlag == null || !this.verlag.equals(other.verlag))) {
            return false;
        }
        if (this.extent != other.extent && (this.extent == null || !this.extent.equals(other.extent))) {
            return false;
        }
        if ((this.massstab == null) ? (other.massstab != null) : !this.massstab.equals(other.massstab)) {
            return false;
        }
        if ((this.isbn == null) ? (other.isbn != null) : !this.isbn.equals(other.isbn)) {
            return false;
        }
        if (this.kartentyp != other.kartentyp) {
            return false;
        }
        if (this.ausgabejahr != other.ausgabejahr) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + (this.blattnummer != null ? this.blattnummer.hashCode() : 0);
        hash = 67 * hash + (this.titel != null ? this.titel.hashCode() : 0);
        hash = 67 * hash + (this.untertitel != null ? this.untertitel.hashCode() : 0);
        hash = 67 * hash + (this.verlag != null ? this.verlag.hashCode() : 0);
        hash = 67 * hash + (this.extent != null ? this.extent.hashCode() : 0);
        hash = 67 * hash + (this.massstab != null ? this.massstab.hashCode() : 0);
        hash = 67 * hash + (this.isbn != null ? this.isbn.hashCode() : 0);
        hash = 67 * hash + this.kartentyp.hashCode();
        hash = 67 * hash + this.ausgabejahr;
        return hash;
    }


    
}
