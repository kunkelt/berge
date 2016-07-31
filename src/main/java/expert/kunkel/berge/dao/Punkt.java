package expert.kunkel.berge.dao;

import com.vividsolutions.jts.geom.Point;

import expert.kunkel.berge.util.StringUtils;

public class Punkt implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7349080382845916304L;
    private int id;
    private String name;
    private String name2;
    private String beschreibung;
    private int hoehe;
    private Point lage;
    private Punkttyp typ;
    private String url;

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getHoehe() {
        return hoehe;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getLage() {
        return lage;
    }

    public void setLage(Point lage) {
        this.lage = lage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public Punkttyp getTyp() {
        return typ;
    }

    public void setTyp(Punkttyp typ) {
        this.typ = typ;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        String result = "" + name;
        if (name2 != null) {
            result += " (" + name2 + ")";
        }
        result += ", " + hoehe + "m";
        if (getTyp() != null) {
            result += " (" + getTyp().getName() + ")";
        }
        return result;
    }

    public int compareTo(Object o) {
        if (!(o instanceof Punkt)) {
            return 1;
        }

        Punkt p = (Punkt) o;

        return this.getName().compareTo(p.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Punkt other = (Punkt) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.name2 == null) ? (other.name2 != null) : !this.name2.equals(other.name2)) {
            return false;
        }
        if ((this.beschreibung == null) ? (other.beschreibung != null) : !this.beschreibung.equals(other.beschreibung)) {
            return false;
        }
        if (this.hoehe != other.hoehe) {
            return false;
        }
        if (this.lage != other.lage && (this.lage == null || !this.lage.equals(other.lage))) {
            return false;
        }
        if (this.typ != other.typ && (this.typ == null || !this.typ.equals(other.typ))) {
            return false;
        }
        if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 41 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
        hash = 41 * hash + (this.beschreibung != null ? this.beschreibung.hashCode() : 0);
        hash = 41 * hash + this.hoehe;
        hash = 41 * hash + (this.lage != null ? this.lage.hashCode() : 0);
        hash = 41 * hash + (this.typ != null ? this.typ.hashCode() : 0);
        hash = 41 * hash + (this.url != null ? this.url.hashCode() : 0);
        return hash;
    }
}
