package expert.kunkel.berge.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.postgis.PGgeometry;


/**
 * The persistent class for the region database table.
 * 
 */
@Entity
@NamedQuery(name="Region.findAll", query="SELECT r FROM Region r")
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REGION_ID_GENERATOR", sequenceName="ID_REGION", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REGION_ID_GENERATOR")
	private Integer id;

	private PGgeometry extent;

	private String name;

	private String umgrenzung;

	//bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name="hoechster_punkt")
	private Punkt punkt;

	//bi-directional many-to-one association to Region
	@ManyToOne
	@JoinColumn(name="oberregion")
	private Region oberregion;

	//bi-directional many-to-one association to Region
	@OneToMany(mappedBy="oberregion")
	private List<Region> regionen;

	public Region() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PGgeometry getExtent() {
		return this.extent;
	}

	public void setExtent(PGgeometry extent) {
		this.extent = extent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUmgrenzung() {
		return this.umgrenzung;
	}

	public void setUmgrenzung(String umgrenzung) {
		this.umgrenzung = umgrenzung;
	}

	public Punkt getPunkt() {
		return this.punkt;
	}

	public void setPunkt(Punkt punkt) {
		this.punkt = punkt;
	}

	public Region getOberregion() {
		return this.oberregion;
	}

	public void setOberregion(Region oberregion) {
		this.oberregion = oberregion;
	}

	public List<Region> getRegionen() {
		return this.regionen;
	}

	public void setRegionen(List<Region> regionen) {
		this.regionen = regionen;
	}

	public Region addRegionen(Region regionen) {
		getRegionen().add(regionen);
		regionen.setOberregion(this);

		return regionen;
	}

	public Region removeRegionen(Region regionen) {
		getRegionen().remove(regionen);
		regionen.setOberregion(null);

		return regionen;
	}

}