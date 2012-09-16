package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import cas2.model.Emplacement;
import cas2.view.Window;

public class SetEmplacementAction implements ActionListener {
    
    Window window;
    List<Emplacement> listEmps;
    
    public SetEmplacementAction(Window window,
                                List<Emplacement> listEmps) {
        this.window = window;
        this.listEmps = listEmps;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // L'utilisateur ne peux saisir qu'un seul
        if (1 < window.getTableEmps().getSelectedRowCount()) {
            window.getLblError().
                        setText("You can select only one emplacement");
            return;
        }

        window.setCurrentEmplacement(
                    listEmps.get(window.getTableEmps().getSelectedRow()));
        
        window.getDialogEmps().dispose();
        
        return;
        
    }

}
