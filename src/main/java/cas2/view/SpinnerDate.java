package cas2.view;

import java.util.Calendar;

import java.sql.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class SpinnerDate extends JSpinner {
    private static final long serialVersionUID = 5570786756247044228L;

    public SpinnerDate() {
        setModel(new SpinnerDateModel(new java.util.Date(), null,
                null, Calendar.DAY_OF_YEAR));
        setEditor(new JSpinner.DateEditor(this, "dd MMM yyyy"));
    }
    
    public Date getDate() {
        return new Date(
                ((SpinnerDateModel) this.getModel()).getDate().getTime());
    }

}
