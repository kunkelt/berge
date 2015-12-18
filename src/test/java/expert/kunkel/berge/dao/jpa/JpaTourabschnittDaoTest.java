package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Region;
import expert.kunkel.berge.model.Tour;
import expert.kunkel.berge.model.Tourabschnitt;
import expert.kunkel.berge.model.Tourentag;

public class JpaTourabschnittDaoTest {

	private static JpaTourabschnittDao dao;

	@BeforeClass
	public static void setUp() throws ParseException {
		dao = JpaDaoFactory.getInstance().getTourabschnittDAO();
	}

	private Tour tour1;
	private Region region;

	@Test
	public void test() throws Exception {
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
		tt1 = JpaDaoFactory.getInstance().getTourentagDAO()
				.insertTourentag(tt1);

		Tourabschnitt ta = new Tourabschnitt();
		ta.setTourentag(tt1);
		ta.setSequenz(1);

		ta = dao.insertTourabschnitt(ta);

		Tourabschnitt ta2 = dao.findById(ta.getId());
		assertEquals(1, ta.getSequenz().intValue());
		ta2.setSequenz(2);
		dao.updateTourabschnitt(ta2);
		ta2 = dao.findById(ta.getId());
		assertEquals(2, ta.getSequenz().intValue());

		List<Tourabschnitt> listTa = dao.selectTourabschnitt(tt1);

		assertEquals(1, listTa.size());
		assertEquals(ta2, listTa.get(0));

		dao.deleteTourabschnitt(ta2);

		assertEquals(0, dao.selectTourabschnitt(tt1).size());
	}
}
