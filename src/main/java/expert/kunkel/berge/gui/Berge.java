package expert.kunkel.berge.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.xml.bind.JAXBException;

import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.KmlException;
import org.boehn.kmlframework.kml.Placemark;
import org.boehn.kmlframework.kml.Point;

import se.kodapan.osm.parser.xml.streaming.StreamingOsmXmlParser;

import com.vividsolutions.jts.geom.Polygon;

import expert.kunkel.berge.dao.jpa.DaoFactory;
import expert.kunkel.berge.dao.jpa.KarteDao;
import expert.kunkel.berge.dao.jpa.PunktDao;
import expert.kunkel.berge.dao.jpa.PunkttypDao;
import expert.kunkel.berge.dao.jpa.RegionDao;
import expert.kunkel.berge.dao.jpa.TourDao;
import expert.kunkel.berge.gui.model.KarteTableModel;
import expert.kunkel.berge.gui.model.PunktTableModel;
import expert.kunkel.berge.gui.model.RegionTableModel;
import expert.kunkel.berge.gui.model.TourTableModel;
import expert.kunkel.berge.model.Karte;
import expert.kunkel.berge.model.Punkt;
import expert.kunkel.berge.model.Punkttyp;
import expert.kunkel.berge.model.Region;
import expert.kunkel.berge.model.Tour;
import expert.kunkel.berge.ovl.OVL_Flaeche;
import expert.kunkel.berge.util.GpxUtils;
import expert.kunkel.berge.util.HtmlExporter;
import expert.kunkel.berge.util.OsmParserListener;

/**
 * 
 * @author thorsten
 */
public class Berge extends javax.swing.JFrame implements MouseListener {

	public class TourTableCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 8508583837824725968L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			if (!isSelected) {
				c.setBackground(UIManager.getColor("Table.background"));
				TourTableModel tableModel = (TourTableModel) table.getModel();
				Tour tour = tableModel.getValueAt(tourRowSorter
						.convertRowIndexToModel(row));
				try {
					if (!tour.hasCompleteTourenabschnitte()) {
						c.setBackground(Color.RED);
					}
				} catch (ClassNotFoundException | SQLException e) {
					c.setBackground(Color.BLACK);
					e.printStackTrace();
				}
			}

			return c;
		}

	}

	private static final long serialVersionUID = -5458265899367590051L;
	private TourTableModel tourTableModel = new TourTableModel();
	private PunktTableModel punktTableModel = new PunktTableModel();
	private KarteTableModel karteTableModel = new KarteTableModel();
	private RegionTableModel regionTableModel = new RegionTableModel();
	private TableRowSorter<TourTableModel> tourRowSorter = new TableRowSorter<TourTableModel>(
			tourTableModel);
	private TableRowSorter<PunktTableModel> punktRowSorter = new TableRowSorter<PunktTableModel>(
			punktTableModel);
	private TableRowSorter<KarteTableModel> karteRowSorter = new TableRowSorter<KarteTableModel>(
			karteTableModel);
	private TableRowSorter<RegionTableModel> regionRowSorter = new TableRowSorter<RegionTableModel>(
			regionTableModel);
	private DaoFactory factory = DaoFactory.getInstance();
	private TourDao tourDao = factory.getTourDAO();
	private KarteDao karteDao = factory.getKarteDAO();
	private RegionDao regionDao = factory.getRegionDAO();
	private PunktDao punktDao = factory.getPunktDAO();
	private PunkttypDao punkttypDao = factory.getPunkttypDAO();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private SimpleDateFormat sdfLang = new SimpleDateFormat("dd. MMMM yyyy");
	public static final String FOLDER_UMZUG = "/Users/thorsten/";
	public static final String FOLDER_TOUREN_CHRONO = FOLDER_UMZUG;
	public static final String FOLDER_GIPFEL_HOEHE = FOLDER_UMZUG;
	public static final String FOLDER_GIPFEL_GEBIET = FOLDER_UMZUG;
	public static final String FOLDER_TOUREN = FOLDER_UMZUG + "/Touren";
	public static final String FOLDER_TMP = "/tmp";
	public static final String FOLDER_DESKTOP = "/Users/thorsten/Desktop";
	public static final String FOLDER_PUNKTE = FOLDER_UMZUG + "/Punkte";
	public static final String FOLDER_KARTE = FOLDER_UMZUG + "/Karten";
	public static final String FOLDER_REGION = FOLDER_UMZUG + "/Gebirgsgruppen";

	/** Creates new form NewApplication */
	public Berge() {
		initComponents();

		tourRowSorter.setComparator(1, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		tourRowSorter.setComparator(3, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});

		tourTable.getColumn(tourTableModel.getColumnName(3)).setCellRenderer(
				new TourTableCellRenderer());

		tourTable.getColumn(tourTableModel.getColumnName(3)).setPreferredWidth(
				10);
		tourTable.getColumn(tourTableModel.getColumnName(2)).setPreferredWidth(
				400);
		tourTable.getColumn(tourTableModel.getColumnName(4)).setPreferredWidth(
				30);
		tourTable.getColumn(tourTableModel.getColumnName(1)).setPreferredWidth(
				10);
		tourTable.getColumn(tourTableModel.getColumnName(0)).setPreferredWidth(
				30);

		karteTable.getColumn(karteTableModel.getColumnName(0))
		.setPreferredWidth(200);
		karteTable.getColumn(karteTableModel.getColumnName(1))
		.setPreferredWidth(200);
		karteTable.getColumn(karteTableModel.getColumnName(2))
		.setPreferredWidth(40);
		karteTable.getColumn(karteTableModel.getColumnName(3))
		.setPreferredWidth(40);
		karteTable.getColumn(karteTableModel.getColumnName(5))
		.setPreferredWidth(20);

		regionTable.getColumn(regionTableModel.getColumnName(0))
		.setPreferredWidth(20);
		regionTable.getColumn(regionTableModel.getColumnName(1))
		.setPreferredWidth(200);
		regionTable.getColumn(regionTableModel.getColumnName(2))
		.setPreferredWidth(150);
		regionTable.getColumn(regionTableModel.getColumnName(3))
		.setPreferredWidth(200);
		regionTable.getColumn(regionTableModel.getColumnName(4))
		.setPreferredWidth(40);

		tourTable.addMouseListener(this);
		punktTable.addMouseListener(this);
		karteTable.addMouseListener(this);
		regionTable.addMouseListener(this);

		this.reloadPunkte();
		this.reloadTouren();
		this.reloadKarten();
		this.reloadRegionen();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		jTabbedPane1 = new javax.swing.JTabbedPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		tourTable = new javax.swing.JTable();
		jScrollPane2 = new javax.swing.JScrollPane();
		punktTable = new javax.swing.JTable();
		jScrollPane3 = new javax.swing.JScrollPane();
		karteTable = new javax.swing.JTable();
		jScrollPane4 = new javax.swing.JScrollPane();
		regionTable = new javax.swing.JTable();
		menuBar = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		jMenuItem2 = new javax.swing.JMenuItem();
		openMenuItem = new javax.swing.JMenuItem();
		jMenuItem5 = new javax.swing.JMenuItem();
		exitMenuItem = new javax.swing.JMenuItem();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenuItem1 = new javax.swing.JMenuItem();
		miLoeschen = new javax.swing.JMenuItem();
		jMenuItem3 = new javax.swing.JMenuItem();
		miExportPunkte = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem6 = new javax.swing.JMenuItem();
		miExportKarte = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		miRegionOeffnen = new javax.swing.JMenuItem();
		miRegionExport = new javax.swing.JMenuItem();
		editMenu = new javax.swing.JMenu();
		cutMenuItem = new javax.swing.JMenuItem();
		miExportGipfel = new javax.swing.JMenuItem();
		miExportGebiete = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Digitales Tourenbuch");
		setLocationByPlatform(true);

		jTabbedPane1.setName("jTabbedPane1"); // NOI18N

		jScrollPane1.setName("jScrollPane1"); // NOI18N

		tourTable.setModel(tourTableModel);
		tourTable.setName("tourTable"); // NOI18N
		tourTable.setRowSorter(tourRowSorter);
		tourTable
		.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tourTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(tourTable);
		tourTable
		.getColumnModel()
		.getSelectionModel()
		.setSelectionMode(
				javax.swing.ListSelectionModel.SINGLE_SELECTION);

		jTabbedPane1.addTab("Touren", jScrollPane1);

		jScrollPane2.setName("jScrollPane2"); // NOI18N

		punktTable.setModel(punktTableModel);
		punktTable.setName("punktTable"); // NOI18N
		punktTable.setRowSorter(punktRowSorter);
		punktTable
		.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		punktTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane2.setViewportView(punktTable);
		punktTable
		.getColumnModel()
		.getSelectionModel()
		.setSelectionMode(
				javax.swing.ListSelectionModel.SINGLE_SELECTION);

		jTabbedPane1.addTab("Punkte", jScrollPane2);

		jScrollPane3.setName("jScrollPane3"); // NOI18N

		karteTable.setModel(karteTableModel);
		karteTable.setName("karteTable"); // NOI18N
		karteTable.setRowSorter(karteRowSorter);
		jScrollPane3.setViewportView(karteTable);

		jTabbedPane1.addTab("Karten", jScrollPane3);

		jScrollPane4.setName("jScrollPane4"); // NOI18N

		regionTable.setModel(regionTableModel);
		regionTable.setName("regionTable"); // NOI18N
		regionTable.setRowSorter(regionRowSorter);
		jScrollPane4.setViewportView(regionTable);

		jTabbedPane1.addTab("Regionen", jScrollPane4);

		fileMenu.setText("Touren");

		jMenuItem2.setText("Neu...");
		jMenuItem2.setName("jMenuItem2"); // NOI18N
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tourNeuActionPerformed(evt);
			}
		});
		fileMenu.add(jMenuItem2);

		openMenuItem.setText("Öffnen");
		openMenuItem.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tourOeffnenItemActionPerformed(evt);
			}
		});
		fileMenu.add(openMenuItem);

		jMenuItem5.setText("Exportieren...");
		jMenuItem5.setName("jMenuItem5"); // NOI18N
		jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tourExportActionPerformed(evt);
			}
		});
		fileMenu.add(jMenuItem5);

		exitMenuItem.setText("Beenden");
		exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				beendenActionPerformed(evt);
			}
		});
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		jMenu1.setText("Punkte");
		jMenu1.setName("jMenu1"); // NOI18N

		jMenuItem4.setText("Neu...");
		jMenuItem4.setName("jMenuItem4"); // NOI18N
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				punktNeuActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem4);

		jMenuItem1.setText("Öffnen");
		jMenuItem1.setName("jMenuItem1"); // NOI18N
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				punkteOeffnenActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		miLoeschen.setText("Löschen");
		miLoeschen.setName("miLoeschen"); // NOI18N
		miLoeschen.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				miLoeschenActionPerformed(evt);
			}
		});
		jMenu1.add(miLoeschen);

		jMenuItem3.setText("Importieren...");
		jMenuItem3.setName("jMenuItem3"); // NOI18N
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				importPunkteActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem3);

		miExportPunkte.setText("Export...");
		miExportPunkte.setName("miExportPunkte"); // NOI18N
		miExportPunkte.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				miExportPunkteActionPerformed(evt);
			}
		});
		jMenu1.add(miExportPunkte);

		menuBar.add(jMenu1);

		jMenu2.setText("Karten");
		jMenu2.setName("jMenu2"); // NOI18N

		jMenuItem6.setText("Neu...");
		jMenuItem6.setName("jMenuItem6"); // NOI18N
		jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				karteNeuActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem6);

		miExportKarte.setText("Export...");
		miExportKarte.setName("miExportKarte"); // NOI18N
		miExportKarte.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				miExportKarteActionPerformed(evt);
			}
		});
		jMenu2.add(miExportKarte);

		menuBar.add(jMenu2);

		jMenu3.setText("Regionen");
		jMenu3.setName("jMenu3"); // NOI18N

		miRegionOeffnen.setText("Öffnen");
		miRegionOeffnen.setName("miRegionOeffnen"); // NOI18N
		miRegionOeffnen.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				miRegionOeffnenActionPerformed(evt);
			}
		});
		jMenu3.add(miRegionOeffnen);

		miRegionExport.setText("Export...");
		miRegionExport.setName("miRegionExport"); // NOI18N
		miRegionExport.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				miRegionExportActionPerformed(evt);
			}
		});
		jMenu3.add(miRegionExport);

		menuBar.add(jMenu3);

		editMenu.setText("Datenbank");

		cutMenuItem.setText("Export chronologisch");
		cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exportChronoMenuItemActionPerformed(evt);
			}
		});
		editMenu.add(cutMenuItem);

		miExportGipfel.setText("Export Gipfel nach Höhe");
		miExportGipfel.setName("miExportGipfel"); // NOI18N
		miExportGipfel.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				miExportGipfelActionPerformed(evt);
			}
		});
		editMenu.add(miExportGipfel);

		miExportGebiete.setText("Export Gipfel nach Gebiet");
		miExportGebiete.setName("miExportGebiete"); // NOI18N
		miExportGebiete.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				miExportGebieteActionPerformed(evt);
			}
		});
		editMenu.add(miExportGebiete);

		menuBar.add(editMenu);

		setJMenuBar(menuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jTabbedPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 825,
								Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jTabbedPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 589,
								Short.MAX_VALUE).addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	protected void miExportGebieteActionPerformed(ActionEvent evt) {
		try {
			JFileChooser jfc = new JFileChooser(new File(FOLDER_GIPFEL_GEBIET));
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc.showOpenDialog(this);
			if (jfc.getSelectedFile() == null) {
				return;
			}

			String folder = jfc.getSelectedFile().getAbsolutePath()
					+ File.separator;

			byte[] encoded = Files.readAllBytes(Paths.get(getClass()
					.getResource("/templates/gipfel-nach-regionen.html")
					.toURI()));
			String html = new String(encoded, "UTF-8");
			StringBuffer content = new StringBuffer();

			File f = new File(folder + "gipfel-nach-regionen.html");
			if (f.exists()) {
				f.delete();
			}

			List<Region> listRegionen = regionDao.findUsedRegions();

			for (Region region : listRegionen) {
				List<Tour> touren = tourDao.findTourInRegion(region);

				content.append("<div class=\"articlearea\"><h1>");
				content.append(region.getName());
				content.append("</h1><p>");

				if (touren != null && touren.size() > 0) {
					Collections.sort(touren);
					for (Tour tour : touren) {
						String link = tour.getLink();
						if (link == null || link.equals("")) {
							content.append(tour.toString() + "<br>\r\n");
						} else {
							content.append("<a href=\"" + link + "\">"
									+ tour.toString() + "</a><br>\r\n");
						}
					}
				}

				content.append("</p></div><div class=\"spacing\">&nbsp;</div>");
			}

			String result = html.replace("%CONTENT%", content);
			Files.write(
					Paths.get(FOLDER_DESKTOP + "/gipfel-nach-regionen.html"),
					result.getBytes());
		} catch (IOException | URISyntaxException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void beendenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_beendenActionPerformed
		System.exit(0);
	}// GEN-LAST:event_beendenActionPerformed

	private void tourOeffnenItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tourOeffnenItemActionPerformed
		reloadTouren();
	}// GEN-LAST:event_tourOeffnenItemActionPerformed

	private void tourNeuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tourNeuActionPerformed
		TourDialog td = new TourDialog(this, null);
		td.setVisible(true);
	}// GEN-LAST:event_tourNeuActionPerformed

	private void exportChronoMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exportChronoMenuItemActionPerformed
		try {
			JFileChooser jfc = new JFileChooser(new File(FOLDER_TOUREN_CHRONO));
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc.showOpenDialog(this);
			if (jfc.getSelectedFile() == null) {
				return;
			}

			String folder = jfc.getSelectedFile().getAbsolutePath()
					+ File.separator;

			for (int i = 1996; i < 2040; i++) {
				List<Tour> touren = tourDao.getAllToursForYear(i);

				if (touren != null && touren.size() > 0) {
					File f = new File(folder + i + ".txt");
					if (f.exists()) {
						f.delete();
					}
					RandomAccessFile raf = new RandomAccessFile(f, "rw");

					Collections.sort(touren);
					for (Tour tour : touren) {
						String link = tour.getLink();
						if (link == null || link.equals("")) {
							raf.writeBytes(tour.toString() + "<br>\r\n");
						} else {
							raf.writeBytes("<a href=\"" + link + "\">"
									+ tour.toString() + "</a><br>\r\n");
						}
					}
					raf.close();
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// GEN-LAST:event_exportChronoMenuItemActionPerformed

	private void punkteOeffnenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_punkteOeffnenActionPerformed
		reloadPunkte();
	}// GEN-LAST:event_punkteOeffnenActionPerformed

	private void importPunkteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_importPunkteActionPerformed
		try {
			JFileChooser jfc = new JFileChooser(new File(FOLDER_DESKTOP));
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showOpenDialog(this);
			if (jfc.getSelectedFile() == null) {
				return;
			}

			if (jfc.getSelectedFile().getAbsolutePath().endsWith(".gpx")) {
				importPunktGpx(jfc);
			} else if (jfc.getSelectedFile().getAbsolutePath().endsWith(".osm")) {
				importPunkteOsm(jfc.getSelectedFile());
			} else {
				JOptionPane
				.showMessageDialog(this,
						"Es können nur OSM- und GPX-Dateien importiert werden!");
			}
		} catch (JAXBException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this,
					"Fehler beim Parsen der GPX-Datei!");
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "GPX-Datei nicht gefunden!");
		}
		this.reloadPunkte();
	}// GEN-LAST:event_importPunkteActionPerformed

	private void importPunkteOsm(File f) throws FileNotFoundException {
		OsmParserListener listener = new OsmParserListener();
		FileReader reader = new FileReader(f);
		try {
			StreamingOsmXmlParser.newInstance().read(reader, listener);
			List<Punkt> result = listener.getPunkte();

			// Kontrolldarstellung
			PunktKontrollDialog dialog = new PunktKontrollDialog(this, result);
			dialog.setVisible(true);

			result = dialog.getPunkte();
			if (result == null) {
				// Abgebrochen
				return;
			}

			punktDao = factory.getPunktDAO();
			for (Punkt punkt : result) {
				punktDao.insertPunkt(punkt);
			}

			JOptionPane.showMessageDialog(this, "Import der " + result.size()
					+ " Punkt(e) war erfolgreich!", "Fertig",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Fehler beim Import: " + e.getMessage(), "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void importPunktGpx(JFileChooser jfc) throws JAXBException,
	FileNotFoundException {
		List<Punkt> result = GpxUtils.convertGpxPunkteToObjects(jfc
				.getSelectedFile());
		punktDao = factory.getPunktDAO();
		for (Punkt punkt : result) {
			punktDao.insertPunkt(punkt);
		}

		JOptionPane.showMessageDialog(this, "Import der " + result.size()
				+ " Punkt(e) war erfolgreich!");
	}

	private void punktNeuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_punktNeuActionPerformed
		PunktDialog pd = new PunktDialog(this, null);
		pd.setVisible(true);
	}// GEN-LAST:event_punktNeuActionPerformed

	private void tourExportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tourExportActionPerformed
		int[] selectedRow = tourTable.getSelectedRows();

		if (selectedRow.length <= 0) {
			JOptionPane.showMessageDialog(this,
					"Eine Zeile selektieren, bitte!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JFileChooser jfc = new JFileChooser(new File(FOLDER_TOUREN));
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showOpenDialog(this);
		if (jfc.getSelectedFile() == null) {
			return;
		}

		for (int element : selectedRow) {
			int row = tourRowSorter.convertRowIndexToModel(element);

			if (row < 0) {
				JOptionPane.showMessageDialog(this,
						"Du musst eine Zeile zum Export auswählen.", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Integer tourId = (Integer) tourTableModel.getValueAt(row, 3);

			Tour tour = tourDao.findById(tourId);
			if (tour != null) {
				new HtmlExporter().exportTour(tour, jfc.getSelectedFile()
						.getAbsolutePath());
			} else {
				JOptionPane.showMessageDialog(this, "Tour nicht gefunden.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}// GEN-LAST:event_tourExportActionPerformed

	private void karteNeuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_karteNeuActionPerformed
		KarteDialog kd = new KarteDialog(this, null);
		kd.setVisible(true);
	}// GEN-LAST:event_karteNeuActionPerformed

	private void miLoeschenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miLoeschenActionPerformed
		int row = punktRowSorter.convertRowIndexToModel(punktTable
				.getSelectedRow());
		Integer id = (Integer) punktTableModel.getValueAt(row, 4);
		int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Punkt "
				+ id + " wirklich löschen?");
		if (showConfirmDialog == JOptionPane.OK_OPTION) {
			punktDao = factory.getPunktDAO();
			punktDao.deletePunkt(punktDao.findById(id));
		}
		this.reloadPunkte();
	}// GEN-LAST:event_miLoeschenActionPerformed

	private void miExportGipfelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miExportGipfelActionPerformed
		String select1 = "select distinct punkt.id, name, hoehe, tt.id_tour, tt.datum from  punkt, tourabschnitt ta1, tourentag tt "
				+ "where punkt.hoehe >= ";
		String select2 = " and punkt.hoehe < ";
		String select3 = " and (punkt.id = ta1.von_punkt or punkt.id = ta1.nach_punkt) and typ = 1  and tt.id_tour=ta1.id_tour and tt.tag = ta1.tag "
				+ "order by hoehe desc";

		int[] hoehengrenzen = new int[] { 10000, 4000, 3500, 3000, 2500, 2000,
				1500, 0 };

		JFileChooser jfc = new JFileChooser(new File(FOLDER_GIPFEL_HOEHE));
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showOpenDialog(this);
		if (jfc.getSelectedFile() == null) {
			return;
		}

		String folder = jfc.getSelectedFile().getAbsolutePath()
				+ File.separator;

		for (int i = 0; i < hoehengrenzen.length - 1; i++) {
			try {
				File f = new File(folder + hoehengrenzen[i + 1] + "bis"
						+ hoehengrenzen[i] + ".txt");
				if (f.exists()) {
					f.delete();
				}

				String select = select1 + hoehengrenzen[i + 1] + select2
						+ hoehengrenzen[i] + select3;
				EntityManager em = DaoFactory.getInstance().getEntityManager();
				List<Object[]> results = em.createNativeQuery(select)
						.getResultList();

				RandomAccessFile raf = new RandomAccessFile(f, "rw");

				for (Object[] objs : results) {
					raf.writeBytes("<a href=\"Touren/tour" + objs[3] + "/\">"
							+ objs[1] + ", " + objs[2] + "m; "
							+ sdfLang.format(objs[4]) + "</a><br/>\r\n");
				}

				raf.close();
			} catch (FileNotFoundException ex) {
				Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null,
						ex);
			} catch (IOException ex) {
				Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null,
						ex);
			}

		}
	}// GEN-LAST:event_miExportGipfelActionPerformed

	private void miExportKarteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miExportKarteActionPerformed
		int[] rows = karteTable.getSelectedRows();

		if (rows.length <= 0) {
			JOptionPane.showMessageDialog(this,
					"Eine Zeile selektieren, bitte!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JFileChooser jfc = new JFileChooser(new File(FOLDER_KARTE));
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showOpenDialog(this);
		if (jfc.getSelectedFile() == null) {
			return;
		}

		for (int row2 : rows) {
			int row = karteRowSorter.convertRowIndexToModel(row2);

			if (row < 0) {
				JOptionPane.showMessageDialog(this,
						"Du musst eine Zeile zum Export auswählen.", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Integer karteId = (Integer) karteTableModel.getValueAt(row, 5);

			Karte karte = karteDao.findById(karteId);
			if (karte != null) {
				exportKarte(karte, jfc.getSelectedFile().getAbsolutePath());
			} else {
				JOptionPane.showMessageDialog(this, "Tour nicht gefunden.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}// GEN-LAST:event_miExportKarteActionPerformed

	private void miRegionOeffnenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miRegionOeffnenActionPerformed
		this.reloadRegionen();
	}// GEN-LAST:event_miRegionOeffnenActionPerformed

	private void miRegionExportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miRegionExportActionPerformed
		int[] rows = regionTable.getSelectedRows();

		if (rows.length <= 0) {
			JOptionPane.showMessageDialog(this,
					"Eine Zeile selektieren, bitte!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JFileChooser jfc = new JFileChooser(new File(FOLDER_REGION));
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showOpenDialog(this);
		if (jfc.getSelectedFile() == null) {
			return;
		}

		for (int row2 : rows) {
			int row = regionRowSorter.convertRowIndexToModel(row2);

			if (row < 0) {
				JOptionPane.showMessageDialog(this,
						"Du musst eine Zeile zum Export auswählen.", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Integer regionId = (Integer) regionTableModel.getValueAt(row, 0);

			Region region = regionDao.findById(regionId);
			if (region != null) {
				new HtmlExporter().exportRegion(region, jfc.getSelectedFile()
						.getAbsolutePath());
			} else {
				JOptionPane.showMessageDialog(this, "Region nicht gefunden.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}// GEN-LAST:event_miRegionExportActionPerformed

	private void miExportPunkteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miExportPunkteActionPerformed
		/*
		 * int[] rows = punktTable.getSelectedRows();
		 * 
		 * if (rows.length <= 0) { JOptionPane.showMessageDialog(this,
		 * "Eine Zeile selektieren, bitte!", "Fehler",
		 * JOptionPane.ERROR_MESSAGE); return; }
		 */

		JFileChooser jfc = new JFileChooser(new File(FOLDER_PUNKTE));
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showOpenDialog(this);
		if (jfc.getSelectedFile() == null) {
			return;
		}
		/*
		 * for (int i = 0; i < rows.length; i++) { int row =
		 * punktRowSorter.convertRowIndexToModel(rows[i]);
		 * 
		 * if (row < 0) { JOptionPane.showMessageDialog(this,
		 * "Du musst eine Zeile zum Export auswählen.", "Fehler",
		 * JOptionPane.ERROR_MESSAGE); return; }
		 * 
		 * Integer punktId = (Integer) punktTableModel.getValueAt(row, 0);
		 * 
		 * List<Punkt> punkte = punktDao.selectPunkt(punktId); if (punkte !=
		 * null && punkte.size() == 1) { exportPunkt(punkte.get(0),
		 * jfc.getSelectedFile().getAbsolutePath()); } else {
		 * JOptionPane.showMessageDialog(this, "Punkt nicht gefunden.",
		 * "Fehler", JOptionPane.ERROR_MESSAGE); return; } }
		 */
		exportPunkt(jfc.getSelectedFile().getAbsolutePath());
	}// GEN-LAST:event_miExportPunkteActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Berge().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JMenuItem cutMenuItem;
	private javax.swing.JMenu editMenu;
	private javax.swing.JMenuItem exitMenuItem;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JMenuItem jMenuItem5;
	private javax.swing.JMenuItem jMenuItem6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTable karteTable;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenuItem miExportGebiete;
	private javax.swing.JMenuItem miExportGipfel;
	private javax.swing.JMenuItem miExportKarte;
	private javax.swing.JMenuItem miExportPunkte;
	private javax.swing.JMenuItem miLoeschen;
	private javax.swing.JMenuItem miRegionExport;
	private javax.swing.JMenuItem miRegionOeffnen;
	private javax.swing.JMenuItem openMenuItem;
	private javax.swing.JTable punktTable;
	private javax.swing.JTable regionTable;
	private javax.swing.JTable tourTable;

	// End of variables declaration//GEN-END:variables

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == tourTable) {
			if (e.getClickCount() == 2) {
				int row = tourRowSorter.convertRowIndexToModel(tourTable
						.getSelectedRow());
				Integer tourId = (Integer) tourTableModel.getValueAt(row, 3);

				Tour tour = tourDao.findById(tourId);
				if (tour != null) {
					TourDialog td = new TourDialog(this, tour);
					td.setVisible(true);
				}
			}
		} else if (e.getSource() == punktTable) {
			if (e.getClickCount() == 2) {
				int row = punktRowSorter.convertRowIndexToModel(punktTable
						.getSelectedRow());
				Integer punktId = (Integer) punktTableModel.getValueAt(row, 4);

				punktDao = factory.getPunktDAO();

				Punkt punkt = punktDao.findById(punktId);
				if (punkt != null) {
					PunktDialog pd = new PunktDialog(this, punkt);
					pd.setVisible(true);
				}
			}
		} else if (e.getSource() == karteTable) {
			if (e.getClickCount() == 2) {
				int row = karteRowSorter.convertRowIndexToModel(karteTable
						.getSelectedRow());
				Integer karteId = (Integer) karteTableModel.getValueAt(row, 5);

				Karte karte = karteDao.findById(karteId);
				if (karte != null) {
					KarteDialog kd = new KarteDialog(this, karte);
					kd.setVisible(true);
				}
			}
		} else if (e.getSource() == regionTable) {
			if (e.getClickCount() == 2) {
				int row = regionRowSorter.convertRowIndexToModel(regionTable
						.getSelectedRow());
				Integer regionId = (Integer) regionTableModel
						.getValueAt(row, 0);

				Region region = regionDao.findById(regionId);
				if (region != null) {
					RegionDialog dialog = new RegionDialog(this, region);
					dialog.setVisible(true);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	protected void reloadTouren() {
		List<Tour> listTouren = factory.getTourDAO().selectTour(null);
		Collections.sort(listTouren);
		tourTableModel.setData(listTouren);
	}

	protected void reloadPunkte() {
		punktTableModel.setData(factory.getPunktDAO().selectPunkt());
	}

	protected void reloadKarten() {
		karteTableModel.setData(karteDao.selectKarte());
	}

	protected void reloadRegionen() {
		regionTableModel.setData(regionDao.selectRegion());
	}

	private void exportKarte(Karte karte, String absolutePath) {
		try {
			String fileName = absolutePath + File.separator + karte.getId()
					+ "_" + karte.getTitel() + "_" + karte.getUntertitel()
					+ ".ovl";
			Polygon polygon = karte.getExtent();
			System.out.println(polygon.toText());
			OVL_Flaeche flaeche = new OVL_Flaeche(polygon, 1, 1, 1, 103, 1);
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			raf.writeBytes("[Symbol 1]\r\n");
			flaeche.write(raf);
			raf.writeBytes("[Overlay]" + "\r\n");
			raf.writeBytes("Symbols=1" + "\r\n");
			raf.writeBytes("[MapLage]" + "\r\n");
			raf.writeBytes("MapName=" + karte.getTitel() + ", "
					+ karte.getUntertitel() + "\r\n");
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

	private void exportPunkt(String absolutePath) {
		String fileName = absolutePath + File.separator + "Punkte.kml";

		Hashtable<Punkttyp, List<Punkt>> punkttypPunkt = new Hashtable<Punkttyp, List<Punkt>>();

		try {
			Kml kml = new Kml();
			Document document = new Document();
			document.setName("Alle Punkte");
			kml.setFeature(document);

			List<Punkttyp> punkttypen = punkttypDao.selectPunkttyp();

			for (int i = 0; i < punkttypen.size(); i++) {
				Punkttyp punkttyp = punkttypen.get(i);
				punkttypPunkt.put(punkttyp, new ArrayList<Punkt>());
			}

			List<Punkt> punkte = punktDao.selectPunkt();
			for (int i = 0; i < punkte.size(); i++) {
				Punkt punkt = punkte.get(i);

				punkttypPunkt.get(punkt.getTyp()).add(punkt);
			}

			for (int i = 0; i < punkttypen.size(); i++) {
				Punkttyp punkttyp = punkttypen.get(i);
				List<Punkt> pe = punkttypPunkt.get(punkttyp);

				Document doc = new Document();
				doc.setName(punkttyp.getName());
				document.addFeature(doc);

				for (int j = 0; j < pe.size(); j++) {
					Punkt punkt = pe.get(j);

					Placemark placemark = new Placemark();
					Point point = new Point(punkt.getLage().getX(), punkt
							.getLage().getY());
					placemark.setGeometry(point);
					placemark.setName(punkt.toString());
					doc.addFeature(placemark);
				}
			}

			kml.createKml(fileName);
		} catch (KmlException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Berge.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
