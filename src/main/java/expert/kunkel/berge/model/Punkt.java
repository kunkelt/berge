package expert.kunkel.berge.model;

import java.io.Serializable;

import javax.persistence.*;

import org.postgis.PGgeometry;

import java.util.List;


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
	@OneToMany(mappedBy="nach_punkt")
	private List<Tourabschnitt> nach_tourabschnitte;

	//bi-directional many-to-one association to Tourabschnitt
	@OneToMany(mappedBy="von_punkt")
	private List<Tourabschnitt> von_tourabschnitte;

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

	public List<Tourabschnitt> getNach_tourabschnitte() {
		return this.nach_tourabschnitte;
	}

	public void setNach_tourabschnitte(List<Tourabschnitt> nach_tourabschnitte) {
		this.nach_tourabschnitte = nach_tourabschnitte;
	}

	public Tourabschnitt addNach_tourabschnitte(Tourabschnitt nach_tourabschnitte) {
		getNach_tourabschnitte().add(nach_tourabschnitte);
		nach_tourabschnitte.setNach_punkt(this);

		return nach_tourabschnitte;
	}

	public Tourabschnitt removeNach_tourabschnitte(Tourabschnitt nach_tourabschnitte) {
		getNach_tourabschnitte().remove(nach_tourabschnitte);
		nach_tourabschnitte.setNach_punkt(null);

		return nach_tourabschnitte;
	}

	public List<Tourabschnitt> getVon_tourabschnitte() {
		return this.von_tourabschnitte;
	}

	public void setVon_tourabschnitte(List<Tourabschnitt> von_tourabschnitte) {
		this.von_tourabschnitte = von_tourabschnitte;
	}

	public Tourabschnitt addVon_tourabschnitte(Tourabschnitt von_tourabschnitte) {
		getVon_tourabschnitte().add(von_tourabschnitte);
		von_tourabschnitte.setVon_punkt(this);

		return von_tourabschnitte;
	}

	public Tourabschnitt removeVon_tourabschnitte(Tourabschnitt von_tourabschnitte) {
		getVon_tourabschnitte().remove(von_tourabschnitte);
		von_tourabschnitte.setVon_punkt(null);

		return von_tourabschnitte;
	}

}