package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the kartentyp database table.
 * 
 */
@Entity
@NamedQuery(name="Kartentyp.findAll", query="SELECT k FROM Kartentyp k")
public class Kartentyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="KARTENTYP_ID_GENERATOR", sequenceName="ID_KARTENTYP", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KARTENTYP_ID_GENERATOR")
	private Integer id;

	private String typ;

	//bi-directional many-to-one association to Karte
	@OneToMany(mappedBy="kartentyp")
	private List<Karte> karten;

	public Kartentyp() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public List<Karte> getKarten() {
		return this.karten;
	}

	public void setKarten(List<Karte> karten) {
		this.karten = karten;
	}

	public Karte addKarten(Karte karten) {
		getKarten().add(karten);
		karten.setKartentyp(this);

		return karten;
	}

	public Karte removeKarten(Karte karten) {
		getKarten().remove(karten);
		karten.setKartentyp(null);

		return karten;
	}

}