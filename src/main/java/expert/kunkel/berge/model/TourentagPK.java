package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tourentag database table.
 * 
 */
@Embeddable
public class TourentagPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_tour")
	private Integer idTour;

	private Integer tag;

	public TourentagPK() {
	}
	public Integer getIdTour() {
		return this.idTour;
	}
	public void setIdTour(Integer idTour) {
		this.idTour = idTour;
	}
	public Integer getTag() {
		return this.tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TourentagPK)) {
			return false;
		}
		TourentagPK castOther = (TourentagPK)other;
		return 
			this.idTour.equals(castOther.idTour)
			&& this.tag.equals(castOther.tag);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idTour.hashCode();
		hash = hash * prime + this.tag.hashCode();
		
		return hash;
	}
}