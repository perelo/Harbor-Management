package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import cas2.controler.CExc;
import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Resident;
import cas2.model.Trip;
import cas2.view.DialogMessage;
import cas2.view.Window;

public class CreateTripAction implements ActionListener {
    
    private Window window;
    
    private Trip trip;
    private Resident res;
    
    public CreateTripAction(Window window) {
        this.window = window;
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
        
            getTrip();
            DAOFactoryJPA.getInstance().getDAOTrip().create(trip);
            updateWindow();
            
            DialogMessage.showInfoMessage(
                    "Trip " + trip.toStringForUsers() + " inserted !");
        
        } catch (CExc e) {
            e.show();
        }
        
    }

    private void updateWindow() {
        
        window.getvTrips().add(trip);
        window.getvMooredResident().remove(res);
        
    }

    private void getTrip() {

        Resident res =
                   (Resident) window.getCbbMooredResident().getSelectedItem();
        Date departureDate     = window.getSpinDepartureDate().getDate();
        Date plannedReturnDate = window.getSpinPlannedReturnDate().getDate();

        trip = new Trip (res, departureDate, plannedReturnDate, null);
        
    }

}
