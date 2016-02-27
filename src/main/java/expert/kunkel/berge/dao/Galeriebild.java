/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expert.kunkel.berge.dao;

import expert.kunkel.berge.util.StringUtils;

import java.io.Serializable;

/**
 *
 * @author thorsten
 */
public class Galeriebild implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4777545327817846146L;
	private Tour tour;
    private int sequenz;
    private int breite;
    private int hoehe;
    private String dateiname;
    private String titel;

    public Galeriebild(Tour tour) {
        this.tour = tour;
    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public String getDateiname() {
        return dateiname;
    }

    public void setDateiname(String dateiname) {
        this.dateiname = dateiname;
    }

    public int getHoehe() {
        return hoehe;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public int getSequenz() {
        return sequenz;
    }

    public void setSequenz(int sequenz) {
        this.sequenz = sequenz;
    }

    public String getTitel() {
        return titel;
    }

    public String getTitelAsHtml() {
        return StringUtils.encodeHTML(titel) ;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    

}
