/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expert.kunkel.berge.gui.model;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import expert.kunkel.berge.dao.DAOFactory;
import expert.kunkel.berge.dao.Region;
import expert.kunkel.berge.dao.RegionDAO;

/**
 *
 * @author thorsten
 */
public class RegionComboBoxModel extends DefaultComboBoxModel {

	private static final long serialVersionUID = -1135233223219459725L;
	private DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
    private List<Region> regionen = null;
    private Region selected = null;

    public RegionComboBoxModel() {
        RegionDAO regionDao = factory.getRegionDAO();
        regionen = regionDao.selectRegion();
    }

    @Override
    public int getSize() {
        if (regionen != null) {
            return regionen.size();
        }
        return 0;
    }

    @Override
    public Object getElementAt(int index) {
        if (regionen != null && regionen.size() >= index) {
            return regionen.get(index);
        }
        return null;
    }

    @Override
    public void setSelectedItem(Object anObject) {
        if ((selected != null && !selected.equals( anObject )) ||
	    selected == null && anObject != null) {
	    selected = (Region)anObject;
	    fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }


}
