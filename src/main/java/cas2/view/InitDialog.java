package cas2.view;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class InitDialog extends JDialog {

    private static final long serialVersionUID = 4018711274751684728L;

    public InitDialog() {
        
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(100, 100);
        setSize(new Dimension(360, 50));
        setResizable(false);
        
        add(new JLabel("Retrieving datas from database, please wait..."));
        
    }
    
}
