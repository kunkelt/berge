package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Region;

public class RegionDaoTest {

	private static RegionDao dao;

	@BeforeClass
	public static void setUp() {
		dao = DaoFactory.getInstance().getRegionDAO();
	}

	@Test
	public void testSelectRegion() {
		Region region = new Region();
		region.setName("Reg 1");
		region.setUmgrenzung("Umgrenzung");

		Region region2 = dao.insertRegion(region);

		Region region3 = dao.findById(region2.getId());
		assertEquals(region2, region3);

		region3.setUmgrenzung("Neue Umgrenzung");
		region3 = dao.updateRegion(region3);
		assertEquals("Neue Umgrenzung", region3.getUmgrenzung());

		List<Region> listRegion = dao.selectRegion();
		assertEquals(1, listRegion.size());
		assertEquals(region2, listRegion.get(0));
	}

	@Test
	public void testFindUsedRegions() {
		List<Region> listRegion = dao.findUsedRegions();
		assertEquals(0, listRegion.size());
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE region CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
}
