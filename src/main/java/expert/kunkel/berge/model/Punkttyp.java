package expert.kunkel.berge.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the punkttyp database table.
 * 
 */
@Entity
@NamedQuery(name="Punkttyp.findAll", query="SELECT p FROM Punkttyp p")
public class Punkttyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PUNKTTYP_ID_GENERATOR", sequenceName="ID_PUNKTTYP", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PUNKTTYP_ID_GENERATOR")
	private Integer id;

	private String name;

	public Punkttyp() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}