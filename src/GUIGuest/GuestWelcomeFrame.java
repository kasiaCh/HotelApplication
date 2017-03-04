package GUIGuest;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GuestWelcomeFrame extends JFrame{
    Connector connector;
    private JPanel panel1;
    private JButton searchForRoomsButton;
    private JButton searchForBookingButton;
    private JTextField welcomeField;
    private String login;


    public GuestWelcomeFrame(Connector connector, String login){
        super("Hotel Application");
        this.connector = connector;
        this.login = login;
        setContentPane(panel1);
        pack();
        makeMenuPanel();
        setVisible(true);
    }

    private void makeMenuPanel(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        searchForRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchForRoomFrame searchForRoomFrame = new SearchForRoomFrame(connector);
                searchForRoomFrame.setLogin(login);
            }
        });

        searchForBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyBookingsFrame myBookingsFrame = new MyBookingsFrame(connector);
                myBookingsFrame.setLogin(login);
                myBookingsFrame.showBookings();
            }
        });
    }


    private void createUIComponents() {

    }


}

