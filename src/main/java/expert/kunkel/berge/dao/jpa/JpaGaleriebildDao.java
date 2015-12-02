package expert.kunkel.berge.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import expert.kunkel.berge.dao.Galeriebild;
import expert.kunkel.berge.dao.GaleriebildDAO;
import expert.kunkel.berge.dao.Tour;

public class JpaGaleriebildDao implements GaleriebildDAO {

	@Override
	public List<Galeriebild> selectGaleriebild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Galeriebild> selectGaleriebild(Tour tour) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertGaleriebild(Galeriebild bild) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteGaleriebild(Tour tour) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

}
