/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.kunkel.berge.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import expert.kunkel.berge.dao.jpa.DaoFactory;
import expert.kunkel.berge.dao.jpa.PunktDao;
import expert.kunkel.berge.dao.jpa.PunkttypDao;
import expert.kunkel.berge.model.Punkt;
import expert.kunkel.gpx.GpxReader;
import expert.kunkel.gpx1_1.jaxb.GpxType;
import expert.kunkel.gpx1_1.jaxb.WptType;

/**
 *
 * @author thorsten
 */
public class GpxUtils {

	private static DaoFactory factory = DaoFactory.getInstance();

	public static List<Punkt> convertGpxPunkteToObjects(File f)
			throws JAXBException, FileNotFoundException {
		PunkttypDao punkttypdao = factory.getPunkttypDAO();
		List<Punkt> result = new ArrayList<Punkt>();

		GpxType gpxType = GpxReader.readGpx1_1FromFile(f);
		List<WptType> waypoints = gpxType.getWpt();

		for (WptType wptType : waypoints) {
			Punkt p = new Punkt();

			if (wptType.getEle() != null) {
				p.setHoehe(wptType.getEle().intValue());
				Coordinate c = new Coordinate(wptType.getLon().doubleValue(),
						wptType.getLat().doubleValue(), wptType.getEle()
								.doubleValue());
				CoordinateArraySequence cas = new CoordinateArraySequence(
						new Coordinate[] { c });
				Point point = new Point(cas, new GeometryFactory());
				p.setLage(point);
			} else {
				Coordinate c = new Coordinate(wptType.getLon().doubleValue(),
						wptType.getLat().doubleValue());
				CoordinateArraySequence cas = new CoordinateArraySequence(
						new Coordinate[] { c });
				Point point = new Point(cas, new GeometryFactory());
				p.setLage(point);
			}

			p.setTyp(punkttypdao.findById(7));
			p.setName(wptType.getName());

			result.add(p);
		}

		return result;
	}

	public static void main(String args[]) throws JAXBException,
			FileNotFoundException {
		List<Punkt> result = convertGpxPunkteToObjects(new File(
				"C:/tmp/Punkte für Import.gpx"));
		PunktDao punktDao = factory.getPunktDAO();

		for (Punkt punkt : result) {
			punktDao.insertPunkt(punkt);
		}
	}
}
