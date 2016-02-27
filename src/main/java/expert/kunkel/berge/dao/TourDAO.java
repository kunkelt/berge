package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

//Interface that all PunktDAOs must support
public interface TourDAO {

    public int insertTour(Tour tour) throws SQLException, ClassNotFoundException;

    public boolean deleteTour(Tour tour) throws SQLException, ClassNotFoundException;

    public List<Tour> findTour(String where);

    public boolean updateTour(Tour tour) throws SQLException, ClassNotFoundException;

    public List<Tour> selectTour(Boolean geplant);

    public List<Tour> selectTour(Integer id, Boolean geplant);

    public List<Tour> getAllToursForYear(int i);

	public List<Tour> findTourInRegion(Region region);
}
