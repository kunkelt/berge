package expert.kunkel.berge.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import expert.kunkel.berge.model.Punkt;

public class PunktTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2712183554104561755L;
	protected final String[] columnNames = { "Name", "Höhe", "Nord", "Ost",
			"ID", "Typ" };
	private List<Punkt> punkte = null;

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		if (punkte == null) {
			return 0;
		}
		return punkte.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (punkte == null || arg0 > punkte.size()) {
			return null;
		}

		Punkt punkt = punkte.get(arg0);

		switch (arg1) {
		case 0:
			return punkt.getName();
		case 1:
			return punkt.getHoehe();
		case 2:
			return punkt.getLage().getCoordinate().y;
		case 3:
			return punkt.getLage().getCoordinate().x;
		case 4:
			return punkt.getId();
		case 5:
			return punkt.getTyp().getName();
		default:
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int c) {
		if (c == 1 || c == 4) {
			return Integer.class;
		}
		if (c == 2 || c == 3) {
			return Double.class;
		}
		return String.class;
	}

	public void setData(List<Punkt> punkte) {
		this.punkte = punkte;

		fireTableDataChanged();
	}

	protected List<Punkt> getData() {
		return punkte;
	}
}
