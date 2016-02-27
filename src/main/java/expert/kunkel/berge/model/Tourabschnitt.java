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

	@Id
	@SequenceGenerator(name="TOURABSCHNITT_ID_GENERATOR", sequenceName="ID_TOURABSCHNITT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOURABSCHNITT_ID_GENERATOR")
	private Integer id;

	@Column(name="id_tour")
	private Integer idTour;

	private Integer sequenz;

	private Integer tag;

	//bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name="nach_punkt")
	private Punkt nachPunkt;

	//bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name="von_punkt")
	private Punkt vonPunkt;

	//bi-directional many-to-one association to Tourentag
	@ManyToOne
	@JoinColumn(name="id_tourentag")
	private Tourentag tourentag;

	public Tourabschnitt() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getTag() {
		return this.tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
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

	public Tourentag getTourentag() {
		return this.tourentag;
	}

	public void setTourentag(Tourentag tourentag) {
		this.tourentag = tourentag;
	}

}