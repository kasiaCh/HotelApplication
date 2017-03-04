package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class PresentReservationsFrame extends JFrame {
    public JPanel panel;
    private JButton editButton;
    private JButton removeButton;
    private JLabel headerLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private Connector connector;
    private JScrollPane scrollPane;
    private JTable reservationsTable;

    public PresentReservationsFrame(Connector connector) throws SQLException {
        this.connector = connector;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(200,100,1000,600);
        setTitle("HotelApp");

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = reservationsTable.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz rezerwację, którą chcesz usunąć.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć tę rezerwację?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }

                try {
                    connector.deleteReservation((int) reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 0));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                refresh();

                JOptionPane.showMessageDialog(null, "Rezerwacja została pomyślnie usunięta.", "Rezerwacja usunięta", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = reservationsTable.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz rezerwację, którą chcesz edytować.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int ID = (int) reservationsTable.getValueAt(row, 0);
                Reservation tmpReservation = (Reservation) reservationsTable.getValueAt(row, RoomTableModel.OBJECT_COL);
                EditReservationDialog editReservationDialog = new EditReservationDialog(PresentReservationsFrame.this, connector, tmpReservation, ID);
                editReservationDialog.setVisible(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = searchTextField.getText();
                List<Reservation> reservations = null;

                if (ID != null && ID.trim().length() > 0){
                    try {
                        reservations = connector.searchReservation(Integer.parseInt(ID), "{call szukaj_obecnych_rezerwacji(?)}");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        reservations = connector.getReservations("SELECT * FROM widok_obecnych_rezerwacji");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                ReservationTableModel reservationTableModel = new ReservationTableModel(reservations);
                reservationsTable.setModel(reservationTableModel);
            }
        });
        scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        reservationsTable = new JTable();
        scrollPane.setViewportView(reservationsTable);
    }
    protected void refresh() {

        try {
            List<Reservation> reservations = connector.getReservations("SELECT * FROM widok_obecnych_rezerwacji");

            // create the model and update the "table"
            ReservationTableModel model = new ReservationTableModel(reservations);

            reservationsTable.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
