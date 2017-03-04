package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TypesOfRoomsEditFrame extends JFrame {
    public JPanel panel;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JLabel headerLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private Connector connector;
    private JScrollPane scrollPane;
    private JTable typesOfRoomsTable;

    public TypesOfRoomsEditFrame (Connector connector) throws SQLException {
        this.connector = connector;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(500,100,500,600);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = typesOfRoomsTable.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz rodzaj pokoju, który chcesz usunąć.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć ten rodzaj pokoju?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }

                try {
                    connector.deleteTypeOfRoom((int)typesOfRoomsTable.getValueAt(row, 0));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                refresh();

                JOptionPane.showMessageDialog(null, "Rodzaj pokoju został pomyślnie usunięty.", "Rodzaj pokoju usunięty", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addTypeOfRoomDialog = new AddTypeOfRoomDialog(TypesOfRoomsEditFrame.this, connector);
                addTypeOfRoomDialog.setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = typesOfRoomsTable.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz rodzaj pokoju, który chcesz edytować.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int ID = (int) typesOfRoomsTable.getValueAt(row,0);

                TypeOfRoom tmpTypeOfRoom = (TypeOfRoom) typesOfRoomsTable.getValueAt(row, TypesOfRoomsTableModel.OBJECT_COL);
                AddTypeOfRoomDialog addTypeOfRoomDialog = new AddTypeOfRoomDialog(TypesOfRoomsEditFrame.this, connector, tmpTypeOfRoom, true, ID);
                addTypeOfRoomDialog.setVisible(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = searchTextField.getText();
                List<TypeOfRoom> typesOfRooms = null;

                if (ID != null && ID.trim().length() > 0){
                    try {
                        typesOfRooms = connector.searchTypesOfRooms(Integer.parseInt(ID));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        typesOfRooms = connector.getAllTypesOfRooms();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                TypesOfRoomsTableModel typesOfRoomsTableModel = new TypesOfRoomsTableModel(typesOfRooms);
                typesOfRoomsTable.setModel(typesOfRoomsTableModel);
            }
        });
        scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        typesOfRoomsTable = new JTable();
        scrollPane.setViewportView(typesOfRoomsTable);
    }

    protected void refresh(){

        try {
            List<TypeOfRoom> typesOfRooms = connector.getAllTypesOfRooms();

            // create the model and update the "table"
            TypesOfRoomsTableModel model = new TypesOfRoomsTableModel(typesOfRooms);

            typesOfRoomsTable.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
