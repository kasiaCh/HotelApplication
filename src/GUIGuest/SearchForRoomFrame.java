package GUIGuest;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SearchForRoomFrame extends JFrame {
    private Connector connector;
    private String login;
    public JPanel panel1;
    private JLabel arrivalDateLabel;
    private JLabel checkOutDateLabel;
    private JLabel numberOfPeopleLabel;
    private JTextField arrivalDateField;
    private JTextField checkOutDateField;
    private JComboBox numberOfPeopleBox;
    private JLabel numberOfBedsLabel;
    private JComboBox numberOfBedsBox;
    private JComboBox standardBox;
    private JLabel standard;
    private JLabel additionalFeatures;
    private JRadioButton balcony;
    private JRadioButton seaView;
    private JComboBox sortBox;
    private JLabel sortLabel;
    private JButton searchButton;
    private JButton bookButton;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel topPanel;
    private JTable table1;

   String arrivalDateText;
    String checkOutDateText;


    public SearchForRoomFrame(Connector connector){
        super("Hotel Application");
        this.connector = connector;
        makeMenuPanels();
        makeSearchButtonListener();
        makeBookButtonListener();
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100,135,1200,500);
        setVisible(true);
    }

    private void makeMenuPanels(){
        numberOfPeopleBox.addItem("1");
        numberOfPeopleBox.addItem("2");
        numberOfPeopleBox.addItem("3");
        numberOfBedsBox.addItem("1");
        numberOfBedsBox.addItem("2");
        numberOfBedsBox.addItem("3");
        standardBox.addItem("Standard");
        standardBox.addItem("Comfort");
        standardBox.addItem("Superior");
        sortBox.addItem("średniej oceny gości");
        sortBox.addItem("popularności");

    }

    private void makeSearchButtonListener(){

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    arrivalDateText = arrivalDateField.getText();
                    checkOutDateText = checkOutDateField.getText();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedArrival = format.parse(arrivalDateText);
                    java.sql.Date arrivalDate = new java.sql.Date(parsedArrival.getTime());
                    if (!arrivalDateText.equals(format.format(arrivalDate))) {
                        JOptionPane.showMessageDialog(null,"Niepoprawny format daty.\n" +
                                "Oczekiwany format: RRRR-MM-DD");
                        return;
                    }
                    java.util.Date parsedCheckOut = format.parse(checkOutDateText);
                    java.sql.Date checkOutDate = new java.sql.Date(parsedCheckOut.getTime());
                    if (!checkOutDateText.equals(format.format(checkOutDate))) {
                        JOptionPane.showMessageDialog(null,"Niepoprawny format daty.\n" +
                                "Oczekiwany format: RRRR-MM-DD");
                        return;
                    }
                    String numberOfPeople = numberOfPeopleBox.getSelectedItem().toString();
                    String numberOfBeds = numberOfBedsBox.getSelectedItem().toString();
                    String standard = standardBox.getSelectedItem().toString();
                    String sort = sortBox.getSelectedItem().toString();
                    Boolean ifBalcony = balcony.isSelected();
                    Boolean ifSeaView = seaView.isSelected();
                    ArrayList<Room> rooms = null;
                    rooms = connector.searchForRooms(sort, standard, numberOfPeople, numberOfBeds, ifBalcony, ifSeaView, arrivalDateText, checkOutDateText);
                    RoomTableModel roomTableModel = new RoomTableModel(rooms);
                    table1.setModel(roomTableModel);
                }

                catch (ParseException pe){
                    JOptionPane.showMessageDialog(null,"Niepoprawny format daty.\n" +
                            "Oczekiwany format: RRRR-MM-DD");
                }


            }
        });
    }

    private void makeBookButtonListener(){
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table1.getSelectedRow();
                    int roomNr = (int) table1.getModel().getValueAt(selectedRow, 0);
                    BookingFrame bookingFrame = new BookingFrame(connector);
                    bookingFrame.setBookingData(login, arrivalDateText, checkOutDateText, roomNr);
                }
                catch (IndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null,"Nie wybrałeś pokoju!");
                }
            }
        });
    }

    public void setLogin(String login){
        this.login = login;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here



    }
}
