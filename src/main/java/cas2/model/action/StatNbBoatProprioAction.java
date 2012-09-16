package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Statistic;
import cas2.view.Window;

public class StatNbBoatProprioAction implements ActionListener {

    private Window window;
    
    public StatNbBoatProprioAction(Window window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        Vector<String> header = new Vector<String>();
        header.add("Propriétaire");
        header.add("Nombre de bateaux");
        
        Vector<Vector<String>> rowData =
                Statistic.getRowData(
                        DAOFactoryJPA.getInstance().getDAOBoat().
                                            computeNbBoatsByOwner());

        window.setPanelResultStatsTable(rowData, header);

    }

}
