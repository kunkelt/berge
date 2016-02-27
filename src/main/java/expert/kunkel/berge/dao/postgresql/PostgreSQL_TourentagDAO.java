package expert.kunkel.berge.dao.postgresql;

import expert.kunkel.berge.dao.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQL_TourentagDAO implements TourentagDAO {

    private static final String TABLE_NAME = "tourentag";
    private static final String COLUMN_ID_TOUR = "id_tour";
    private static final String COLUMN_TAG = "tag";
    private static final String COLUMN_BESCHREIBUNG = "beschreibung";
    private static final String COLUMN_DATUM = "datum";
    private static final String COLUMN_REGION = "region";
    private static final String COLUMN_HMAUFSTIEG = "hmaufstieg";
    private static final String COLUMN_HMABSTIEG = "hmabstieg";
    private static final String COLUMN_GEHZEIT = "gehzeit";
    private static final String COLUMN_SCHWIERIGKT = "schwierigkt";
    private static final String COLUMN_BILDDATEI = "bilddatei";
    private static final String COLUMN_BILTITEL = "bildtitel";
    private static final String COLUMN_TRACK = "track";

    public boolean deleteTourentag(Tourentag tourentag) throws SQLException, ClassNotFoundException {
        if (tourentag == null || tourentag.getTour() == null) {
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
        deleteSql.append(tourentag.getTour().getId());

        s.execute(deleteSql.toString());

        s.close();

        return true;
    }

    public Tourentag findTourentag() {
        // TODO Auto-generated method stub
        return null;
    }

    public int insertTourentag(Tourentag tag) throws SQLException,
            ClassNotFoundException {
        Connection c = PostgreSQL_DAOFactory.getConnection();
        PreparedStatement ps = null;

        StringBuffer insert = new StringBuffer("insert into ");
        insert.append(TABLE_NAME);
        insert.append(" (");
        insert.append(COLUMN_ID_TOUR);
        insert.append(", ");
        insert.append(COLUMN_TAG);
        insert.append(", ");
        insert.append(COLUMN_BESCHREIBUNG);
        insert.append(", ");
        insert.append(COLUMN_DATUM);
        insert.append(", ");
        insert.append(COLUMN_REGION);
        insert.append(", ");
        insert.append(COLUMN_HMAUFSTIEG);
        insert.append(", ");
        insert.append(COLUMN_HMABSTIEG);
        insert.append(", ");
        insert.append(COLUMN_GEHZEIT);
        insert.append(", ");
        insert.append(COLUMN_SCHWIERIGKT);
        insert.append(", ");
        insert.append(COLUMN_BILDDATEI);
        insert.append(", ");
        insert.append(COLUMN_BILTITEL);
        insert.append(") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

        System.out.println(insert);

        ps = c.prepareStatement(insert.toString());

        ps.setInt(1, tag.getTour().getId());
        ps.setInt(2, tag.getTag());
        ps.setString(3, tag.getBeschreibung());
        ps.setDate(4, tag.getDate());
        ps.setInt(5, tag.getRegion().getId());
        if (tag.getHmAufstieg() != null) {
            ps.setInt(6, tag.getHmAufstieg());
        }
        else {
            ps.setNull(6, Types.NULL);
        }
        if (tag.getHmAbstieg() != null) {
            ps.setInt(7, tag.getHmAbstieg());
        }
        else {
            ps.setNull(7, Types.NULL);
        }
        if (tag.getGehzeit() != null) {
            ps.setDouble(8, tag.getGehzeit());
        }
        else {
            ps.setNull(8, Types.NULL);
        }
        ps.setString(9, tag.getSchwierigkeiten());
        ps.setString(10, tag.getBilddatei());
        ps.setString(11, tag.getBildttitel());

        ps.execute();
        ps.close();

        return 0;
    }

    public List<Tourentag> selectTourentag(Tour tour) throws SQLException, ClassNotFoundException {
        List<Tourentag> list = new ArrayList<Tourentag>();
        Connection c = PostgreSQL_DAOFactory.getConnection();
        Statement s = c.createStatement();

        if (tour == null || tour.getId() <= 0) {
            return list;
        }

        StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
        sb.append(" where ");
        sb.append(COLUMN_ID_TOUR);
        sb.append(" = ");
        sb.append(tour.getId());

        ResultSet rs = s.executeQuery(sb.toString());

        while (rs.next()) {
            Tourentag tt = new Tourentag(tour);
            tt.setTag(rs.getInt(COLUMN_TAG));
            tt.setBeschreibung(rs.getString(COLUMN_BESCHREIBUNG));
            tt.setDate(rs.getDate(COLUMN_DATUM));
            tt.setHmAufstieg(rs.getInt(COLUMN_HMAUFSTIEG));
            tt.setHmAbstieg(rs.getInt(COLUMN_HMABSTIEG));
            tt.setGehzeit(rs.getDouble(COLUMN_GEHZEIT));
            tt.setSchwierigkeiten(rs.getString(COLUMN_SCHWIERIGKT));
            tt.setBilddatei(rs.getString(COLUMN_BILDDATEI));
            tt.setBildttitel(rs.getString(COLUMN_BILTITEL));

            int region = rs.getInt(COLUMN_REGION);
            DAOFactory daof = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
            PostgreSQL_RegionDAO regionDao = (PostgreSQL_RegionDAO) daof.getRegionDAO();
            Region r = (Region) (regionDao.selectRegion(region)).get(0);

            tt.setRegion(r);

            list.add(tt);
        }

        rs.close();

        return list;
    }

    public boolean updateTourentag(Tourentag tag) throws SQLException,
            ClassNotFoundException {
        Connection c = PostgreSQL_DAOFactory.getConnection();
        PreparedStatement ps = null;

        StringBuffer updateSql = new StringBuffer("update ");
        updateSql.append(TABLE_NAME);
        updateSql.append(" set ");
        updateSql.append(COLUMN_DATUM);
        updateSql.append(" = ?, ");
        updateSql.append(COLUMN_BESCHREIBUNG);
        updateSql.append(" = ?, ");
        updateSql.append(COLUMN_HMAUFSTIEG);
        updateSql.append(" = ?, ");
        updateSql.append(COLUMN_HMABSTIEG);
        updateSql.append(" = ?, ");
        updateSql.append(COLUMN_GEHZEIT);
        updateSql.append(" = ?, ");
        updateSql.append(COLUMN_SCHWIERIGKT);
        updateSql.append(" = ?, ");
        updateSql.append(COLUMN_BILDDATEI);
        updateSql.append(" = ?, ");
        updateSql.append(COLUMN_BILTITEL);
        updateSql.append(" = ?, ");
        updateSql.append(COLUMN_REGION);
        updateSql.append(" = ? ");
        updateSql.append(" where ");
        updateSql.append(COLUMN_ID_TOUR);
        updateSql.append(" = ?");
        updateSql.append(" and ");
        updateSql.append(COLUMN_TAG);
        updateSql.append(" = ?");

        System.out.println(updateSql);

        ps = c.prepareStatement(updateSql.toString());

        ps.setDate(1, tag.getDate());
        ps.setString(2, tag.getBeschreibung());
        if (tag.getHmAufstieg() != null) {
            ps.setInt(3, tag.getHmAufstieg());
        }
        else {
            ps.setNull(3, Types.NULL);
        }
        if (tag.getHmAbstieg() != null) {
            ps.setInt(4, tag.getHmAbstieg());
        }
        else {
            ps.setNull(4, Types.NULL);
        }
        if (tag.getGehzeit() != null) {
            ps.setDouble(5, tag.getGehzeit());
        }
        else {
            ps.setNull(5, Types.NULL);
        }
        ps.setString(6, tag.getSchwierigkeiten());
        ps.setString(7, tag.getBilddatei());
        ps.setString(8, tag.getBildttitel());
        ps.setInt(9, tag.getRegion().getId());
        ps.setInt(10, tag.getTour().getId());
        ps.setInt(11, tag.getTag());

        ps.execute();
        ps.close();

        return true;
    }
}
