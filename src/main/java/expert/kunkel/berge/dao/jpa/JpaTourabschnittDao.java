package expert.kunkel.berge.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import expert.kunkel.berge.dao.Tourabschnitt;
import expert.kunkel.berge.dao.TourabschnittDAO;
import expert.kunkel.berge.dao.Tourentag;

public class JpaTourabschnittDao implements TourabschnittDAO {

	@Override
	public int insertTourabschnitt(Tourabschnitt abschnitt)
			throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteTourabschnitt(Tourentag ttag) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Tourabschnitt findTourabschnitt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateTourabschnitt(Tourabschnitt abschnitt)
			throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Tourabschnitt> selectTourabschnitt(Tourentag ttag)
			throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
