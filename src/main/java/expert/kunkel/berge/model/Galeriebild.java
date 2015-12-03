package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the galeriebild database table.
 * 
 */
@Entity
@NamedQuery(name="Galeriebild.findAll", query="SELECT g FROM Galeriebild g")
public class Galeriebild implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GaleriebildPK id;

	private Integer breite;

	private String dateiname;

	private Integer hoehe;

	private String titel;

	//bi-directional many-to-one association to Tour
	@ManyToOne
	@JoinColumn(name="id_tour")
	private Tour tour;

	public Galeriebild() {
	}

	public GaleriebildPK getId() {
		return this.id;
	}

	public void setId(GaleriebildPK id) {
		this.id = id;
	}

	public Integer getBreite() {
		return this.breite;
	}

	public void setBreite(Integer breite) {
		this.breite = breite;
	}

	public String getDateiname() {
		return this.dateiname;
	}

	public void setDateiname(String dateiname) {
		this.dateiname = dateiname;
	}

	public Integer getHoehe() {
		return this.hoehe;
	}

	public void setHoehe(Integer hoehe) {
		this.hoehe = hoehe;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Tour getTour() {
		return this.tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

}