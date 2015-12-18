package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Region;
import expert.kunkel.berge.model.Tour;
import expert.kunkel.berge.model.Tourentag;

public class JpaTourentagDaoTest {

	private static Region region;
	private static JpaTourentagDao dao;
	private static Tour tour1;

	@BeforeClass
	public static void setUp() throws ParseException {
		dao = JpaDaoFactory.getInstance().getTourentagDAO();
	}

	@Test
	public void test() throws ParseException {
		tour1 = new Tour();
		tour1.setName("Hier waren wir");
		tour1.setGeplant(Boolean.FALSE);
		tour1 = JpaDaoFactory.getInstance().getTourDAO().insertTour(tour1);

		region = new Region();
		region.setName("Allgäu");
		region = JpaDaoFactory.getInstance().getRegionDAO()
				.insertRegion(region);

		Tourentag tt1 = new Tourentag();
		tt1.setDatum(DateUtils.parseDate("2014-12-31", "yyyy-MM-dd"));
		tt1.setTag(1);
		tt1.setTour(tour1);
		tt1.setRegion(region);
		tt1 = dao.insertTourentag(tt1);

		tt1.setTag(2);
		dao.updateTourentag(tt1);

		Tourentag tt2 = dao.findForId(tt1.getId());

		List<Tourentag> listTt = dao.findForTour(tt2.getTour());
		assertEquals(1, listTt.size());
		assertEquals(tt1, tt2);
		assertEquals(tt1, listTt.get(0));

		dao.deleteTourentag(tt1);

		assertEquals(0, dao.findForTour(tt2.getTour()).size());
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
