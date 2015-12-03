package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Region;
import expert.kunkel.berge.model.Tour;


public class JpaTourDao  {

	public Tour insertTour(Tour tour) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Tour tour2 = em.merge(tour);
		em.getTransaction().commit();
		return tour2;
	}

	public boolean deleteTour(Tour tour)  {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Tour> findTour(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateTour(Tour tour) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Tour> selectTour(Boolean geplant) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Tour> selectTour(Integer id, Boolean geplant) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Tour> getAllToursForYear(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Tour> findTourInRegion(Region region) {
		// TODO Auto-generated method stub
		return null;
	}

}
