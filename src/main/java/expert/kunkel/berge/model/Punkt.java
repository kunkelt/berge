package expert.kunkel.berge.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.postgis.PGgeometry;


/**
 * The persistent class for the punkt database table.
 * 
 */
@Entity
@NamedQuery(name="Punkt.findAll", query="SELECT p FROM Punkt p")
public class Punkt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PUNKT_ID_GENERATOR", sequenceName="ID_PUNKT", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PUNKT_ID_GENERATOR")
	private Integer id;

	private String beschreibung;

	private Integer hoehe;

	private PGgeometry lage;

	private String name;

	private String name2;

	private Integer typ;

	private String url;

	//bi-directional many-to-one association to Region
	@OneToMany(mappedBy="punkt")
	private List<Region> regions;

	//bi-directional many-to-one association to Tourabschnitt
	@OneToMany(mappedBy="nachPunkt")
	private List<Tourabschnitt> nachTourabschnitt;

	//bi-directional many-to-one association to Tourabschnitt
	@OneToMany(mappedBy="vonPunkt")
	private List<Tourabschnitt> vonTourabschnitt;

	public Punkt() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBeschreibung() {
		return this.beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Integer getHoehe() {
		return this.hoehe;
	}

	public void setHoehe(Integer hoehe) {
		this.hoehe = hoehe;
	}

	public PGgeometry getLage() {
		return this.lage;
	}

	public void setLage(PGgeometry lage) {
		this.lage = lage;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public Integer getTyp() {
		return this.typ;
	}

	public void setTyp(Integer typ) {
		this.typ = typ;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Region> getRegions() {
		return this.regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public Region addRegion(Region region) {
		getRegions().add(region);
		region.setPunkt(this);

		return region;
	}

	public Region removeRegion(Region region) {
		getRegions().remove(region);
		region.setPunkt(null);

		return region;
	}

	public List<Tourabschnitt> getNachTourabschnitt() {
		return this.nachTourabschnitt;
	}

	public void setNachTourabschnitt(List<Tourabschnitt> nachTourabschnitt) {
		this.nachTourabschnitt = nachTourabschnitt;
	}

	public Tourabschnitt addNachTourabschnitt(Tourabschnitt nachTourabschnitt) {
		getNachTourabschnitt().add(nachTourabschnitt);
		nachTourabschnitt.setNachPunkt(this);

		return nachTourabschnitt;
	}

	public Tourabschnitt removeNachTourabschnitt(Tourabschnitt nachTourabschnitt) {
		getNachTourabschnitt().remove(nachTourabschnitt);
		nachTourabschnitt.setNachPunkt(null);

		return nachTourabschnitt;
	}

	public List<Tourabschnitt> getVonTourabschnitt() {
		return this.vonTourabschnitt;
	}

	public void setVonTourabschnitt(List<Tourabschnitt> vonTourabschnitt) {
		this.vonTourabschnitt = vonTourabschnitt;
	}

	public Tourabschnitt addVonTourabschnitt(Tourabschnitt vonTourabschnitt) {
		getVonTourabschnitt().add(vonTourabschnitt);
		vonTourabschnitt.setVonPunkt(this);

		return vonTourabschnitt;
	}

	public Tourabschnitt removeVonTourabschnitt(Tourabschnitt vonTourabschnitt) {
		getVonTourabschnitt().remove(vonTourabschnitt);
		vonTourabschnitt.setVonPunkt(null);

		return vonTourabschnitt;
	}

}