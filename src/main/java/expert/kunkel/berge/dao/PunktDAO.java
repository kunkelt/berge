package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

//Interface that all PunktDAOs must support
public interface PunktDAO {
	
	public int insertPunkt(Punkt punkt);
	public boolean deletePunkt(int id) throws SQLException, ClassNotFoundException;
	public Punkt findPunkt();
	public boolean updatePunkt(Punkt punkt) throws SQLException, ClassNotFoundException;
	public List<Punkt> selectPunkt();
	public List<Punkt> selectPunkt(Integer id);
}