package expert.kunkel.berge.dao.postgresql;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgis.PGgeometry;

import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;

import expert.kunkel.berge.dao.*;
import expert.kunkel.berge.dao.Karte.Kartentyp;

public class PostgreSQL_KarteDAO implements KarteDAO {

    private static final String TABLE_NAME = "karte";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AUSGABEJAHR = "ausgabejahr";
    private static final String COLUMN_BLATTNUMMER = "blattnummer";
    private static final String COLUMN_EXTENT = "extent";
    private static final String COLUMN_KARTENTYP = "kartentyp";
    private static final String COLUMN_MASSSTAB = "massstab";
    private static final String COLUMN_TITEL = "titel";
    private static final String COLUMN_UNTERTITEL = "untertitel";
    private static final String COLUMN_VERLAG = "verlag";
    private static final String COLUMN_ISBN = "isbn";
    private WKTReader wktReader = new WKTReader();
    private VerlagDAO verlagDao = null;

    public PostgreSQL_KarteDAO() {
        super();
        verlagDao = PostgreSQL_DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getVerlagDAO();
    }

    public int insertKarte(Karte karte) {
        // zunächst neue ID ermitteln
        int nextId = -1;
        Connection c = null;
        try {
            c = PostgreSQL_DAOFactory.getConnection();
            Statement s = c.createStatement();
            String selectId = new String("select max(" + COLUMN_ID + ")+1 from " + TABLE_NAME);
            ResultSet rs = s.executeQuery(selectId);
            if (rs == null || !rs.next() || rs.getInt(1) == 0) {
                nextId = 1;
            } else {
                nextId = rs.getInt(1);
            }
            rs.close();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQL_KarteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostgreSQL_KarteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringBuffer insert = new StringBuffer("insert into ");
        insert.append(TABLE_NAME);
        insert.append(" (");
        StringBuffer values = new StringBuffer(") values (");

        insert.append(COLUMN_ID);
        values.append(nextId);

        insert.append(", ");
        values.append(", ");

        insert.append(COLUMN_AUSGABEJAHR);
        values.append(karte.getAusgabejahr());

        insert.append(", ");
        values.append(", ");

        String blattnummer = null;
        if (karte.getBlattnummer() != null && !karte.getBlattnummer().equals("")) {
            blattnummer = "'" + karte.getBlattnummer().replace("'", "''") + "'";
        }
        insert.append(COLUMN_BLATTNUMMER);
        values.append(blattnummer);

        insert.append(", ");
        values.append(", ");

        String extent = "null";
        if (karte.getExtent() != null && !karte.getExtent().toString().equals("")) {
            extent = "geometry('SRID=4326;" + karte.getExtent() + "')";
        }
        insert.append(COLUMN_EXTENT);
        values.append(extent);

        insert.append(", ");
        values.append(", ");

        String massstab = "null";
        if (karte.getMassstab() != null && !karte.getMassstab().equals("")) {
            massstab = "'" + karte.getMassstab() + "'";
        }
        insert.append(COLUMN_MASSSTAB);
        values.append(massstab);

        insert.append(", ");
        values.append(", ");

        insert.append(COLUMN_KARTENTYP);
        values.append(karte.getKartentyp().ordinal() + 1);

        insert.append(", ");
        values.append(", ");

        String titel = "null";
        if (karte.getTitel() != null && !karte.getTitel().equals("")) {
            titel = "'" + karte.getTitel().replace("'", "''") + "'";
        }
        insert.append(COLUMN_TITEL);
        values.append(titel);

        insert.append(", ");
        values.append(", ");

        String isbn = "null";
        if (karte.getIsbn() != null && !karte.getIsbn().equals("")) {
            isbn = "'" + karte.getIsbn().replace("'", "''") + "'";
        }
        insert.append(COLUMN_ISBN);
        values.append(isbn);

        insert.append(", ");
        values.append(", ");

        String untertitel = "null";
        if (karte.getUntertitel() != null && !karte.getUntertitel().equals("")) {
            untertitel = "'" + karte.getUntertitel().replace("'", "''") + "'";
        }
        insert.append(COLUMN_UNTERTITEL);
        values.append(untertitel);

        insert.append(", ");
        values.append(", ");

        insert.append(COLUMN_VERLAG);
        if (karte.getVerlag() == null) {
            values.append(0);
        } else {
            values.append(karte.getVerlag().getId());
        }

        values.append(")");
        insert.append(values);

        System.out.println(insert);

        try {
            Statement s = c.createStatement();
            s.execute(insert.toString());
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    public boolean deleteKarte(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    public Karte findKarte() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean updateKarte(Karte karte) throws SQLException, ClassNotFoundException {
        StringBuffer update = new StringBuffer("update ");
        update.append(TABLE_NAME);
        update.append(" set ");

        String ausgabejahr = "null";
        if (karte.getAusgabejahr() != 0) {
            ausgabejahr = Integer.toString(karte.getAusgabejahr());
        }
        update.append(COLUMN_AUSGABEJAHR);
        update.append("=");
        update.append(ausgabejahr);
        update.append(",");

        String blattnummer = "null";
        if (karte.getBlattnummer() != null && !karte.getBlattnummer().equals("")) {
            blattnummer = "'" + karte.getBlattnummer().replace("'", "''") + "'";
        }
        update.append(COLUMN_BLATTNUMMER);
        update.append("=");
        update.append(blattnummer);
        update.append(",");

        String extent = "null";
        if (karte.getExtent() != null && !karte.getExtent().toString().equals("")) {
            Polygon polygon = karte.getExtent();
            int srid = polygon.getSRID();
            if (srid <= 0) {
                srid = 4326;
            }
            extent = "'SRID=" + srid + ";" + polygon.toText() + "'";
        }
        update.append(COLUMN_EXTENT);
        update.append("=");
        update.append(extent);
        update.append(",");

        update.append(COLUMN_KARTENTYP);
        update.append("=");
        update.append(karte.getKartentyp().ordinal() + 1);
        update.append(",");

        String titel = "null";
        if (karte.getTitel() != null && !karte.getTitel().equals("")) {
            titel = "'" + karte.getTitel().replace("'", "''") + "'";
        }
        update.append(COLUMN_TITEL);
        update.append("=");
        update.append(titel);
        update.append(",");

        String isbn = "null";
        if (karte.getIsbn() != null && !karte.getIsbn().equals("")) {
            isbn = "'" + karte.getIsbn().replace("'", "''") + "'";
        }
        update.append(COLUMN_ISBN);
        update.append("=");
        update.append(isbn);
        update.append(",");

        String massstab = "null";
        if (karte.getMassstab() != null && !karte.getMassstab().equals("")) {
            massstab = "'" + karte.getMassstab().replace("'", "''") + "'";
        }
        update.append(COLUMN_MASSSTAB);
        update.append("=");
        update.append(massstab);
        update.append(",");

        String untertitel = "null";
        if (karte.getUntertitel() != null && !karte.getUntertitel().equals("")) {
            untertitel = "'" + karte.getUntertitel().replace("'", "''") + "'";
        }
        update.append(COLUMN_UNTERTITEL);
        update.append("=");
        update.append(untertitel);
        update.append(",");

        update.append(COLUMN_VERLAG);
        update.append("=");

        if (karte.getVerlag() == null) {
            update.append("0");
        } else {
            update.append(karte.getVerlag().getId());
        }

        update.append(" where ");
        update.append(COLUMN_ID);
        update.append("=");
        update.append(karte.getId());

        System.out.println(update);

        Connection c = PostgreSQL_DAOFactory.getConnection();
        Statement s = c.createStatement();
        return s.execute(update.toString());
    }

    public List<Karte> selectKarte(Integer id) {
        List<Karte> list = new ArrayList<Karte>();

        try {
            Connection c = PostgreSQL_DAOFactory.getConnection();
            Statement s = c.createStatement();

            StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
            if (id != null) {
                sb.append(" where id = ");
                sb.append(id);
            }
            sb.append(" order by ");
            sb.append(COLUMN_TITEL);
            sb.append(", ");
            sb.append(COLUMN_UNTERTITEL);

            ResultSet rs = s.executeQuery(sb.toString());

            while (rs.next()) {
                Karte modLoc = new Karte();
                modLoc.setId(rs.getInt("id"));
                modLoc.setAusgabejahr(rs.getInt(COLUMN_AUSGABEJAHR));
                modLoc.setBlattnummer(rs.getString(COLUMN_BLATTNUMMER));

                PGgeometry geom = (PGgeometry) rs.getObject(COLUMN_EXTENT);
                if (geom != null && !geom.toString().equals("")) {
                    String wkt = geom.getValue();
                    String srid = null;
                    if (wkt.startsWith("SRID")) {
                        String[] split = PGgeometry.splitSRID(geom.getValue());
                        srid = split[0].substring(5);
                        wkt = split[1];
                    }

//					System.out.println(wkt);
                    Polygon polygon = (Polygon) wktReader.read(wkt);
                    if (srid != null) {
                        polygon.setSRID(Integer.parseInt(srid));
                    }
                    modLoc.setExtent(polygon);
                }
                modLoc.setMassstab(rs.getString(COLUMN_MASSSTAB));

                int ktyp = rs.getInt(COLUMN_KARTENTYP);
                Kartentyp kartentyp = Kartentyp.values()[ktyp - 1];

                modLoc.setKartentyp(kartentyp);
                modLoc.setTitel(rs.getString(COLUMN_TITEL));
                modLoc.setUntertitel(rs.getString(COLUMN_UNTERTITEL));
                modLoc.setIsbn(rs.getString(COLUMN_ISBN));
                List<Verlag> verlage = verlagDao.selectVerlag(rs.getInt(COLUMN_VERLAG));
                if (verlage.size() > 0) {
                    modLoc.setVerlag(verlage.get(0));
                }

                list.add(modLoc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Karte> selectKarte() {
        return selectKarte(null);
    }

    public boolean transformExtent(Integer kartenId, Integer to) {
        if (kartenId == null || to == null) {
            return false;
        }

        Polygon polygon = null;

        String sql = "select transform(extent, " + to + ") from karte where id = " + kartenId;

        Statement s = null;
        ResultSet rs = null;

        try {
            Connection c = PostgreSQL_DAOFactory.getConnection();
            s = c.createStatement();

            rs = s.executeQuery(sql.toString());

            while (rs.next()) {
                PGgeometry geom = (PGgeometry) rs.getObject(1);
                if (geom != null && !geom.toString().equals("")) {
                    String wkt = geom.getValue();
                    String srid = null;
                    if (wkt.startsWith("SRID")) {
                        String[] split = PGgeometry.splitSRID(geom.getValue());
                        srid = split[0].substring(5);
                        wkt = split[1];
                    }

//					System.out.println(wkt);
                    polygon = (Polygon) wktReader.read(wkt);
                    if (srid != null) {
                        polygon.setSRID(Integer.parseInt(srid));
                    }
                }
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                s.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        sql = "update karte set extent = ";

        int srid = polygon.getSRID();
        if (srid <= 0) {
            srid = 4326;
        }
        sql += "'SRID=" + srid + ";" + polygon.toText() + "'";
        sql += " where id = " + kartenId;

        boolean result = false;
        try {
            Connection c = PostgreSQL_DAOFactory.getConnection();
            s = c.createStatement();
            s.execute(sql.toString());
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        PostgreSQL_KarteDAO dao = new PostgreSQL_KarteDAO();
        dao.transformExtent(20, 4326);
    }
}
