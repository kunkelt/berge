package expert.kunkel.berge.gui.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import expert.kunkel.berge.dao.Tour;
import expert.kunkel.berge.dao.Tourentag;

public class TourTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 6628860076060462522L;
    protected final String[] columnNames = {"Datum", "Tage", "Name", "ID", "Geplant"};
    private List<Tour> touren = null;

    public int getColumnCount() {
        return 5;
    }

    public int getRowCount() {
        if (touren == null) {
            return 0;
        }
        return touren.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int arg0, int arg1) {
        if (touren == null || arg0 > touren.size()) {
            return null;
        }

        List<Tourentag> tt = null;


        Tour tour = touren.get(arg0);
        try {
            tt = tour.getTourentage();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        switch (arg1) {
            case 0:
//                return sdf.format(tt.get(0).getDate());
			return tt.get(0).getDate();
           case 1:
                return tt.size();
            case 2:
                return tour.getName();
            case 3:
                return tour.getId();
            case 4:
                return tour.isGeplant();
            default:
                return null;
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public Class getColumnClass(int c) {
        if (c == 1 || c == 3) {
            return Integer.class;
        }
        if (c == 4) {
            return Boolean.class;
        }
        if (c == 0) {
            return Date.class;
        }
        return String.class;
    }

    public void setData(List<Tour> touren) {
        this.touren = touren;

        fireTableDataChanged();
    }

    protected List<Tour> getData() {
        return touren;
    }

	public Tour getValueAt(int row) {
		return touren.get(row);
	}
}
