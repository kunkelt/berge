package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

//Interface that all ModuleLocationDAOs must support
public interface VerlagDAO {
	
	public int insertVerlag(Verlag verlag);
	public boolean deleteVerlag(int id);
	public Verlag findVerlag();
	public boolean updateVerlag(Verlag verlag) throws SQLException, ClassNotFoundException;
	public List<Verlag> selectVerlag();
	public List<Verlag> selectVerlag(Integer id);
}