package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

//Interface that all ModuleLocationDAOs must support
public interface VerlagDAO {
	
	public List<Verlag> selectVerlag();
	public List<Verlag> selectVerlag(Integer id);
}