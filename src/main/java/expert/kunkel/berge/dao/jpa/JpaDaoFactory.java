package expert.kunkel.berge.dao.jpa;

//CSV concrete DAO Factory implementation
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

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

	public JpaKarteDao getKarteDAO() {
		return new JpaKarteDao();
	}

	public JpaRegionDao getRegionDAO() {
		return new JpaRegionDao();
	}

	public JpaPunktDao getPunktDAO() {
		return new JpaPunktDao();
	}

	public JpaPunkttypDao getPunkttypDAO() {
		return new JpaPunkttypDao();
	}

	public JpaTourDao getTourDAO() {
		return new JpaTourDao();
	}

	public JpaTourabschnittDao getTourabschnittDAO() {
		return new JpaTourabschnittDao();
	}

	public JpaTourentagDao getTourentagDAO() {
		return new JpaTourentagDao();
	}

	public JpaVerlagDao getVerlagDAO() {
		return new JpaVerlagDao();
	}

	public JpaGaleriebildDao getGaleriebildDAO() {
		return new JpaGaleriebildDao();
	}

	public JpaKartentypDao getKartentypDao() {
		return new JpaKartentypDao();
	}
}
