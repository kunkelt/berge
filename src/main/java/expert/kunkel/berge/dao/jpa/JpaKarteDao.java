package expert.kunkel.berge.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import expert.kunkel.berge.dao.Karte;
import expert.kunkel.berge.dao.KarteDAO;

public class JpaKarteDao implements KarteDAO {

	@Override
	public int insertKarte(Karte karte) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteKarte(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Karte findKarte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateKarte(Karte karte) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Karte> selectKarte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Karte> selectKarte(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean transformExtent(Integer kartenId, Integer to) {
		// TODO Auto-generated method stub
		return false;
	}

}
