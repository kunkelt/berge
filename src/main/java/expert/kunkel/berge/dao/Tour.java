package expert.kunkel.berge.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.KmlException;
import org.boehn.kmlframework.kml.Placemark;
import org.boehn.kmlframework.kml.Point;

import expert.kunkel.berge.util.StringUtils;

public class Tour implements java.io.Serializable, Comparable<Tour> {

	private DAOFactory factory = DAOFactory
			.getDAOFactory(DAOFactory.POSTGRESQL);
	private static final long serialVersionUID = 6367515334052469880L;
	private int id;
	private String name;
	private String beschreibung;
	private String link;
	private String zusatzinfo;
	private boolean geplant;
	private List<Karte> karten = new ArrayList<Karte>();
	private List<Tourentag> ttage = new ArrayList<Tourentag>();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM yyyy");

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Karte> getKarten() {
		return karten;
	}

	public void setKarten(List<Karte> karten) {
		this.karten = karten;
	}

	public String getZusatzinfo() {
		return zusatzinfo;
	}

	public boolean isGeplant() {
		return geplant;
	}

	public void setGeplant(boolean geplant) {
		this.geplant = geplant;
	}

	public void setZusatzinfo(String zusatzinfo) {
		this.zusatzinfo = zusatzinfo;
	}

	public void setKarten(Object[] karten) {
		if (karten == null) {
			this.karten = new ArrayList<Karte>();
		} else {
			this.karten.clear();
		}
		for (Object element : karten) {
			this.karten.add((Karte) element);
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(" (");

		List<Tourentag> ttage = null;
		try {
			ttage = getTourentage();
		} catch (SQLException ex) {
			Logger.getLogger(Tour.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Tour.class.getName()).log(Level.SEVERE, null, ex);
		}

		if (ttage != null) {
			List<Region> regionen = new ArrayList<Region>();

			for (int i = 0; i < ttage.size(); i++) {
				Tourentag ttag = ttage.get(i);

				if (!regionen.contains(ttag.getRegion())) {
					regionen.add(ttag.getRegion());
				}
			}

			for (int i = 0; i < regionen.size(); i++) {
				Region region = regionen.get(i);

				if (i != 0) {
					sb.append("/");
				}

				sb.append(region.getName());
			}
			sb.append("; ");

			Tourentag ttag = ttage.get(0);

			sb.append(sdf.format(ttag.getDate()));

			if (ttage.size() > 1) {
				ttag = ttage.get(ttage.size() - 1);
				sb.append(" - ");
				sb.append(sdf.format(ttag.getDate()));
			}
		}
		sb.append(")");

		return sb.toString();
	}

	public List<Tourentag> getTourentage() throws SQLException,
			ClassNotFoundException {
		if (ttage == null || ttage.isEmpty()) {
			TourentagDAO dao = factory.getTourentagDAO();
			ttage = dao.selectTourentag(this);
		}
		return ttage;
	}

	@Override
	public int compareTo(Tour t) {
		Tourentag tt1 = null;
		Tourentag tt2 = null;
		try {
			tt1 = this.getTourentage().get(0);
			tt2 = t.getTourentage().get(0);
		} catch (SQLException ex) {
			Logger.getLogger(Tour.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Tour.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (tt1 == null || tt2 == null) {
			return 1;
		}

		return tt2.getDate().compareTo(tt1.getDate());
	}

	public String getZeitraum() throws SQLException, ClassNotFoundException {
		List<Tourentag> ttage = this.getTourentage();
		String result = sdf.format(ttage.get(0).getDate());

		if (ttage.size() > 1) {
			result += " bis ";
			result += sdf.format(ttage.get(ttage.size() - 1).getDate());
		}

		return result;
	}

	public boolean hasCompleteTourenabschnitte() throws ClassNotFoundException,
			SQLException {
		boolean complete = true;
		for (Tourentag tag : getTourentage()) {
			if (DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getTourabschnittDAO().selectTourabschnitt(tag).isEmpty()) {
				complete = false;
			}
		}
		return complete;
	}
}
