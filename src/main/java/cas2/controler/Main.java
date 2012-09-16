package cas2.controler;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.view.InitDialog;
import cas2.view.Window;

public class Main {
    
    public static void main( String[] args )
    {
        JDialog dialogInit = new InitDialog();
        dialogInit.setVisible(true);
        
        //DAOFactoryJPA.getInstance().generateTuples();

        Window frame = new Window();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                DAOFactoryJPA.getInstance().closeEm();
                System.exit(0);
            }
        });
        
        dialogInit.dispose();
        frame.setVisible(true);
        
    }

}
