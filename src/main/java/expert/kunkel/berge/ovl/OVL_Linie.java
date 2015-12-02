package expert.kunkel.berge.ovl;

import java.io.*;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

/**
 * Copyright: GeoTask AG, Basel<br>
 * 
 * @author Thorsten Kunkel
 */
public class OVL_Linie extends Symbol {
    
    int group;
    int col;
    int zoom;
    int size;
    int art;
    int punkte;
    Coordinate[] coordinates = null;
    
    public OVL_Linie(int g, int c, int z, int s, int a, Coordinate[] coords) {
        super(SYMBOL_TYP_LINIE);
        
    	this.group = g;
    	this.col = c;
    	this.zoom = z;
    	this.size = s;
    	this.art = a;
    	this.punkte = coords.length;
    	this.coordinates = coords;
    }

    public OVL_Linie(RandomAccessFile raf) {
        super(SYMBOL_TYP_LINIE);
        
        try {
            group = parseInt("Group", raf.readLine());
            col = parseInt("Col", raf.readLine());
            zoom = parseInt("Zoom", raf.readLine());
            size = parseInt("Size", raf.readLine());
            art = parseInt("Art", raf.readLine());
            punkte = parseInt("Punkte", raf.readLine());
            coordinates = new Coordinate[punkte];
            
            for (int i=0; i<punkte; i++) {
                coordinates[i] = parseCoordinate(i, raf.readLine(), raf.readLine());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public LineString getAsLineString() {
    	CoordinateArraySequence cas = new CoordinateArraySequence(coordinates);
        return new LineString(cas, new GeometryFactory());
    }
    
    public Polygon getAsPolygon() {
    	CoordinateArraySequence cas = new CoordinateArraySequence(coordinates);
        LinearRing lr = new LinearRing(cas, new GeometryFactory());
        return new Polygon(lr, null, new GeometryFactory());
    }
    
    public String toText() {
        return getAsLineString().toText();
    }
    
    public void write(RandomAccessFile raf) throws IOException {
        raf.writeBytes("Typ="+typ+"\r\n");
        raf.writeBytes("Group="+group+"\r\n");
        raf.writeBytes("Col="+col+"\r\n");
        raf.writeBytes("Zoom="+zoom+"\r\n");
        raf.writeBytes("Size="+size+"\r\n");
        raf.writeBytes("Art="+art+"\r\n");
        raf.writeBytes("Punkte="+punkte+"\r\n");
        for (int i = 0; i < punkte; i++) {
            raf.writeBytes("XKoord"+i+"="+coordinates[i].x+"\r\n");
            raf.writeBytes("YKoord"+i+"="+coordinates[i].y+"\r\n");
        }
    }

}
