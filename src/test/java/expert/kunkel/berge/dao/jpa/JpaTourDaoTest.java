package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import expert.kunkel.berge.model.Region;
import expert.kunkel.berge.model.Tour;
import expert.kunkel.berge.model.Tourentag;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JpaTourDaoTest {

	private static JpaTourDao dao;

	private static Tour tour1;
	private static Tour tour2;
	private static Region region;

	private static final Boolean geplant = Boolean.TRUE;
	private static final String link = "http://www.example.com";
	private static final String zusatzinfo = "Zusatzinfo";

	@BeforeClass
	public static void setUp() throws ParseException {
		dao = JpaDaoFactory.getInstance().getTourDAO();

		tour1 = new Tour();
		tour1.setBeschreibung("Geplant");
		tour1.setGeplant(geplant);
		tour1.setLink(link);
		tour1.setName("Name 1");
		tour1.setZusatzinfo(zusatzinfo);

		tour2 = new Tour();
		tour2.setBeschreibung("Ungeplant");
		tour2.setGeplant(!geplant);
		tour2.setLink(link);
		tour2.setName("Name 2");
		tour2.setZusatzinfo(zusatzinfo);
	}

	@Test
	public void test1_Insert() {
		tour1 = dao.insertTour(tour1);

		Tour test = dao.findById(tour1.getId());
		assertEquals(tour1, test);
	}

	@Test
	public void test2_Update() {
		tour1.setName("Name 2");
		dao.updateTour(tour1);

		Tour test = dao.findById(tour1.getId());
		assertTrue(tour1.equals(test));
		assertEquals("Name 2", test.getName());
	}

	@Test
	public void test3_SelectGeplant() {
		tour2 = dao.insertTour(tour2);

		List<Tour> test = dao.selectTour(Boolean.TRUE);
		assertEquals(1, test.size());
		assertEquals(2, dao.selectTour(null).size());
	}

	@Test
	public void test4_TourInA_Year() throws ParseException {
		region = new Region();
		region.setName("Allgäu");
		region = JpaDaoFactory.getInstance().getRegionDAO()
				.insertRegion(region);

		Tourentag tt1 = new Tourentag();
		tt1.setDatum(DateUtils.parseDate("2014-12-31", "yyyy-MM-dd"));
		tt1.setTour(tour1);
		tt1.setTag(1);
		tt1.setRegion(region);
		tt1 = JpaDaoFactory.getInstance().getTourentagDAO()
				.insertTourentag(tt1);

		Tourentag tt2 = new Tourentag();
		tt2.setDatum(DateUtils.parseDate("2015-01-01", "yyyy-MM-dd"));
		tt2.setTour(tour1);
		tt2.setTag(2);
		tt2.setRegion(region);
		tt2 = JpaDaoFactory.getInstance().getTourentagDAO()
				.insertTourentag(tt2);

		tour1.addTourentage(tt1);
		tour1.addTourentage(tt2);

		dao.updateTour(tour1);

		assertEquals(0, dao.getAllToursForYear(2013).size());
		assertEquals(1, dao.getAllToursForYear(2014).size());
		assertEquals(1, dao.getAllToursForYear(2015).size());
		assertEquals(0, dao.getAllToursForYear(2016).size());
	}

	@Test
	public void test5_FindUsedRegions() {
		assertEquals(1, JpaDaoFactory.getInstance().getRegionDAO()
				.findUsedRegions().size());
	}

	@Test
	public void test6_FindTourInRegion() {
		List<Tour> touren = dao.findTourInRegion(region);
		assertEquals(1, touren.size());
		assertEquals(tour1, touren.get(0));
	}

	@Test
	public void test9_Delete() {
		for (Tourentag tt : tour1.getTourentage()) {
			JpaDaoFactory.getInstance().getTourentagDAO().deleteTourentag(tt);
		}

		dao.deleteTour(tour1);
		dao.deleteTour(tour2);

		List<Tour> test = dao.selectTour(null);
		assertEquals(0, test.size());
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE tour CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE tourentag CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE region CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
}
