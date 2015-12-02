/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.kunkel.berge.util;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import expert.kunkel.gpx.GpxReader;
import expert.kunkel.gpx1_1.jaxb.GpxType;
import expert.kunkel.gpx1_1.jaxb.WptType;
import expert.kunkel.berge.dao.DAOFactory;
import expert.kunkel.berge.dao.Punkt;
import expert.kunkel.berge.dao.PunktDAO;
import expert.kunkel.berge.dao.PunkttypDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

/**
 *
 * @author thorsten
 */
public class GpxUtils {

    private static DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);

    public static List<Punkt> convertGpxPunkteToObjects(File f) throws JAXBException, FileNotFoundException {
        PunkttypDAO punkttypdao = factory.getPunkttypDAO();
        List<Punkt> result = new ArrayList<Punkt>();

        GpxType gpxType = GpxReader.readGpx1_1FromFile(f);
        List<WptType> waypoints = gpxType.getWpt();

        for (Iterator<WptType> it = waypoints.iterator(); it.hasNext();) {
            Punkt p = new Punkt();

            WptType wptType = it.next();
            p.setHoehe(wptType.getEle().intValue());

            Coordinate c = new Coordinate(wptType.getLon().doubleValue(), wptType.getLat().doubleValue(), wptType.getEle().doubleValue());
            CoordinateArraySequence cas = new CoordinateArraySequence(new Coordinate[] {c});
            Point point = new Point(cas, new GeometryFactory());
            p.setLage(point);
            p.setTyp(punkttypdao.selectPunkttyp(7));
            p.setName(wptType.getName());

            result.add(p);
        }

        return result;
    }

    public static void main(String args[]) throws JAXBException, FileNotFoundException {
        List<Punkt> result = convertGpxPunkteToObjects(new File("C:/tmp/Punkte für Import.gpx"));
        PunktDAO punktDao = factory.getPunktDAO();

        for (Iterator<Punkt> it = result.iterator(); it.hasNext();) {
            Punkt punkt = it.next();
            punktDao.insertPunkt(punkt);
        }
    }
}
