package expert.kunkel.berge.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import expert.kunkel.berge.dao.Verlag;
import expert.kunkel.berge.dao.VerlagDAO;

public class JpaVerlagDao implements VerlagDAO {

	@Override
	public int insertVerlag(Verlag verlag) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean deleteVerlag(int id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Verlag findVerlag() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean updateVerlag(Verlag verlag) throws SQLException,
			ClassNotFoundException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<Verlag> selectVerlag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Verlag> selectVerlag(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
