package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import expert.kunkel.berge.model.Region;
import expert.kunkel.berge.model.Tour;

public class TourDao {

	public Tour insertTour(Tour tour) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Tour tour2 = em.merge(tour);
		em.getTransaction().commit();
		return tour2;
	}

	public void deleteTour(Tour tour) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.remove(tour);
		em.getTransaction().commit();
	}

	public void updateTour(Tour tour) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.merge(tour);
		em.getTransaction().commit();
	}

	public Tour findById(Integer id) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.find(Tour.class, id);
	}

	public List<Tour> selectTour(Boolean geplant) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		String select = "SELECT t FROM Tour t";
		if (geplant != null) {
			select += " WHERE t.geplant = :geplant";
		}
		TypedQuery<Tour> query = em.createQuery(select, Tour.class);
		if (geplant != null) {
			query.setParameter("geplant", geplant);
		}
		return query.getResultList();
	}

	/**
	 * 
	 * @param jahr
	 *            Kalenderjahr (Jahreszahl)
	 * @return alle Touren für ein Kalenderjahr (ohne geplante)
	 */
	public List<Tour> getAllToursForYear(int jahr) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		String select = "SELECT DISTINCT t FROM Tour t LEFT JOIN t.tourentage tt "
				+ " WHERE date_part('year', tt.datum)  = :jahr";
		TypedQuery<Tour> query = em.createQuery(select, Tour.class);
		query.setParameter("jahr", jahr);
		return query.getResultList();
	}

	public List<Tour> findTourInRegion(Region region) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		String select = "SELECT DISTINCT t FROM Tour t LEFT JOIN t.tourentage tt "
				+ "WHERE tt.region = :region";
		TypedQuery<Tour> query = em.createQuery(select, Tour.class);
		query.setParameter("region", region);
		return query.getResultList();
	}

}
