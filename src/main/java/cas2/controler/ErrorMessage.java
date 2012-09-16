package cas2.controler;

import java.sql.SQLException;

public class ErrorMessage {

    public static final String FULL_WAITING_LIST =
                "Too many boats in the waiting list.";
    public static final String DEP_AFTER_RETURN =
                "The departure date is after the return date.";
    public static final String NOT_RESIDENT =
                "This boat is not a resident.";
    public static final String UNIQ_TRIP =
                "This boat already has a trip for this departure date.";
    public static final String UNIQ_HARBOR =
                "This harbor code already exists.";
    public static final String UNIQ_MODEL =
                "There is already a model for this couple serie/type.";
    public static final String UNIQ_PONTOON =
                "There is already a pontoon for this couple basin/pontoon.";
    public static final String UNIQ_EMP =
                "There is already an emplacement type for theses measures.";
    public static final String UNIQ_OCC =
                "This boat is already occupying this emplacement.";
    public static final String DEST_IS_ORIGIN =
                "You cannot come from your destination.";
    public static final String NAME_OR_CODE_NULL =
                "Harbor must have a code and a name.";
    public static final String SERIE_OR_TYPE_NULL =
                "Model must have a serie and a type.";
    public static final String NAME_BOAT_NULL =
                "Boat must have a name.";
    public static final String UNIQ_NAME_BOAT =
                "There is already a boat with this name.";
    public static final String DEFAULT = "SQL Error :\n";
    public static final String UNKNOWN_ERROR = "Unknow non-sql error";
    
    public static String getSQLMessage (Exception e) {

        e = toSQLException(e);
        if (e == toSQLException(e))
            return UNKNOWN_ERROR;
        
        switch (((SQLException) e).getErrorCode()) {
        case 20001 :
            return FULL_WAITING_LIST;
        case 20002 :
            return DEP_AFTER_RETURN;
        case 20003 :
            return NOT_RESIDENT;
        case 20004 :
            return UNIQ_TRIP;
        case 20005 :
            return UNIQ_HARBOR;
        case 20006 :
            return UNIQ_MODEL;
        case 20007 :
            return UNIQ_PONTOON;
        case 20008 :
            return UNIQ_EMP;
        case 20009 :
            return DEP_AFTER_RETURN;
        case 20010 :
            return UNIQ_OCC;
        case 20011 :
            return DEST_IS_ORIGIN;
        case 20012 :
            return NAME_OR_CODE_NULL;
        case 20013 :
            return SERIE_OR_TYPE_NULL;
        case 20014 :
            return NAME_BOAT_NULL;
        case 20015 :
            return UNIQ_NAME_BOAT;
        default:
            return DEFAULT + e.getLocalizedMessage();
        }
    }
    
    public static SQLException toSQLException(Exception e) {

        if (null == e.getCause()) return null;

        try {
            
            SQLException sqlExc =
                            (SQLException) e.getCause().getCause();
            
            if (null == sqlExc)
                sqlExc = (SQLException) e.getCause();
            
            if (sqlExc != null && sqlExc instanceof SQLException) return sqlExc;
            else return null;
            
        } catch (Exception e1) {
            return null;
        }
        
    }
    
}
