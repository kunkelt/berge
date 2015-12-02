package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the verlag database table.
 * 
 */
@Entity
@NamedQuery(name="Verlag.findAll", query="SELECT v FROM Verlag v")
public class Verlag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VERLAG_ID_GENERATOR", sequenceName="ID_VERLAG")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VERLAG_ID_GENERATOR")
	private Integer id;

	private String anschrift;

	private String bezugsquelle;

	private String name;

	private String telefon;

	public Verlag() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnschrift() {
		return this.anschrift;
	}

	public void setAnschrift(String anschrift) {
		this.anschrift = anschrift;
	}

	public String getBezugsquelle() {
		return this.bezugsquelle;
	}

	public void setBezugsquelle(String bezugsquelle) {
		this.bezugsquelle = bezugsquelle;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}