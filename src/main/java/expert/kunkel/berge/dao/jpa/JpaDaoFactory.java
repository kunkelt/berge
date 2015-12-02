package expert.kunkel.berge.dao.jpa;

//CSV concrete DAO Factory implementation
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import expert.kunkel.berge.dao.DAOFactory;
import expert.kunkel.berge.dao.GaleriebildDAO;
import expert.kunkel.berge.dao.KarteDAO;
import expert.kunkel.berge.dao.PunktDAO;
import expert.kunkel.berge.dao.PunkttypDAO;
import expert.kunkel.berge.dao.RegionDAO;
import expert.kunkel.berge.dao.TourDAO;
import expert.kunkel.berge.dao.TourabschnittDAO;
import expert.kunkel.berge.dao.TourentagDAO;
import expert.kunkel.berge.dao.VerlagDAO;

public class JpaDaoFactory extends DAOFactory {
//	public static final String DATABASE = "jdbc:postgresql://localhost/bergetest";

    public static final String DATABASE = "jdbc:postgresql://localhost:5432/berge_test";
    public static final String USER = "thorsten";
    public static final String PASSWORD = "thorsten";
    public static final String DRIVER_NAME = "org.postgresql.Driver";
    private static Connection c = null;

    @PersistenceUnit 
    EntityManagerFactory emf;
    
    private static EntityManager emInstance;
    
    // method to create  connections
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (c == null || c.isClosed()) {
            Class.forName(DRIVER_NAME);
            c = DriverManager.getConnection(DATABASE, USER, PASSWORD);
        }
        return c;
    }
    
    public static EntityManager getEntityManager() {
    	if (emInstance ==null) {
    		emInstance = emf...
    	}
    	return emInstance;
    }

    @Override
    public KarteDAO getKarteDAO() {
        return new JpaKarteDao();
    }

    @Override
    public RegionDAO getRegionDAO() {
        return new JpaRegionDao();
    }

    @Override
    public PunktDAO getPunktDAO() {
        return new JpaPunktDao();
    }

    @Override
    public PunkttypDAO getPunkttypDAO() {
        return new JpaPunkttypDao();
    }

    @Override
    public TourDAO getTourDAO() {
        return new JpaTourDao();
    }

    @Override
    public TourabschnittDAO getTourabschnittDAO() {
        return new JpaTourabschnittDao();
    }

    @Override
    public TourentagDAO getTourentagDAO() {
        return new JpaTourentagDao();
    }

    @Override
    public VerlagDAO getVerlagDAO() {
        return new JpaVerlagDao();
    }

    @Override
    public GaleriebildDAO getGaleriebildDAO() {
        return new JpaGaleriebildDao();
    }
}
