package expert.kunkel.berge.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the tour database table.
 * 
 */
@Entity
@NamedQuery(name = "Tour.findAll", query = "SELECT t FROM Tour t")
public class Tour implements Serializable, Comparable<Tour> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TOUR_ID_GENERATOR", sequenceName = "ID_TOUR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOUR_ID_GENERATOR")
	private Integer id;

	private String beschreibung;

	private Boolean geplant;

	private String link;

	private String name;

	private String zusatzinfo;

	// bi-directional many-to-one association to Galeriebild
	@OneToMany(mappedBy = "tour")
	private List<Galeriebild> galeriebilder;

	// bi-directional many-to-many association to Karte
	@ManyToMany
	@JoinTable(name = "tour_karte", joinColumns = { @JoinColumn(name = "id_tour") }, inverseJoinColumns = { @JoinColumn(name = "id_karte") })
	private List<Karte> karten;

	// bi-directional many-to-one association to Tourentag
	@OneToMany(mappedBy = "tour")
	private List<Tourentag> tourentage = new ArrayList<Tourentag>();

	public Tour() {
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

	public Boolean getGeplant() {
		return geplant;
	}

	public void setGeplant(Boolean geplant) {
		this.geplant = geplant;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZusatzinfo() {
		return zusatzinfo;
	}

	public void setZusatzinfo(String zusatzinfo) {
		this.zusatzinfo = zusatzinfo;
	}

	public List<Galeriebild> getGaleriebilder() {
		return galeriebilder;
	}

	public void setGaleriebilder(List<Galeriebild> galeriebilder) {
		this.galeriebilder = galeriebilder;
	}

	public Galeriebild addGaleriebilder(Galeriebild galeriebilder) {
		getGaleriebilder().add(galeriebilder);
		galeriebilder.setTour(this);

		return galeriebilder;
	}

	public Galeriebild removeGaleriebilder(Galeriebild galeriebilder) {
		getGaleriebilder().remove(galeriebilder);
		galeriebilder.setTour(null);

		return galeriebilder;
	}

	public List<Karte> getKarten() {
		return karten;
	}

	public void setKarten(List<Karte> karten) {
		this.karten = karten;
	}

	public List<Tourentag> getTourentage() {
		return tourentage;
	}

	public void setTourentage(List<Tourentag> tourentage) {
		this.tourentage = tourentage;
	}

	public Tourentag addTourentage(Tourentag tourentage) {
		getTourentage().add(tourentage);
		tourentage.setTour(this);

		return tourentage;
	}

	public Tourentag removeTourentage(Tourentag tourentage) {
		getTourentage().remove(tourentage);
		tourentage.setTour(null);

		return tourentage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beschreibung == null) ? 0 : beschreibung.hashCode());
		result = prime * result + ((geplant == null) ? 0 : geplant.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((zusatzinfo == null) ? 0 : zusatzinfo.hashCode());
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
		Tour other = (Tour) obj;
		if (beschreibung == null) {
			if (other.beschreibung != null) {
				return false;
			}
		} else if (!beschreibung.equals(other.beschreibung)) {
			return false;
		}
		if (geplant == null) {
			if (other.geplant != null) {
				return false;
			}
		} else if (!geplant.equals(other.geplant)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (link == null) {
			if (other.link != null) {
				return false;
			}
		} else if (!link.equals(other.link)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (zusatzinfo == null) {
			if (other.zusatzinfo != null) {
				return false;
			}
		} else if (!zusatzinfo.equals(other.zusatzinfo)) {
			return false;
		}
		return true;
	}

	public boolean hasCompleteTourenabschnitte() throws ClassNotFoundException,
			SQLException {
		boolean complete = true;
		for (Tourentag tag : getTourentage()) {
			if (tag.getTourabschnitte().isEmpty()) {
				complete = false;
			}
		}
		return complete;
	}

	@Override
	public int compareTo(Tour t2) {
		Tour t1 = this;

		if (t2 == null || t2.getTourentage().isEmpty()) {
			return 1;
		}

		if (t1.getTourentage().isEmpty()) {
			return -1;
		}

		return t1.getTourentage().get(0).compareTo(t2.getTourentage().get(0));
	}
}