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

import org.postgis.PGgeometry;

/**
 * The persistent class for the region database table.
 * 
 */
@Entity
@NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r")
public class Region implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extent == null) ? 0 : extent.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((punkt == null) ? 0 : punkt.hashCode());
		result = prime * result
				+ ((umgrenzung == null) ? 0 : umgrenzung.hashCode());
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
		Region other = (Region) obj;
		if (extent == null) {
			if (other.extent != null) {
				return false;
			}
		} else if (!extent.equals(other.extent)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (punkt == null) {
			if (other.punkt != null) {
				return false;
			}
		} else if (!punkt.equals(other.punkt)) {
			return false;
		}
		if (umgrenzung == null) {
			if (other.umgrenzung != null) {
				return false;
			}
		} else if (!umgrenzung.equals(other.umgrenzung)) {
			return false;
		}
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "REGION_ID_GENERATOR", sequenceName = "ID_REGION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGION_ID_GENERATOR")
	private Integer id;

	private PGgeometry extent;

	private String name;

	private String umgrenzung;

	// bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name = "hoechster_punkt")
	private Punkt punkt;

	// bi-directional many-to-one association to Region
	@ManyToOne
	@JoinColumn(name = "oberregion")
	private Region oberregion;

	// bi-directional many-to-one association to Region
	@OneToMany(mappedBy = "oberregion")
	private List<Region> regionen;

	// bi-directional many-to-one association to Tourentag
	@OneToMany(mappedBy = "region")
	private List<Tourentag> tourentage;

	public Region() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PGgeometry getExtent() {
		return extent;
	}

	public void setExtent(PGgeometry extent) {
		this.extent = extent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUmgrenzung() {
		return umgrenzung;
	}

	public void setUmgrenzung(String umgrenzung) {
		this.umgrenzung = umgrenzung;
	}

	public Punkt getPunkt() {
		return punkt;
	}

	public void setPunkt(Punkt punkt) {
		this.punkt = punkt;
	}

	public Region getOberregion() {
		return oberregion;
	}

	public void setOberregion(Region oberregion) {
		this.oberregion = oberregion;
	}

	public List<Region> getRegionen() {
		return regionen;
	}

	public void setRegionen(List<Region> regionen) {
		this.regionen = regionen;
	}

	public Region addRegionen(Region regionen) {
		getRegionen().add(regionen);
		regionen.setOberregion(this);

		return regionen;
	}

	public Region removeRegionen(Region regionen) {
		getRegionen().remove(regionen);
		regionen.setOberregion(null);

		return regionen;
	}

	public List<Tourentag> getTourentage() {
		return tourentage;
	}

	public void setTourentage(List<Tourentag> tourentage) {
		this.tourentage = tourentage;
	}

	public Tourentag addTourentage(Tourentag tourentage) {
		getTourentage().add(tourentage);
		tourentage.setRegion(this);

		return tourentage;
	}

	public Tourentag removeTourentage(Tourentag tourentage) {
		getTourentage().remove(tourentage);
		tourentage.setRegion(null);

		return tourentage;
	}

}