package expert.kunkel.berge.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgis.LineString;

import expert.kunkel.berge.util.StringUtils;

public class Tourentag implements java.io.Serializable {

	private static DAOFactory factory = DAOFactory
			.getDAOFactory(DAOFactory.POSTGRESQL);
	private static TourabschnittDAO taDao = factory.getTourabschnittDAO();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private static final long serialVersionUID = 4309981437753834023L;
	private int tag;
	private Tour tour;
	private String beschreibung;
	private Date date;
	private Region region;
	private Integer hmAufstieg;
	private Integer hmAbstieg;
	private Double gehzeit;
	private String schwierigkeiten;
	private String bilddatei;
	private String bildttitel;
	private LineString track;

	public LineString getTrack() {
		return track;
	}

	public void setTrack(LineString track) {
		this.track = track;
	}

	public Tourentag(Tour tour) {
		this.tour = tour;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Double getGehzeit() {
		return gehzeit;
	}

	public String getGehzeitAsString() {
		if (gehzeit != null) {
			return gehzeit.toString();
		}
		return null;
	}

	public void setGehzeit(Double gehzeit) {
		if (gehzeit == 0.0) {
			this.gehzeit = null;
		} else {
			this.gehzeit = gehzeit;
		}
	}

	public void setGehzeit(String gehzeit) {
		if (gehzeit == null || gehzeit.equals("")) {
			this.gehzeit = null;
		} else {
			gehzeit = gehzeit.replace(",", ".");
			this.gehzeit = Double.parseDouble(gehzeit);
		}
	}

	public Integer getHmAbstieg() {
		return hmAbstieg;
	}

	public String getHmAbstiegAsString() {
		if (hmAbstieg != null) {
			return hmAbstieg.toString();
		}
		return null;
	}

	public void setHmAbstieg(Integer hmAbstieg) {
		if (hmAbstieg == 0.0) {
			this.hmAbstieg = null;
		} else {
			this.hmAbstieg = hmAbstieg;
		}
	}

	public void setHmAbstieg(String hmAbstieg) {
		if (hmAbstieg == null || hmAbstieg.equals("")) {
			this.hmAbstieg = null;
		} else {
			this.hmAbstieg = Integer.parseInt(hmAbstieg);
		}
	}

	public Integer getHmAufstieg() {
		return hmAufstieg;
	}

	public String getHmAufstiegAsString() {
		if (hmAufstieg != null) {
			return hmAufstieg.toString();
		}
		return null;
	}

	public void setHmAufstieg(Integer hmAufstieg) {
		if (hmAufstieg == 0.0) {
			this.hmAufstieg = null;
		} else {
			this.hmAufstieg = hmAufstieg;
		}
	}

	public void setHmAufstieg(String hmAufstieg) {
		if (hmAufstieg == null || hmAufstieg.equals("")) {
			this.hmAufstieg = null;
		} else {
			this.hmAufstieg = Integer.parseInt(hmAufstieg);
		}
	}

	public String getSchwierigkeiten() {
		return schwierigkeiten;
	}

	public String getBilddatei() {
		return bilddatei;
	}

	public void setBilddatei(String bilddatei) {
		this.bilddatei = bilddatei;
	}

	public String getBildttitel() {
		return bildttitel;
	}

	public void setBildttitel(String bildttitel) {
		this.bildttitel = bildttitel;
	}

	public void setSchwierigkeiten(String schwierigkeiten) {
		this.schwierigkeiten = schwierigkeiten;
	}

	@Override
	public String toString() {
		return "Tag " + this.getTag() + " (" + sdf.format(this.getDate()) + ")";
	}
}
