package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Kartentyp;

public class KartentypDao {

	public List<Kartentyp> selectKartentyp() {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT kt FROM Kartentyp kt", Kartentyp.class)
				.getResultList();
	}

	public Kartentyp findById(Integer id) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.find(Kartentyp.class, id);
	}

	public Kartentyp insertKartentyp(Kartentyp kartentyp) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Kartentyp kt = em.merge(kartentyp);
		em.getTransaction().commit();
		return kt;
	}

}
