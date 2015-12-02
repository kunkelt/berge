package expert.kunkel.berge.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import expert.kunkel.berge.dao.Region;
import expert.kunkel.berge.dao.RegionDAO;

public class JpaRegionDao implements RegionDAO {

	@Override
	public int insertRegion(Region region) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteRegion(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Region findRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateRegion(Region region) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Region> selectRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Region> selectRegion(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Region getOberregion(Region region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Region> findUsedRegions() {
		// TODO Auto-generated method stub
		return null;
	}

}
