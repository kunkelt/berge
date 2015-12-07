package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import expert.kunkel.berge.model.Galeriebild;
import expert.kunkel.berge.model.Tour;

public class JpaGaleriebildDao {

	public List<Galeriebild> selectGaleriebild() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT gb FROM Galeriebild gb",
				Galeriebild.class).getResultList();
	}

	public List<Galeriebild> selectGaleriebild(Tour tour) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		TypedQuery<Galeriebild> query = em.createQuery("SELECT gb FROM Galeriebild gb WHERE gb.tour = :tour",
				Galeriebild.class);
		query.setParameter("tour", tour);
		return query.getResultList();
	}

	public Galeriebild insertGaleriebild(Galeriebild bild) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Galeriebild bild2 = em.merge(bild);
		em.getTransaction().commit();
		return bild2;
	}

	public void deleteGaleriebild(Tour tour) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		for (Galeriebild gb : selectGaleriebild(tour)) {
			em.remove(gb);
		}
		em.getTransaction().commit();
	}

	public Galeriebild findById(Integer id) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.find(Galeriebild.class, id);
	}

}
