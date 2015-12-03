package expert.kunkel.berge.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Verlag;


public class JpaVerlagDao {

	public List<Verlag> selectVerlag() {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT v FROM Verlag v",Verlag.class).getResultList();
	}

	public List<Verlag> selectVerlag(Integer id) {
		throw new UnsupportedOperationException();
	}

	public Verlag findById(Integer id) {
		EntityManager em = JpaDaoFactory.getInstance().getEntityManager();
		return em.find(Verlag.class, id);
	}

}
