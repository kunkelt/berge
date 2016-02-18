package expert.kunkel.berge.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the kartentyp database table.
 * 
 */
@Entity
@NamedQuery(name = "Kartentyp.findAll", query = "SELECT k FROM Kartentyp k")
public class Kartentyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "KARTENTYP_ID_GENERATOR", sequenceName = "ID_KARTENTYP", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KARTENTYP_ID_GENERATOR")
	private Integer id;

	private String typ;

	// bi-directional many-to-one association to Karte
	@OneToMany(mappedBy = "kartentyp")
	private List<Karte> karten;

	public Kartentyp() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public List<Karte> getKarten() {
		return karten;
	}

	public void setKarten(List<Karte> karten) {
		this.karten = karten;
	}

	public Karte addKarten(Karte karten) {
		getKarten().add(karten);
		karten.setKartentyp(this);

		return karten;
	}

	public Karte removeKarten(Karte karten) {
		getKarten().remove(karten);
		karten.setKartentyp(null);

		return karten;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((typ == null) ? 0 : typ.hashCode());
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
		Kartentyp other = (Kartentyp) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (typ == null) {
			if (other.typ != null) {
				return false;
			}
		} else if (!typ.equals(other.typ)) {
			return false;
		}
		return true;
	}

}