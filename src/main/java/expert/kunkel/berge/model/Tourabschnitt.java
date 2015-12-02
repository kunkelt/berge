package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tourabschnitt database table.
 * 
 */
@Entity
@NamedQuery(name="Tourabschnitt.findAll", query="SELECT t FROM Tourabschnitt t")
public class Tourabschnitt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TourabschnittPK id;

	//bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name="nach_punkt")
	private Punkt nach_punkt;

	//bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name="von_punkt")
	private Punkt von_punkt;

	public Tourabschnitt() {
	}

	public TourabschnittPK getId() {
		return this.id;
	}

	public void setId(TourabschnittPK id) {
		this.id = id;
	}

	public Punkt getNach_punkt() {
		return this.nach_punkt;
	}

	public void setNach_punkt(Punkt nach_punkt) {
		this.nach_punkt = nach_punkt;
	}

	public Punkt getVon_punkt() {
		return this.von_punkt;
	}

	public void setVon_punkt(Punkt von_punkt) {
		this.von_punkt = von_punkt;
	}

}