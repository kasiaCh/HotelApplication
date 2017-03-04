package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class PresentGuestsFrame extends JFrame {
    public JPanel panel;
    private JFrame frame = this;
    private JButton addButton;
    private JButton addServiceButton;
    private JButton editButton;
    private JLabel headerLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private Connector connector;
    private JScrollPane scrollPane;
    private JTable guestsTable;

    public PresentGuestsFrame(Connector connector) throws SQLException {

        this.connector = connector;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(500,100,600,600);
        setTitle("HotelApp");

        addServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog serviceForGuestFrame = new JDialog(frame, "HotelApp");
                serviceForGuestFrame.setContentPane(new ServiceForGuestFrame(connector, guestsTable.getValueAt(guestsTable.getSelectedRow(), 0).toString()).panel);
                serviceForGuestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                serviceForGuestFrame.pack();
                serviceForGuestFrame.setVisible(true);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addGuestDialog = new AddGuestDialog(PresentGuestsFrame.this, connector);
                addGuestDialog.setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = guestsTable.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz gościa, którego dane chcesz edytować.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Guest tmpGuest = (Guest) guestsTable.getValueAt(row, GuestTableModel.OBJECT_COL);
                AddGuestDialog addGuestDialog = new AddGuestDialog(PresentGuestsFrame.this, connector, tmpGuest, true);
                addGuestDialog.setVisible(true);
            }
        });

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
                        guests = connector.getGuests("SELECT * FROM widok_obecnych_gości");
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
    }

    protected void refresh() {

        try {
            List<Guest> guests = connector.getGuests("SELECT * FROM widok_obecnych_gości");

            // create the model and update the "table"
            GuestTableModel model = new GuestTableModel(guests);

            guestsTable.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
