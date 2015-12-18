package expert.kunkel.berge;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import expert.kunkel.berge.dao.jpa.JpaDaoFactoryTest;
import expert.kunkel.berge.dao.jpa.JpaGaleriebildDaoTest;
import expert.kunkel.berge.dao.jpa.JpaKarteDaoTest;
import expert.kunkel.berge.dao.jpa.JpaKartentypDaoTest;
import expert.kunkel.berge.dao.jpa.JpaPunkttypDaoTest;
import expert.kunkel.berge.dao.jpa.JpaRegionDaoTest;
import expert.kunkel.berge.dao.jpa.JpaTourDaoTest;
import expert.kunkel.berge.dao.jpa.JpaTourabschnittDaoTest;
import expert.kunkel.berge.dao.jpa.JpaTourentagDaoTest;
import expert.kunkel.berge.dao.jpa.JpaVerlagDaoTest;

@RunWith(Suite.class)
@SuiteClasses({ JpaDaoFactoryTest.class, JpaGaleriebildDaoTest.class,
		JpaVerlagDaoTest.class, JpaPunkttypDaoTest.class, JpaTourDaoTest.class,
	JpaKarteDaoTest.class, JpaRegionDaoTest.class,
	JpaKartentypDaoTest.class, JpaTourentagDaoTest.class,
	JpaTourabschnittDaoTest.class })
public class AllTests {

}
