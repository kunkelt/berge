/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * KarteDialog.java
 *
 * Created on 27.01.2010, 18:28:45
 */
package expert.kunkel.berge.gui;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;

import com.vividsolutions.jts.geom.Polygon;

import expert.kunkel.berge.dao.jpa.DaoFactory;
import expert.kunkel.berge.dao.jpa.KarteDao;
import expert.kunkel.berge.dao.jpa.VerlagDao;
import expert.kunkel.berge.model.Karte;
import expert.kunkel.berge.model.Kartentyp;
import expert.kunkel.berge.model.Verlag;
import expert.kunkel.berge.util.TtqvUtilities;
import expert.kunkel.ttqv.jaxb.Ttqv;

/**
 *
 * @author thorsten
 */
public class KarteDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = 4597222007736385092L;
	private DefaultComboBoxModel<Verlag> verlagCbModel = null;
	private static DaoFactory factory = DaoFactory.getInstance();
	private static KarteDao karteDao = factory.getKarteDAO();
	private static VerlagDao verlagDao = factory.getVerlagDAO();
	private Karte karte = null;
	private boolean isNewKarte = true;

	/** Creates new form KarteDialog */
	public KarteDialog(java.awt.Frame parent, Karte karte) {
		super(parent, true);

		verlagCbModel = new DefaultComboBoxModel<Verlag>(verlagDao
				.selectVerlag().toArray(new Verlag[0]));

		initComponents();

		for (Kartentyp kt : DaoFactory.getInstance().getKartentypDao()
				.selectKartentyp()) {
			cbKartentyp.addItem(kt);
		}

		if (karte != null) {
			isNewKarte = false;
			this.setData(karte);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		tfTitel = new javax.swing.JTextField();
		tfUntertitel = new javax.swing.JTextField();
		tfBlattnummer = new javax.swing.JTextField();
		tfMassstab = new javax.swing.JTextField();
		cbVerlag = new javax.swing.JComboBox();
		tfAusgabejahr = new javax.swing.JTextField();
		tfISBN = new javax.swing.JTextField();
		cbKartentyp = new javax.swing.JComboBox();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jLabel9 = new javax.swing.JLabel();
		tfAusdehnung = new javax.swing.JTextField();
		bAusdehnung = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Karte bearbeiten");
		setLocationByPlatform(true);

		jLabel1.setText("Titel");
		jLabel1.setName("jLabel1"); // NOI18N

		jLabel2.setText("Untertitel");
		jLabel2.setName("jLabel2"); // NOI18N

		jLabel3.setText("Blattnummer");
		jLabel3.setName("jLabel3"); // NOI18N

		jLabel4.setText("Maßstab");
		jLabel4.setName("jLabel4"); // NOI18N

		jLabel5.setText("Verlag");
		jLabel5.setName("jLabel5"); // NOI18N

		jLabel6.setText("Ausgabejahr");
		jLabel6.setName("jLabel6"); // NOI18N

		jLabel7.setText("ISBN");
		jLabel7.setName("jLabel7"); // NOI18N

		jLabel8.setText("Kartentyp");
		jLabel8.setName("jLabel8"); // NOI18N

		tfTitel.setName("tfTitel"); // NOI18N

		tfUntertitel.setName("tfUntertitel"); // NOI18N

		tfBlattnummer.setName("tfBlattnummer"); // NOI18N

		tfMassstab.setName("tfMassstab"); // NOI18N

		cbVerlag.setModel(verlagCbModel);
		cbVerlag.setName("cbVerlag"); // NOI18N

		tfAusgabejahr.setName("tfAusgabejahr"); // NOI18N

		tfISBN.setName("tfISBN"); // NOI18N

		cbKartentyp.setName("cbKartentyp"); // NOI18N

		jButton1.setText("OK");
		jButton1.setName("jButton1"); // NOI18N
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bOKActionPerformed(evt);
			}
		});

		jButton2.setText("Abbrechen");
		jButton2.setName("jButton2"); // NOI18N
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAbbrechenActionPerformed(evt);
			}
		});

		jLabel9.setText("Ausdehnung");
		jLabel9.setName("jLabel9"); // NOI18N

		tfAusdehnung.setEditable(false);
		tfAusdehnung.setName("tfAusdehnung"); // NOI18N

		bAusdehnung.setText("Öffnen...");
		bAusdehnung.setName("bAusdehnung"); // NOI18N
		bAusdehnung.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAusdehnungActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel8)
										.addGroup(
												layout.createSequentialGroup()
												.addGroup(
														layout.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(
																		jLabel6)
																		.addComponent(
																				jLabel7)
																				.addComponent(
																						jLabel3)
																						.addComponent(
																								jLabel4)
																								.addComponent(
																										jLabel5)
																										.addComponent(
																												jLabel1)
																												.addComponent(
																														jLabel2))
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addGroup(
																																		layout.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.TRAILING)
																																				.addComponent(
																																						cbVerlag,
																																						0,
																																						344,
																																						Short.MAX_VALUE)
																																						.addGroup(
																																								javax.swing.GroupLayout.Alignment.LEADING,
																																								layout.createParallelGroup(
																																										javax.swing.GroupLayout.Alignment.TRAILING,
																																										false)
																																										.addComponent(
																																												tfMassstab,
																																												javax.swing.GroupLayout.Alignment.LEADING)
																																												.addComponent(
																																														tfBlattnummer,
																																														javax.swing.GroupLayout.Alignment.LEADING,
																																														javax.swing.GroupLayout.DEFAULT_SIZE,
																																														104,
																																														Short.MAX_VALUE)
																																														.addComponent(
																																																tfISBN,
																																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																																117,
																																																Short.MAX_VALUE)
																																																.addComponent(
																																																		tfAusgabejahr,
																																																		javax.swing.GroupLayout.Alignment.LEADING,
																																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																																		111,
																																																		Short.MAX_VALUE)
																																																		.addComponent(
																																																				cbKartentyp,
																																																				0,
																																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																																				Short.MAX_VALUE))
																																																				.addComponent(
																																																						tfTitel,
																																																						javax.swing.GroupLayout.Alignment.LEADING,
																																																						javax.swing.GroupLayout.DEFAULT_SIZE,
																																																						344,
																																																						Short.MAX_VALUE)
																																																						.addComponent(
																																																								tfUntertitel,
																																																								javax.swing.GroupLayout.Alignment.LEADING,
																																																								javax.swing.GroupLayout.DEFAULT_SIZE,
																																																								344,
																																																								Short.MAX_VALUE)))
																																																								.addGroup(
																																																										layout.createSequentialGroup()
																																																										.addComponent(
																																																												jButton1)
																																																												.addPreferredGap(
																																																														javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																																																														277,
																																																														Short.MAX_VALUE)
																																																														.addComponent(
																																																																jButton2))
																																																																.addGroup(
																																																																		layout.createSequentialGroup()
																																																																		.addComponent(
																																																																				jLabel9)
																																																																				.addPreferredGap(
																																																																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																						.addComponent(
																																																																								tfAusdehnung,
																																																																								javax.swing.GroupLayout.DEFAULT_SIZE,
																																																																								260,
																																																																								Short.MAX_VALUE)
																																																																								.addPreferredGap(
																																																																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																										.addComponent(
																																																																												bAusdehnung)))
																																																																												.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel1)
										.addComponent(
												tfTitel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												20,
												javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(
														javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(
																layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jLabel2)
																		.addComponent(
																				tfUntertitel,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addGroup(
																								layout.createParallelGroup(
																										javax.swing.GroupLayout.Alignment.BASELINE)
																										.addComponent(jLabel3)
																										.addComponent(
																												tfBlattnummer,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																												.addPreferredGap(
																														javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addGroup(
																																layout.createParallelGroup(
																																		javax.swing.GroupLayout.Alignment.BASELINE)
																																		.addComponent(jLabel4)
																																		.addComponent(
																																				tfMassstab,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				20,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																																				.addPreferredGap(
																																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																						.addGroup(
																																								layout.createParallelGroup(
																																										javax.swing.GroupLayout.Alignment.BASELINE)
																																										.addComponent(jLabel5)
																																										.addComponent(
																																												cbVerlag,
																																												javax.swing.GroupLayout.PREFERRED_SIZE,
																																												javax.swing.GroupLayout.DEFAULT_SIZE,
																																												javax.swing.GroupLayout.PREFERRED_SIZE))
																																												.addPreferredGap(
																																														javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																														.addGroup(
																																																layout.createParallelGroup(
																																																		javax.swing.GroupLayout.Alignment.BASELINE)
																																																		.addComponent(jLabel6)
																																																		.addComponent(
																																																				tfAusgabejahr,
																																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																																				20,
																																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																																																				.addPreferredGap(
																																																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																						.addGroup(
																																																								layout.createParallelGroup(
																																																										javax.swing.GroupLayout.Alignment.BASELINE)
																																																										.addComponent(jLabel7)
																																																										.addComponent(
																																																												tfISBN,
																																																												javax.swing.GroupLayout.PREFERRED_SIZE,
																																																												javax.swing.GroupLayout.DEFAULT_SIZE,
																																																												javax.swing.GroupLayout.PREFERRED_SIZE))
																																																												.addPreferredGap(
																																																														javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																														.addGroup(
																																																																layout.createParallelGroup(
																																																																		javax.swing.GroupLayout.Alignment.BASELINE)
																																																																		.addComponent(jLabel8)
																																																																		.addComponent(
																																																																				cbKartentyp,
																																																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																																																																				.addPreferredGap(
																																																																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																																																						.addGroup(
																																																																								layout.createParallelGroup(
																																																																										javax.swing.GroupLayout.Alignment.BASELINE)
																																																																										.addComponent(jLabel9)
																																																																										.addComponent(
																																																																												tfAusdehnung,
																																																																												javax.swing.GroupLayout.PREFERRED_SIZE,
																																																																												javax.swing.GroupLayout.DEFAULT_SIZE,
																																																																												javax.swing.GroupLayout.PREFERRED_SIZE)
																																																																												.addComponent(bAusdehnung))
																																																																												.addPreferredGap(
																																																																														javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																																																																														31, Short.MAX_VALUE)
																																																																														.addGroup(
																																																																																layout.createParallelGroup(
																																																																																		javax.swing.GroupLayout.Alignment.BASELINE)
																																																																																		.addComponent(jButton1)
																																																																																		.addComponent(jButton2))
																																																																																		.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void bOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bOKActionPerformed
		// wenn es eine neue Karte ist
		if (isNewKarte) {
			karte = new Karte();
		}
		karte.setAusgabejahr(Integer.parseInt(tfAusgabejahr.getText()));
		karte.setBlattnummer(tfBlattnummer.getText());
		karte.setIsbn(tfISBN.getText());
		karte.setKartentyp((Kartentyp) cbKartentyp.getSelectedItem());
		karte.setMassstab(tfMassstab.getText());
		karte.setTitel(tfTitel.getText());
		karte.setUntertitel(tfUntertitel.getText());
		karte.setVerlag((Verlag) cbVerlag.getSelectedItem());

		// wenn es eine neue Karte ist
		if (isNewKarte) {
			karteDao.insertKarte(karte);
		} // wenn es eine bestehende Karte ist
		else {
			karteDao.updateKarte(karte);
		}

		((Berge) this.getParent()).reloadKarten();

		this.setVisible(false);
	}// GEN-LAST:event_bOKActionPerformed

	private void bAbbrechenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAbbrechenActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_bAbbrechenActionPerformed

	private void bAusdehnungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAusdehnungActionPerformed
		try {
			JFileChooser jfc = new JFileChooser(new File(Berge.FOLDER_TMP));
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showOpenDialog(this);
			if (jfc.getSelectedFile() == null) {
				return;
			}
			File file = jfc.getSelectedFile();
			tfAusdehnung.setText(file.getAbsolutePath());

			Ttqv ttqv = TtqvUtilities.readTTQV_XML(tfAusdehnung.getText());

			Polygon polygon = TtqvUtilities.convertTtqvXML_ToPolygon(ttqv);

			karte.setExtent(polygon);
		} catch (Exception ex) {
			Logger.getLogger(KarteDialog.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}// GEN-LAST:event_bAusdehnungActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Karte karte = karteDao.findById(1);
				KarteDialog dialog = new KarteDialog(new javax.swing.JFrame(),
						karte);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {

					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bAusdehnung;
	private javax.swing.JComboBox cbKartentyp;
	private javax.swing.JComboBox cbVerlag;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JTextField tfAusdehnung;
	private javax.swing.JTextField tfAusgabejahr;
	private javax.swing.JTextField tfBlattnummer;
	private javax.swing.JTextField tfISBN;
	private javax.swing.JTextField tfMassstab;
	private javax.swing.JTextField tfTitel;
	private javax.swing.JTextField tfUntertitel;

	// End of variables declaration//GEN-END:variables

	private void setData(Karte karte) {
		this.karte = karte;

		if (karte != null) {
			tfTitel.setText(karte.getTitel());
			tfUntertitel.setText(karte.getUntertitel());
			tfAusgabejahr.setText(karte.getAusgabejahrAsString());
			tfBlattnummer.setText(karte.getBlattnummer());
			tfISBN.setText(karte.getIsbn());
			tfMassstab.setText(karte.getMassstab());
			cbVerlag.setSelectedItem(karte.getVerlag());
			cbKartentyp.setSelectedItem(karte.getKartentyp());
		}
	}
}
