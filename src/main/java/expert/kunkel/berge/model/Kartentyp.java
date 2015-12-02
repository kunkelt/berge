package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the kartentyp database table.
 * 
 */
@Entity
@NamedQuery(name="Kartentyp.findAll", query="SELECT k FROM Kartentyp k")
public class Kartentyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="KARTENTYP_ID_GENERATOR", sequenceName="ID_KARTENTYP")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KARTENTYP_ID_GENERATOR")
	private Integer id;

	private String typ;

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

}