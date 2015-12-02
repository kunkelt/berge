package expert.kunkel.berge.util;

import java.io.*;

import org.geotools.data.shapefile.shp.*;

import com.vividsolutions.jts.geom.*;

public class ShapeUtilities {

	public static void createShapeFile(LineString ls) throws IOException {
//		LineString[] geoms = new LineString[] { ls };
//		MultiLineString gc = new MultiLineString(geoms, new GeometryFactory());
		
		RandomAccessFile shp = new RandomAccessFile("C:/tmp/myshape.shp", "rw");
		RandomAccessFile shx = new RandomAccessFile("C:/tmp/myshape.shx", "rw");
		
		ShapefileWriter sw = new ShapefileWriter(shp.getChannel(), shx.getChannel());
		sw.writeHeaders(ls.getEnvelopeInternal(), ShapeType.ARC, 1, 0);
		sw.writeGeometry(ls);
		
		shp.close();
		shx.close();
	}
	
	public static void createShapeFile(Polygon polygon) throws IOException {
//		Polygon[] geoms = new Polygon[] { polygon };
//		MultiPolygon gc = new MultiPolygon(geoms, new GeometryFactory());
		
		RandomAccessFile shp = new RandomAccessFile("C:/tmp/myshape.shp", "rw");
		RandomAccessFile shx = new RandomAccessFile("C:/tmp/myshape.shx", "rw");
		
		ShapefileWriter sw = new ShapefileWriter(shp.getChannel(), shx.getChannel());
		sw.writeHeaders(polygon.getEnvelopeInternal(), ShapeType.POLYGON, 1, 0);
		sw.writeGeometry(polygon);
		
		shp.close();
		shx.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
