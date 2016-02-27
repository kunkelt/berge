package expert.kunkel.berge.dao.postgresql;

//CSV concrete DAO Factory implementation
import expert.kunkel.berge.dao.*;

import java.sql.*;

public class PostgreSQL_DAOFactory extends DAOFactory {
//	public static final String DATABASE = "jdbc:postgresql://localhost/bergetest";

    public static final String DATABASE = "jdbc:postgresql://localhost:5432/berge";
    public static final String USER = "thorsten";
    public static final String PASSWORD = "thorsten";
    public static final String DRIVER_NAME = "org.postgresql.Driver";
    private static Connection c = null;

    // method to create  connections
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (c == null || c.isClosed()) {
            Class.forName(DRIVER_NAME);
            c = DriverManager.getConnection(DATABASE, USER, PASSWORD);
        }
        return c;
    }

    @Override
    public KarteDAO getKarteDAO() {
        return new PostgreSQL_KarteDAO();
    }

    @Override
    public RegionDAO getRegionDAO() {
        return new PostgreSQL_RegionDAO();
    }

    @Override
    public PunktDAO getPunktDAO() {
        return new PostgreSQL_PunktDAO();
    }

    @Override
    public PunkttypDAO getPunkttypDAO() {
        return new PostgreSQL_PunkttypDAO();
    }

    @Override
    public TourDAO getTourDAO() {
        return new PostgreSQL_TourDAO();
    }

    @Override
    public TourabschnittDAO getTourabschnittDAO() {
        return new PostgreSQL_TourabschnittDAO();
    }

    @Override
    public TourentagDAO getTourentagDAO() {
        return new PostgreSQL_TourentagDAO();
    }

    @Override
    public VerlagDAO getVerlagDAO() {
        return new PostgreSQL_VerlagDAO();
    }

    @Override
    public GaleriebildDAO getGaleriebildDAO() {
        return new PostgreSQL_GaleriebildDAO();
    }
}
