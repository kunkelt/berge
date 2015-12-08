package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import org.postgresql.geometric.PGpolygon;
import java.util.List;


/**
 * The persistent class for the tourentag database table.
 * 
 */
@Entity
@NamedQuery(name="Tourentag.findAll", query="SELECT t FROM Tourentag t")
public class Tourentag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TOURENTAG_ID_GENERATOR", sequenceName="ID_TOURENTAG", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOURENTAG_ID_GENERATOR")
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

	private PGpolygon track;

	//bi-directional many-to-one association to Tourabschnitt
	@OneToMany(mappedBy="tourentag")
	private List<Tourabschnitt> tourabschnitte;

	//bi-directional many-to-one association to Region
	@ManyToOne
	@JoinColumn(name="region")
	private Region region;

	//bi-directional many-to-one association to Tour
	@ManyToOne
	@JoinColumn(name="id_tour")
	private Tour tour;

	public Tourentag() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBeschreibung() {
		return this.beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getBilddatei() {
		return this.bilddatei;
	}

	public void setBilddatei(String bilddatei) {
		this.bilddatei = bilddatei;
	}

	public String getBildtitel() {
		return this.bildtitel;
	}

	public void setBildtitel(String bildtitel) {
		this.bildtitel = bildtitel;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Double getGehzeit() {
		return this.gehzeit;
	}

	public void setGehzeit(Double gehzeit) {
		this.gehzeit = gehzeit;
	}

	public Integer getHmabstieg() {
		return this.hmabstieg;
	}

	public void setHmabstieg(Integer hmabstieg) {
		this.hmabstieg = hmabstieg;
	}

	public Integer getHmaufstieg() {
		return this.hmaufstieg;
	}

	public void setHmaufstieg(Integer hmaufstieg) {
		this.hmaufstieg = hmaufstieg;
	}

	public String getSchwierigkt() {
		return this.schwierigkt;
	}

	public void setSchwierigkt(String schwierigkt) {
		this.schwierigkt = schwierigkt;
	}

	public Integer getTag() {
		return this.tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public PGpolygon getTrack() {
		return this.track;
	}

	public void setTrack(PGpolygon track) {
		this.track = track;
	}

	public List<Tourabschnitt> getTourabschnitte() {
		return this.tourabschnitte;
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
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Tour getTour() {
		return this.tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

}