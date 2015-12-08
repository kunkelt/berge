package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Punkttyp;

public class JpaPunkttypDaoTest {

	private static JpaPunkttypDao dao;

	@BeforeClass
	public static void setUp() {
		dao = JpaDaoFactory.getInstance().getPunkttypDAO();
	}

	@Test
	public void testSelectPunkttyp() {
		Punkttyp punkttyp = new Punkttyp();
		punkttyp.setName("Name 1");

		Punkttyp punkttyp2 = dao.insertPunkttyp(punkttyp);

		Punkttyp punkttyp3 = dao.findById(punkttyp2.getId());
		assertEquals(punkttyp2, punkttyp3);

		Punkttyp punkttyp4 = new Punkttyp();
		punkttyp4.setName("Name 2");
		punkttyp4 = dao.insertPunkttyp(punkttyp4);

		List<Punkttyp> listVerlage = dao.selectPunkttyp();
		assertEquals(2, listVerlage.size());
		assertEquals(punkttyp2, listVerlage.get(0));
		assertEquals(punkttyp4, listVerlage.get(1));
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE punkttyp").executeUpdate();
		em.getTransaction().commit();
	}
}
