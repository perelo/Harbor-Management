package cas2.model.action;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JTable;

import cas2.controler.CExc;
import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Emplacement;
import cas2.model.Occupation;
import cas2.model.Parking;
import cas2.model.Resident;
import cas2.model.WaitingBoat;
import cas2.view.DialogMessage;
import cas2.view.Window;

public class AssignEmpWaitingBoatAction implements ActionListener {

    private Window window;
    private Date today;

    private int rowIndex;
    private Emplacement emp;
    private Resident res;
    private Occupation occ;
    private WaitingBoat waitingBoat;
    private Object[]    wbAndDelay; // [0] = waiting boat
                                    // [1] = it's nb of days to wait
    
    public AssignEmpWaitingBoatAction(Window window) {
        this.window = window;
        this.today = new Date(Calendar.getInstance().getTimeInMillis());
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            
            getWaitingBoat();
            
            Iterable<Object[]> emps =
                    DAOFactoryJPA.getInstance().getDAOEmplacement().
                    findEmptyByModel(waitingBoat.getModel(),
                                     Parking.NO_DURATION);
            
            if (emps.iterator().hasNext())
                window.showDialogEmps(emps);
            else
                throw new CExc(
                        "Waiting list",
                        "Ouch, " +
                        "there should be at least one empty emplacement, " +
                        "but there isn't...\n" +
                        "Please try again later");

            getAndInsertOccupation();
            updateWindow();
            
            DialogMessage.showInfoMessage(
                    waitingBoat.getNameBoat() +
                    " is now a resident and occupation created :\n" +
                    occ.toStringForUsers());
            
        } catch (CExc e) {
            e.show();
        }

    }

    private void updateWindow() {

        window.getvMooredResident().add(res);
        window.getCbbMooredResident().validate();
        
        updatevWaitingList();
        // barbare mais je n'ai pas comment faire autrement
        Vector<Vector<String>> rowData =
                WaitingBoat.getRowDataWaitingList(window.getvWaitingList());
        window.getPanelWaitingList().remove(0); // remove la liste
        window.getPanelWaitingList().add( // et la rajouter...
                                new JTable(rowData,
                                window.getHeaderWaitingList()),
                                BorderLayout.CENTER);
        window.getPanelWaitingList().validate();
        
    }

    private void updatevWaitingList() {
        
        window.getvWaitingList().remove(wbAndDelay);
        for (int i = 0; i < window.getvWaitingList().size(); ++i) {
            WaitingBoat wb = (WaitingBoat) window.getvWaitingList().get(i)[0];
            
            int rank = wb.getRank();
            if (rank > waitingBoat.getRank())
                wb.setRank(rank-1);
            
            window.getvWaitingList().get(i)[1] =
                    DAOFactoryJPA.getInstance().getDAOWaitingBoat().
                                        computeNbDaysToWait(wb.getModel());
        }
        
    }

    private void getAndInsertOccupation() throws CExc {

        emp = window.getCurrentEmplacement();
        window.setCurrentEmplacement(null);
        if (null == emp) return;
        
        res = new Resident(waitingBoat);
        
        occ = new Occupation(res, emp, today,
                             waitingBoat.getPlannedOccupationDuration());

        deleteWaitingBoat();
        DAOFactoryJPA.getInstance().getDAOResident().create(res);
        DAOFactoryJPA.getInstance().getDAOOccupation().create(occ);
        
    }

    private void deleteWaitingBoat() throws CExc {
        
        DAOFactoryJPA.getInstance().getDAOWaitingBoat().
                                                    updateRank(waitingBoat);
        DAOFactoryJPA.getInstance().getDAOWaitingBoat().delete(waitingBoat);
        
    }

    private void getWaitingBoat() throws CExc {

        rowIndex = window.getTableWaitingList().getSelectedRow();
        wbAndDelay = window.getvWaitingList().get(rowIndex);

        waitingBoat = (WaitingBoat) wbAndDelay[0];
        if ((Integer) wbAndDelay[1] > 0)
            throw new CExc(
                "Waiting list",
                "Delay not done, please wait " + wbAndDelay[1] + " days more");
        
    }

}
