package cas2.model.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cas2.controler.CExc;
import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Model;
import cas2.view.DialogMessage;
import cas2.view.Window;

public class CreateModelAction implements ActionListener {
    
    private Window window;
    
    private Model model;
    
    public CreateModelAction(Window window) {
        this.window = window;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            
            getModel();
            DAOFactoryJPA.getInstance().getDAOModel().create(model);
            updateWindow();
            
            DialogMessage.showInfoMessage(
                    "Model " + model.toStringForUsers() + " inserted !");
            clearForm();
            
        } catch (CExc e1) {
            e1.show();
        }

    }
    
    private void clearForm() {

        window.getTxtSerie().setText("");
        window.getTxtType().setText("");
        window.getTxtConstructor().setText("");
        window.getTxtLengthModel().setText("");
        window.getTxtWidthModel().setText("");
        window.getTxtDraftModel().setText("");
        
    }

    private void updateWindow() {
        
        window.getvModels().add(model);
        window.getCbbModelPassenger().validate();
        window.getCbbModelResident().validate();
        
    }

    private void getModel() throws CExc {
        
        int type, length, width, draft;
        try {
            type = Integer.parseInt(window.getTxtType().getText());
        } catch (IllegalArgumentException e) {
            throw new CExc("Model", "Invalid type");
        }
        
        try {
            length = Integer.parseInt(window.getTxtLengthModel().getText());
            width  = Integer.parseInt(window.getTxtWidthModel ().getText());
            draft  = Integer.parseInt(window.getTxtDraftModel ().getText());
        } catch (IllegalArgumentException e) {
            throw new CExc("Model", "Invalid dimensions");
        }
        
        model = new Model(window.getTxtSerie().getText(), type,
                          window.getTxtConstructor().getText(),
                          length, width, draft);
    }

}
