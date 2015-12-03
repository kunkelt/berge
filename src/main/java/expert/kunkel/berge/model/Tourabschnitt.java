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
	private Punkt nachPunkt;

	//bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name="von_punkt")
	private Punkt vonPunkt;

	public Tourabschnitt() {
	}

	public TourabschnittPK getId() {
		return this.id;
	}

	public void setId(TourabschnittPK id) {
		this.id = id;
	}

	public Punkt getNachPunkt() {
		return this.nachPunkt;
	}

	public void setNachPunkt(Punkt nachPunkt) {
		this.nachPunkt = nachPunkt;
	}

	public Punkt getVonPunkt() {
		return this.vonPunkt;
	}

	public void setVonPunkt(Punkt vonPunkt) {
		this.vonPunkt = vonPunkt;
	}

}