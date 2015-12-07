package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Galeriebild;
import expert.kunkel.berge.model.Tour;

public class JpaGaleriebildDaoTest {

	private static JpaGaleriebildDao dao;
	private static JpaTourDao daoTour;

	private static final int sequenz = 1;
	private static final int breite = 600;
	private static final int hoehe = 400;
	private static final String dateiname = "/tmp/test.png";
	private static final String titel = "Wurstsalat";

	@BeforeClass
	public static void setUp() {
		dao = JpaDaoFactory.getInstance().getGaleriebildDAO();
		daoTour = JpaDaoFactory.getInstance().getTourDAO();
	}

	@Test
	public void testSelectGaleriebild() {
		Tour tour = new Tour();
		tour.setName("Testtour");
		tour.setGeplant(Boolean.TRUE);
		Tour tour2 = daoTour.insertTour(tour); // FIXME

		Galeriebild bild = new Galeriebild();
		bild.setBreite(breite);
		bild.setDateiname(dateiname);
		bild.setHoehe(hoehe);
		bild.setTitel(titel);
		bild.setTour(tour2);
		bild.setSequenz(sequenz);
		bild.setTour(tour2);

		Galeriebild bild2 = dao.insertGaleriebild(bild);
		Galeriebild bild3 = dao.findById(bild2.getId());

		assertEquals(bild2, bild3);
		assertEquals(bild2.getTour().getId(), bild3.getTour().getId());
		assertEquals(bild2.getId(), bild3.getId());

		List<Galeriebild> listBilder = dao.selectGaleriebild();
		assertEquals(1, listBilder.size());
		assertEquals(bild2, listBilder.get(0));

		listBilder = dao.selectGaleriebild(tour2);
		assertEquals(1, listBilder.size());
		assertEquals(bild2, listBilder.get(0));

		dao.deleteGaleriebild(tour2);
		assertEquals(0, dao.selectGaleriebild(tour2).size());
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE galeriebild CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE tour CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
}
