package expert.kunkel.berge.ovl;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
/**
 * @author Thorsten Kunkel
 */
public class LineStringConnector {

    private static double maxDistance = 0.001;
    
    public static Polygon connect(LineString[] lines) throws Exception {
        if (lines == null) {
            return null;
        }
        if (lines.length == 0) {
            return null;
        }
        if (lines.length == 1) {
        	Coordinate[] coords = lines[0].getCoordinates();
            if (coords[0].distance(coords[coords.length-1]) <= maxDistance) {
                coords[coords.length-1] = coords[0];
            }
            else {
                throw new Exception("LineString is not closed!");
            }
        	CoordinateArraySequence cas = new CoordinateArraySequence(coords);
            LinearRing lr = new LinearRing(cas, new GeometryFactory());
            return new Polygon(lr, null, new GeometryFactory());
        }
        
//        int p = 0-lines.length;
//        for (int i = 0; i < lines.length; i++) {
//            p += lines[i].getNumPoints();
//        }
        Coordinate[] coords = null;
        
        Point start1 = lines[0].getStartPoint();
        Point end1 = lines[0].getEndPoint();
        Point start2 = lines[1].getStartPoint();
        Point end2 = lines[1].getEndPoint();
    
        if (end1.distance(start2) < 0.001 || end1.distance(end2) <= maxDistance) {
            System.out.println("richtig orientiert");
            coords = lines[0].getCoordinates();
            
        }
        else if (start1.distance(start2) < 0.001 || start1.distance(end2) <= maxDistance) {
            System.out.println("falsch orientiert");
            int coordCount = lines[0].getNumPoints();
            coords = new Coordinate[coordCount];
            for (int i = 0; i < coordCount; i++) {
                coords[i] = lines[0].getCoordinateN(coordCount-i-1);
            }
        }
        else {
            throw new Exception("Connection failed!");
        }
        
        for (int i = 1; i < lines.length; i++) {
            coords = connect(coords, lines[i].getCoordinates());
        }
        
        if (coords[0].distance(coords[coords.length-1]) <= maxDistance) {
            coords[coords.length-1] = coords[0];
        }
        else {
            throw new Exception("LineStrings are not closed!");
        }
        
    	CoordinateArraySequence cas = new CoordinateArraySequence(coords);
        LinearRing lr = new LinearRing(cas, new GeometryFactory());
        return new Polygon(lr, null, new GeometryFactory());
    }
    
    private static Coordinate[] connect(Coordinate[] cs1, Coordinate[] cs2) throws Exception {
        Coordinate[] result = new Coordinate[cs1.length + cs2.length - 1];
        System.arraycopy(cs1, 0, result, 0, cs1.length);
        
        System.out.println(cs1[cs1.length-1].distance(cs2[0]));
        System.out.println(cs1[cs1.length-1].distance(cs2[cs2.length-1]));
        
        if (cs1[cs1.length-1].distance(cs2[0]) <= maxDistance) {
            System.arraycopy(cs2, 1, result, cs1.length, cs2.length-1);
        }
        else if (cs1[cs1.length-1].distance(cs2[cs2.length-1]) <= maxDistance) {
            for (int i = 1; i < cs2.length; i++) {
                result[cs1.length-1 + i] = cs2[cs2.length-i-1];
            }
        }
        else {
            throw new Exception("Connection failed in LineStringConnector.connect(Coordinate[] cs1, Coordinate[] cs2)!");
        }
        
        
        return result;
    }
}
