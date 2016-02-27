package expert.kunkel.berge.dao.postgresql;

import expert.kunkel.berge.dao.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQL_TourabschnittDAO implements TourabschnittDAO {

    private static final String TABLE_NAME = "tourabschnitt";
    private static final String COLUMN_ID_TOUR = "id_tour";
    private static final String COLUMN_TAG = "tag";
    private static final String COLUMN_SEQUENZ = "sequenz";
    private static final String COLUMN_VON_PUNKT = "von_punkt";
    private static final String COLUMN_NACH_PUNKT = "nach_punkt";
    
    private DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
    private PostgreSQL_PunktDAO punktDao = (PostgreSQL_PunktDAO) factory.getPunktDAO();

    public boolean deleteTourabschnitt(Tourentag ttag) throws SQLException, ClassNotFoundException {
        if (ttag == null || ttag.getTour() == null) {
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
        deleteSql.append(ttag.getTour().getId());
        deleteSql.append(" and ");
        deleteSql.append(COLUMN_TAG);
        deleteSql.append(" = ");
        deleteSql.append(ttag.getTag());

        s.execute(deleteSql.toString());

        s.close();

        return true;
    }

    public Tourabschnitt findTourabschnitt() {
        // TODO Auto-generated method stub
        return null;
    }

    public int insertTourabschnitt(Tourabschnitt ta) throws SQLException, ClassNotFoundException {
        Connection c = PostgreSQL_DAOFactory.getConnection();
        PreparedStatement ps = null;

        StringBuffer insert = new StringBuffer("insert into ");
        insert.append(TABLE_NAME);
        insert.append(" (");
        insert.append(COLUMN_ID_TOUR);
        insert.append(", ");
        insert.append(COLUMN_TAG);
        insert.append(", ");
        insert.append(COLUMN_SEQUENZ);
        insert.append(", ");
        insert.append(COLUMN_VON_PUNKT);
        insert.append(", ");
        insert.append(COLUMN_NACH_PUNKT);
//TODO		insert.append(", ");
//		insert.append(COLUMN_TRACK);
        insert.append(") values (?, ?, ?, ?, ?) ");

        System.out.println(insert);

        ps = c.prepareStatement(insert.toString());

        ps.setInt(1, ta.getTtag().getTour().getId());
        ps.setInt(2, ta.getTtag().getTag());
        ps.setInt(3, ta.getSequenz());
        ps.setInt(4, ta.getVonPunkt().getId());
        ps.setInt(5, ta.getNachPunkt().getId());

        ps.execute();
        ps.close();

        return 0;
    }

    public List<Tourabschnitt> selectTourabschnitt(Tourentag ttag) throws SQLException, ClassNotFoundException {
        List<Tourabschnitt> list = new ArrayList<Tourabschnitt>();
        Connection c = PostgreSQL_DAOFactory.getConnection();
        Statement s = c.createStatement();

        if (ttag == null || ttag.getTag() <= 0) {
            return list;
        }

        StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
        sb.append(" where ");
        sb.append(COLUMN_ID_TOUR);
        sb.append(" = ");
        sb.append(ttag.getTour().getId());
        sb.append(" and ");
        sb.append(COLUMN_TAG);
        sb.append(" = ");
        sb.append(ttag.getTag());

        ResultSet rs = s.executeQuery(sb.toString());

        while (rs.next()) {
            Tourabschnitt ta = new Tourabschnitt();
            ta.setTtag(ttag);
            ta.setSequenz(rs.getInt(COLUMN_SEQUENZ));

            int vonPunkt = rs.getInt(COLUMN_VON_PUNKT);
            Punkt p = (Punkt) (punktDao.selectPunkt(vonPunkt)).get(0);
            ta.setVonPunkt(p);

            int nachPunkt = rs.getInt(COLUMN_NACH_PUNKT);
            p = (Punkt) (punktDao.selectPunkt(nachPunkt)).get(0);
            ta.setNachPunkt(p);

            list.add(ta);
        }

        rs.close();

        return list;
    }

    public boolean updateTourabschnitt(Tourabschnitt abschnitt)
            throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        return false;
    }
}
