package cas2.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import cas2.controler.dao.jpa.DAOFactoryJPA;
import cas2.model.Emplacement;
import cas2.model.EmplacementType;
import cas2.model.Harbor;
import cas2.model.Model;
import cas2.model.Occupation;
import cas2.model.Owner;
import cas2.model.Pontoon;
import cas2.model.Resident;
import cas2.model.Trip;
import cas2.model.WaitingBoat;
import cas2.model.action.AssignEmpPassengerAction;
import cas2.model.action.AssignEmpResidentAction;
import cas2.model.action.AssignEmpWaitingBoatAction;
import cas2.model.action.BoatTabChange;
import cas2.model.action.CreateHarborAction;
import cas2.model.action.CreateModelAction;
import cas2.model.action.CreateTripAction;
import cas2.model.action.RegisterReturnAction;
import cas2.model.action.SetEmplacementAction;
import cas2.model.action.StatBasinMoreTripsAction;
import cas2.model.action.StatNbBoatModelAction;
import cas2.model.action.StatNbBoatProprioAction;
import cas2.model.action.StatNbTripsBoatAction;

public class Window extends JFrame {
    private static final long serialVersionUID = 5854337211559873701L;

    private JPanel contentPane;
    private JTextField txtNameBoat;
    private JTextField txtNumSerie;
    private JTextField txtInsurance;
    private JTextField txtParkingDuration;
    private JTextField txtNameOwner;
    private JTextField txtStreetNum;
    private JTextField txtNameStreet;
    private JTextField txtZipCode;
    private JTextField txtCity;
    private JTextField txtPhone;
    private JTextField txtSerie;
    private JTextField txtLengthModel;
    private JTextField txtWidthModel;
    private JTextField txtDraftModel;
    private JTextField txtType;
    private JTextField txtConstructor;
    private JTextField txtCode;
    private JTextField txtNameHarbor;
    private JTextField txtLengthEmp;
    private JTextField txtDepthEmp;
    private JTextField txtWidthEmp;
    private JTextField txtPontoon;
    private JTextField txtNumInPontoon;
    private JTextField txtNbMonths;

    private Vector<Resident> vMooredResident;
    private Vector<Harbor> vHarbors;
    private Vector<Trip> vTrips;
    private Vector<Owner> vOwners;
    private Vector<Model> vModels;
    private Vector<Pontoon> vPontoon;
    private Vector<Emplacement> vEmplacements;
    private Vector<EmplacementType> vEmpTypes;
    private Vector<Object[]> vWaitingList;
    private Vector<String> headerWaitingList;

    private JComboBox cbbMooredResident;
    private JComboBox cbbOwner;
    private JComboBox cbbHarbor;
    private JComboBox cbbModelResident;
    private JComboBox cbbModelPassenger;
    private JComboBox cbbTrips;
    private JComboBox cbbOccupationDuration;
    private JComboBox cbbOrigin;
    private JComboBox cbbDestination;
    private JComboBox cbbIsResident;
    private JComboBox cbbEmpType;
    private JComboBox cbbPontoon;
    private JComboBox cbbBasin;
    private JComboBox cbbBoatStat;

    private JSpinner spinDepartureDate;
    private JSpinner spinPlannedReturnDate;
    private JSpinner spinRealReturnDate;

    private JLabel lblError;

    private final String lblShow = "Show";

    private JTable tableEmps;
    private JTable tableStatsResult;
    private JTable tableWaitingList;

    private JDialog dialogEmps;

    private JCheckBox checkIsResident;

    private Owner fakeOwner;
    private Emplacement currentEmplacement;
    private Integer currentOccupationDuration;
    private Integer currentParkingDuration;

    private JPanel panelResultStats;
    private JPanel panelStatistics;
    private JPanel panelNewHarborModel;
    private JPanel panelWaitingList;
    
    private MaskFormatter phoneMask;
    private MaskFormatter zipCodeMask;

    /**
     * Create the frame.
     */
    public Window() {
        setTitle("Harbor Management");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 1, 0, 0));

        JTabbedPane tabGeneral = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabGeneral);

        // Initialisation des listes
        DAOFactoryJPA daoF = DAOFactoryJPA.getInstance();
        vHarbors = (Vector<Harbor>) daoF.getDAOHarbor().findAll();
        vMooredResident = (Vector<Resident>) daoF.getDAOResident()
                                                    .findMooredResidents();
        vTrips = (Vector<Trip>) daoF.getDAOTrip().findCurrentTrips();
        vOwners = (Vector<Owner>) daoF.getDAOOwner().findAll();
        vModels = (Vector<Model>) daoF.getDAOModel().findAll();
        vPontoon = (Vector<Pontoon>) daoF.getDAOPontoon().findAll();
        vEmplacements = (Vector<Emplacement>) daoF.getDAOEmplacement().
                findAll();
        vEmpTypes = (Vector<EmplacementType>) daoF.getDAOEmplacementType().
                findAll();
        
        vWaitingList = new Vector<Object[]>();
        for (WaitingBoat wb : daoF.getDAOWaitingBoat().findAll()) {
            Object[] wbAndDelay = new Object[2];
            wbAndDelay[0] = wb;
            wbAndDelay[1] = daoF.getDAOWaitingBoat().
                                computeNbDaysToWait(wb.getModel());
            
            vWaitingList.add(wbAndDelay);
        }

        fakeOwner = new Owner();
        vOwners.add(0, fakeOwner);
        
        try {
            phoneMask = new MaskFormatter("##########");
            zipCodeMask = new MaskFormatter("#####");
        } catch (ParseException e) {
            DialogMessage.showErrorMessage("Ouch", "OMFG, WRONG MASK FORMATTER DIE!");
            return;
        }

        tabGeneral.addTab("Trips management", null,
                createPanelTripManagement(), null);
        tabGeneral.addTab("Boats management", null,
                createPanelBoatManagement(), null);
        tabGeneral.addTab("Statistics", null, createPanelStats(), null);

    } // Window()

    private JPanel createPanelTripManagement() {

        JPanel panelTripManagement = new JPanel(new BorderLayout());

        JPanel panelAddTrip = new JPanel();
        panelAddTrip.add(createBoxAddTrip());

        JPanel panelUpdateTrip = new JPanel();
        panelUpdateTrip.add(createBoxAddReturn());

        panelTripManagement.add(panelAddTrip, BorderLayout.WEST);
        panelTripManagement.add(panelUpdateTrip, BorderLayout.EAST);

        return panelTripManagement;

    } // createPanelTripManagement()

    private Box createBoxAddTrip() {

        Box boxAddTrip = Box.createVerticalBox();
        boxAddTrip.setBorder(createRedTitledBorder("Add a trip"));

        JLabel lblNumBateau = new JLabel("Boat");
        boxAddTrip.add(lblNumBateau);

        cbbMooredResident = new JComboBox(vMooredResident);
        cbbMooredResident.setRenderer(new CustomTextListRenderer(this));
        boxAddTrip.add(cbbMooredResident);

        JLabel lblTripDate = new JLabel("Departure date");
        boxAddTrip.add(lblTripDate);

        spinDepartureDate = new SpinnerDate();
        boxAddTrip.add(spinDepartureDate);

        JLabel lblPlannedReturnDate = new JLabel(
                "Planned return date");
        boxAddTrip.add(lblPlannedReturnDate);

        spinPlannedReturnDate = new SpinnerDate();
        boxAddTrip.add(spinPlannedReturnDate);

        JButton btnAddTrip = new JButton("Add");
        btnAddTrip.addActionListener(new CreateTripAction(this));
        boxAddTrip.add(btnAddTrip);

        return boxAddTrip;

    } // createBoxAddTrip()

    private Box createBoxAddReturn() {

        Box boxAddReturn = Box.createVerticalBox();
        boxAddReturn.setBorder(createRedTitledBorder("Register a return"));

        JLabel lblNumTrip = new JLabel("Trip");
        boxAddReturn.add(lblNumTrip);

        cbbTrips = new JComboBox(vTrips);
        cbbTrips.setRenderer(new CustomTextListRenderer(this));
        boxAddReturn.add(cbbTrips);

        JLabel lblRealReturnDate = new JLabel("Return date");
        boxAddReturn.add(lblRealReturnDate);

        spinRealReturnDate = new SpinnerDate();
        boxAddReturn.add(spinRealReturnDate);

        JButton btnRegisterReturn = new JButton("Register");
        btnRegisterReturn.addActionListener(new RegisterReturnAction(this));
        boxAddReturn.add(btnRegisterReturn);

        return boxAddReturn;

    } // createBoxAddReturn()

    private JPanel createPanelBoatManagement() {

        JPanel panelBoatManagement = new JPanel();
        panelBoatManagement.setLayout(new GridLayout(0, 1, 0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        panelBoatManagement.add(tabbedPane);
        
        tabbedPane.addChangeListener(new BoatTabChange(this));

        tabbedPane.addTab("Resident", null, createPanelResident(), null);
        tabbedPane.addTab("Passenger", null, createPanelPassenger(), null);
        tabbedPane.addTab("Waiting list", null, createWaitingTab(), null);
        
        return panelBoatManagement;
    }

    private JPanel createPanelResident() {

        JPanel tabResident = new JPanel(new BorderLayout());

        JPanel panelBoxBoat = new JPanel();
        panelBoxBoat.add(createBoxBoat());

        JPanel panelBoxOwner = new JPanel();
        panelBoxOwner.add(createBoxOwner());

        tabResident.add(panelBoxBoat, BorderLayout.WEST);
        tabResident.add(panelBoxOwner, BorderLayout.CENTER);
        //tabResident.add(getPanelNewHarborModel(), BorderLayout.EAST);

        JButton btnSearchEmplacement = new JButton("Search an emplacement");
        btnSearchEmplacement.addActionListener(new AssignEmpResidentAction(this));
        tabResident.add(btnSearchEmplacement, BorderLayout.SOUTH);

        return tabResident;
    }
    
    public JPanel getPanelNewHarborModel() {
        
        if (null != panelNewHarborModel) return panelNewHarborModel;
        
        panelNewHarborModel = new JPanel();
        
        panelNewHarborModel.add(createBoxAddModel());
        panelNewHarborModel.add(createBoxAddHarbor());
        
        return panelNewHarborModel;
        
    }

    private Box createBoxBoat() {

        Box boxBoat = Box.createVerticalBox();
        boxBoat.setBorder(createRedTitledBorder("Add a resident boat"));

        JLabel lblNameBoat = new JLabel("Boat name");
        boxBoat.add(lblNameBoat);

        txtNameBoat = new JTextField();
        boxBoat.add(txtNameBoat);
        txtNameBoat.setColumns(10);

        JLabel lblOwner = new JLabel("Owner");
        boxBoat.add(lblOwner);

        cbbOwner = new JComboBox(vOwners);
        cbbOwner.setRenderer(new CustomTextListRenderer(this));
        boxBoat.add(cbbOwner);

        JLabel lblHarbor = new JLabel("Harbor");
        boxBoat.add(lblHarbor);

        cbbHarbor = new JComboBox(vHarbors);
        cbbHarbor.setRenderer(new CustomTextListRenderer(this));
        boxBoat.add(cbbHarbor);

        JLabel lblModel = new JLabel("Model");
        boxBoat.add(lblModel);

        cbbModelResident = new JComboBox(vModels.toArray());
        cbbModelResident.setRenderer(new CustomTextListRenderer(this));
        boxBoat.add(cbbModelResident);

        JLabel lblNumSerie = new JLabel("Serie number");
        boxBoat.add(lblNumSerie);

        txtNumSerie = new JTextField();
        boxBoat.add(txtNumSerie);
        txtNumSerie.setColumns(10);

        JLabel lblInsurance = new JLabel("Insurance");
        boxBoat.add(lblInsurance);

        txtInsurance = new JTextField();
        boxBoat.add(txtInsurance);
        txtInsurance.setColumns(10);

        JLabel lblOccupationDuration = new JLabel("Occupation Duration");
        boxBoat.add(lblOccupationDuration);

        cbbOccupationDuration = new JComboBox(Occupation
                .getOccupationDurations().keySet().toArray());
        boxBoat.add(cbbOccupationDuration);

        return boxBoat;

    } // boxBoat()

    private Box createBoxOwner() {

        Box boxOwner = Box.createVerticalBox();
        boxOwner.setBorder(createRedTitledBorder("New owner"));

        JLabel lblNameOwner = new JLabel("Name");
        boxOwner.add(lblNameOwner);

        txtNameOwner = new JTextField();
        boxOwner.add(txtNameOwner);
        txtNameOwner.setColumns(10);

        JLabel lblStreetNum = new JLabel("Street number");
        boxOwner.add(lblStreetNum);

        txtStreetNum = new JTextField();
        boxOwner.add(txtStreetNum);
        txtStreetNum.setColumns(10);

        JLabel lblNameStreet = new JLabel("Name street");
        boxOwner.add(lblNameStreet);

        txtNameStreet = new JTextField();
        boxOwner.add(txtNameStreet);
        txtNameStreet.setColumns(10);

        JLabel lblZipCode = new JLabel("Zip code");
        boxOwner.add(lblZipCode);

        txtZipCode = new JFormattedTextField(zipCodeMask);
        boxOwner.add(txtZipCode);
        txtZipCode.setColumns(10);

        JLabel lblCity = new JLabel("City");
        boxOwner.add(lblCity);

        txtCity = new JTextField();
        boxOwner.add(txtCity);
        txtCity.setColumns(10);

        JLabel lblPhone = new JLabel("Phone");
        boxOwner.add(lblPhone);

        txtPhone = new JFormattedTextField(phoneMask);
        boxOwner.add(txtPhone);
        txtPhone.setColumns(10);

        return boxOwner;

    } // createBoxOwner()

    private JPanel createPanelPassenger() {

        JPanel tabPassenger = new JPanel(new BorderLayout());

        JPanel panelPassenger = new JPanel(new BorderLayout());
        tabPassenger.add(panelPassenger, BorderLayout.CENTER);
        
        JPanel panelBoxPassenger = new JPanel();
        panelBoxPassenger.add(createBoxPassenger());
        
        panelPassenger.add(panelBoxPassenger, BorderLayout.WEST);
//        panelPassenger.add(getPanelNewHarborModel(), BorderLayout.EAST);

        JButton btnSearchEmplacement = new JButton("Search an emplacement");
        btnSearchEmplacement
                .addActionListener(new AssignEmpPassengerAction(this));
        tabPassenger.add(btnSearchEmplacement, BorderLayout.SOUTH);

        return tabPassenger;

    }

    private Box createBoxPassenger() {

        Box boxPassenger = Box.createVerticalBox();
        boxPassenger.setBorder(createRedTitledBorder("Add a passenger boat"));

        JLabel lblOrigin = new JLabel("Origin");
        boxPassenger.add(lblOrigin);

        cbbOrigin = new JComboBox(vHarbors);
        cbbOrigin.setRenderer(new CustomTextListRenderer(this));
        boxPassenger.add(cbbOrigin);

        JLabel lblDestination = new JLabel("Destination");
        boxPassenger.add(lblDestination);

        cbbDestination = new JComboBox(vHarbors);
        cbbDestination.setRenderer(new CustomTextListRenderer(this));
        boxPassenger.add(cbbDestination);

        JLabel lblModelPassenger = new JLabel("Model");
        boxPassenger.add(lblModelPassenger);

        cbbModelPassenger = new JComboBox(vModels);
        cbbModelPassenger.setRenderer(new CustomTextListRenderer(this));
        boxPassenger.add(cbbModelPassenger);

        JLabel lblParkingDuration = new JLabel("Parking duration (days)");
        boxPassenger.add(lblParkingDuration);

        txtParkingDuration = new JTextField();
        boxPassenger.add(txtParkingDuration);
        txtParkingDuration.setColumns(10);

        return boxPassenger;

    } // createBoxPassenger()
    
    private JPanel createWaitingTab() {
        
        panelWaitingList = new JPanel(new BorderLayout());
        
        headerWaitingList = new Vector<String>();
        headerWaitingList.add("Boat");
        headerWaitingList.add("Planned occupation");
        headerWaitingList.add("Rank");
        headerWaitingList.add("Days left");

        tableWaitingList = new JTable(
                WaitingBoat.getRowDataWaitingList(vWaitingList), headerWaitingList);
        panelWaitingList.add(tableWaitingList, BorderLayout.CENTER);
        
        JButton btnSearchEmp = new JButton("Search an emplacement");
        btnSearchEmp.addActionListener(new AssignEmpWaitingBoatAction(this));
        panelWaitingList.add(btnSearchEmp, BorderLayout.SOUTH);
        
        return panelWaitingList;
        
    } // createWaitingTab()
    
    private JPanel createPanelStats() {

        panelStatistics = new JPanel(new BorderLayout());

        panelStatistics.add(createPanelBoutonsStats(), BorderLayout.WEST);
        panelResultStats = new JPanel();
        panelStatistics.add(panelResultStats, BorderLayout.EAST);

        return panelStatistics;

    } // createPanelResearch()

    private JPanel createPanelBoutonsStats() {

        JPanel panelBtnsStats = new JPanel();

        panelBtnsStats.add(createBoxBtnsStats());

        return panelBtnsStats;

    } // createPanelBoutonStats()

    private Box createBoxBtnsStats() {
        
        Box boxBtnsStats = Box.createVerticalBox();
        boxBtnsStats.setBorder(createRedTitledBorder("Statistics"));

        JLabel lblNbBoatProprio = new JLabel("Number of boats for each owners");
        boxBtnsStats.add(lblNbBoatProprio);
        
        JButton btnNbBoatProprio = new JButton(lblShow);
        btnNbBoatProprio.addActionListener(new StatNbBoatProprioAction(this));
        boxBtnsStats.add(btnNbBoatProprio);
        
        JLabel lblNbTripsBoat =
                    new JLabel("Number of trips for each year and each boat");
        boxBtnsStats.add(lblNbTripsBoat);
        
        JButton btnNbTripsBoat = new JButton(lblShow);
        btnNbTripsBoat.addActionListener(new StatNbTripsBoatAction(this));
        boxBtnsStats.add(btnNbTripsBoat);

        JLabel lblNbBoatModel = new JLabel("Number of boats for each models");
        boxBtnsStats.add(lblNbBoatModel);
        
        JButton btnNbBoatModel = new JButton(lblShow);
        btnNbBoatModel.addActionListener(new StatNbBoatModelAction(this));
        boxBtnsStats.add(btnNbBoatModel);

        JLabel lblBasinMoreTrips = new JLabel(
                "Basin with the more trips theses last months");
        boxBtnsStats.add(lblBasinMoreTrips);
        
        txtNbMonths = new JTextField();
        txtNbMonths.setColumns(10);
        boxBtnsStats.add(txtNbMonths);
        
        JButton btnBasinMoreTrips = new JButton(lblShow);
        btnBasinMoreTrips.addActionListener(new StatBasinMoreTripsAction(this));
        boxBtnsStats.add(btnBasinMoreTrips);

        return boxBtnsStats;

    } // createBoxBtnsStats

    public Box createBoxAddHarbor() {

        Box boxAddHarbor = Box.createVerticalBox();
        boxAddHarbor.setBorder(createRedTitledBorder("New harbor"));

        JLabel lblCode = new JLabel("Code");
        boxAddHarbor.add(lblCode);

        txtCode = new JTextField();
        boxAddHarbor.add(txtCode);
        txtCode.setColumns(10);

        JLabel lblNameHarbor = new JLabel("Name");
        boxAddHarbor.add(lblNameHarbor);
        
        txtNameHarbor = new JTextField();
        boxAddHarbor.add(txtNameHarbor);
        txtNameHarbor.setColumns(10);

        JButton btnAddHarbor = new JButton("Add");
        boxAddHarbor.add(btnAddHarbor);
        btnAddHarbor.addActionListener(new CreateHarborAction(this));

        return boxAddHarbor;

    } // createBoxAddHarbor()

    private Box createBoxAddModel() {

        Box boxModel = Box.createVerticalBox();
        boxModel.setBorder(createRedTitledBorder("New model"));

        JLabel lblSerie = new JLabel("Serie");
        boxModel.add(lblSerie);

        txtSerie = new JTextField();
        boxModel.add(txtSerie);
        txtSerie.setColumns(10);

        JLabel lblType = new JLabel("Type");
        boxModel.add(lblType);

        txtType = new JTextField();
        txtType.setColumns(10);
        boxModel.add(txtType);

        JLabel lblConstructor = new JLabel("Constructor");
        boxModel.add(lblConstructor);

        txtConstructor = new JTextField();
        boxModel.add(txtConstructor);
        txtConstructor.setColumns(10);

        JLabel lblLengthModel = new JLabel("Length");
        boxModel.add(lblLengthModel);

        txtLengthModel = new JTextField();
        boxModel.add(txtLengthModel);
        txtLengthModel.setColumns(10);

        JLabel lblWidthModel = new JLabel("Width");
        boxModel.add(lblWidthModel);

        txtWidthModel = new JTextField();
        boxModel.add(txtWidthModel);
        txtWidthModel.setColumns(10);

        JLabel lblDraftModel = new JLabel("Draft");
        boxModel.add(lblDraftModel);

        txtDraftModel = new JTextField();
        boxModel.add(txtDraftModel);
        txtDraftModel.setColumns(10);

        JButton btnAddModel = new JButton("Add");
        boxModel.add(btnAddModel);
        btnAddModel.addActionListener(new CreateModelAction(this));

        return boxModel;

    } // createBoxAddModel()

    private Border createRedTitledBorder(String title) {
        return new TitledBorder(null, title, TitledBorder.LEADING,
                TitledBorder.TOP, null, Color.RED);
    }

    public void showDialogEmps(Iterable<Object[]> empsWithOpt) {

        int nbCol = 4;
        
        dialogEmps = new JDialog(this, true);

        Vector<String> header = new Vector<String>(nbCol);
        header.add("Emplacement");
        header.add("Length");
        header.add("Width");
        header.add("Optimized");

        Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
        List<Emplacement> listEmps = new ArrayList<Emplacement>();

        int maxOpt = 0, i = -1, maxOptIndex = i;
        for (Object[] empWithOpt : empsWithOpt) {
        	++i;
            Vector<Object> oneRow = new Vector<Object>(nbCol);
            Emplacement e = (Emplacement) empWithOpt[0];
            listEmps.add(e);

            int opt = ((Number) empWithOpt[1]).intValue();
            if (opt > maxOpt) {
            	maxOpt = opt;
            	maxOptIndex = i;
            }
            
            oneRow.add(e.toStringForUsers());
            oneRow.add(e.getEmpType().getLength());
            oneRow.add(e.getEmpType().getWidth());
            oneRow.add(new DecimalFormat("##.##").format(opt) + " %");

            rowData.add(oneRow);

        }

        tableEmps = new JTable(rowData, header);
        tableEmps.setRowSelectionInterval(maxOptIndex, maxOptIndex);
        tableEmps.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableEmps.getColumnModel().getColumn(0).setPreferredWidth(100);

        JPanel panelTable = new JPanel();
        dialogEmps.getContentPane().add(panelTable, BorderLayout.CENTER);
        panelTable.add(tableEmps.getTableHeader(), BorderLayout.NORTH);
        panelTable.add(tableEmps, BorderLayout.CENTER);

        JPanel panelLbl = new JPanel(new BorderLayout());
        dialogEmps.add(panelLbl, BorderLayout.NORTH);
        
        panelLbl.add(
                new JLabel("Choose an emplacement"), BorderLayout.CENTER);
        
        lblError = new JLabel();
        panelLbl.add(lblError, BorderLayout.SOUTH);
        lblError.setForeground(Color.RED);

        JButton btnAssignEmp = new JButton("Assign");
        dialogEmps.getContentPane().add(btnAssignEmp, BorderLayout.SOUTH);
        btnAssignEmp.addActionListener(
                new SetEmplacementAction(this, listEmps));

        dialogEmps.setSize(new Dimension(400, 300));
        dialogEmps.setVisible(true);

    } // showDialogEmps()

    public boolean askForWaitingList() {

        String[] options = { "Yes", "No" };

        int answer = JOptionPane.showOptionDialog(this,
                "Sorry, there are no available emplacements for this model.\n"
                        + "Do you wish to be added in the waiting list ?",
                "No more emplacements", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        return answer == JOptionPane.YES_OPTION ? true : false;

    } // showDialogNoEmps()

    public void setPanelResultStatsTable(Vector<Vector<String>> rowData,
                                         Vector<String> header) {

        // Les header ne sont pas visibles :(
        panelResultStats.removeAll();
        panelStatistics.validate();
        tableStatsResult = new JTable(rowData, header);
        tableStatsResult.setEnabled(false);
        tableStatsResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableStatsResult.getColumnModel().getColumn(0).setPreferredWidth(200);
        panelResultStats.add(tableStatsResult);
        
        panelStatistics.validate();

    } // setPanelResultStatsTable()

    /*
     * Getters
     */
    public JTextField getTxtNameBoat() {
        return txtNameBoat;
    }

    public JTextField getTxtNumSerie() {
        return txtNumSerie;
    }

    public JTextField getTxtInsurance() {
        return txtInsurance;
    }

    public JTextField getTxtParkingDuration() {
        return txtParkingDuration;
    }

    public JTextField getTxtNameOwner() {
        return txtNameOwner;
    }

    public JTextField getTxtStreetNum() {
        return txtStreetNum;
    }

    public JTextField getTxtNameStreet() {
        return txtNameStreet;
    }

    public JTextField getTxtZipCode() {
        return txtZipCode;
    }

    public JTextField getTxtCity() {
        return txtCity;
    }

    public JTextField getTxtPhone() {
        return txtPhone;
    }

    public JTextField getTxtSerie() {
        return txtSerie;
    }

    public JTextField getTxtLengthModel() {
        return txtLengthModel;
    }

    public JTextField getTxtWidthModel() {
        return txtWidthModel;
    }

    public JTextField getTxtDraftModel() {
        return txtDraftModel;
    }

    public JTextField getTxtType() {
        return txtType;
    }

    public JTextField getTxtConstructor() {
        return txtConstructor;
    }

    public JTextField getTxtCode() {
        return txtCode;
    }

    public JTextField getTxtNameHarbor() {
        return txtNameHarbor;
    }

    public JTextField getTxtLengthEmp() {
        return txtLengthEmp;
    }

    public JTextField getTxtDepthEmp() {
        return txtDepthEmp;
    }

    public JTextField getTxtWidthEmp() {
        return txtWidthEmp;
    }

    public JTextField getTxtPontoon() {
        return txtPontoon;
    }

    public JTextField getTxtNumInPontoon() {
        return txtNumInPontoon;
    }

    public JTextField getTxtNbMonths() {
        return txtNbMonths;
    }

    public JComboBox getCbbMooredResident() {
        return cbbMooredResident;
    }

    public JComboBox getCbbOwner() {
        return cbbOwner;
    }

    public JComboBox getCbbHarbor() {
        return cbbHarbor;
    }

    public JComboBox getCbbModelResident() {
        return cbbModelResident;
    }

    public JComboBox getCbbModelPassenger() {
        return cbbModelPassenger;
    }

    public JComboBox getCbbTrips() {
        return cbbTrips;
    }

    public JComboBox getCbbOccupationDuration() {
        return cbbOccupationDuration;
    }

    public JComboBox getCbbOrigin() {
        return cbbOrigin;
    }

    public JComboBox getCbbDestination() {
        return cbbDestination;
    }

    public JComboBox getCbbIsResident() {
        return cbbIsResident;
    }

    public JComboBox getCbbEmpType() {
        return cbbEmpType;
    }

    public JComboBox getCbbPontoon() {
        return cbbPontoon;
    }

    public JComboBox getCbbBasin() {
        return cbbBasin;
    }

    public JComboBox getCbbBoatStat() {
        return cbbBoatStat;
    }

    public SpinnerDate getSpinDepartureDate() {
        return (SpinnerDate) spinDepartureDate;
    }

    public SpinnerDate getSpinPlannedReturnDate() {
        return (SpinnerDate) spinPlannedReturnDate;
    }

    public SpinnerDate getSpinRealReturnDate() {
        return (SpinnerDate) spinRealReturnDate;
    }

    public JLabel getLblError() {
        return lblError;
    }

    public JTable getTableEmps() {
        return tableEmps;
    }

    public JTable getTableStatsResult() {
        return tableStatsResult;
    }

    public JTable getTableWaitingList() {
        return tableWaitingList;
    }
    
    public JTable setTableWaitingList(JTable tableWaitingList) {
        return this.tableWaitingList = tableWaitingList;
    }

    public JDialog getDialogEmps() {
        return dialogEmps;
    }

    public JCheckBox getCheckIsResident() {
        return checkIsResident;
    }

    public Owner getFakeOwner() {
        return fakeOwner;
    }

    public Emplacement getCurrentEmplacement() {
        return currentEmplacement;
    }

    public void setCurrentEmplacement(Emplacement currentEmplacement) {
        this.currentEmplacement = currentEmplacement;
    }

    public Integer getCurrentOccupationDuration() {
        return currentOccupationDuration;
    }
    
    public void setCurrentOccupationDuration(Integer occupationDuration) {
        this.currentOccupationDuration = occupationDuration;
    }

    public Integer getCurrentParkingDuration() {
        return currentParkingDuration;
    }

    public void setCurrentParkingDuration(Integer currentParkingDuration) {
        this.currentParkingDuration = currentParkingDuration;
    }

    public Vector<Resident> getvMooredResident() {
        return vMooredResident;
    }

    public List<Harbor> getvHarbors() {
        return vHarbors;
    }

    public Vector<Trip> getvTrips() {
        return vTrips;
    }

    public Vector<Owner> getvOwners() {
        return vOwners;
    }

    public Vector<Model> getvModels() {
        return vModels;
    }

    public Vector<Pontoon> getvPontoon() {
        return vPontoon;
    }

    public Vector<Emplacement> getvEmplacements() {
        return vEmplacements;
    }

    public Vector<EmplacementType> getvEmpTypes() {
        return vEmpTypes;
    }

    public Vector<Object[]> getvWaitingList() {
        return vWaitingList;
    }

    public Vector<String> getHeaderWaitingList() {
        return headerWaitingList;
    }

    public JPanel getPanelResultStats() {
        return panelResultStats;
    }

    public JPanel getPanelWaitingList() {
        return panelWaitingList;
    }

}
