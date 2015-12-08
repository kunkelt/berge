package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Tour;
import expert.kunkel.berge.model.Tourentag;

public class JpaTourentagDao {

	public Tourentag insertTourentag(Tourentag tt) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Tourentag t = em.merge(tt);
		em.getTransaction().commit();
		return t;
	}

	public void deleteTourentag(Tourentag tourentag) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.remove(tourentag);
		em.getTransaction().commit();
	}

	public Tourentag findTourentag() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateTourentag(Tourentag tag) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Tourentag> findForTour(Tour tour) {
		// TODO Auto-generated method stub
		return null;
	}

}
