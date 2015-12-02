package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tour database table.
 * 
 */
@Entity
@NamedQuery(name="Tour.findAll", query="SELECT t FROM Tour t")
public class Tour implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TOUR_ID_GENERATOR", sequenceName="ID_TOUR", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOUR_ID_GENERATOR")
	private Integer id;

	private String beschreibung;

	private Boolean geplant;

	private String link;

	private String name;

	private String zusatzinfo;

	//bi-directional many-to-many association to Karte
	@ManyToMany
	@JoinTable(
		name="tour_karte"
		, joinColumns={
			@JoinColumn(name="id_tour")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_karte")
			}
		)
	private List<Karte> karten;

	public Tour() {
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

	public Boolean getGeplant() {
		return this.geplant;
	}

	public void setGeplant(Boolean geplant) {
		this.geplant = geplant;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZusatzinfo() {
		return this.zusatzinfo;
	}

	public void setZusatzinfo(String zusatzinfo) {
		this.zusatzinfo = zusatzinfo;
	}

	public List<Karte> getKarten() {
		return this.karten;
	}

	public void setKarten(List<Karte> karten) {
		this.karten = karten;
	}

}