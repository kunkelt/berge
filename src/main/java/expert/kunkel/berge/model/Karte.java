package expert.kunkel.berge.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import org.postgis.PGgeometry;


/**
 * The persistent class for the karte database table.
 * 
 */
@Entity
@NamedQuery(name="Karte.findAll", query="SELECT k FROM Karte k")
public class Karte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="KARTE_ID_GENERATOR", sequenceName="ID_KARTE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KARTE_ID_GENERATOR")
	private Integer id;

	private Integer ausgabejahr;

	private String blattnummer;

	private PGgeometry extent;

	private String isbn;

	private Integer kartentyp;

	private String massstab;

	private String titel;

	private String untertitel;

	private Integer verlag;

	//bi-directional many-to-many association to Tour
	@ManyToMany(mappedBy="karten")
	private List<Tour> touren;

	public Karte() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAusgabejahr() {
		return this.ausgabejahr;
	}

	public void setAusgabejahr(Integer ausgabejahr) {
		this.ausgabejahr = ausgabejahr;
	}

	public String getBlattnummer() {
		return this.blattnummer;
	}

	public void setBlattnummer(String blattnummer) {
		this.blattnummer = blattnummer;
	}

	public PGgeometry getExtent() {
		return this.extent;
	}

	public void setExtent(PGgeometry extent) {
		this.extent = extent;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getKartentyp() {
		return this.kartentyp;
	}

	public void setKartentyp(Integer kartentyp) {
		this.kartentyp = kartentyp;
	}

	public String getMassstab() {
		return this.massstab;
	}

	public void setMassstab(String massstab) {
		this.massstab = massstab;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getUntertitel() {
		return this.untertitel;
	}

	public void setUntertitel(String untertitel) {
		this.untertitel = untertitel;
	}

	public Integer getVerlag() {
		return this.verlag;
	}

	public void setVerlag(Integer verlag) {
		this.verlag = verlag;
	}

	public List<Tour> getTouren() {
		return this.touren;
	}

	public void setTouren(List<Tour> touren) {
		this.touren = touren;
	}

}