package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Galeriebild;
import expert.kunkel.berge.model.GaleriebildPK;
import expert.kunkel.berge.model.Tour;

public class JpaGaleriebildDao {

	public List<Galeriebild> selectGaleriebild() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT gb FROM Galeriebild gb",
				Galeriebild.class).getResultList();
	}

	public List<Galeriebild> selectGaleriebild(Tour tour) {
		// TODO Auto-generated method stub
		return null;
	}

	public Galeriebild insertGaleriebild(Galeriebild bild) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Galeriebild bild2 = em.merge(bild);
		em.getTransaction().commit();
		return bild2;
	}

	public boolean deleteGaleriebild(Tour tour) {
		// TODO Auto-generated method stub
		return false;
	}

	public Galeriebild findById(GaleriebildPK pk) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.find(Galeriebild.class, pk);
	}

}
