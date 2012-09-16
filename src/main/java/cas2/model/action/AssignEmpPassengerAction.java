package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

import cas2.controler.CExc;
import cas2.controler.ErrorMessage;
import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Emplacement;
import cas2.model.Harbor;
import cas2.model.Model;
import cas2.model.Parking;
import cas2.model.Passenger;
import cas2.view.DialogMessage;
import cas2.view.Window;

public class AssignEmpPassengerAction implements ActionListener {

    private Window window;
    private Date today;
    
    private Emplacement emp;
    private Parking parking;
    private Model model;
    private Passenger passenger;
    private int parkingDuration;
    
    public AssignEmpPassengerAction(Window window) {
        this.window = window;
        this.today = new Date(Calendar.getInstance().getTimeInMillis());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            
            getParkingFields();

            Iterable<Object[]> emps =
                    DAOFactoryJPA.getInstance().getDAOEmplacement().
                                     findEmptyByModel(model, parkingDuration);

            if (emps.iterator().hasNext())
                window.showDialogEmps(emps);
            else {
                DialogMessage.showInfoMessage(
                                "No empty emplacements, come back later");
                return;
            }
            
            emp = window.getCurrentEmplacement();
            window.setCurrentEmplacement(null);
            if (null == emp) return;
            
            insertParking();
            
            DialogMessage.showInfoMessage(
                    "Parking " + parking.toStringForUsers() + " inserted !");
            clearForm();
            
        } catch (CExc e1) {
            e1.show();
        }
        
    }

    private void clearForm() {

        window.getCbbOrigin().setSelectedIndex(0);
        window.getCbbDestination().setSelectedIndex(0);
        window.getCbbModelPassenger().setSelectedIndex(0);
        window.getTxtParkingDuration().setText("");
        
    }

    private void insertParking() throws CExc {
        
        parking = new Parking(passenger, emp, today, parkingDuration);
        
        DAOFactoryJPA.getInstance().getDAOPassenger().create(passenger);
        DAOFactoryJPA.getInstance().getDAOParking().create(parking);
        
    }

    private void getParkingFields() throws CExc {
        
        model = (Model) window.getCbbModelPassenger().getSelectedItem();
        
        Harbor origin      =
                (Harbor) window.getCbbOrigin().getSelectedItem();
        Harbor destination =
                (Harbor) window.getCbbDestination().getSelectedItem();
        
        if (origin.equals(destination))
            throw new CExc("Passenger", ErrorMessage.DEST_IS_ORIGIN);
        
        passenger = new Passenger(origin, destination);
        
        try {
            parkingDuration = Integer.parseInt(
                    window.getTxtParkingDuration().getText());
        } catch (IllegalArgumentException e1) {
            throw new CExc("Parking", "Invalid parking duration");
        }
        
    }

}
