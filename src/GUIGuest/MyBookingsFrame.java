package GUIGuest;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MyBookingsFrame extends JFrame{
    Connector connector;
    String login;
    private JPanel panel1;
    private JButton showUsedServicesButton;
    private JPanel topPanel;
    private JTable bookingsTable;
    private JButton giveMarkButton;
    private JButton cancelReservationButton;

    public MyBookingsFrame(Connector connector){
        super("Hotel Application");
        this.connector = connector;
        setContentPane(panel1);
        pack();
        makeShowServicesButtonListener();
        makeGiveMarkButtonListener();
        makeCancelReservationButtonListener();
        setBounds(100,200,1200,500);
        setVisible(true);
    }

    public  void showBookings(){
        ArrayList<Booking> bookings = null;
        bookings = connector.searchForBookings(login);
        BookingTableModel bookingTableModel = new BookingTableModel(bookings);
        bookingsTable.setModel(bookingTableModel);
    }

    private void makeShowServicesButtonListener(){
        showUsedServicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int selectedRow = bookingsTable.getSelectedRow();
                    int bookingID = (int) bookingsTable.getModel().getValueAt(selectedRow,0);
                    ServicesFrame servicesFrame = new ServicesFrame(connector);
                    servicesFrame.setBookingID(bookingID);
                    servicesFrame.showServices();
                }
                catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null,"Nie wybrałeś rezerwacji!");
                }


            }
        });
    }

    private void makeGiveMarkButtonListener(){
        giveMarkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = bookingsTable.getSelectedRow();
                    int bookingID = (int) bookingsTable.getModel().getValueAt(selectedRow, 0);
                    GivingMarkFrame givingMarkFrame = new GivingMarkFrame(connector);
                    givingMarkFrame.setBookingID(bookingID);
                    showBookings();
                }
                catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null,"Nie wybrałeś rezerwacji!");
                }

            }
        });
    }

    private void makeCancelReservationButtonListener(){
        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = bookingsTable.getSelectedRow();
                    int bookingID = (int) bookingsTable.getModel().getValueAt(selectedRow, 0);
                    connector.cancelReservation(bookingID);
                    showBookings();
                }
                catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null,"Nie wybrałeś rezerwacji!");
                }
            }
        });
    }

    public void setLogin(String login){
        this.login = login;
    }
}
