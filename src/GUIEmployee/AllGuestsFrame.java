package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class AllGuestsFrame extends JFrame{
    public JPanel panel;
    private JLabel headerLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton addReservationButton;
    private Connector connector;
    private JScrollPane scrollPane;
    private JTable guestsTable;

    public AllGuestsFrame(Connector connector) throws SQLException {
        this.connector = connector;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(500,100,600,600);
        setTitle("HotelApp");


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String PESEL = searchTextField.getText();
                List<Guest> guests = null;

                if (PESEL != null && PESEL.trim().length() > 0){
                    try {
                        guests = connector.searchGuest(PESEL);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        guests = connector.getGuests("SELECT * FROM widok_wszystkich_gości");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                GuestTableModel guestTableModel = new GuestTableModel(guests);
                guestsTable.setModel(guestTableModel);
            }
        });

        scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        guestsTable = new JTable();
        scrollPane.setViewportView(guestsTable);
        addReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = guestsTable.getSelectedRow();
                String login = guestsTable.getValueAt(row,0).toString();
                GUIGuest.SearchForRoomFrame searchForRoomFrame = new GUIGuest.SearchForRoomFrame(connector);
                searchForRoomFrame.setLogin(login);
            }
        });
    }

}
