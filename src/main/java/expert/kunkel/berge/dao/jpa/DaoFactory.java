package expert.kunkel.berge.dao.jpa;

//CSV concrete DAO Factory implementation
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DaoFactory {
	private EntityManager em = getEntityManager();

	private static DaoFactory instance;

	private DaoFactory() {
	}

	public static DaoFactory getInstance() {
		if (instance == null) {
			instance = new DaoFactory();
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

	public KarteDao getKarteDAO() {
		return new KarteDao();
	}

	public RegionDao getRegionDAO() {
		return new RegionDao();
	}

	public PunktDao getPunktDAO() {
		return new PunktDao();
	}

	public PunkttypDao getPunkttypDAO() {
		return new PunkttypDao();
	}

	public TourDao getTourDAO() {
		return new TourDao();
	}

	public TourabschnittDao getTourabschnittDAO() {
		return new TourabschnittDao();
	}

	public TourentagDao getTourentagDAO() {
		return new TourentagDao();
	}

	public VerlagDao getVerlagDAO() {
		return new VerlagDao();
	}

	public GaleriebildDao getGaleriebildDAO() {
		return new GaleriebildDao();
	}

	public KartentypDao getKartentypDao() {
		return new KartentypDao();
	}
}
