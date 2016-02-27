/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expert.kunkel.berge.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author thorsten
 */
public interface GaleriebildDAO {
	public List<Galeriebild> selectGaleriebild();

	public List<Galeriebild> selectGaleriebild(Tour tour);

	public void insertGaleriebild(Galeriebild bild) throws SQLException,
			ClassNotFoundException;

	public boolean deleteGaleriebild(Tour tour) throws SQLException,
			ClassNotFoundException;
}
