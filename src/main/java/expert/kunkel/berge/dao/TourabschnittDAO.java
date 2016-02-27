package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

//Interface that all PunktDAOs must support
public interface TourabschnittDAO {
	
	public int insertTourabschnitt(Tourabschnitt abschnitt) throws  SQLException, ClassNotFoundException;
	public boolean deleteTourabschnitt(Tourentag ttag) throws SQLException, ClassNotFoundException;
	public Tourabschnitt findTourabschnitt();
	public boolean updateTourabschnitt(Tourabschnitt abschnitt) throws SQLException, ClassNotFoundException;
	public List<Tourabschnitt> selectTourabschnitt(Tourentag ttag) throws  SQLException, ClassNotFoundException;
}