package expert.kunkel.berge.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import org.postgis.PGgeometry;

/**
 * The persistent class for the karte database table.
 * 
 */
@Entity
@NamedQuery(name = "Karte.findAll", query = "SELECT k FROM Karte k")
public class Karte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "KARTE_ID_GENERATOR", sequenceName = "ID_KARTE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KARTE_ID_GENERATOR")
	private Integer id;

	private Integer ausgabejahr;

	private String blattnummer;

	private PGgeometry extent;

	private String isbn;

	private String massstab;

	private String titel;

	private String untertitel;

	// bi-directional many-to-one association to Kartentyp
	@ManyToOne
	@JoinColumn(name = "kartentyp")
	private Kartentyp kartentyp;

	// bi-directional many-to-one association to Verlag
	@ManyToOne
	@JoinColumn(name = "verlag")
	private Verlag verlag;

	// bi-directional many-to-many association to Tour
	@ManyToMany(mappedBy = "karten")
	private List<Tour> touren;

	public Karte() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAusgabejahr() {
		return ausgabejahr;
	}

	public void setAusgabejahr(Integer ausgabejahr) {
		this.ausgabejahr = ausgabejahr;
	}

	public String getBlattnummer() {
		return blattnummer;
	}

	public void setBlattnummer(String blattnummer) {
		this.blattnummer = blattnummer;
	}

	public PGgeometry getExtent() {
		return extent;
	}

	public void setExtent(PGgeometry extent) {
		this.extent = extent;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getMassstab() {
		return massstab;
	}

	public void setMassstab(String massstab) {
		this.massstab = massstab;
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

	public Kartentyp getKartentyp() {
		return kartentyp;
	}

	public void setKartentyp(Kartentyp kartentyp) {
		this.kartentyp = kartentyp;
	}

	public Verlag getVerlag() {
		return verlag;
	}

	public void setVerlag(Verlag verlag) {
		this.verlag = verlag;
	}

	public List<Tour> getTouren() {
		return touren;
	}

	public void setTouren(List<Tour> touren) {
		this.touren = touren;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ausgabejahr == null) ? 0 : ausgabejahr.hashCode());
		result = prime * result
				+ ((blattnummer == null) ? 0 : blattnummer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result
				+ ((massstab == null) ? 0 : massstab.hashCode());
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
		result = prime * result
				+ ((untertitel == null) ? 0 : untertitel.hashCode());
		result = prime * result + ((verlag == null) ? 0 : verlag.hashCode());
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
		Karte other = (Karte) obj;
		if (ausgabejahr == null) {
			if (other.ausgabejahr != null) {
				return false;
			}
		} else if (!ausgabejahr.equals(other.ausgabejahr)) {
			return false;
		}
		if (blattnummer == null) {
			if (other.blattnummer != null) {
				return false;
			}
		} else if (!blattnummer.equals(other.blattnummer)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (isbn == null) {
			if (other.isbn != null) {
				return false;
			}
		} else if (!isbn.equals(other.isbn)) {
			return false;
		}
		if (massstab == null) {
			if (other.massstab != null) {
				return false;
			}
		} else if (!massstab.equals(other.massstab)) {
			return false;
		}
		if (titel == null) {
			if (other.titel != null) {
				return false;
			}
		} else if (!titel.equals(other.titel)) {
			return false;
		}
		if (untertitel == null) {
			if (other.untertitel != null) {
				return false;
			}
		} else if (!untertitel.equals(other.untertitel)) {
			return false;
		}
		if (verlag == null) {
			if (other.verlag != null) {
				return false;
			}
		} else if (!verlag.equals(other.verlag)) {
			return false;
		}
		return true;
	}

}