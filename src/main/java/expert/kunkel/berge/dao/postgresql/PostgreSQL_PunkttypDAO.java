package expert.kunkel.berge.dao.postgresql;

import expert.kunkel.berge.dao.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class PostgreSQL_PunkttypDAO implements PunkttypDAO {

	private static final String TABLE_NAME = "punkttyp";

	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";

	public PostgreSQL_PunkttypDAO() {
		super();
	}

	public Punkttyp selectPunkttyp(Integer id) {
		Punkttyp punkttyp = null;

		if (id == null) {
			return null;
		}
		try {
			Connection c = PostgreSQL_DAOFactory.getConnection();
			Statement s = c.createStatement();

			StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);
			sb.append(" where "+COLUMN_ID+" = ");
			sb.append(id);

			ResultSet rs = s.executeQuery(sb.toString());

			if (rs.next()) {
				punkttyp = new Punkttyp();
				punkttyp.setId(rs.getInt(COLUMN_ID));
				punkttyp.setName(rs.getString(COLUMN_NAME));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return punkttyp;
	}

	public List<Punkttyp> selectPunkttyp() {
		List<Punkttyp> punkttypen = new ArrayList<Punkttyp>();
		try {
			Connection c = PostgreSQL_DAOFactory.getConnection();
			Statement s = c.createStatement();

			StringBuffer sb = new StringBuffer("select * from " + TABLE_NAME);

			ResultSet rs = s.executeQuery(sb.toString());

			while (rs.next()) {
				Punkttyp punkttyp = new Punkttyp();
				punkttyp.setId(rs.getInt(COLUMN_ID));
				punkttyp.setName(rs.getString(COLUMN_NAME));
				punkttypen.add(punkttyp);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return punkttypen;
	}

}
