package expert.kunkel.berge;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import expert.kunkel.berge.dao.jpa.DaoFactoryTest;
import expert.kunkel.berge.dao.jpa.GaleriebildDaoTest;
import expert.kunkel.berge.dao.jpa.KarteDaoTest;
import expert.kunkel.berge.dao.jpa.KartentypDaoTest;
import expert.kunkel.berge.dao.jpa.PunktDaoTest;
import expert.kunkel.berge.dao.jpa.PunkttypDaoTest;
import expert.kunkel.berge.dao.jpa.RegionDaoTest;
import expert.kunkel.berge.dao.jpa.TourDaoTest;
import expert.kunkel.berge.dao.jpa.TourabschnittDaoTest;
import expert.kunkel.berge.dao.jpa.TourentagDaoTest;
import expert.kunkel.berge.dao.jpa.VerlagDaoTest;

@RunWith(Suite.class)
@SuiteClasses({
	// @formatter:off
	DaoFactoryTest.class,
	GaleriebildDaoTest.class,
	VerlagDaoTest.class,
	PunkttypDaoTest.class,
	TourDaoTest.class,
	KarteDaoTest.class,
	RegionDaoTest.class,
	KartentypDaoTest.class,
	TourentagDaoTest.class,
	TourabschnittDaoTest.class,
	PunktDaoTest.class
	// @formatter:on
})
public class AllTests {

}
