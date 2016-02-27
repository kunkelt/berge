package expert.kunkel.berge.dao.postgresql;

import expert.kunkel.berge.dao.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PostgreSQL_TourDAO implements TourDAO {

	private DAOFactory factory = DAOFactory
			.getDAOFactory(DAOFactory.POSTGRESQL);
	private static final String TABLE_NAME = "tour";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_BESCHREIBUNG = "beschreibung";
	private static final String COLUMN_LINK = "link";
	private static final String COLUMN_ZUSATZINFO = "zusatzinfo";
	private static final String TABLE_TOUR_KARTE = "tour_karte";
	private static final String COLUMN_ID_TOUR = "id_tour";
	private static final String COLUMN_ID_KARTE = "id_karte";
	private static final String COLUMN_GEPLANT = "geplant";

	public boolean deleteTour(Tour tour) throws SQLException,
			ClassNotFoundException {
		if (tour == null || tour.getId() <= 0) {
			return false;
		}

		Connection c = PostgreSQL_DAOFactory.getConnection();

		// erst die Referenzen auf die Tour löschen
		Statement s = c.createStatement();

		StringBuffer deleteSql = new StringBuffer();
		deleteSql.append("delete from ");
		deleteSql.append(TABLE_TOUR_KARTE);
		deleteSql.append(" where ");
		deleteSql.append(COLUMN_ID_TOUR);
		deleteSql.append(" = ");
		deleteSql.append(tour.getId());

		s.execute(deleteSql.toString());
		s.close();

		// dann die Tour löschen
		s = c.createStatement();

		deleteSql = new StringBuffer();
		deleteSql.append("delete from ");
		deleteSql.append(TABLE_NAME);
		deleteSql.append(" where ");
		deleteSql.append(COLUMN_ID);
		deleteSql.append(" = ");
		deleteSql.append(tour.getId());

		s.execute(deleteSql.toString());
		s.close();

		return true;
	}

	public List<Tour> findTour(String filter) {
		return null;
	}

	public int insertTour(Tour tour) throws SQLException,
			ClassNotFoundException {
		int nextId = -1;
		Connection c = PostgreSQL_DAOFactory.getConnection();
		PreparedStatement ps = null;

		Statement s = c.createStatement();
		String selectId = new String("select max(" + COLUMN_ID + ")+1 from "
				+ TABLE_NAME);
		ResultSet rs = s.executeQuery(selectId);
		if (rs == null || !rs.next() || rs.getInt(1) == 0) {
			nextId = 1;
		} else {
			nextId = rs.getInt(1);
		}
		rs.close();
		s.close();

		StringBuffer insert = new StringBuffer("insert into ");
		insert.append(TABLE_NAME);
		insert.append(" (");
		insert.append(COLUMN_ID);
		insert.append(", ");
		insert.append(COLUMN_NAME);
		insert.append(", ");
		insert.append(COLUMN_LINK);
		insert.append(", ");
		insert.append(COLUMN_BESCHREIBUNG);
		insert.append(", ");
		insert.append(COLUMN_ZUSATZINFO);
		insert.append(", ");
		insert.append(COLUMN_GEPLANT);
		insert.append(") ");
		insert.append(" values (?,?,?,?,?,?)");

		System.out.println(insert);

		ps = c.prepareStatement(insert.toString());

		ps.setInt(1, nextId);
		ps.setString(2, tour.getName());
		ps.setString(3, tour.getLink());
		ps.setString(4, tour.getBeschreibung());
		ps.setString(5, tour.getZusatzinfo());
		ps.setBoolean(6, tour.isGeplant());

		ps.execute();
		ps.close();

		tour.setId(nextId);

		List<Karte> karten = tour.getKarten();
		for (Iterator<Karte> it = karten.iterator(); it.hasNext();) {
			Karte karte = it.next();

			StringBuffer sb = new StringBuffer();
			sb.append("insert into ");
			sb.append(TABLE_TOUR_KARTE);
			sb.append("(");
			sb.append(COLUMN_ID_TOUR);
			sb.append(", ");
			sb.append(COLUMN_ID_KARTE);
			sb.append(") values (");
			sb.append(tour.getId());
			sb.append(",");
			sb.append(karte.getId());
			sb.append(")");
			s = c.createStatement();

			System.out.println(sb.toString());

			s.execute(sb.toString());
			s.close();
		}

		return 0;
	}

	public List<Tour> selectTour(Boolean geplant) {
		return selectTour(null, geplant);
	}

	public List<Tour> selectTour(Integer id, Boolean geplant) {
		List<Tour> list = new ArrayList<Tour>();

		try {
			Connection c = PostgreSQL_DAOFactory.getConnection();
			Statement s = c.createStatement();

			StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
			if (id != null) {
				sb.append(" where id = ");
				sb.append(id);
				if (geplant != null) {
					sb.append(" and geplant = ").append(geplant);
				}
			} else {
				if (geplant != null) {
					sb.append(" where geplant = ").append(geplant);
				}
			}
			sb.append(" order by ");
			sb.append(COLUMN_ID);

			ResultSet rs = s.executeQuery(sb.toString());

			while (rs.next()) {
				Tour tour = new Tour();
				tour.setId(rs.getInt(COLUMN_ID));
				tour.setName(rs.getString(COLUMN_NAME));
				tour.setBeschreibung(rs.getString(COLUMN_BESCHREIBUNG));
				tour.setLink(rs.getString(COLUMN_LINK));
				tour.setZusatzinfo(rs.getString(COLUMN_ZUSATZINFO));
				tour.setGeplant(rs.getBoolean(COLUMN_GEPLANT));

				// alle Karten für die Tour holen
				tour.setKarten(selectKartenFuerTour(tour.getId()));

				list.add(tour);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean updateTour(Tour tour) throws SQLException,
			ClassNotFoundException {
		Connection c = PostgreSQL_DAOFactory.getConnection();
		PreparedStatement ps = null;

		StringBuffer updateSql = new StringBuffer("update ");
		updateSql.append(TABLE_NAME);
		updateSql.append(" set ");
		updateSql.append(COLUMN_NAME);
		updateSql.append(" = ?, ");
		updateSql.append(COLUMN_BESCHREIBUNG);
		updateSql.append(" = ?, ");
		updateSql.append(COLUMN_LINK);
		updateSql.append(" = ?, ");
		updateSql.append(COLUMN_ZUSATZINFO);
		updateSql.append(" = ?, ");
		updateSql.append(COLUMN_GEPLANT);
		updateSql.append(" = ? ");
		updateSql.append(" where ");
		updateSql.append(COLUMN_ID);
		updateSql.append(" = ?");

		System.out.println(updateSql);

		ps = c.prepareStatement(updateSql.toString());

		ps.setString(1, tour.getName());
		ps.setString(2, tour.getBeschreibung());
		ps.setString(3, tour.getLink());
		ps.setString(4, tour.getZusatzinfo());
		ps.setBoolean(5, tour.isGeplant());
		ps.setInt(6, tour.getId());

		ps.execute();
		ps.close();

		// jetzt müssen noch die FKs zur Karte aktualisiert werden
		String deleteSql = new String("delete from " + TABLE_TOUR_KARTE
				+ " where " + COLUMN_ID_TOUR + " = " + tour.getId());
		Statement s = c.createStatement();
		s.execute(deleteSql);
		s.close();

		for (Iterator<Karte> it = tour.getKarten().iterator(); it.hasNext();) {
			Karte karte = it.next();

			String insertSql = "insert into " + TABLE_TOUR_KARTE + " ("
					+ COLUMN_ID_TOUR + ", " + COLUMN_ID_KARTE + ") values ("
					+ tour.getId() + ", " + karte.getId() + ")";

			s = c.createStatement();
			s.execute(insertSql);
			s.close();
		}

		return true;
	}

	public List<Tour> getAllToursForYear(int year) {
		List<Tour> list = new ArrayList<Tour>();
		HashSet<Integer> ids = new HashSet<Integer>();

		StringBuffer sql = new StringBuffer(
				"select distinct t.id, t.name, t.beschreibung, t.link, t.zusatzinfo, tt.datum from tour t, tourentag tt where t.id = tt.id_tour and datum >= '");
		sql.append(year);
		sql.append("-01-01' and datum <= '");
		sql.append(year);
		sql.append("-12-31' and geplant = false order by datum desc;");

		try {
			Connection c = PostgreSQL_DAOFactory.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql.toString());

			while (rs.next()) {
				Integer id = rs.getInt(COLUMN_ID);
				if (!ids.contains(id)) {
					ids.add(id);
					Tour tour = new Tour();
					tour.setId(rs.getInt(COLUMN_ID));
					tour.setName(rs.getString(COLUMN_NAME));
					tour.setBeschreibung(rs.getString(COLUMN_BESCHREIBUNG));
					tour.setLink(rs.getString(COLUMN_LINK));
					tour.setZusatzinfo(rs.getString(COLUMN_ZUSATZINFO));
					// alle Karten für die Tour holen
					tour.setKarten(selectKartenFuerTour(tour.getId()));
					list.add(tour);
				}
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	private List<Karte> selectKartenFuerTour(int tourId) {
		List<Karte> list = new ArrayList<Karte>();

		try {
			Connection c = PostgreSQL_DAOFactory.getConnection();
			Statement s = c.createStatement();

			StringBuffer sb = new StringBuffer("select " + COLUMN_ID_KARTE
					+ " from " + TABLE_TOUR_KARTE);
			sb.append(" where " + COLUMN_ID_TOUR + " = ");
			sb.append(tourId);

			ResultSet rs = s.executeQuery(sb.toString());

			while (rs.next()) {
				int idKarte = rs.getInt(COLUMN_ID_KARTE);

				PostgreSQL_KarteDAO karteDao = (PostgreSQL_KarteDAO) factory
						.getKarteDAO();
				List<Karte> karten = karteDao.selectKarte(idKarte);

				list.add(karten.get(0));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Tour> findTourInRegion(Region region) {
		List<Tour> list = new ArrayList<Tour>();

		try {
			Connection c = PostgreSQL_DAOFactory.getConnection();
			Statement s = c.createStatement();

			StringBuffer sb = new StringBuffer(
					"SELECT DISTINCT tour.id FROM public.tour, public.tourentag "
							+ " WHERE tourentag.id_tour = tour.id");
			sb.append(" AND tourentag.region = " + region.getId());
			
			ResultSet rs = s.executeQuery(sb.toString());

			while (rs.next()) {
				List<Tour> touren = selectTour(rs.getInt(1), Boolean.FALSE);

				list.add(touren.get(0));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
