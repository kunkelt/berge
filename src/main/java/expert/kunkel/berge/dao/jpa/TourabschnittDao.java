package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import expert.kunkel.berge.model.Tourabschnitt;
import expert.kunkel.berge.model.Tourentag;

public class TourabschnittDao {

	public Tourabschnitt insertTourabschnitt(Tourabschnitt abschnitt) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Tourabschnitt a = em.merge(abschnitt);
		em.getTransaction().commit();
		return a;
	}

	public void deleteTourabschnitt(Tourabschnitt ttag) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.remove(ttag);
		em.getTransaction().commit();
	}

	public void deleteAllTourenabschnitte(Tourentag ttag) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Query query = em
				.createQuery("DELETE from Tourabschnitt t WHERE t.tourentag = :ttag");
		query.setParameter("ttag", ttag);
		query.executeUpdate();
		em.getTransaction().commit();
	}

	public Tourabschnitt findById(Integer id) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.find(Tourabschnitt.class, id);
	}

	public void updateTourabschnitt(Tourabschnitt abschnitt) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.merge(abschnitt);
		em.getTransaction().commit();
	}

	public List<Tourabschnitt> selectTourabschnitt(Tourentag ttag) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		TypedQuery<Tourabschnitt> query = em.createQuery(
				"SELECT ta FROM Tourabschnitt ta WHERE ta.tourentag = :ttag",
				Tourabschnitt.class);
		query.setParameter("ttag", ttag);
		return query.getResultList();
	}

}
