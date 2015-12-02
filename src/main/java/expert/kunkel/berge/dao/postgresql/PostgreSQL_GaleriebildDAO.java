/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expert.kunkel.berge.dao.postgresql;

import expert.kunkel.berge.dao.Galeriebild;
import expert.kunkel.berge.dao.GaleriebildDAO;
import expert.kunkel.berge.dao.Tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thorsten
 */
public class PostgreSQL_GaleriebildDAO implements GaleriebildDAO {

    private static final String TABLE_NAME = "galeriebild";
    private static final String COLUMN_ID_TOUR = "id_tour";
    private static final String COLUMN_SEQUENZ = "sequenz";
    private static final String COLUMN_DATEINAME = "dateiname";
    private static final String COLUMN_BREITE = "breite";
    private static final String COLUMN_HOEHE = "hoehe";
    private static final String COLUMN_TITEL = "titel";

    public List<Galeriebild> selectGaleriebild(Tour tour) {
        List<Galeriebild> list = new ArrayList<Galeriebild>();

        try {
            Connection c = PostgreSQL_DAOFactory.getConnection();
            Statement s = c.createStatement();

            StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
            if (tour != null) {
                sb.append(" where "+COLUMN_ID_TOUR+" = ");
                sb.append(tour.getId());
            }
            sb.append(" order by ");
            sb.append(COLUMN_SEQUENZ);

            ResultSet rs = s.executeQuery(sb.toString());

            while (rs.next()) {
                Galeriebild bild = new Galeriebild(tour);
                bild.setBreite(rs.getInt(COLUMN_BREITE));
                bild.setHoehe(rs.getInt(COLUMN_HOEHE));
                bild.setTitel(rs.getString(COLUMN_TITEL));
                bild.setSequenz(rs.getInt(COLUMN_SEQUENZ));
                bild.setDateiname(rs.getString(COLUMN_DATEINAME));

                list.add(bild);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Galeriebild> selectGaleriebild() {
        return selectGaleriebild(null);
    }

    public void insertGaleriebild(Galeriebild bild) throws SQLException, ClassNotFoundException {
        Connection c = PostgreSQL_DAOFactory.getConnection();
        PreparedStatement ps = null;

        StringBuffer insert = new StringBuffer("insert into ");
        insert.append(TABLE_NAME);
        insert.append(" (");
        insert.append(COLUMN_ID_TOUR);
        insert.append(", ");
        insert.append(COLUMN_SEQUENZ);
        insert.append(", ");
        insert.append(COLUMN_DATEINAME);
        insert.append(", ");
        insert.append(COLUMN_BREITE);
        insert.append(", ");
        insert.append(COLUMN_HOEHE);
        insert.append(", ");
        insert.append(COLUMN_TITEL);
        insert.append(") values (?, ?, ?, ?, ?, ?) ");

        System.out.println(insert);

        ps = c.prepareStatement(insert.toString());

        ps.setInt(1, bild.getTour().getId());
        ps.setInt(2, bild.getSequenz());
        ps.setString(3, bild.getDateiname());
        ps.setInt(4, bild.getBreite());
        ps.setInt(5, bild.getHoehe());
        ps.setString(6, bild.getTitel());

        ps.execute();
        ps.close();
    }

    public boolean deleteGaleriebild(Tour tour) throws SQLException, ClassNotFoundException {
        if (tour == null) {
            return false;
        }

        Connection c = PostgreSQL_DAOFactory.getConnection();
        Statement s = c.createStatement();
        StringBuffer deleteSql = new StringBuffer();
        deleteSql.append("delete from ");
        deleteSql.append(TABLE_NAME);
        deleteSql.append(" where ");
        deleteSql.append(COLUMN_ID_TOUR);
        deleteSql.append(" = ");
        deleteSql.append(tour.getId());

        s.execute(deleteSql.toString());

        s.close();

        return true;
    }
}
