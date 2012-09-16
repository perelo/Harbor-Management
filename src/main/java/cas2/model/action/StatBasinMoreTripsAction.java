package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import cas2.controler.CExc;
import cas2.controler.ErrorMessage;
import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.view.Window;

public class StatBasinMoreTripsAction implements ActionListener {

    private Window window;
    
    Vector<Vector<String>> rowData;
    private Iterable<Object[]> basinsWithTrips; // [0] = basin
                                                // [1] = nb trips
    
    public StatBasinMoreTripsAction(Window window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {

            computeStat();
            
            Vector<String> header = new Vector<String>();
            header.add("Basin");
            header.add("Number of trips");
            getRowData();
            
            window.setPanelResultStatsTable(rowData, header);
            
        } catch (CExc e) {
            e.show();
        }

    }

    private void getRowData() throws CExc {

        computeStat();
        Long maxNbTrips = (Long) basinsWithTrips.iterator().next()[1];
        
        // Il faudrais extract les basins qui ont le plus de trips
        // puis exécuter Statistic.getRowData(),
        // mais ici c'est plus simple de faire tout d'un coup 
        rowData = new Vector<Vector<String>>();
        
        for (Object[] basinWithTrips: basinsWithTrips) {
            Vector<String> oneRow = new Vector<String>();
            if ((Long) basinWithTrips[1] < maxNbTrips) break;

            oneRow.add(""+basinWithTrips[0]);
            oneRow.add(""+basinWithTrips[1]);
            rowData.add(oneRow);
        }
        
    }

    private void computeStat() throws CExc {

        int nbMonths;
        try {
            nbMonths = Integer.parseInt(window.getTxtNbMonths().getText());
        } catch (IllegalArgumentException e) {
            throw new CExc("Statistic", "Invalid number of months");
        }
        
        try {
            basinsWithTrips = 
                    DAOFactoryJPA.getInstance().getDAOPontoon().
                    findNbTripsByBasinMonths(nbMonths);
        } catch (Exception e) { // ORA-01841
            throw new CExc("Statistic", ErrorMessage.getSQLMessage(e));
        }
        
        if (!basinsWithTrips.iterator().hasNext())
            throw new CExc("Statistic",
                           "No trips theses last " + nbMonths + " months");
        
    }

}
