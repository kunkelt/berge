package expert.kunkel.berge.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Polygon;

/**
 * The persistent class for the tourentag database table.
 * 
 */
@Entity
@NamedQuery(name = "Tourentag.findAll", query = "SELECT t FROM Tourentag t")
public class Tourentag implements Serializable, Comparable<Tourentag> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TOURENTAG_ID_GENERATOR", sequenceName = "ID_TOURENTAG", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOURENTAG_ID_GENERATOR")
	private Integer id;

	private String beschreibung;

	private String bilddatei;

	private String bildtitel;

	@Temporal(TemporalType.DATE)
	private Date datum;

	private Double gehzeit;

	private Integer hmabstieg;

	private Integer hmaufstieg;

	private String schwierigkt;

	private Integer tag;

	@Type(type = "org.hibernate.spatial.GeometryType")
	private Polygon track;

	// bi-directional many-to-one association to Tourabschnitt
	@OneToMany(mappedBy = "tourentag")
	private List<Tourabschnitt> tourabschnitte;

	// bi-directional many-to-one association to Region
	@ManyToOne
	@JoinColumn(name = "region")
	private Region region;

	// bi-directional many-to-one association to Tour
	@ManyToOne
	@JoinColumn(name = "id_tour")
	private Tour tour;

	public Tourentag() {
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

	public String getBilddatei() {
		return bilddatei;
	}

	public void setBilddatei(String bilddatei) {
		this.bilddatei = bilddatei;
	}

	public String getBildtitel() {
		return bildtitel;
	}

	public void setBildtitel(String bildtitel) {
		this.bildtitel = bildtitel;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Double getGehzeit() {
		return gehzeit;
	}

	public void setGehzeit(Double gehzeit) {
		this.gehzeit = gehzeit;
	}

	public Integer getHmabstieg() {
		return hmabstieg;
	}

	public void setHmabstieg(Integer hmabstieg) {
		this.hmabstieg = hmabstieg;
	}

	public Integer getHmaufstieg() {
		return hmaufstieg;
	}

	public void setHmaufstieg(Integer hmaufstieg) {
		this.hmaufstieg = hmaufstieg;
	}

	public String getSchwierigkt() {
		return schwierigkt;
	}

	public void setSchwierigkt(String schwierigkt) {
		this.schwierigkt = schwierigkt;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public Polygon getTrack() {
		return track;
	}

	public void setTrack(Polygon track) {
		this.track = track;
	}

	public List<Tourabschnitt> getTourabschnitte() {
		return tourabschnitte;
	}

	public void setTourabschnitte(List<Tourabschnitt> tourabschnitte) {
		this.tourabschnitte = tourabschnitte;
	}

	public Tourabschnitt addTourabschnitte(Tourabschnitt tourabschnitte) {
		getTourabschnitte().add(tourabschnitte);
		tourabschnitte.setTourentag(this);

		return tourabschnitte;
	}

	public Tourabschnitt removeTourabschnitte(Tourabschnitt tourabschnitte) {
		getTourabschnitte().remove(tourabschnitte);
		tourabschnitte.setTourentag(null);

		return tourabschnitte;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Tour getTour() {
		return tour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beschreibung == null) ? 0 : beschreibung.hashCode());
		result = prime * result
				+ ((bilddatei == null) ? 0 : bilddatei.hashCode());
		result = prime * result
				+ ((bildtitel == null) ? 0 : bildtitel.hashCode());
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + ((gehzeit == null) ? 0 : gehzeit.hashCode());
		result = prime * result
				+ ((hmabstieg == null) ? 0 : hmabstieg.hashCode());
		result = prime * result
				+ ((hmaufstieg == null) ? 0 : hmaufstieg.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result
				+ ((schwierigkt == null) ? 0 : schwierigkt.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result
				+ ((tourabschnitte == null) ? 0 : tourabschnitte.hashCode());
		result = prime * result + ((track == null) ? 0 : track.hashCode());
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
		Tourentag other = (Tourentag) obj;
		if (beschreibung == null) {
			if (other.beschreibung != null) {
				return false;
			}
		} else if (!beschreibung.equals(other.beschreibung)) {
			return false;
		}
		if (bilddatei == null) {
			if (other.bilddatei != null) {
				return false;
			}
		} else if (!bilddatei.equals(other.bilddatei)) {
			return false;
		}
		if (bildtitel == null) {
			if (other.bildtitel != null) {
				return false;
			}
		} else if (!bildtitel.equals(other.bildtitel)) {
			return false;
		}
		if (datum == null) {
			if (other.datum != null) {
				return false;
			}
		} else if (!datum.equals(other.datum)) {
			return false;
		}
		if (gehzeit == null) {
			if (other.gehzeit != null) {
				return false;
			}
		} else if (!gehzeit.equals(other.gehzeit)) {
			return false;
		}
		if (hmabstieg == null) {
			if (other.hmabstieg != null) {
				return false;
			}
		} else if (!hmabstieg.equals(other.hmabstieg)) {
			return false;
		}
		if (hmaufstieg == null) {
			if (other.hmaufstieg != null) {
				return false;
			}
		} else if (!hmaufstieg.equals(other.hmaufstieg)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (region == null) {
			if (other.region != null) {
				return false;
			}
		} else if (!region.equals(other.region)) {
			return false;
		}
		if (schwierigkt == null) {
			if (other.schwierigkt != null) {
				return false;
			}
		} else if (!schwierigkt.equals(other.schwierigkt)) {
			return false;
		}
		if (tag == null) {
			if (other.tag != null) {
				return false;
			}
		} else if (!tag.equals(other.tag)) {
			return false;
		}
		if (tourabschnitte == null) {
			if (other.tourabschnitte != null) {
				return false;
			}
		} else if (!tourabschnitte.equals(other.tourabschnitte)) {
			return false;
		}
		if (track == null) {
			if (other.track != null) {
				return false;
			}
		} else if (!track.equals(other.track)) {
			return false;
		}
		return true;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public String getGehzeitAsString() {
		if (gehzeit != null) {
			return gehzeit.toString();
		}
		return null;
	}

	public String getHmAbstiegAsString() {
		if (hmabstieg != null) {
			return hmabstieg.toString();
		}
		return null;
	}

	public String getHmAufstiegAsString() {
		if (hmaufstieg != null) {
			return hmaufstieg.toString();
		}
		return null;
	}

	@Override
	public int compareTo(Tourentag o) {
		if (datum == null) {
			return -1;
		}
		if (o == null || o.datum == null) {
			return 1;
		}
		return o.datum.compareTo(datum);
	}
}