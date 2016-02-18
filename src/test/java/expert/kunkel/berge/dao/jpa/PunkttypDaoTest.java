package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Punkttyp;

public class PunkttypDaoTest {

	private static PunkttypDao dao;

	@BeforeClass
	public static void setUp() {
		dao = DaoFactory.getInstance().getPunkttypDAO();
	}

	@Test
	public void test() {
		Punkttyp punkttyp = new Punkttyp();
		punkttyp.setName("Name 1");

		Punkttyp punkttyp2 = dao.insertPunkttyp(punkttyp);

		Punkttyp punkttyp3 = dao.findById(punkttyp2.getId());
		assertEquals(punkttyp2, punkttyp3);

		Punkttyp punkttyp4 = new Punkttyp();
		punkttyp4.setName("Name 2");
		punkttyp4 = dao.insertPunkttyp(punkttyp4);

		List<Punkttyp> listPunkttyp = dao.selectPunkttyp();
		assertEquals(2, listPunkttyp.size());
		assertEquals(punkttyp2, listPunkttyp.get(0));
		assertEquals(punkttyp4, listPunkttyp.get(1));
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE punkttyp CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
}
