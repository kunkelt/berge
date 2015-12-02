package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the region database table.
 * 
 */
@Entity
@NamedQuery(name="Region.findAll", query="SELECT r FROM Region r")
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REGION_ID_GENERATOR", sequenceName="ID_REGION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REGION_ID_GENERATOR")
	private Integer id;

	private Object extent;

	private String name;

	private String umgrenzung;

	//bi-directional many-to-one association to Punkt
	@ManyToOne
	@JoinColumn(name="hoechster_punkt")
	private Punkt punkt;

	//bi-directional many-to-one association to Region
	@ManyToOne
	@JoinColumn(name="oberregion")
	private Region region;

	//bi-directional many-to-one association to Region
	@OneToMany(mappedBy="region")
	private List<Region> regions;

	public Region() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Object getExtent() {
		return this.extent;
	}

	public void setExtent(Object extent) {
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

	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Region> getRegions() {
		return this.regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public Region addRegion(Region region) {
		getRegions().add(region);
		region.setRegion(this);

		return region;
	}

	public Region removeRegion(Region region) {
		getRegions().remove(region);
		region.setRegion(null);

		return region;
	}

}