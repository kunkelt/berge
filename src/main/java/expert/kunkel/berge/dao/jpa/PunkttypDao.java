package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Punkttyp;

public class PunkttypDao {

	public Punkttyp insertPunkttyp(Punkttyp punkttyp) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Punkttyp pt = em.merge(punkttyp);
		em.getTransaction().commit();
		return pt;
	}

	public List<Punkttyp> selectPunkttyp() {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT pt FROM Punkttyp pt", Punkttyp.class)
				.getResultList();
	}

	public Punkttyp findById(Integer id) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.find(Punkttyp.class, id);
	}

}
