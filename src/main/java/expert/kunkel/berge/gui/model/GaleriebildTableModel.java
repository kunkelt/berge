package expert.kunkel.berge.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import expert.kunkel.berge.model.Galeriebild;

public class GaleriebildTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 138728201463485453L;
	protected final String[] columnNames = { "Dateiname", "Titel", "Breite",
			"Höhe" };
	private List<Galeriebild> bilder = null;

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		if (bilder == null) {
			return 0;
		}
		return bilder.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (bilder == null || arg0 > bilder.size()) {
			return null;
		}

		Galeriebild bild = bilder.get(arg0);

		switch (arg1) {
		case 0:
			return bild.getDateiname();
		case 1:
			return bild.getTitel();
		case 2:
			return bild.getBreite();
		case 3:
			return bild.getHoehe();
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Galeriebild bild = bilder.get(rowIndex);

		switch (columnIndex) {
		case 0:
			bild.setDateiname((String) aValue);
			break;
		case 1:
			bild.setTitel((String) aValue);
			break;
		case 2:
			bild.setBreite((Integer) aValue);
			break;
		case 3:
			bild.setHoehe((Integer) aValue);
			break;
		}
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int c) {
		if (c == 2 || c == 3) {
			return Integer.class;
		}
		return String.class;
	}

	public void setData(List<Galeriebild> bilder) {
		this.bilder = bilder;

		fireTableDataChanged();
	}

	public List<Galeriebild> getData() {
		return bilder;
	}
}
