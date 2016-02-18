package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Punkt;

public class JpaPunktDao {

	public Punkt insertPunkt(Punkt punkt) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Punkt p = em.merge(punkt);
		em.getTransaction().commit();
		return p;
	}

	public void deletePunkt(Punkt p) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
	}

	public Punkt findById(Integer id) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.find(Punkt.class, id);
	}

	public Punkt updatePunkt(Punkt punkt) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Punkt p = em.merge(punkt);
		em.getTransaction().commit();
		return p;
	}

	public List<Punkt> selectPunkt() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT p FROM Punkt p", Punkt.class)
				.getResultList();
	}

}
