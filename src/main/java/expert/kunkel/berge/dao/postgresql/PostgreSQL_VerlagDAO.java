/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.kunkel.berge.dao.postgresql;

import expert.kunkel.berge.dao.Verlag;
import expert.kunkel.berge.dao.VerlagDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thorsten
 */
public class PostgreSQL_VerlagDAO implements VerlagDAO {

    private static final String TABLE_NAME = "verlag";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TELEFON = "telefon";
    private static final String COLUMN_ANSCHRIFT = "anschrift";
    private static final String COLUMN_BEZUGSQUELLE = "bezugsquelle";

    public PostgreSQL_VerlagDAO() {
    }

    public int insertVerlag(Verlag verlag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deleteVerlag(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Verlag findVerlag() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateVerlag(Verlag verlag) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Verlag> selectVerlag() {
        return selectVerlag(null);
    }

    public List<Verlag> selectVerlag(Integer id) {
        List<Verlag> list = new ArrayList<Verlag>();

        try {
            Connection c = PostgreSQL_DAOFactory.getConnection();
            java.sql.Statement s = c.createStatement();

            StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
            if (id != null) {
                sb.append(" where id = ");
                sb.append(id);
            }

            ResultSet rs = s.executeQuery(sb.toString());

            while (rs.next()) {
                Verlag verlag = new Verlag();
                verlag.setId(rs.getInt(COLUMN_ID));
                verlag.setAnschrift(rs.getString(COLUMN_ANSCHRIFT));
                verlag.setBezugsquelle(rs.getString(COLUMN_BEZUGSQUELLE));
                verlag.setName(rs.getString(COLUMN_NAME));
                verlag.setTelefon(rs.getString(COLUMN_TELEFON));
                list.add(verlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
