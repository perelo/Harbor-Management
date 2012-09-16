package cas2.view;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cas2.model.UserEditable;

public class CustomTextListRenderer implements ListCellRenderer {

    private Window window;
    
    public CustomTextListRenderer(Window window) {
        this.window = window;
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        
        
        DefaultListCellRenderer deflt = new DefaultListCellRenderer();
        if (isSelected) {
            deflt.setBackground(list.getSelectionBackground());
        }
        deflt.setOpaque(true);

        try {
            if (value == window.getFakeOwner())
                deflt.setText("New owner >>");
            else // la seule instruction vraiment utile...
                deflt.setText(((UserEditable) value).toStringForUsers());
        }
        // Catché si le vecteur de la combobox est vide
        catch (NullPointerException e) {
        }

        return deflt;
    }

}
