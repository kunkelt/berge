package expert.kunkel.berge;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import expert.kunkel.berge.dao.jpa.JpaDaoFactoryTest;
import expert.kunkel.berge.dao.jpa.JpaGaleriebildDaoTest;
import expert.kunkel.berge.dao.jpa.JpaVerlagDaoTest;

@RunWith(Suite.class)
@SuiteClasses({ JpaDaoFactoryTest.class, JpaGaleriebildDaoTest.class,
		JpaVerlagDaoTest.class })
public class AllTests {

}
