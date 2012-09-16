package cas2.model.action;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cas2.view.Window;

public class BoatTabChange implements ChangeListener {

    private Window window;
    
    public BoatTabChange (Window window) {
        this.window = window;
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {

        JPanel panel = (JPanel) ((JTabbedPane) e.getSource()).getSelectedComponent();
        
        if (panel == window.getPanelWaitingList()) return;
        
        panel.add(window.getPanelNewHarborModel(), BorderLayout.EAST);
        panel.getParent().validate();
        
    }

}
