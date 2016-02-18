package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import expert.kunkel.berge.model.Tour;
import expert.kunkel.berge.model.Tourentag;

public class TourentagDao {

	public Tourentag insertTourentag(Tourentag tt) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Tourentag t = em.merge(tt);
		em.getTransaction().commit();
		return t;
	}

	public void deleteTourentag(Tourentag tourentag) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.remove(tourentag);
		em.getTransaction().commit();
	}

	public void updateTourentag(Tourentag tag) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.merge(tag);
		em.getTransaction().commit();
	}

	public List<Tourentag> findForTour(Tour tour) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		TypedQuery<Tourentag> query = em.createQuery(
				"SELECT tt FROM Tourentag tt WHERE tt.tour = :tour",
				Tourentag.class);
		query.setParameter("tour", tour);
		return query.getResultList();
	}

	public Tourentag findForId(Integer id) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.find(Tourentag.class, id);
	}

}
