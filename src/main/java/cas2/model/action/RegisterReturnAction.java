package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cas2.controler.CExc;
import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Resident;
import cas2.model.Trip;
import cas2.view.DialogMessage;
import cas2.view.Window;

public class RegisterReturnAction implements ActionListener {

    private Window window;
    
    private Trip trip;
    
    public RegisterReturnAction(Window window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            
            trip = (Trip) window.getCbbTrips().getSelectedItem();
            if (null == trip) return;
            
            trip.setRealReturnDate(window.getSpinRealReturnDate().getDate());
            DAOFactoryJPA.getInstance().getDAOTrip().update(trip);
            updateWindow();
            
            DialogMessage.showInfoMessage(
                    "Trip " + trip.toStringForUsers() + " updated !");
            
        } catch (CExc e) {
            e.show();
        }
    }

    private void updateWindow() {

        window.getvMooredResident().add((Resident) trip.getBoat());
        window.getvTrips().remove(trip);
        window.getCbbMooredResident().validate();
        window.getCbbTrips().validate();
        
        if (window.getvTrips().size() > 0)
            window.getCbbTrips().setSelectedIndex(0);
        
    }

}
