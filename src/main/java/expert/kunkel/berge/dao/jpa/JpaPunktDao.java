package expert.kunkel.berge.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import expert.kunkel.berge.dao.Punkt;
import expert.kunkel.berge.dao.PunktDAO;

public class JpaPunktDao implements PunktDAO {

	@Override
	public int insertPunkt(Punkt punkt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deletePunkt(int id) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Punkt findPunkt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePunkt(Punkt punkt) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Punkt> selectPunkt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Punkt> selectPunkt(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
