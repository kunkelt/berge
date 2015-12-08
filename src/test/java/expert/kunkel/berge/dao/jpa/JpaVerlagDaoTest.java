package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Verlag;

public class JpaVerlagDaoTest {

	private static final String ANSCHRIFT = "Straße 1";
	private static final String BEZUGSQUELLE = "Irgendwo";
	private static final String NAME = "Name des Verlagssssssssss";
	private static final String TELEFON = "+49 89/123789098765";
	private static JpaVerlagDao dao;

	@BeforeClass
	public static void setUp() {
		dao = JpaDaoFactory.getInstance().getVerlagDAO();
	}

	@Test
	public void testSelectVerlag() {
		Verlag verlag = new Verlag();
		verlag.setAnschrift(ANSCHRIFT);
		verlag.setBezugsquelle(BEZUGSQUELLE);
		verlag.setName(NAME);
		verlag.setTelefon(TELEFON);

		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Verlag verlag2 = em.merge(verlag);
		em.getTransaction().commit();

		Verlag verlag3 = dao.findById(verlag2.getId());
		assertEquals(verlag2, verlag3);

		List<Verlag> listVerlage = dao.selectVerlag();
		assertEquals(1, listVerlage.size());
		assertEquals(verlag2, listVerlage.get(0));

		assertEquals(
				1l,
				em.createNativeQuery(
						"SELECT COUNT(*) FROM verlag WHERE id = "
								+ verlag2.getId()).getSingleResult());
	}

	@Test
	public void testSelectVerlagOld() {
		try {
			dao.selectVerlag(1);
		} catch (UnsupportedOperationException e) {
			return;
		}
		fail("Keine UnsupportedOperationException aufgetreten!");
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE verlag CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
}
