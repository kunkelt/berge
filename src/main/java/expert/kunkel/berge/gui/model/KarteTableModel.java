package expert.kunkel.berge.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import expert.kunkel.berge.model.Karte;

public class KarteTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6380195042050962318L;
	protected final String[] columnNames = { "Titel", "Untertitel",
			"Blattnummer", "Maßstab", "Verlag", "ID" };
	private List<Karte> karten = null;

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		if (karten == null) {
			return 0;
		}
		return karten.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (karten == null || arg0 > karten.size()) {
			return null;
		}

		Karte karte = karten.get(arg0);

		switch (arg1) {
		case 0:
			return karte.getTitel();
		case 1:
			return karte.getUntertitel();
		case 2:
			return karte.getBlattnummer();
		case 3:
			return karte.getMassstab();
		case 4:
			if (karte.getVerlag() != null) {
				return karte.getVerlag().getName();
			}
			break;
		case 5:
			return karte.getId();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int c) {
		if (c == 5) {
			return Integer.class;
		}
		return String.class;
	}

	public void setData(List<Karte> karten) {
		this.karten = karten;

		fireTableDataChanged();
	}

	protected List<Karte> getData() {
		return karten;
	}
}
