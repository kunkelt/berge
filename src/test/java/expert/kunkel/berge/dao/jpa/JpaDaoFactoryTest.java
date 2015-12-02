package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.Test;

public class JpaDaoFactoryTest {

	@Test
	public void testEntityManager() {
		JpaDaoFactory jpaMgr = JpaDaoFactory.getInstance();
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
		assertTrue(JpaDaoFactory.getInstance().getEntityManager() == 
				JpaDaoFactory.getInstance().getEntityManager());
	}

}
