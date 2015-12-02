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

	private Double breite;

	private String dateiname;

	private Double hoehe;

	private String titel;

	public Galeriebild() {
	}

	public GaleriebildPK getId() {
		return this.id;
	}

	public void setId(GaleriebildPK id) {
		this.id = id;
	}

	public Double getBreite() {
		return this.breite;
	}

	public void setBreite(Double breite) {
		this.breite = breite;
	}

	public String getDateiname() {
		return this.dateiname;
	}

	public void setDateiname(String dateiname) {
		this.dateiname = dateiname;
	}

	public Double getHoehe() {
		return this.hoehe;
	}

	public void setHoehe(Double hoehe) {
		this.hoehe = hoehe;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

}