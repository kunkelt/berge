package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Karte;

public class JpaKarteDao {

	public Karte insertKarte(Karte karte) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Karte k = em.merge(karte);
		em.getTransaction().commit();
		return k;
	}

	public void deleteKarte(Karte karte) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.remove(karte);
		em.getTransaction().commit();
	}

	public Karte updateKarte(Karte karte) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Karte k = em.merge(karte);
		em.getTransaction().commit();
		return k;
	}

	public List<Karte> selectKarte() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT k FROM Karte k", Karte.class)
				.getResultList();
	}

	public Karte findById(Integer id) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.find(Karte.class, id);
	}
}
