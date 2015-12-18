package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Kartentyp;

public class JpaKartentypDaoTest {

	private static JpaKartentypDao dao;

	@BeforeClass
	public static void setUp() {
		dao = JpaDaoFactory.getInstance().getKartentypDao();
	}

	@Test
	public void testSelectPunkttyp() {
		Kartentyp kartentyp = new Kartentyp();
		kartentyp.setTyp("Name 1");

		Kartentyp kartentyp2 = dao.insertKartentyp(kartentyp);

		Kartentyp kartentyp3 = dao.findById(kartentyp2.getId());
		assertEquals(kartentyp2, kartentyp3);

		Kartentyp kartentyp4 = new Kartentyp();
		kartentyp4.setTyp("Name 2");
		kartentyp4 = dao.insertKartentyp(kartentyp4);

		List<Kartentyp> listKartentyp = dao.selectKartentyp();
		assertEquals(2, listKartentyp.size());
		assertEquals(kartentyp2, listKartentyp.get(0));
		assertEquals(kartentyp4, listKartentyp.get(1));
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE kartentyp CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
}
