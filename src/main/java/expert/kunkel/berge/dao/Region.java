package expert.kunkel.berge.dao;

import com.vividsolutions.jts.geom.Polygon;

public class Region implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8959474993816159509L;
	private int id;
	private String name;
	private String umgrenzung;
	private Punkt hoechsterPunkt;
	private Region oberregion;
	private Polygon extent;

	public Polygon getExtent() {
		return extent;
	}

	public void setExtent(Polygon extent) {
		this.extent = extent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Punkt getHoechsterPunkt() {
		return hoechsterPunkt;
	}

	public void setHoechsterPunkt(Punkt hoechsterPunkt) {
		this.hoechsterPunkt = hoechsterPunkt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Region getOberregion() {
		return oberregion;
	}

	public void setOberregion(Region oberregion) {
		this.oberregion = oberregion;
	}

	public String getUmgrenzung() {
		return umgrenzung;
	}

	public void setUmgrenzung(String umgrenzung) {
		this.umgrenzung = umgrenzung;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Region)) {
			return false;
		}

		Region r = (Region) o;
		if (this.name.equals(r.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + this.id;
		hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 59
				* hash
				+ (this.hoechsterPunkt != null ? this.hoechsterPunkt.hashCode()
						: 0);
		hash = 59 * hash
				+ (this.oberregion != null ? this.oberregion.hashCode() : 0);
		return hash;
	}

}
