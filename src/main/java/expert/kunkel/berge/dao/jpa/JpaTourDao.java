package expert.kunkel.berge.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import expert.kunkel.berge.dao.Region;
import expert.kunkel.berge.dao.Tour;
import expert.kunkel.berge.dao.TourDAO;

public class JpaTourDao implements TourDAO {

	@Override
	public int insertTour(Tour tour) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteTour(Tour tour) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Tour> findTour(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateTour(Tour tour) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Tour> selectTour(Boolean geplant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> selectTour(Integer id, Boolean geplant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> getAllToursForYear(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> findTourInRegion(Region region) {
		// TODO Auto-generated method stub
		return null;
	}

}
