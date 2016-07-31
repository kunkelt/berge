package expert.kunkel.berge.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.KmlException;
import org.boehn.kmlframework.kml.Placemark;
import org.boehn.kmlframework.kml.Point;

import com.vividsolutions.jts.geom.Polygon;

import expert.kunkel.berge.dao.jpa.TourDao;
import expert.kunkel.berge.gui.Berge;
import expert.kunkel.berge.model.Galeriebild;
import expert.kunkel.berge.model.Karte;
import expert.kunkel.berge.model.Punkt;
import expert.kunkel.berge.model.Region;
import expert.kunkel.berge.model.Tour;
import expert.kunkel.berge.model.Tourabschnitt;
import expert.kunkel.berge.model.Tourentag;
import expert.kunkel.berge.ovl.OVL_Flaeche;

public class HtmlExporter {
	private SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM yyyy");
	private SimpleDateFormat sdfLang = new SimpleDateFormat("dd. MMMM yyyy");

	public void exportTour(Tour tour, String baseFolder) {

		String folder = baseFolder + File.separator + "tour" + tour.getId()
				+ File.separator;
		File ffolder = new File(folder);
		ffolder.mkdir();

		StringBuffer template = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new FileReader(
					Berge.FOLDER_UMZUG + File.separator + "template-tour.html"));

			while (br.ready()) {
				template.append(br.readLine());
			}
			br.close();
		} catch (IOException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
		}

		String tourenBericht = template.toString();
		tourenBericht = tourenBericht.replace("%TOUR_NAME%", getName(tour));
		tourenBericht = tourenBericht.replace("%TOUR_ZEITRAUM%",
				getZeitraum(tour));
		tourenBericht = tourenBericht.replace("%KARTENEMPFEHLUNGEN%",
				getKarten(tour.getKarten()));
		if (getZusatzinfoMitKml(tour, folder) != null
				&& !getZusatzinfoMitKml(tour, folder).equals("")) {
			tourenBericht = tourenBericht.replace(
					"%ZUSATZINFORMATIONEN%",
					"<h3>Zusatzinformationen:</h3>"
							+ getZusatzinfoMitKml(tour, folder));
		} else {
			tourenBericht = tourenBericht.replace("%ZUSATZINFORMATIONEN%", "");
		}
		if (tour.getBeschreibung() != null && !getBeschreibung(tour).equals("")) {
			tourenBericht = tourenBericht.replace("%TOURENBESCHREIBUNG%",
					"<h3>Kurze Tourenbeschreibung:</h3>"
							+ getBeschreibung(tour));
		} else {
			tourenBericht = tourenBericht.replace("%TOURENBESCHREIBUNG%", "");
		}

		tourenBericht = tourenBericht.replace("%TOURENVERLAUF%",
				getTourenverlauf(tour));
		tourenBericht = tourenBericht.replace("%TOURENBERICHT%",
				getTourenbericht(tour));
		tourenBericht = tourenBericht.replace("%JAHR%",
				Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
		tourenBericht = tourenBericht
				.replace("%DATUM%", sdf.format(new Date()));

		FileWriter fw;
		try {
			fw = new FileWriter(folder + "index.html");
			fw.write(tourenBericht);
			fw.close();
		} catch (IOException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
		}

		// zu guter letzt speichern wir noch den Link auf die Tour
		tour.setLink("Touren/tour" + tour.getId() + "/index.html");
		new TourDao().updateTour(tour);
	}

	private String getBeschreibung(Tour tour) {
		return StringUtils.encodeHTML(tour.getBeschreibung());
	}

	private String getKarten(List<Karte> karten) {
		StringBuffer result = new StringBuffer();

		if (karten.size() == 0) {
			return "<ul><li>Keine.</li></ul>";
		}

		result.append("<ul>");
		for (int i = 0; i < karten.size(); i++) {
			Karte karte = karten.get(i);
			result.append("<li>");
			result.append(toHtmlString(karte));
			result.append("</li>\r\n");
		}
		result.append("</ul>");
		return result.toString();
	}

	private String getTourenbericht(Tour tour) {
		StringBuffer sb = new StringBuffer();
		List<Tourentag> ttage = tour.getTourentage();
		for (int i = 0; i < ttage.size(); i++) {
			Tourentag tourentag = ttage.get(i);
			sb.append(getTourenbericht(tourentag));
		}

		// nach den Tourentagen bauen wir noch die Bildergalerie hinten an
		List<Galeriebild> bilder = tour.getGaleriebilder();

		for (int i = 0; i < bilder.size(); i++) {
			Galeriebild bild = bilder.get(i);

			sb.append("<p align=\"center\">");
			sb.append("<img src=\"");
			sb.append(bild.getDateiname());
			sb.append("\" alt=\"");
			sb.append(getTitel(bild));
			sb.append("\" title=\"");
			sb.append(getTitel(bild));
			sb.append("\" border=\"0\"");
			if (bild.getBreite() > 0) {
				sb.append(" width=\"" + bild.getBreite() + "\"");
			}
			if (bild.getHoehe() > 0) {
				sb.append(" height=\"" + bild.getHoehe() + "\"");
			}
			sb.append("><br>");
			sb.append(getTitel(bild));
			sb.append("</p>");
		}

		return sb.toString();
	}

	private String getTourenverlauf(Tour tour) {
		StringBuffer sb = new StringBuffer();
		List<Tourentag> ttage = tour.getTourentage();
		for (int i = 0; i < ttage.size(); i++) {
			Tourentag tourentag = ttage.get(i);
			sb.append(getTourenverlauf(tourentag));
		}
		return sb.toString();
	}

	private String getZeitraum(Tour tour) {
		return StringUtils.encodeHTML(tour.getZeitraum());
	}

	private String getZusatzinfoMitKml(Tour tour, String folder) {
		String result = "<p>";
		try {
			Kml kml = getTourenverlaufAsKml(tour);
			kml.createKml(folder + "tour" + tour.getId() + ".kml");
			result += "<a href=\"tour" + tour.getId() + ".kml\">";
			result += "Tourenverlauf als KML-Datei f&uuml;r Google Earth";
			result += "</a></p>";
			if (tour.getZusatzinfo() != null
					&& !tour.getZusatzinfo().equals("")) {
				result += tour.getZusatzinfo();
			}
		} catch (KmlException ex) {
			Logger.getLogger(Tour.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Tour.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	private String getName(Tour tour) {
		return StringUtils.encodeHTML(tour.getName());
	}

	private Kml getTourenverlaufAsKml(Tour tour) {
		Kml kml = new Kml();
		Document document = new Document();
		document.setName(tour.getName());
		kml.setFeature(document);

		List<Tourentag> ttage = tour.getTourentage();

		for (int i = 0; i < ttage.size(); i++) {
			Tourentag ttag = ttage.get(i);

			List<Tourabschnitt> tas = ttag.getTourabschnitte();

			Document doc = new Document();
			doc.setName(sdf.format(ttag.getDatum()) + ", Tag " + (i + 1));
			document.addFeature(doc);

			for (int j = 0; j < tas.size(); j++) {
				Tourabschnitt ta = tas.get(j);

				if (j == 0) {
					Placemark placemark = new Placemark();
					Point point = new Point(ta.getVonPunkt().getLage().getX(),
							ta.getVonPunkt().getLage().getY());
					placemark.setGeometry(point);
					placemark.setName(ta.getVonPunkt().getName());
					doc.addFeature(placemark);
				}

				Placemark placemark = new Placemark();
				Point point = new Point(ta.getNachPunkt().getLage().getX(), ta
						.getNachPunkt().getLage().getY());
				placemark.setGeometry(point);
				placemark.setName(ta.getNachPunkt().getName());
				doc.addFeature(placemark);
			}
		}

		return kml;
	}

	private String getTitel(Galeriebild bild) {
		return StringUtils.encodeHTML(bild.getTitel());
	}

	private String toHtmlString(Karte karte) {
		String result = "";

		if (karte.getVerlag() != null) {
			result += karte.getVerlag().getName() + "; ";
		}
		result += karte.getTitel();
		if (karte.getUntertitel() != null) {
			result += ", " + karte.getUntertitel();
		}
		if (karte.getBlattnummer() != null) {
			result += "; Blatt " + karte.getBlattnummer();
		}
		if (karte.getMassstab() != null) {
			result += "; Maßstab " + karte.getMassstab();
		}
		return StringUtils.encodeHTML(result);
	}

	private String getBeschreibung(Tourentag ttag) {
		return StringUtils.encodeHTML(ttag.getBeschreibung());
	}

	private String getSchwierigkeiten(Tourentag ttag) {
		if (ttag.getSchwierigkt() == null) {
			return "";
		}
		return StringUtils.encodeHTML(ttag.getSchwierigkt());
	}

	private String getTourenbericht(Tourentag ttag) {
		StringBuffer sb = new StringBuffer();

		NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
		nf.setMinimumFractionDigits(0);
		String sGehzeit = null;
		if (ttag.getGehzeit() != null) {
			sGehzeit = nf.format(ttag.getGehzeit());
		}

		sb.append("<h4>");
		sb.append(sdfLang.format(ttag.getDatum()) + ":");
		sb.append("</h4>\r\n");

		// Schreiben des img-Links
		if (ttag.getBilddatei() != null) {
			sb.append("<img src=\"" + ttag.getBilddatei() + "\" ");
			if (ttag.getBildtitel() != null) {
				String htmlTitel = StringUtils.encodeHTML(ttag.getBildtitel());
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
		if (ttag.getHmAufstiegAsString() != null) {
			sb.append("<i>Aufstieg</i>: ca. " + ttag.getHmAufstiegAsString()
					+ "m<br/>\r\n");
		}
		if (ttag.getHmAbstiegAsString() != null) {
			sb.append("<i>Abstieg</i>: ca. " + ttag.getHmAbstiegAsString()
					+ "m<br/>\r\n");
		}
		if (sGehzeit != null) {
			sb.append("<i>Gehzeit</i>: ca. " + sGehzeit + "h<br/>\r\n");
		}
		String schwk = getSchwierigkeiten(ttag);
		if (schwk != null && !schwk.equals("")) {
			sb.append("<i>Besondere Schwierigkeiten</i>: " + schwk
					+ "<br/>\r\n");
		}
		sb.append("</p>\r\n");
		sb.append("<p>");
		sb.append("<i>Gipfelbesteigungen</i>:");
		sb.append("</p>\r\n");
		sb.append("<ul>");

		List<Tourabschnitt> tas = ttag.getTourabschnitte();
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
					sb.append(getNameAndHoehe(p));
					sb.append("</li>\r\n");
				}
			}
		}

		sb.append("</ul>\r\n");

		if (getBeschreibung(ttag) != null) {
			sb.append("<p>");
			sb.append(getBeschreibung(ttag));
			sb.append("</p>");
		}

		return sb.toString();
	}

	private String getTourenverlauf(Tourentag ttag) {
		StringBuffer sb = new StringBuffer();
		sb.append("<p><strong>");
		sb.append(sdfLang.format(ttag.getDatum()));
		sb.append("</strong></p>\r\n");
		sb.append("<p/>");
		List<Tourabschnitt> tas = ttag.getTourabschnitte();
		for (int i = 0; i < tas.size(); i++) {
			Tourabschnitt tourabschnitt = tas.get(i);
			if (i == 0) {
				sb.append("<i>");
				sb.append(getName(tourabschnitt.getVonPunkt()));
				sb.append("</i><br/>");
			}
			sb.append("<i>");
			sb.append(getName(tourabschnitt.getNachPunkt()));
			sb.append("</i>");
			if (i < tas.size() - 1) {
				sb.append("<br/>");
			}
		}
		return sb.toString();
	}

	private String getName2(Punkt punkt) {
		return punkt.getName2();
	}

	private String getNameAndHoehe(Punkt punkt) {
		String result = null;
		if (punkt.getUrl() == null || punkt.getUrl().equals("")) {
			result = getName(punkt);
			result += ", ";
			result += punkt.getHoehe();
			result += "m";
		} else {
			result = "<a href=\"" + punkt.getUrl() + "\" target=\"_blank\">"
					+ getName(punkt);
			result += ", ";
			result += punkt.getHoehe();
			result += "m";
			result += "</a>";
		}

		return result;
	}

	private String getName(Punkt punkt) {
		String result = null;
		if (punkt.getUrl() == null || punkt.getUrl().equals("")) {
			result = StringUtils.encodeHTML(punkt.getName());
			if (getName2(punkt) != null && !getName2(punkt).equals("")) {
				result += " (" + getName2(punkt) + ")";
			}
		} else {
			result = "<a href=\"" + punkt.getUrl() + "\" target=\"_blank\">"
					+ StringUtils.encodeHTML(punkt.getName());
			if (getName2(punkt) != null && !getName2(punkt).equals("")) {
				result += " (" + getName2(punkt) + ")";
			}
			result += "</a>";
		}
		return result;
	}

	public void exportRegion(Region region, String absolutePath) {
		try {
			String fileName = absolutePath + File.separator + region.getId()
					+ " - " + region.getName() + ".ovl";
			Polygon polygon = region.getExtent();
			System.out.println(polygon.toText());
			OVL_Flaeche flaeche = new OVL_Flaeche(polygon, 1, 1, 1, 103, 1);
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			raf.writeBytes("[Symbol 1]\r\n");
			flaeche.write(raf);
			raf.writeBytes("[Overlay]" + "\r\n");
			raf.writeBytes("Symbols=1" + "\r\n");
			raf.writeBytes("[MapLage]" + "\r\n");
			raf.writeBytes("MapName=" + region.getName() + "\r\n");
			raf.writeBytes("DimmFc=100" + "\r\n");
			raf.writeBytes("ZoomFc=100" + "\r\n");
			raf.writeBytes("CenterLat=" + polygon.getCentroid().getY() + "\r\n");
			raf.writeBytes("CenterLong=" + polygon.getCentroid().getX()
					+ "\r\n");
			raf.writeBytes("RefOn=1" + "\r\n");
			raf.writeBytes("RefLat="
					+ polygon.getExteriorRing().getStartPoint().getY() + "\r\n");
			raf.writeBytes("RefLong="
					+ polygon.getExteriorRing().getStartPoint().getX() + "\r\n");
			raf.close();
		} catch (IOException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
