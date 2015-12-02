package expert.kunkel.berge.ovl;

import java.io.*;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

/**
 * Copyright: GeoTask AG, Basel<br>
 * 
 * @author Thorsten Kunkel
 */
public class OVL_Flaeche extends Symbol {
    
    int group;
    int col;
    int zoom;
    int size;
    int area;
    int punkte;
    Coordinate[] coordinates = null;

    public OVL_Flaeche(RandomAccessFile raf) {
        super(SYMBOL_TYP_FLAECHE);
        
        try {
            group = parseInt("Group", raf.readLine());
            col = parseInt("Col", raf.readLine());
            zoom = parseInt("Zoom", raf.readLine());
            size = parseInt("Size", raf.readLine());
            area = parseInt("Area", raf.readLine());
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
    
    public OVL_Flaeche(Polygon polygon, int group, int col, int zoom, int size, int area) {
        super(4);
        this.group = group;
        this.col = col; 
        this.zoom = zoom;
        this.size = size;
        this.area = area;
        this.coordinates = polygon.getCoordinates();
        this.punkte = coordinates.length; 
    }
    
    public Polygon getAsPolygon() {
    	CoordinateArraySequence cas = new CoordinateArraySequence(coordinates);
        LinearRing lr = new LinearRing(cas, new GeometryFactory());
        return new Polygon(lr, null, new GeometryFactory());
    }
    
    public String toText() {
        return getAsPolygon().toText();
    }
    
    public void write(RandomAccessFile raf) throws IOException {
        raf.writeBytes("Typ="+typ+"\r\n");
        raf.writeBytes("Group="+group+"\r\n");
        raf.writeBytes("Col="+col+"\r\n");
        raf.writeBytes("Zoom="+zoom+"\r\n");
        raf.writeBytes("Size="+size+"\r\n");
        raf.writeBytes("Area="+area+"\r\n");
        raf.writeBytes("Punkte="+punkte+"\r\n");
        for (int i = 0; i < punkte; i++) {
            raf.writeBytes("XKoord"+i+"="+coordinates[i].x+"\r\n");
            raf.writeBytes("YKoord"+i+"="+coordinates[i].y+"\r\n");
        }
    }
}
