package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import expert.kunkel.berge.model.Punkt;
import expert.kunkel.berge.model.Punkttyp;

public class PunktDaoTest {

	private static PunktDao dao;

	@BeforeClass
	public static void setUp() {
		dao = DaoFactory.getInstance().getPunktDAO();
	}

	@Test
	public void test() {
		Punkttyp punkttyp = new Punkttyp();
		punkttyp.setName("Name 1");

		punkttyp = DaoFactory.getInstance().getPunkttypDAO()
				.insertPunkttyp(punkttyp);

		GeometryFactory fact = new GeometryFactory();
		Point point = fact.createPoint(new Coordinate(1, 2));

		Punkt punkt = new Punkt();
		punkt.setBeschreibung("Meine Hütte");
		punkt.setHoehe(123);
		punkt.setLage(point);
		punkt.setName("Name");
		punkt.setName2("Name2");
		punkt.setTyp(punkttyp);

		Punkt punkt2 = dao.insertPunkt(punkt);

		Punkt punkt3 = dao.findById(punkt2.getId());
		assertEquals(punkt2, punkt3);

		punkt3.setName("NAME4");
		punkt3 = dao.updatePunkt(punkt3);
		assertEquals("NAME4", punkt3.getName());

		List<Punkt> listPunkt = dao.selectPunkt();
		assertEquals(1, listPunkt.size());
		assertEquals(punkt2, listPunkt.get(0));

		dao.deletePunkt(listPunkt.get(0));
		assertEquals(0, dao.selectPunkt().size());
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE punkttyp CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE punkt CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
}
