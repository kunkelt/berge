package expert.kunkel.berge.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.*;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import expert.kunkel.berge.ovl.LineStringConnector;
import expert.kunkel.ttqv.jaxb.*;
import expert.kunkel.ttqv.jaxb.Drawings.Drawing;

public class TtqvUtilities {

	public static Ttqv readTTQV_XML(String filename) {
		try {
			JAXBContext jc = JAXBContext.newInstance("de.thorsten_kunkel.ttqv.jaxb");
			Unmarshaller unm = jc.createUnmarshaller();
			return (Ttqv) unm.unmarshal(new File(filename));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static LineString convertPointsToLineString(PointsType points) {
		List<JAXBElement<BigDecimal>> coo = points.getLatOrLon();
		Coordinate[] coordinates = new Coordinate[coo.size() / 2];
		for (int k = 0; k < coo.size(); k += 2) {
			BigDecimal breite = coo.get(k).getValue();
			BigDecimal laenge = coo.get(k + 1).getValue();
			coordinates[k / 2] = new Coordinate(laenge.doubleValue(), breite.doubleValue());
		}
		CoordinateArraySequence cas = new CoordinateArraySequence(coordinates);
		return new LineString(cas, new GeometryFactory());
	}

	public static LineString convertPointsToPolygon(PointsType points) {
		List<JAXBElement<BigDecimal>> coo = points.getLatOrLon();
		Coordinate[] coordinates = new Coordinate[coo.size() / 2+1];
		for (int k = 0; k < coo.size(); k += 2) {
			BigDecimal breite = coo.get(k).getValue();
			BigDecimal laenge = coo.get(k + 1).getValue();
			coordinates[k / 2] = new Coordinate(laenge.doubleValue(), breite.doubleValue());
                        if (k == 0) {
                            coordinates[coo.size() / 2] = new Coordinate(laenge.doubleValue(), breite.doubleValue());
                        }
                }
		CoordinateArraySequence cas = new CoordinateArraySequence(coordinates);
		return new LineString(cas, new GeometryFactory());
	}

	public static Polygon convertTtqvXML_ToPolygon(Ttqv ttqv, String[] names) throws Exception {
		if (ttqv == null) {
			return null;
		}
		if (names == null || names.length == 0) {
			return null;
		}

		Drawings draws = ttqv.getDrawings();
		if (draws == null) {
			return null;
		}
		Drawing drawing = draws.getDrawing();
		if (drawing == null) {
			return null;
		}

		List<EntityType> entities = drawing.getEntity();
		LineString[] lines = new LineString[names.length];
		for (int i = 0; i < names.length; i++) {
			for (int j = 0; j < entities.size(); j++) {
				EntityType entity = entities.get(j);
				if (entity.getName().equals(names[i])) {
					LineString ls = TtqvUtilities.convertPointsToLineString(entity.getPoints());
					lines[i] = ls;
				}
			}
		}
		return LineStringConnector.connect(lines);
	}

	public static Polygon convertTtqvXML_ToPolygon(Ttqv ttqv) throws Exception {
		if (ttqv == null) {
			return null;
		}

                Drawings draws = ttqv.getDrawings();
		if (draws == null) {
			return null;
		}
		Drawing drawing = draws.getDrawing();
		if (drawing == null) {
			return null;
		}

		List<EntityType> entities = drawing.getEntity();
		
                EntityType entity = entities.get(0);

        	LineString ls = TtqvUtilities.convertPointsToPolygon(entity.getPoints());

                LinearRing lr = new LinearRing(ls.getCoordinateSequence(), new GeometryFactory());

                return new Polygon(lr, null, new GeometryFactory());
	}

	public static LineString convertTtqvXML_ToLineString(Ttqv ttqv, String name) throws Exception {
		if (ttqv == null) {
			return null;
		}
		if (name == null) {
			return null;
		}

		Drawings draws = ttqv.getDrawings();
		if (draws == null) {
			return null;
		}
		Drawing drawing = draws.getDrawing();
		if (drawing == null) {
			return null;
		}

		List<EntityType> entities = drawing.getEntity();
		for (int j = 0; j < entities.size(); j++) {
			EntityType entity = entities.get(j);
			if (entity.getName().equals(name)) {
				return TtqvUtilities.convertPointsToLineString(entity.getPoints());
			}
		}
		return null;
	}

}
