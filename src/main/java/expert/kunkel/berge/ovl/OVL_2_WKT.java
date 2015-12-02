package expert.kunkel.berge.ovl;

import java.io.*;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.Polygon;


/**
 * Copyright: GeoTask AG, Basel<br>
 * 
 * @author Thorsten Kunkel
 */
public class OVL_2_WKT {

//    private MapLage mapLage = null;
    private Symbol[] symbols = null;

    public OVL_2_WKT(String filename, String outputfile) {
        super();
        int nmb = readNumberOfSymbols(filename);

        symbols = new Symbol[nmb];
        for (int i = 1; i <= nmb; i++) {
            try {
                RandomAccessFile raf = new RandomAccessFile(filename, "r");
                String line = null;
                while ((line = raf.readLine()) != null) {
                  if (line.equals("[Symbol "+i+"]")) {
                      line = raf.readLine();
                      String typ = line.substring(line.indexOf("=")+1);
                      symbols[i-1] = Symbol.getSymbol(Integer.parseInt(typ), raf);
                  }
                }
                raf.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        //Test connection of lines
        LineString[] lines = new LineString[] {
                        ((OVL_Linie)symbols[24]).getAsLineString(),
                        ((OVL_Linie)symbols[20]).getAsLineString(),
                        ((OVL_Linie)symbols[21]).getAsLineString(),
                        ((OVL_Linie)symbols[22]).getAsLineString(),
                        ((OVL_Linie)symbols[23]).getAsLineString()
        };
        
        try {
            Polygon polygon = LineStringConnector.connect(lines);
            OVL_Flaeche flaeche = new OVL_Flaeche(polygon, 1, 4, 1, 103, 1);
            System.out.println(polygon.toText());

            try {
                RandomAccessFile raf = new RandomAccessFile(outputfile, "rw");
                raf.writeBytes("[Symbol 1]"+"\r\n");
                flaeche.write(raf);
                raf.writeBytes("[Overlay]"+"\r\n");
                raf.writeBytes("Symbols=1"+"\r\n");
                raf.writeBytes("[MapLage]"+"\r\n");
                raf.writeBytes("MapName=Top. Karte 1:50 000 Bayern-Süd"+"\r\n");
                raf.writeBytes("DimmFc=100"+"\r\n");
                raf.writeBytes("ZoomFc=100"+"\r\n");
                raf.writeBytes("CenterLat="+polygon.getCentroid().getY()+"\r\n");
                raf.writeBytes("CenterLong="+polygon.getCentroid().getX()+"\r\n");
                raf.writeBytes("RefOn=1"+"\r\n");
                raf.writeBytes("RefLat="+polygon.getExteriorRing().getStartPoint().getY()+"\r\n");
                raf.writeBytes("RefLong="+polygon.getExteriorRing().getStartPoint().getX()+"\r\n");
                raf.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int readNumberOfSymbols(String filename) {
        try {
            RandomAccessFile raf = new RandomAccessFile(filename, "r");
            String line = null;
            while ((line = raf.readLine()) != null) {
              if (line.equals("[Overlay]")) {
                  line = raf.readLine();
                  String nmb = line.substring(line.indexOf("=")+1);
                  return Integer.parseInt(nmb);
              }
            }
            raf.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static void main(String[] args) {
        new OVL_2_WKT("C:/tmp/berge/2,5-11.ovl", "C:/tmp/berge/polygone/output.ovl");
    }
    

}
