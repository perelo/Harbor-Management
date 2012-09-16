package cas2.controler;

import cas2.view.DialogMessage;

public class CExc extends Exception {
    private static final long serialVersionUID = 5935487267428528001L;
    
    public static final int CREATE = 1;
    public static final int READ   = 2;
    public static final int UPDATE = 3;
    public static final int DELETE = 4;
    
    public static final int OWNER = 1;
    public static final int BOAT  = 2;
    public static final int EMPLACEMENT = 3;
    public static final int MODEL = 4;
    public static final int PARKING = 5;
    
    private String title;
    private String  message;
    
    public CExc(String title, String message) {
        super();
        this.title = title;
        this.message = message;
    }
    
    public void show() {
        DialogMessage.showErrorMessage(title, message);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
