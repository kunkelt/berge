package expert.kunkel.berge.dao;

//Abstract class DAO Factory

import expert.kunkel.berge.dao.postgresql.PostgreSQL_DAOFactory;

public abstract class DAOFactory {
	
	// List of DAO types supported by the factory
	public static final int MYSQL = 1;
	public static final int POSTGRESQL = 2;
	
	// There will be a method for each DAO that can be 
	// created. The concrete factories will have to 
	// implement these methods.
	public abstract GaleriebildDAO getGaleriebildDAO();
	public abstract KarteDAO getKarteDAO();
	public abstract RegionDAO getRegionDAO();
	public abstract PunktDAO getPunktDAO();
	public abstract PunkttypDAO getPunkttypDAO();
	public abstract TourDAO getTourDAO();
	public abstract TourabschnittDAO getTourabschnittDAO();
	public abstract TourentagDAO getTourentagDAO();
	public abstract VerlagDAO getVerlagDAO();
	
	public static DAOFactory getDAOFactory(
			int whichFactory) {
		
		switch (whichFactory) {
//		case MYSQL: 
//			return new MySQL_DAOFactory();
		case POSTGRESQL: 
			return new PostgreSQL_DAOFactory();
		default           : 
			return null;
		}
	}
}