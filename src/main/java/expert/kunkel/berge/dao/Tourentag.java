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
	private SimpleDateFormat sdfLang = new SimpleDateFormat("dd. MMMM yyyy");
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

	public String getBeschreibungAsHtml() {
		return StringUtils.encodeHTML(beschreibung);
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

	public String getSchwierigkeitenAsHtml() {
		if (schwierigkeiten == null) {
			return "";
		}
		return StringUtils.encodeHTML(schwierigkeiten);
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

	public String getTourenverlaufAsHtml() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("<p><strong>");
			sb.append(sdfLang.format(this.getDate()));
			sb.append("</strong></p>\r\n");
			sb.append("<p/>");
			List<Tourabschnitt> tas = taDao.selectTourabschnitt(this);
			for (int i = 0; i < tas.size(); i++) {
				Tourabschnitt tourabschnitt = tas.get(i);
				if (i == 0) {
					sb.append("<i>");
					sb.append(tourabschnitt.getVonPunkt().getNameAsHtml());
					sb.append("</i><br/>");
				}
				sb.append("<i>");
				sb.append(tourabschnitt.getNachPunkt().getNameAsHtml());
				sb.append("</i>");
				if (i < tas.size() - 1) {
					sb.append("<br/>");
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(Tourentag.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Tourentag.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return sb.toString();
	}

	public String getTourenberichtAsHtml() {
		StringBuffer sb = new StringBuffer();

		NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
		nf.setMinimumFractionDigits(0);
		String sGehzeit = null;
		if (this.getGehzeit() != null) {
			sGehzeit = nf.format(this.getGehzeit());
		}

		sb.append("<h4>");
		sb.append(sdfLang.format(this.getDate()) + ":");
		sb.append("</h4>\r\n");

		// Schreiben des img-Links
		if (bilddatei != null) {
			sb.append("<img src=\"" + bilddatei + "\" ");
			if (bildttitel != null) {
				String htmlTitel = StringUtils.encodeHTML(bildttitel);
				sb.append(" alt=\"");
				sb.append(htmlTitel);
				sb.append("\"");
				sb.append(" title=\"");
				sb.append(htmlTitel);
				sb.append("\"");
			}
			sb.append(" border=\"0\" align=\"right\"/>");
		}

		sb.append("<p>");
		if (this.getHmAufstiegAsString() != null) {
			sb.append("<i>Aufstieg</i>: ca. " + this.getHmAufstiegAsString()
					+ "m<br/>\r\n");
		}
		if (this.getHmAbstiegAsString() != null) {
			sb.append("<i>Abstieg</i>: ca. " + this.getHmAbstiegAsString()
					+ "m<br/>\r\n");
		}
		if (sGehzeit != null) {
			sb.append("<i>Gehzeit</i>: ca. " + sGehzeit + "h<br/>\r\n");
		}
		String schwk = this.getSchwierigkeitenAsHtml();
		if (schwk != null && !schwk.equals("")) {
			sb.append("<i>Besondere Schwierigkeiten</i>: " + schwk
					+ "<br/>\r\n");
		}
		sb.append("</p>\r\n");
		sb.append("<p>");
		sb.append("<i>Gipfelbesteigungen</i>:");
		sb.append("</p>\r\n");
		sb.append("<ul>");

		List<Tourabschnitt> tas = null;
		try {
			tas = taDao.selectTourabschnitt(this);
		} catch (SQLException ex) {
			Logger.getLogger(Tourentag.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Tourentag.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		List<Punkt> gipfel = new ArrayList<Punkt>();

		if (tas == null || tas.isEmpty()) {
			sb.append("<li>Keine</li>\r\n");
		} else {
			for (int i = 0; i < tas.size(); i++) {
				Tourabschnitt tourabschnitt = tas.get(i);
				Punkt vonP = tourabschnitt.getVonPunkt();
				if (vonP.getTyp().getName().equals("Gipfel")) {
					if (!gipfel.contains(vonP)) {
						gipfel.add(vonP);
					}
				}
				Punkt nachP = tourabschnitt.getNachPunkt();
				if (nachP.getTyp().getName().equals("Gipfel")) {
					if (!gipfel.contains(nachP)) {
						gipfel.add(nachP);
					}
				}
			}

			if (gipfel.isEmpty()) {
				sb.append("<li>Keine</li>\r\n");
			} else {
				for (int i = 0; i < gipfel.size(); i++) {
					Punkt p = gipfel.get(i);
					sb.append("<li>");
					sb.append(p.getNameAndHoeheAsHtml());
					sb.append("</li>\r\n");
				}
			}
		}

		sb.append("</ul>\r\n");

		if (this.getBeschreibung() != null) {
			sb.append("<p>");
			sb.append(this.getBeschreibungAsHtml());
			sb.append("</p>");
		}

		return sb.toString();
	}
}
