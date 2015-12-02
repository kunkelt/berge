package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import org.postgresql.geometric.PGpolygon;


/**
 * The persistent class for the tourentag database table.
 * 
 */
@Entity
@NamedQuery(name="Tourentag.findAll", query="SELECT t FROM Tourentag t")
public class Tourentag implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TourentagPK id;

	private String beschreibung;

	private String bilddatei;

	private String bildtitel;

	@Temporal(TemporalType.DATE)
	private Date datum;

	private Double gehzeit;

	private Integer hmabstieg;

	private Integer hmaufstieg;

	private Integer region;

	private String schwierigkt;

	private PGpolygon track;

	public Tourentag() {
	}

	public TourentagPK getId() {
		return this.id;
	}

	public void setId(TourentagPK id) {
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

	public Integer getRegion() {
		return this.region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public String getSchwierigkt() {
		return this.schwierigkt;
	}

	public void setSchwierigkt(String schwierigkt) {
		this.schwierigkt = schwierigkt;
	}

	public PGpolygon getTrack() {
		return this.track;
	}

	public void setTrack(PGpolygon track) {
		this.track = track;
	}

}