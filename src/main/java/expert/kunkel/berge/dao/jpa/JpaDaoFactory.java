package expert.kunkel.berge.dao.jpa;

//CSV concrete DAO Factory implementation
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

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
	private EntityManager em = getEntityManager();

	private static JpaDaoFactory instance;

	public static JpaDaoFactory getInstance() {
		if (instance == null) {
			instance = new JpaDaoFactory();
		}
		return instance;
	}

	public EntityManager getEntityManager() {
    	if (em == null) {
    		em = Persistence.createEntityManagerFactory("Berge").createEntityManager();
    	}
    	return em;
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
