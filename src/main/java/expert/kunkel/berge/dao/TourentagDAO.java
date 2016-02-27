package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

//Interface that all PunktDAOs must support
public interface TourentagDAO {
	
	public int insertTourentag(Tourentag tag) throws SQLException, ClassNotFoundException;
	public boolean deleteTourentag(Tourentag tourentag) throws SQLException, ClassNotFoundException;
	public Tourentag findTourentag();
	public boolean updateTourentag(Tourentag tag) throws SQLException, ClassNotFoundException;
	public List<Tourentag> selectTourentag(Tour tour) throws SQLException, ClassNotFoundException;
}