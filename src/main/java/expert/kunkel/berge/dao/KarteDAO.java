package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

//Interface that all ModuleLocationDAOs must support
public interface KarteDAO {
	
	public int insertKarte(Karte karte);
	public boolean deleteKarte(int id);
	public Karte findKarte();
	public boolean updateKarte(Karte karte) throws SQLException, ClassNotFoundException;
	public List<Karte> selectKarte();
	public List<Karte> selectKarte(Integer id);
	public boolean transformExtent(Integer kartenId, Integer to);
}