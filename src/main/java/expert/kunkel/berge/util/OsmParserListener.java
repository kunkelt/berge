package expert.kunkel.berge.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.kodapan.osm.domain.Node;
import se.kodapan.osm.domain.Relation;
import se.kodapan.osm.domain.Way;
import se.kodapan.osm.parser.xml.streaming.StreamingOsmXmlParserListener;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;

import expert.kunkel.berge.dao.Punkt;
import expert.kunkel.berge.dao.postgresql.PostgreSQL_PunkttypDAO;

public class OsmParserListener extends StreamingOsmXmlParserListener {

	private Map<Long, Node> completeNodes = new HashMap<Long, Node>();
	private PostgreSQL_PunkttypDAO punkttypDao = new PostgreSQL_PunkttypDAO();
	private GeometryFactory factory = new GeometryFactory(new PrecisionModel(),
			4326);
	private List<Punkt> punkte = new ArrayList<Punkt>();
	
	@Override
	public void processParsedNode(Node node) {
		completeNodes.put(node.getId(), node);

		Map<String, String> tags = node.getTags();

		if (tags != null) {
			if (tags.containsKey("tourism")
					&& tags.get("tourism").equals("alpine_hut")) {
				parsePunkt(node, 2); // HÜTTE
			} else if (tags.containsKey("building")
					&& tags.get("building").equals("yes")
					&& tags.containsKey("name")
					&& (tags.get("name").contains("alm")
							|| tags.get("name").contains("alpe")
							|| tags.get("name").contains("Alm") || tags.get(
							"name").contains("Alpe"))) {
				parsePunkt(node, 9); // ALM
			} else if (tags.containsKey("mountain_pass")
					&& tags.get("mountain_pass").equals("yes")) {
				parsePunkt(node, 3); // SCHARTE/JOCH
			} else if (tags.containsKey("natural")
					&& tags.get("natural").equals("peak")) {
				parsePunkt(node, 1); // GIPFEL
			} else if (tags.containsKey("amenity")
					&& tags.get("amenity").equals("parking")) {
				parsePunkt(node, 10); // PARKPLATZ
			} else {
				parsePunkt(node, 7); // UNBEKANNT
			}
		}
	}

	private void parsePunkt(Node node, int punkttyp) {
		Punkt punkt = new Punkt();
		if (node.getTag("ele") != null) {
			punkt.setHoehe(Integer.parseInt(node.getTag("ele")));
		}
		punkt.setName(parseName(node.getTags()));
		punkt.setName2(parseName2(node.getTags()));
		punkt.setTyp(punkttypDao.selectPunkttyp(punkttyp));
		punkt.setLage(factory.createPoint(new Coordinate(node.getX(), node
				.getY())));

		System.out.println(punkt);
		punkte.add(punkt);
	}

	private String parseName(Map<String, String> map) {
		if (map.containsKey("name:de")) {
			return map.get("name:de");
		}
		return map.get("name");
	}

	@Override
	public void processParsedWay(Way way) {
		Map<String, String> tags = way.getTags();

		if (tags.containsKey("tourism")
				&& tags.get("tourism").equals("alpine_hut")) {
			parseWay(way, 2); // HÜTTE
		} else if (tags.containsKey("building")
				&& tags.get("building").equals("yes")
				&& tags.containsKey("name")
				&& (tags.get("name").contains("alm")
						|| tags.get("name").contains("alpe")
						|| tags.get("name").contains("Alm") || tags.get("name")
						.contains("Alpe"))) {
			parseWay(way, 9); // ALM
		} else {
			parseWay(way, 7); // UNBEKANNT
		}
	}

	private void parseWay(Way way, int punkttyp) {
		Punkt punkt = new Punkt();
		if (way.getTag("ele") != null) {
			punkt.setHoehe(Integer.parseInt(way.getTag("ele")));
		}
		punkt.setName(parseName(way.getTags()));
		punkt.setName2(parseName2(way.getTags()));
		punkt.setTyp(punkttypDao.selectPunkttyp(punkttyp));

		extractGeometryNotPoint(way, punkt);
		System.out.println(punkt);
		punkte.add(punkt);
	}

	private String parseName2(Map<String, String> tags) {
		if (tags.containsKey("name:it")) {
			return tags.get("name:it");
		}
		return null;
	}

	private void extractGeometryNotPoint(Way way, Punkt punkt) {
		List<Node> nodes = way.getNodes();
		if (nodes.size() > 1) {
			// Linie oder Polygon
			Coordinate[] coords = new Coordinate[nodes.size()];
			for (int i = 0; i < nodes.size(); i++) {
				Node node = completeNodes.get(nodes.get(i).getId());
				coords[i] = new Coordinate(node.getX(), node.getY());
			}
			if (nodes.get(0).getId() == nodes.get(nodes.size() - 1).getId()) {
				// Polygon
				LinearRing shell = factory.createLinearRing(coords);
				Polygon polygon = new Polygon(shell, null, factory);
				punkt.setLage(polygon.getCentroid());
			} else {
				// Linie
				LineString line = factory.createLineString(coords);
				punkt.setLage(line.getCentroid());
			}
		}
	}

	@Override
	public void processParsedRelation(Relation relation) {
		// TODO Auto-generated method stub
		super.processParsedRelation(relation);
	}

	public List<Punkt> getPunkte() {
		return punkte;
	}
}
