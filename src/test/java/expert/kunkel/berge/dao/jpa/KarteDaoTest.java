package expert.kunkel.berge.dao.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import expert.kunkel.berge.model.Karte;
import expert.kunkel.berge.model.Kartentyp;

public class KarteDaoTest {

	private static KarteDao dao;

	@BeforeClass
	public static void setUp() {
		dao = DaoFactory.getInstance().getKarteDAO();
	}

	@Test
	public void testSelectKarte() {
		Kartentyp kartentyp = new Kartentyp();
		kartentyp.setTyp("Analog");
		kartentyp = DaoFactory.getInstance().getKartentypDao()
				.insertKartentyp(kartentyp);

		Karte karte = new Karte();
		karte.setAusgabejahr(2015);
		karte.setBlattnummer("50/2");
		karte.setIsbn("37800089944");
		karte.setKartentyp(kartentyp); // FIXME
		karte.setTitel("Titel");
		karte.setMassstab("1:50000");
		karte.setUntertitel("Untertitel");

		Karte karte2 = dao.insertKarte(karte);

		Karte karte3 = dao.findById(karte2.getId());
		assertEquals(karte2, karte3);

		karte3.setMassstab("1:100.000");
		karte3 = dao.updateKarte(karte3);
		assertEquals(karte3.getMassstab(), "1:100.000");

		List<Karte> listKarte = dao.selectKarte();
		assertEquals(1, listKarte.size());
		assertEquals(karte2, listKarte.get(0));

		dao.deleteKarte(karte3);
		assertEquals(0, dao.selectKarte().size());
	}

	@AfterClass
	public static void tearDown() {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE karte CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE kartentyp CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
}
