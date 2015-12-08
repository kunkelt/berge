package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

public interface RegionDAO {

	public int insertRegion(Region region);

	public boolean deleteRegion(int id);

	public Region findRegion();

	public boolean updateRegion(Region region) throws SQLException,
			ClassNotFoundException;

	public List<Region> selectRegion();

	public List<Region> selectRegion(Integer id);

	public Region getOberregion(Region region);

	public List<Region> findUsedRegions();
}