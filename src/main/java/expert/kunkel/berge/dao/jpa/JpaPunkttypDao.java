package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Punkttyp;

public class JpaPunkttypDao {

	public Punkttyp insertPunkttyp(Punkttyp punkttyp) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Punkttyp pt = em.merge(punkttyp);
		em.getTransaction().commit();
		return pt;
	}

	public List<Punkttyp> selectPunkttyp() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT pt FROM Punkttyp pt", Punkttyp.class)
				.getResultList();
	}

	public Punkttyp findById(Integer id) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.find(Punkttyp.class, id);
	}

}
