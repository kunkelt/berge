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
 * The persistent class for the tourabschnitt database table.
 * 
 */
@Entity
@NamedQuery(name = "Tourabschnitt.findAll", query = "SELECT t FROM Tourabschnitt t")
public class Tourabschnitt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TOURABSCHNITT_ID_GENERATOR", sequenceName = "ID_TOURABSCHNITT", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOURABSCHNITT_ID_GENERATOR")
	private Integer id;

	private Integer sequenz;

	// bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name = "nach_punkt")
	private Punkt nachPunkt;

	// bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name = "von_punkt")
	private Punkt vonPunkt;

	// bi-directional many-to-one association to Tourentag
	@ManyToOne
	@JoinColumn(name = "id_tourentag")
	private Tourentag tourentag;

	public Tourabschnitt() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSequenz() {
		return sequenz;
	}

	public void setSequenz(Integer sequenz) {
		this.sequenz = sequenz;
	}

	public Punkt getNachPunkt() {
		return nachPunkt;
	}

	public void setNachPunkt(Punkt nachPunkt) {
		this.nachPunkt = nachPunkt;
	}

	public Punkt getVonPunkt() {
		return vonPunkt;
	}

	public void setVonPunkt(Punkt vonPunkt) {
		this.vonPunkt = vonPunkt;
	}

	public Tourentag getTourentag() {
		return tourentag;
	}

	public void setTourentag(Tourentag tourentag) {
		this.tourentag = tourentag;
	}

}