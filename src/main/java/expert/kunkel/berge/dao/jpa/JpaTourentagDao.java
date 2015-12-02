package expert.kunkel.berge.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import expert.kunkel.berge.dao.Tour;
import expert.kunkel.berge.dao.Tourentag;
import expert.kunkel.berge.dao.TourentagDAO;

public class JpaTourentagDao implements TourentagDAO {

	@Override
	public int insertTourentag(Tourentag tag) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteTourentag(Tourentag tourentag) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Tourentag findTourentag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateTourentag(Tourentag tag) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Tourentag> selectTourentag(Tour tour) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
