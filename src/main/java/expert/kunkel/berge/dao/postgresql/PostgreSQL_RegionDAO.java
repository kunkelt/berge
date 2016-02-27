package expert.kunkel.berge.dao.postgresql;

import expert.kunkel.berge.dao.*;

import java.sql.*;
import java.util.*;

import org.postgis.PGgeometry;

import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;

public class PostgreSQL_RegionDAO implements RegionDAO {

	private DAOFactory factory = DAOFactory
			.getDAOFactory(DAOFactory.POSTGRESQL);
	private PunktDAO punktDao = factory.getPunktDAO();
	private static final String TABLE_NAME = "region";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_EXTENT = "extent";
	private static final String COLUMN_UMGRENZUNG = "umgrenzung";
	private static final String COLUMN_HOECHSTER_PUNKT = "hoechster_punkt";
	private static final String COLUMN_OBERREGION = "oberregion";
	private WKTReader wktReader = new WKTReader();

	public PostgreSQL_RegionDAO() {
		super();
	}

	public int insertRegion(Region region) {
		StringBuffer insert = new StringBuffer("insert into ");
		insert.append(TABLE_NAME);
		insert.append(" (");
		StringBuffer values = new StringBuffer(") values (");

		insert.append(COLUMN_ID);
		values.append(region.getId());

		insert.append(", ");
		values.append(", ");

		insert.append(COLUMN_NAME);
		values.append("'" + region.getName() + "'");

		insert.append(", ");
		values.append(", ");

		String extent = "null";
		if (!region.getExtent().equals("")) {
			extent = "geometry('SRID=4326;" + region.getExtent() + "')";
		}
		insert.append(COLUMN_EXTENT);
		values.append(extent);

		insert.append(", ");
		values.append(", ");

		String umgrenzung = "null";
		if (!region.getUmgrenzung().equals("")) {
			umgrenzung = "'" + region.getUmgrenzung().replace("'", "''") + "'";
		}
		insert.append(COLUMN_UMGRENZUNG);
		values.append(umgrenzung);

		insert.append(", ");
		values.append(", ");

		insert.append(COLUMN_HOECHSTER_PUNKT);
		values.append(region.getHoechsterPunkt());

		insert.append(", ");
		values.append(", ");

		insert.append(COLUMN_OBERREGION);
		values.append(region.getOberregion());

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

	public boolean deleteRegion(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Region findRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateRegion(Region region) throws SQLException,
			ClassNotFoundException {
		StringBuffer update = new StringBuffer("update ");
		update.append(TABLE_NAME);
		update.append(" set ");

		update.append(COLUMN_HOECHSTER_PUNKT);
		update.append("=");
		if (region.getHoechsterPunkt() != null) {
			update.append(region.getHoechsterPunkt().getId());
		} else {
			update.append("null");

		}
		update.append(",");

		String name = "null";
		if (region.getName() != null && !region.getName().equals("")) {
			name = "'" + region.getName().replace("'", "''") + "'";
		}
		update.append(COLUMN_NAME);
		update.append("=");
		update.append(name);
		update.append(",");

		String extent = "null";
		if (region.getExtent() != null && !region.getExtent().equals("")) {
			extent = "'SRID=4326;"
					+ region.getExtent().toText().replace("'", "''") + "'";
		}
		update.append(COLUMN_EXTENT);
		update.append("=GeomFromEWKT(");
		update.append(extent);
		update.append("),");

		update.append(COLUMN_OBERREGION);
		update.append("=");
		if (region.getOberregion() != null) {
			update.append(region.getOberregion().getId());
		} else {
			update.append("null");
		}
		update.append(",");

		String umgrenzung = "null";
		if (region.getUmgrenzung() != null
				&& !region.getUmgrenzung().equals("")) {
			umgrenzung = "'" + region.getUmgrenzung().replace("'", "''") + "'";
		}
		update.append(COLUMN_UMGRENZUNG);
		update.append("=");
		update.append(umgrenzung);

		update.append(" where ");
		update.append(COLUMN_ID);
		update.append("=");
		update.append(region.getId());

		System.out.println(update);

		Connection c = PostgreSQL_DAOFactory.getConnection();
		Statement s = c.createStatement();
		return s.execute(update.toString());
	}

	public List<Region> selectRegion(Integer id) {
		List<Region> list = new ArrayList<Region>();

		try {
			Connection c = PostgreSQL_DAOFactory.getConnection();
			Statement s = c.createStatement();

			StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
			if (id != null) {
				sb.append(" where id = ");
				sb.append(id);
			} else {
				sb.append(" ORDER BY ");
				sb.append(COLUMN_NAME);
			}

			ResultSet rs = s.executeQuery(sb.toString());

			while (rs.next()) {
				Region region = new Region();
				region.setId(rs.getInt("id"));

				int punktId = rs.getInt(COLUMN_HOECHSTER_PUNKT);
				if (punktId > 0) {
					List<Punkt> punkte = punktDao.selectPunkt(punktId);
					if (punkte != null && punkte.size() == 1) {
						region.setHoechsterPunkt(punkte.get(0));
					}
				}

				region.setName(rs.getString(COLUMN_NAME));

				PGgeometry geom = (PGgeometry) rs.getObject(COLUMN_EXTENT);
				if (geom != null && !geom.equals("")) {
					// String wkt = geom.getValue();
					String wkt = PGgeometry.splitSRID(geom.getValue())[1];
					// System.out.println(wkt);
					region.setExtent((Polygon) wktReader.read(wkt));
				}

				int oberregionId = rs.getInt(COLUMN_OBERREGION);
				if (oberregionId > 0) {
					List<Region> regionen = this.selectRegion(oberregionId);
					if (regionen != null && regionen.size() == 1) {
						region.setOberregion(regionen.get(0));
					}
				}

				region.setUmgrenzung(rs.getString(COLUMN_UMGRENZUNG));

				list.add(region);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Region> selectRegion() {
		return selectRegion(null);
	}

	public Region getOberregion(Region region) {
		List<Region> regionen = selectRegion(Integer.valueOf(region.getId()));
		return regionen.get(0);
	}

	@Override
	public List<Region> findUsedRegions() {
		List<Region> list = new ArrayList<Region>();

		try {
			Connection c = PostgreSQL_DAOFactory.getConnection();
			Statement s = c.createStatement();

			StringBuffer sb = new StringBuffer(
					"select DISTINCT region.id, region.name from " + TABLE_NAME
							+ ", tour, tourentag ");
			sb.append(" where tourentag.region = region.id AND ");
			sb.append(" tourentag.id_tour = tour.id ");
			sb.append(" ORDER BY ");
			sb.append(" region.name");

			ResultSet rs = s.executeQuery(sb.toString());

			while (rs.next()) {
				list.add(selectRegion(rs.getInt("id")).get(0));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
