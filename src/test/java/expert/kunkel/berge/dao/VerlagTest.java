package expert.kunkel.berge.dao;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.junit.Test;

import expert.kunkel.berge.dao.jpa.JpaDaoFactory;
import expert.kunkel.berge.model.Verlag;

public class VerlagTest {

	private static final String ANSCHRIFT = "Straße 1";
	private static final String BEZUGSQUELLE = "Irgendwo";
	private static final String NAME = "Name des Verlagssssssssss";
	private static final String TELEFON = "+49 89/123789098765";

	@Test
	public void testVerlag() {
		Verlag verlag = new Verlag();
		verlag.setAnschrift(ANSCHRIFT);
		verlag.setBezugsquelle(BEZUGSQUELLE);
		verlag.setName(NAME);
		verlag.setTelefon(TELEFON);

		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		Verlag verlag2 = em.merge(verlag);

		Verlag verlag3 = em.find(Verlag.class, verlag2.getId());
		assertEquals(verlag2, verlag3);
	}
}
