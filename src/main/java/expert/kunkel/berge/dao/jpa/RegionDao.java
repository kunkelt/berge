package expert.kunkel.berge.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import expert.kunkel.berge.model.Region;

public class RegionDao {

	public Region insertRegion(Region region) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Region r = em.merge(region);
		em.getTransaction().commit();
		return r;
	}

	public Region updateRegion(Region region) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		em.getTransaction().begin();
		Region r = em.merge(region);
		em.getTransaction().commit();
		return r;
	}

	public List<Region> selectRegion() {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.createQuery("SELECT r FROM Region r", Region.class)
				.getResultList();
	}

	public Region findById(Integer id) {
		EntityManager em = DaoFactory.getInstance().getEntityManager();
		return em.find(Region.class, id);
	}

	public List<Region> findUsedRegions() {
		List<Region> list = new ArrayList<Region>();

		StringBuffer sb = new StringBuffer(
				"select DISTINCT region.id, region.name from region "
						+ ", tour, tourentag ");
		sb.append(" where tourentag.region = region.id AND ");
		sb.append(" tourentag.id_tour = tour.id ");
		sb.append(" ORDER BY ");
		sb.append(" region.name");

		EntityManager em = DaoFactory.getInstance().getEntityManager();
		@SuppressWarnings("unchecked")
		List<Object[]> result = em.createNativeQuery(sb.toString())
		.getResultList();

		for (Object[] o : result) {
			list.add(findById((Integer) o[0]));
		}

		return list;
	}
}
