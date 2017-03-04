package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class RoomsEditFrame extends JFrame{


    public JPanel panel;
    private JButton newRoomButton;
    private JButton editRoomButton;
    private JButton removeRoomButton;
    private JLabel headerLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private Connector connector;
    private JScrollPane scrollPane;
    private JTable roomsTable;

    public RoomsEditFrame (Connector connector) throws SQLException {

        this.connector = connector;

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(500,100,500,600);
        setTitle("HotelApp");


        removeRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = roomsTable.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz pokój, który chcesz usunąć.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć ten pokój?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }

                try {
                    connector.deleteRoom((int) roomsTable.getValueAt(roomsTable.getSelectedRow(), 0));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                refresh();

                JOptionPane.showMessageDialog(null, "Pokój został pomyślnie usunięty.", "Pokój usunięty", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        newRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addRoomDialog = new AddRoomDialog(RoomsEditFrame.this, connector);
                addRoomDialog.setVisible(true);
            }
        });

        editRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = roomsTable.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz pokój, który chcesz edytować.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Room tmpRoom = (Room) roomsTable.getValueAt(row, RoomTableModel.OBJECT_COL);
                AddRoomDialog addRoomDialog = new AddRoomDialog(RoomsEditFrame.this, connector, tmpRoom, true);
                addRoomDialog.setVisible(true);


            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numberOfRoom = searchTextField.getText();
                List<Room> rooms = null;

                if (numberOfRoom != null && numberOfRoom.trim().length() > 0){
                    try {
                        rooms = connector.searchRooms(Integer.parseInt(numberOfRoom));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        rooms = connector.getAllRooms();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                RoomTableModel roomTableModel = new RoomTableModel(rooms);
                roomsTable.setModel(roomTableModel);
            }
        });

        scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        roomsTable = new JTable();
        scrollPane.setViewportView(roomsTable);
    }

    protected void refresh(){

        try {
            List<Room> rooms = connector.getAllRooms();

            // create the model and update the "table"
            RoomTableModel model = new RoomTableModel(rooms);

            roomsTable.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
