package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Statistic;
import cas2.view.Window;

public class StatNbTripsBoatAction implements ActionListener {

    private Window window;
    
    public StatNbTripsBoatAction(Window window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Vector<String> header = new Vector<String>();
        header.add("Boat");
        header.add("Year");
        header.add("Number of trips");
        
        Vector<Vector<String>> rowData =
                Statistic.getRowData(
                        DAOFactoryJPA.getInstance().getDAOTrip().
                                            computeNbTripsByYearBoat());

        window.setPanelResultStatsTable(rowData, header);
        
    }

}
