package expert.kunkel.berge.util;

import java.io.FileReader;

import junit.framework.TestCase;

import org.junit.Ignore;

import se.kodapan.osm.parser.xml.streaming.StreamingOsmXmlParser;
import se.kodapan.osm.parser.xml.streaming.StreamingOsmXmlParserListener;

@Ignore
public class TestReadOSM extends TestCase {

	@Ignore
	public void testReadPolygonHut() throws Exception {
		StreamingOsmXmlParserListener listener = new OsmParserListener();
		FileReader reader = new FileReader("/Users/thorsten/Desktop/POI.osm");
		StreamingOsmXmlParser.newInstance().read(reader, listener);
	}
}
