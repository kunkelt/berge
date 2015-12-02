package expert.kunkel.berge.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import expert.kunkel.berge.dao.DAOFactory;
import expert.kunkel.berge.dao.Region;
import expert.kunkel.berge.dao.RegionDAO;
import expert.kunkel.berge.ovl.OVL_Flaeche;
import expert.kunkel.ttqv.jaxb.Ttqv;

public class TestRegionDAO extends TestCase {

	private static final String PATH_POLYGONE_NICHT_IN_DB = "C:/Users/thorsten/Hobbies/Berge/Gebirgsgruppen/polygone_nicht_in_db/";
	private static final String PATH_POLYGONE_IN_DB = "C:/Users/thorsten/Hobbies/Berge/Gebirgsgruppen/polygon_in_db_abgefuellt/";
	private static final String PATH_KANTEN = "C:/Users/thorsten/Hobbies/Berge/Gebirgsgruppen/export_test/";

	private static final int ID_REGION = 84;
	private static final String FILENAME = "84 - Südliche Karnische Alpen";

	private static final String[] names = { 
		"216",
		"217",
                "215",
                "214"
		};
/*
	public void testReadOVL_AndUpdateDB() throws IOException, SQLException, ClassNotFoundException {
		Region region = getRegion(ID_REGION);

		RandomAccessFile raf = new RandomAccessFile(PATH_POLYGONE_NICHT_IN_DB + FILENAME + ".ovl", "r");

		// erste Zeile auf Symbol testen
		String firstLine = raf.readLine();
		if (!firstLine.startsWith("[Symbol")) {
			throw new IOException("Fehler in Datei! Symbol-Markierung nicht gefunden.");
		}
		// Typ aus zweiter Zeile auslesen
		String typ = raf.readLine().substring(4);

		if (Integer.parseInt(typ) != Symbol.SYMBOL_TYP_FLAECHE) {
			throw new IOException("Fehler in Datei! Typ ist nicht Flaeche");
		}

		OVL_Flaeche flaeche = new OVL_Flaeche(raf);
		Polygon polygon = flaeche.getAsPolygon();

		System.out.println(polygon.toText());

		region.setExtent(polygon);

		updateRegion(region);
	}
*/
	public void testReadDB_AndGenerateOVL() throws ParseException, IOException {
		Region region = getRegion(ID_REGION);
		String wkt = region.getExtent().toText();

		WKTReader wktReader = new WKTReader();
		Geometry geom = wktReader.read(wkt);

		assertTrue(geom instanceof Polygon);

		Polygon polygon = (Polygon) geom;
		System.out.println(polygon.toText());

		OVL_Flaeche flaeche = new OVL_Flaeche(polygon, 1, 1, 1, 103, 1);
		RandomAccessFile raf = new RandomAccessFile(PATH_POLYGONE_NICHT_IN_DB + FILENAME + ".ovl", "rw");
		raf.writeBytes("[Symbol 1]\r\n");
		flaeche.write(raf);
		raf.writeBytes("[Overlay]" + "\r\n");
		raf.writeBytes("Symbols=1" + "\r\n");
		raf.writeBytes("[MapLage]" + "\r\n");
		raf.writeBytes("MapName=" + FILENAME + "\r\n");
		raf.writeBytes("DimmFc=100" + "\r\n");
		raf.writeBytes("ZoomFc=100" + "\r\n");
		raf.writeBytes("CenterLat=" + polygon.getCentroid().getY() + "\r\n");
		raf.writeBytes("CenterLong=" + polygon.getCentroid().getX() + "\r\n");
		raf.writeBytes("RefOn=1" + "\r\n");
		raf.writeBytes("RefLat=" + polygon.getExteriorRing().getStartPoint().getY() + "\r\n");
		raf.writeBytes("RefLong=" + polygon.getExteriorRing().getStartPoint().getX() + "\r\n");
		raf.close();
	}
/*
	public void testReadTtqvAndWriteOVL() throws ParseException, IOException {
		Ttqv ttqv = TtqvUtilities.readTTQV_XML(PATH_KANTEN + "Kanten.xml");
		List<EntityType> entities = ttqv.getDrawings().getDrawing().getEntity();

		RandomAccessFile raf = new RandomAccessFile(PATH_KANTEN + "Kanten.ovl", "rw");

		for (int j = 0; j < entities.size(); j++) {
			EntityType entity = entities.get(j);
			LineString ls = TtqvUtilities.convertPointsToLineString(entity.getPoints());
			OVL_Linie linie = new OVL_Linie(1,1,1,104,1,ls.getCoordinates());

			raf.writeBytes("[Symbol "+(j+1)+"]\r\n");
			linie.write(raf);
		}

		raf.writeBytes("[Overlay]" + "\r\n");
		raf.writeBytes("Symbols=" + entities.size() + "\r\n");
		raf.writeBytes("[MapLage]" + "\r\n");
		raf.writeBytes("MapName=" + FILENAME + "\r\n");
		raf.writeBytes("DimmFc=100" + "\r\n");
		raf.writeBytes("ZoomFc=100" + "\r\n");
		raf.writeBytes("CenterLat=" + "10.5" + "\r\n");
		raf.writeBytes("CenterLong=" + "47.2" + "\r\n");
		raf.writeBytes("RefOn=1" + "\r\n");
		raf.writeBytes("RefLat=" + "10.5" + "\r\n");
		raf.writeBytes("RefLong=" + "47.2" + "\r\n");
		raf.close();
	}
*/
	public void testReadTtqvAndUpdateDB() throws Exception {
		Ttqv ttqv = TtqvUtilities.readTTQV_XML(PATH_KANTEN + "Kanten2_neu.xml");
		Polygon polygon = TtqvUtilities.convertTtqvXML_ToPolygon(ttqv, names);
		System.out.println(polygon.toText());

		Region region = getRegion(ID_REGION);
		region.setExtent(polygon);

		updateRegion(region);
	}
/*
	public void testReadDBAndUpdateDB() throws Exception {
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		RegionDAO regionDao = factory.getRegionDAO();
		List<Region> regionen = regionDao.selectRegion();

		for (int i = 0; i < regionen.size(); i++) {
			Region region = regionen.get(i);
			updateRegion(region);
		}
	}

	public void testReadTtqvAndWriteShape() throws Exception {
		Ttqv ttqv = TtqvUtilities.readTTQV_XML(PATH_KANTEN + "Kanten.xml");
		LineString ls = TtqvUtilities.convertTtqvXML_ToLineString(ttqv, "001");
		ShapeUtilities.createShapeFile(ls);
	}

	public void testReadDB_AndWriteShapePolygon() throws Exception {
		Polygon polygon = getRegion(ID_REGION).getExtent();
		ShapeUtilities.createShapeFile(polygon);
	}
*/
	private Region getRegion(int id) {
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		RegionDAO regionDao = factory.getRegionDAO();
		List<Region> regionen = regionDao.selectRegion(new Integer(id));
		return regionen.get(0);
	}

	private boolean updateRegion(Region region) throws SQLException, ClassNotFoundException {
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		RegionDAO regionDao = factory.getRegionDAO();
		return regionDao.updateRegion(region);
	}

	public static void main(String[] args) throws Exception {
		TestRegionDAO test = new TestRegionDAO();
		test.testReadTtqvAndUpdateDB();
		test.testReadDB_AndGenerateOVL();
	}
}
