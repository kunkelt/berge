package expert.kunkel.berge.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import expert.kunkel.berge.model.Region;

public class RegionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 2798458946456367836L;
	protected final String[] columnNames = { "ID", "Name", "Höchster Gipfel",
			"Oberregion", "Ausdehnung" };
	private List<Region> regionen = null;

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		if (regionen == null) {
			return 0;
		}
		return regionen.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (regionen == null || arg0 > regionen.size()) {
			return null;
		}

		Region region = regionen.get(arg0);

		switch (arg1) {
		case 0:
			return region.getId();
		case 1:
			return region.getName();
		case 2:
			if (region.getPunkt() != null) {
				return region.getPunkt().getName();
			} else {
				return null;
			}
		case 3:
			if (region.getOberregion() != null) {
				return region.getOberregion().getName();
			} else {
				return null;
			}
		case 4:
			return region.getExtent() != null;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int c) {
		if (c == 0) {
			return Integer.class;
		}
		if (c == 4) {
			return Boolean.class;
		}
		return String.class;
	}

	public void setData(List<Region> regionen) {
		this.regionen = regionen;

		fireTableDataChanged();
	}

	protected List<Region> getData() {
		return regionen;
	}
}
