package cas2.model;

import java.util.Vector;

public class Statistic {
    
    public static Vector<Vector<String>> getRowData(Iterable<Object[]> stats) {
        
        Vector<Vector<String>> rowData = new Vector<Vector<String>>();
        
        for (Object[] stat : stats) {
            
            Vector<String> oneRow = new Vector<String>();
            
            oneRow.add(((UserEditable) stat[0]).toStringForUsers());
            
            for (int i = 1; i < stat.length; ++i) {
                oneRow.add(""+stat[i]);
            }
            
            rowData.add(oneRow);
        }
        
        return rowData;
        
    }

}
