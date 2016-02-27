package expert.kunkel.berge.dao.postgresql;

import expert.kunkel.berge.dao.*;

import java.sql.*;
import java.util.*;

import org.postgis.PGgeometry;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTReader;

public class PostgreSQL_PunktDAO implements PunktDAO {

    private static final String TABLE_NAME = "punkt";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NAME2 = "name2";
    private static final String COLUMN_HOEHE = "hoehe";
    private static final String COLUMN_BESCHREIBUNG = "beschreibung";
    private static final String COLUMN_LAGE = "lage";
    private static final String COLUMN_TYP = "typ";
    private static final String COLUMN_URL = "url";
    private WKTReader wktReader = new WKTReader();
    PunkttypDAO punkttypDao = null;

    public PostgreSQL_PunktDAO() {
        super();
        punkttypDao = PostgreSQL_DAOFactory.getDAOFactory(PostgreSQL_DAOFactory.POSTGRESQL).getPunkttypDAO();
    }

    public synchronized int insertPunkt(Punkt punkt) {
        int nextId = -1;
        try {
            Connection c = PostgreSQL_DAOFactory.getConnection();
            Statement s = c.createStatement();
            String selectId = new String("select max(" + COLUMN_ID + ")+1 from " + TABLE_NAME);
            ResultSet rs = s.executeQuery(selectId);
            if (rs == null || !rs.next()) {
                nextId = 1;
            } else {
                nextId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        StringBuffer insert = new StringBuffer("insert into ");
        insert.append(TABLE_NAME);
        insert.append(" (");
        StringBuffer values = new StringBuffer(") values (");

        insert.append(COLUMN_ID);
        values.append(nextId);

        insert.append(", ");
        values.append(", ");

        String name = "null";
        if (punkt.getName() != null && !punkt.getName().equals("")) {
            name = "'" + punkt.getName().replace("'", "''") + "'";
        }
        insert.append(COLUMN_NAME);
        values.append(name);

        insert.append(", ");
        values.append(", ");

        String name2 = "null";
        if (punkt.getName2() != null && !punkt.getName2().equals("")) {
            name2 = "'" + punkt.getName2().replace("'", "''") + "'";
        }
        insert.append(COLUMN_NAME2);
        values.append(name2);

        insert.append(", ");
        values.append(", ");

        insert.append(COLUMN_HOEHE);
        values.append(punkt.getHoehe());

        insert.append(", ");
        values.append(", ");

        String beschreibung = "null";
        if (punkt.getBeschreibung() != null && !punkt.getBeschreibung().equals("")) {
            beschreibung = "'" + punkt.getBeschreibung().replace("'", "''") + "'";
        }
        insert.append(COLUMN_BESCHREIBUNG);
        values.append(beschreibung);

        insert.append(", ");
        values.append(", ");

        String lage = "null";
        if (punkt.getLage() != null && !punkt.getLage().equals("")) {
            lage = "geometry('SRID=4326;" + punkt.getLage() + "')";
        }
        insert.append(COLUMN_LAGE);
        values.append(lage);

        insert.append(", ");
        values.append(", ");

        insert.append(COLUMN_TYP);
        values.append(punkt.getTyp().getId());

        insert.append(", ");
        values.append(", ");

        String url = "null";
        if (punkt.getUrl() != null && !punkt.getUrl().equals("")) {
            url = "'" + punkt.getUrl().replace("'", "''") + "'";
        }
        insert.append(COLUMN_URL);
        values.append(url);

        values.append(")");
        insert.append(values);

        System.out.println(insert);

        try {
            Connection c = PostgreSQL_DAOFactory.getConnection();
            Statement s = c.createStatement();
            s.execute(insert.toString());
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    public boolean deletePunkt(int id)  throws SQLException, ClassNotFoundException{
        Connection c = PostgreSQL_DAOFactory.getConnection();
        Statement s = c.createStatement();
        StringBuffer deleteSql = new StringBuffer();
        deleteSql.append("delete from ");
        deleteSql.append(TABLE_NAME);
        deleteSql.append(" where ");
        deleteSql.append(COLUMN_ID);
        deleteSql.append(" = ");
        deleteSql.append(id);

        s.execute(deleteSql.toString());

        s.close();

        return true;
    }

    public Punkt findPunkt() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean updatePunkt(Punkt punkt) throws SQLException, ClassNotFoundException {
        StringBuffer update = new StringBuffer("update ");
        update.append(TABLE_NAME);
        update.append(" set ");

        String name = "null";
        if (punkt.getName() != null && !punkt.getName().equals("")) {
            name = "'" + punkt.getName().replace("'", "''") + "'";
        }
        update.append(COLUMN_NAME);
        update.append("=");
        update.append(name);
        update.append(",");

        String name2 = "null";
        if (punkt.getName2() != null && !punkt.getName2().equals("")) {
            name2 = "'" + punkt.getName2().replace("'", "''") + "'";
        }
        update.append(COLUMN_NAME2);
        update.append("=");
        update.append(name2);
        update.append(",");

        update.append(COLUMN_HOEHE);
        update.append("=");
        update.append(punkt.getHoehe());
        update.append(",");

        String beschreibung = "null";
        if (punkt.getBeschreibung() != null && !punkt.getBeschreibung().equals("")) {
            beschreibung = "'" + punkt.getBeschreibung().replace("'", "''") + "'";
        }
        update.append(COLUMN_BESCHREIBUNG);
        update.append("=");
        update.append(beschreibung);
        update.append(",");

        update.append(COLUMN_LAGE);
        update.append("='");
        update.append("SRID=4326;" + punkt.getLage());
        update.append("',");

        update.append(COLUMN_TYP);
        update.append("=");
        update.append(punkt.getTyp().getId());
        update.append(",");

        String url = "null";
        if (punkt.getUrl() != null && !punkt.getUrl().equals("")) {
            url = "'" + punkt.getUrl().replace("'", "''") + "'";
        }
        update.append(COLUMN_URL);
        update.append("=");
        update.append(url);

        update.append(" where ");
        update.append(COLUMN_ID);
        update.append("=");
        update.append(punkt.getId());

        System.out.println(update);

        Connection c = PostgreSQL_DAOFactory.getConnection();
        Statement s = c.createStatement();
        s.execute(update.toString());

        return false;
    }

    public List<Punkt> selectPunkt(Integer id) {
        List<Punkt> list = new ArrayList<Punkt>();

        try {
            Connection c = PostgreSQL_DAOFactory.getConnection();
            Statement s = c.createStatement();

            StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
            if (id != null) {
                sb.append(" where id = ");
                sb.append(id);
            }
            sb.append(" order by ");
            sb.append(COLUMN_NAME);

            ResultSet rs = s.executeQuery(sb.toString());

            while (rs.next()) {
                Punkt punkt = new Punkt();
                punkt.setId(rs.getInt("id"));
                punkt.setName(rs.getString(COLUMN_NAME));
                punkt.setName2(rs.getString(COLUMN_NAME2));
                punkt.setHoehe(rs.getInt(COLUMN_HOEHE));
                punkt.setBeschreibung(rs.getString(COLUMN_BESCHREIBUNG));

                PGgeometry geom = (PGgeometry) rs.getObject(COLUMN_LAGE);
                if (geom != null && !geom.equals("")) {
                    String wkt = PGgeometry.splitSRID(geom.getValue())[1];
//					String wkt = geom.getValue();
//                    System.out.println(wkt);
                    punkt.setLage((Point) wktReader.read(wkt));
                }

                Punkttyp punkttyp = punkttypDao.selectPunkttyp(rs.getInt(COLUMN_TYP));
                punkt.setTyp(punkttyp);
                punkt.setUrl(rs.getString(COLUMN_URL));

                list.add(punkt);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Punkt> selectPunkt() {
        return selectPunkt(null);
    }
}
