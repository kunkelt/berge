package expert.kunkel.berge.dao.jpa;

//CSV concrete DAO Factory implementation
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import expert.kunkel.berge.dao.KarteDAO;
import expert.kunkel.berge.dao.PunktDAO;
import expert.kunkel.berge.dao.PunkttypDAO;
import expert.kunkel.berge.dao.RegionDAO;
import expert.kunkel.berge.dao.TourDAO;
import expert.kunkel.berge.dao.TourabschnittDAO;
import expert.kunkel.berge.dao.TourentagDAO;

public class JpaDaoFactory {
	private EntityManager em = getEntityManager();

	private static JpaDaoFactory instance;

	private JpaDaoFactory() {
	}

	public static JpaDaoFactory getInstance() {
		if (instance == null) {
			instance = new JpaDaoFactory();
		}
		return instance;
	}

	public EntityManager getEntityManager() {
		if (em == null) {
			em = Persistence.createEntityManagerFactory("Berge")
					.createEntityManager();
		}
		return em;
	}

	public KarteDAO getKarteDAO() {
		return new JpaKarteDao();
	}

	public RegionDAO getRegionDAO() {
		return new JpaRegionDao();
	}

	public PunktDAO getPunktDAO() {
		return new JpaPunktDao();
	}

	public PunkttypDAO getPunkttypDAO() {
		return new JpaPunkttypDao();
	}

	public JpaTourDao getTourDAO() {
		return new JpaTourDao();
	}

	public TourabschnittDAO getTourabschnittDAO() {
		return new JpaTourabschnittDao();
	}

	public TourentagDAO getTourentagDAO() {
		return new JpaTourentagDao();
	}

	public JpaVerlagDao getVerlagDAO() {
		return new JpaVerlagDao();
	}

	public JpaGaleriebildDao getGaleriebildDAO() {
		return new JpaGaleriebildDao();
	}
}
