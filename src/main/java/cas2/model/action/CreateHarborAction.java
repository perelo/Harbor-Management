package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cas2.controler.CExc;
import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Harbor;
import cas2.view.DialogMessage;
import cas2.view.Window;

public class CreateHarborAction implements ActionListener {

    private Window window;
    
    private Harbor harbor;
    
    public CreateHarborAction(Window window) {
        this.window = window;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            
            Harbor harbor =
                    new Harbor(window.getTxtCode().getText().toUpperCase(),
                               window.getTxtNameHarbor().getText());
            
            DAOFactoryJPA.getInstance().getDAOHarbor().create(harbor);

            updateWindow();
            DialogMessage.showInfoMessage(
                    "Harbor " + harbor.toStringForUsers() + " inserted !");
            clearForm();

        } catch (CExc e1) {
            e1.show();
        }

    }

    private void clearForm() {

        window.getTxtCode().setText("");
        window.getTxtNameHarbor().setText("");
        
    }

    private void updateWindow() {

        window.getvHarbors().add(harbor);
        window.getCbbHarbor().validate();
        window.getCbbOrigin().validate();
        window.getCbbDestination().validate();
        
    }

}
