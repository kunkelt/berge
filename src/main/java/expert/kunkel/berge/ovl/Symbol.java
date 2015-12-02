package expert.kunkel.berge.ovl;

import java.io.*;
import java.io.RandomAccessFile;

import com.vividsolutions.jts.geom.Coordinate;


/**
 * Copyright: GeoTask AG, Basel<br>
 * 
 * @author Thorsten Kunkel
 */
public abstract class Symbol {

    protected int typ;
    
    public static final int SYMBOL_TYP_LINIE = 3;
    public static final int SYMBOL_TYP_FLAECHE = 4;
    
    public Symbol(int typ) {
        super();
        this.typ = typ;
    }
    
    public static Symbol getSymbol(int typ, RandomAccessFile raf) throws Exception {
        if (typ == 3) {
            return new OVL_Linie(raf);
        }
        if (typ == 4) {
            return new OVL_Flaeche(raf);
        }
        throw new Exception("Typ "+typ+" nicht unterstützt!");
    }
    
    protected int parseInt(String name, String line) throws Exception {
        if (line.startsWith(name+"=")) {
            String s = line.substring(line.indexOf("=")+1);
            return Integer.parseInt(s);
        }
        throw new Exception("Name \""+name+"\" not in line \""+line+"\"!");
    }
    
    protected double parseDouble(String name, String line) throws Exception {
        if (line.startsWith(name+"=")) {
            String s = line.substring(line.indexOf("=")+1);
            return Double.parseDouble(s);
        }
        throw new Exception("Name \""+name+"\" not in line \""+line+"\"!");
    }
    
    protected Coordinate parseCoordinate(int number, String linex, String liney) throws Exception {
        double sx = parseDouble("XKoord"+number, linex);
        double sy = parseDouble("YKoord"+number, liney);
        return new Coordinate(sx, sy);
    }
    
    public abstract String toText();
    
    protected void write(Coordinate[] coords, RandomAccessFile raf) throws IOException {
        if (coords == null) {
            return;
        }
        for (int i = 0; i < coords.length; i++) {
            raf.writeBytes("XKoord"+i+"="+coords[i].x);
            raf.writeBytes("YKoord"+i+"="+coords[i].y);
        }
    }
}
