package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.Test;

public class DaoFactoryTest {

	@Test
	public void testEntityManager() {
		DaoFactory jpaMgr = DaoFactory.getInstance();
		EntityManager em = jpaMgr.getEntityManager();

		assertNotNull(em);
		assertNotNull(jpaMgr.getGaleriebildDAO());
		assertNotNull(jpaMgr.getKarteDAO());
		assertNotNull(jpaMgr.getPunktDAO());
		assertNotNull(jpaMgr.getPunkttypDAO());
		assertNotNull(jpaMgr.getRegionDAO());
		assertNotNull(jpaMgr.getTourabschnittDAO());
		assertNotNull(jpaMgr.getTourDAO());
		assertNotNull(jpaMgr.getTourentagDAO());
		assertNotNull(jpaMgr.getVerlagDAO());
		assertTrue(DaoFactory.getInstance().getEntityManager() == 
				DaoFactory.getInstance().getEntityManager());
	}

}
