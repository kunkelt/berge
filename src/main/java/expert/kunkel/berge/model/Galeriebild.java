package expert.kunkel.berge.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the galeriebild database table.
 * 
 */
@Entity
@NamedQuery(name = "Galeriebild.findAll", query = "SELECT g FROM Galeriebild g")
public class Galeriebild implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "GALERIEBILD_ID_GENERATOR", sequenceName = "ID_GALERIEBILD", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GALERIEBILD_ID_GENERATOR")
	private Integer id;

	private Integer breite;

	private String dateiname;

	private Integer hoehe;

	private Integer sequenz;

	private String titel;

	// bi-directional many-to-one association to Tour
	@ManyToOne
	@JoinColumn(name = "id_tour")
	private Tour tour;

	public Galeriebild() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBreite() {
		return breite;
	}

	public void setBreite(Integer breite) {
		this.breite = breite;
	}

	public String getDateiname() {
		return dateiname;
	}

	public void setDateiname(String dateiname) {
		this.dateiname = dateiname;
	}

	public Integer getHoehe() {
		return hoehe;
	}

	public void setHoehe(Integer hoehe) {
		this.hoehe = hoehe;
	}

	public Integer getSequenz() {
		return sequenz;
	}

	public void setSequenz(Integer sequenz) {
		this.sequenz = sequenz;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breite == null) ? 0 : breite.hashCode());
		result = prime * result
				+ ((dateiname == null) ? 0 : dateiname.hashCode());
		result = prime * result + ((hoehe == null) ? 0 : hoehe.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sequenz == null) ? 0 : sequenz.hashCode());
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
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
		Galeriebild other = (Galeriebild) obj;
		if (breite == null) {
			if (other.breite != null) {
				return false;
			}
		} else if (!breite.equals(other.breite)) {
			return false;
		}
		if (dateiname == null) {
			if (other.dateiname != null) {
				return false;
			}
		} else if (!dateiname.equals(other.dateiname)) {
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
		if (sequenz == null) {
			if (other.sequenz != null) {
				return false;
			}
		} else if (!sequenz.equals(other.sequenz)) {
			return false;
		}
		if (titel == null) {
			if (other.titel != null) {
				return false;
			}
		} else if (!titel.equals(other.titel)) {
			return false;
		}
		return true;
	}

}