package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import expert.kunkel.berge.model.Tourabschnitt;
import expert.kunkel.berge.model.Tourentag;

public class JpaTourabschnittDao {

	public Tourabschnitt insertTourabschnitt(Tourabschnitt abschnitt) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Tourabschnitt a = em.merge(abschnitt);
		em.getTransaction().commit();
		return a;
	}

	public void deleteTourabschnitt(Tourabschnitt ttag) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.remove(ttag);
		em.getTransaction().commit();
	}

	public Tourabschnitt findById(Integer id) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.find(Tourabschnitt.class, id);
	}

	public void updateTourabschnitt(Tourabschnitt abschnitt) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.merge(abschnitt);
		em.getTransaction().commit();
	}

	public List<Tourabschnitt> selectTourabschnitt(Tourentag ttag) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		TypedQuery<Tourabschnitt> query = em.createQuery(
				"SELECT ta FROM Tourabschnitt ta WHERE ta.tourentag = :ttag",
				Tourabschnitt.class);
		query.setParameter("ttag", ttag);
		return query.getResultList();
	}

}
