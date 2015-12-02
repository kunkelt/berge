package expert.kunkel.berge.dao;

import java.util.List;

//Interface that all PunkttypDAOs must support
public interface PunkttypDAO {
	
	public List<Punkttyp> selectPunkttyp();
	public Punkttyp selectPunkttyp(Integer id);
}