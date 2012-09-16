package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.table.DefaultTableModel;

import cas2.controler.CExc;
import cas2.controler.ErrorMessage;
import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Address;
import cas2.model.Boat;
import cas2.model.Emplacement;
import cas2.model.Harbor;
import cas2.model.Model;
import cas2.model.Occupation;
import cas2.model.Owner;
import cas2.model.Parking;
import cas2.model.Resident;
import cas2.model.WaitingBoat;
import cas2.view.DialogMessage;
import cas2.view.Window;

public class AssignEmpResidentAction implements ActionListener {
    
    private Window window;
    private Date today;
    
    private Resident res;
    private Occupation occ;
    private Emplacement emp;
    private WaitingBoat wb;
    private Model model;
    private Owner owner;
    private Boat boat;
    private boolean newOwner = false;
    private int occupationDuration;
    private int rank;
    private int delay;
    
    public AssignEmpResidentAction(Window window) {
        this.window = window;
        this.today = new Date(Calendar.getInstance().getTimeInMillis());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            
            getOccupationFields();
        
            // Liste des emplacements disponibles
            Iterable<Object[]> emps =
                    DAOFactoryJPA.getInstance().getDAOEmplacement().
                        findEmptyByModel(model, Parking.NO_DURATION);
    
            if (emps.iterator().hasNext()) {
                window.showDialogEmps(emps);
                
                emp = window.getCurrentEmplacement();
                window.setCurrentEmplacement(null);
                if (null == emp) return;
                
                insertOccupation();
                updateWindow();
                DialogMessage.showInfoMessage(
                    "Occupation " + occ.toStringForUsers() + " inserted !");
            } else if (window.askForWaitingList()) {
                insertWaitingBoat();
                DialogMessage.showInfoMessage(
                        wb.getNameBoat() +
                        " have been added to the waiting list\n" +
                        "An emplacement will be available in " +
                        delay + " days.");
            } else return;
            
            clearForm();
            newOwner = false;
            
        } catch (CExc e1) {
            e1.show();
        }
        
    }

    private void updateWindow() {
        
        if (newOwner) {
            window.getvOwners().add(owner);
            window.getCbbOwner().validate();
        }
        
        window.getvMooredResident().add(res);
        window.getCbbMooredResident().validate();

    }
    
    private void clearForm() {
        
        window.getTxtNameBoat().setText("");
        window.getCbbOwner().setSelectedIndex(0);
        window.getCbbHarbor().setSelectedIndex(0);
        window.getCbbModelResident().setSelectedIndex(0);
        window.getTxtNumSerie().setText("");
        window.getTxtConstructor().setText("");
        window.getCbbOccupationDuration().setSelectedIndex(0);
        
        if (!newOwner) return;
        
        window.getTxtNameOwner().setText("");
        window.getTxtStreetNum().setText("");
        window.getTxtNameStreet().setText("");
        window.getTxtZipCode().setText("");
        window.getTxtCity().setText("");
        window.getTxtPhone().setText("");
        
    }

    private void insertWaitingBoat() throws CExc {
        
        rank = DAOFactoryJPA.getInstance().getDAOWaitingBoat().getNextRank();
        
        wb = new WaitingBoat(boat, today, rank,
                             Occupation.getOccupationDurations().get(
                                        window.getCbbOccupationDuration().
                                                        getSelectedItem()));

        if (newOwner)
            DAOFactoryJPA.getInstance().getDAOOwner().create(owner);
        
        DAOFactoryJPA.getInstance().getDAOWaitingBoat().create(wb);
        
        addInList(wb);

    }

    private void addInList(WaitingBoat wb) {
        
        delay = DAOFactoryJPA.getInstance().getDAOWaitingBoat().
                                        computeNbDaysToWait(wb.getModel());
        
        window.getvWaitingList().add(new Object[] { wb, delay });
        
        DefaultTableModel tableModel =
                    (DefaultTableModel) window.getTableWaitingList().getModel();
        tableModel.addRow(new Object[] { wb.toStringForUsers(),
                                         wb.getPlannedOccupationDuration(),
                                         rank, delay });
        
    }

    private void insertOccupation() throws CExc {
        
        res = new Resident(boat);
        
        occ = new Occupation(res, emp, today, occupationDuration);
        
        if (newOwner)
            DAOFactoryJPA.getInstance().getDAOOwner().create(owner);
        DAOFactoryJPA.getInstance().getDAOResident().create(res);
        DAOFactoryJPA.getInstance().getDAOOccupation().create(occ);
        
    }

    private void getOccupationFields() throws CExc {
        
        model = (Model) window.getCbbModelResident().getSelectedItem();
        owner = getOwner();
        boat = getBoat();
            
        occupationDuration = Occupation.getOccupationDurations().get(
                                        window.getCbbOccupationDuration().
                                                            getSelectedItem());
        
    }

    private Boat getBoat() throws CExc {
        
        // Boat
        String nameBoat = window.getTxtNameBoat().getText();
        if (nameBoat.equals("")) {
            throw new CExc("Boat", ErrorMessage.NAME_BOAT_NULL);
        }
        
        Integer numSerie;
        try {
            numSerie = Integer.parseInt(window.getTxtNumSerie().getText());
        } catch (IllegalArgumentException e1) {
            throw new CExc("Boat", "Invalid serial number");
        }
        
        // On peux utiliser owner même s'il est pas encore persistant
        // car c'est une référence
        Boat boat = new Boat(
                        window.getTxtNameBoat().getText(), numSerie,
                        window.getTxtInsurance().getText(), owner,
                        (Harbor) window.getCbbHarbor().getSelectedItem(),
                        (Model)  window.getCbbModelResident().
                                                       getSelectedItem());
        
        return boat;
    }

    private Owner getOwner() throws CExc {
        
        Owner owner = (Owner) window.getCbbOwner().getSelectedItem();
        
        if (owner != window.getFakeOwner()) return owner;
        
        newOwner = true;

        Integer streetNum;
        try {
            streetNum = Integer.parseInt(window.getTxtStreetNum().getText());
        } catch (IllegalArgumentException e1) {
            throw new CExc("Owner", "Street number invalid");
        }
        
        Address address =
                new Address(window.getTxtNameStreet().getText(),
                            streetNum,
                            Integer.parseInt(window.getTxtZipCode().getText()),
                            window.getTxtCity().getText());

        owner = new Owner (window.getTxtNameOwner().getText(),
                           window.getTxtPhone().getText(),
                           address);
        
        return owner;
    }

}
