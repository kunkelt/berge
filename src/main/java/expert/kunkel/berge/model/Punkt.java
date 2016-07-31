package expert.kunkel.berge.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

/**
 * The persistent class for the punkt database table.
 * 
 */
@Entity
@NamedQuery(name = "Punkt.findAll", query = "SELECT p FROM Punkt p")
public class Punkt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PUNKT_ID_GENERATOR", sequenceName = "ID_PUNKT", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PUNKT_ID_GENERATOR")
	private Integer id;

	private String beschreibung;

	private Integer hoehe;

	@Type(type = "org.hibernate.spatial.GeometryType")
	private Point lage;

	private String name;

	private String name2;

	@ManyToOne
	@JoinColumn(name = "typ")
	private Punkttyp typ;

	private String url;

	// bi-directional many-to-one association to Region
	@OneToMany(mappedBy = "punkt")
	private List<Region> regionen;

	// bi-directional many-to-one association to Tourabschnitt
	@OneToMany(mappedBy = "nachPunkt")
	private List<Tourabschnitt> nachTourabschnitt;

	// bi-directional many-to-one association to Tourabschnitt
	@OneToMany(mappedBy = "vonPunkt")
	private List<Tourabschnitt> vonTourabschnitt;

	public Punkt() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Integer getHoehe() {
		return hoehe;
	}

	public void setHoehe(Integer hoehe) {
		this.hoehe = hoehe;
	}

	public Point getLage() {
		return lage;
	}

	public void setLage(Point point) {
		lage = point;
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

	public List<Region> getRegionen() {
		return regionen;
	}

	public void setRegionen(List<Region> regionen) {
		this.regionen = regionen;
	}

	public Region addRegionen(Region regionen) {
		getRegionen().add(regionen);
		regionen.setPunkt(this);

		return regionen;
	}

	public Region removeRegionen(Region regionen) {
		getRegionen().remove(regionen);
		regionen.setPunkt(null);

		return regionen;
	}

	public List<Tourabschnitt> getNachTourabschnitt() {
		return nachTourabschnitt;
	}

	public void setNachTourabschnitt(List<Tourabschnitt> nachTourabschnitt) {
		this.nachTourabschnitt = nachTourabschnitt;
	}

	public Tourabschnitt addNachTourabschnitt(Tourabschnitt nachTourabschnitt) {
		getNachTourabschnitt().add(nachTourabschnitt);
		nachTourabschnitt.setNachPunkt(this);

		return nachTourabschnitt;
	}

	public Tourabschnitt removeNachTourabschnitt(Tourabschnitt nachTourabschnitt) {
		getNachTourabschnitt().remove(nachTourabschnitt);
		nachTourabschnitt.setNachPunkt(null);

		return nachTourabschnitt;
	}

	public List<Tourabschnitt> getVonTourabschnitt() {
		return vonTourabschnitt;
	}

	public void setVonTourabschnitt(List<Tourabschnitt> vonTourabschnitt) {
		this.vonTourabschnitt = vonTourabschnitt;
	}

	public Tourabschnitt addVonTourabschnitt(Tourabschnitt vonTourabschnitt) {
		getVonTourabschnitt().add(vonTourabschnitt);
		vonTourabschnitt.setVonPunkt(this);

		return vonTourabschnitt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beschreibung == null) ? 0 : beschreibung.hashCode());
		result = prime * result + ((hoehe == null) ? 0 : hoehe.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lage == null) ? 0 : lage.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((name2 == null) ? 0 : name2.hashCode());
		result = prime * result + ((typ == null) ? 0 : typ.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Punkt other = (Punkt) obj;
		if (beschreibung == null) {
			if (other.beschreibung != null) {
				return false;
			}
		} else if (!beschreibung.equals(other.beschreibung)) {
			return false;
		}
		if (hoehe == null) {
			if (other.hoehe != null) {
				return false;
			}
		} else if (!hoehe.equals(other.hoehe)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lage == null) {
			if (other.lage != null) {
				return false;
			}
		} else if (!lage.equals(other.lage)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (name2 == null) {
			if (other.name2 != null) {
				return false;
			}
		} else if (!name2.equals(other.name2)) {
			return false;
		}
		if (typ == null) {
			if (other.typ != null) {
				return false;
			}
		} else if (!typ.equals(other.typ)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}

	public Tourabschnitt removeVonTourabschnitt(Tourabschnitt vonTourabschnitt) {
		getVonTourabschnitt().remove(vonTourabschnitt);
		vonTourabschnitt.setVonPunkt(null);

		return vonTourabschnitt;
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
}