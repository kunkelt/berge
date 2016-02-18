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
 * The persistent class for the verlag database table.
 * 
 */
@Entity
@NamedQuery(name = "Verlag.findAll", query = "SELECT v FROM Verlag v")
public class Verlag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "VERLAG_ID_GENERATOR", sequenceName = "ID_VERLAG", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERLAG_ID_GENERATOR")
	private Integer id;

	private String anschrift;

	private String bezugsquelle;

	private String name;

	private String telefon;

	// bi-directional many-to-one association to Karte
	@OneToMany(mappedBy = "verlag")
	private List<Karte> karten;

	public Verlag() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public List<Karte> getKarten() {
		return karten;
	}

	public void setKarten(List<Karte> karten) {
		this.karten = karten;
	}

	public Karte addKarten(Karte karten) {
		getKarten().add(karten);
		karten.setVerlag(this);

		return karten;
	}

	public Karte removeKarten(Karte karten) {
		getKarten().remove(karten);
		karten.setVerlag(null);

		return karten;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((anschrift == null) ? 0 : anschrift.hashCode());
		result = prime * result
				+ ((bezugsquelle == null) ? 0 : bezugsquelle.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((karten == null) ? 0 : karten.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((telefon == null) ? 0 : telefon.hashCode());
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
		Verlag other = (Verlag) obj;
		if (anschrift == null) {
			if (other.anschrift != null) {
				return false;
			}
		} else if (!anschrift.equals(other.anschrift)) {
			return false;
		}
		if (bezugsquelle == null) {
			if (other.bezugsquelle != null) {
				return false;
			}
		} else if (!bezugsquelle.equals(other.bezugsquelle)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (karten == null) {
			if (other.karten != null) {
				return false;
			}
		} else if (!karten.equals(other.karten)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (telefon == null) {
			if (other.telefon != null) {
				return false;
			}
		} else if (!telefon.equals(other.telefon)) {
			return false;
		}
		return true;
	}

}