package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the galeriebild database table.
 * 
 */
@Embeddable
public class GaleriebildPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_tour", insertable=false, updatable=false)
	private Integer idTour;

	private Integer sequenz;

	public GaleriebildPK() {
	}
	public Integer getIdTour() {
		return this.idTour;
	}
	public void setIdTour(Integer idTour) {
		this.idTour = idTour;
	}
	public Integer getSequenz() {
		return this.sequenz;
	}
	public void setSequenz(Integer sequenz) {
		this.sequenz = sequenz;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GaleriebildPK)) {
			return false;
		}
		GaleriebildPK castOther = (GaleriebildPK)other;
		return 
			this.idTour.equals(castOther.idTour)
			&& this.sequenz.equals(castOther.sequenz);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idTour.hashCode();
		hash = hash * prime + this.sequenz.hashCode();
		
		return hash;
	}
}