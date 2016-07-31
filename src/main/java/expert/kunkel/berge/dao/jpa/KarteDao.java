package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Karte;

public class KarteDao {

	public Karte insertKarte(Karte karte) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Karte k = em.merge(karte);
		em.getTransaction().commit();
		return k;
	}

	public void deleteKarte(Karte karte) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.remove(karte);
		em.getTransaction().commit();
	}

	public Karte updateKarte(Karte karte) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Karte k = em.merge(karte);
		em.getTransaction().commit();
		return k;
	}

	public List<Karte> selectKarte() {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT k FROM Karte k ORDER BY titel",
				Karte.class).getResultList();
	}

	public Karte findById(Integer id) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.find(Karte.class, id);
	}
}
