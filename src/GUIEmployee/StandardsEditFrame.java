package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class StandardsEditFrame extends JFrame{
    public JPanel panel;
    private JButton newStandardButton;
    private JButton editStandardButton;
    private JButton removeStandardButton;
    private JLabel headerLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private Connector connector;
    private JScrollPane scrollPane;
    private JTable standardsTable;

    public StandardsEditFrame(Connector connector) throws SQLException {
        this.connector = connector;

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(200,100,1200,600);
        setTitle("HotelApp");

        removeStandardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = standardsTable.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz standard, który chcesz usunąć.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć ten standard?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }

                try {
                    connector.deleteStandard(standardsTable.getValueAt(standardsTable.getSelectedRow(), 1).toString());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                refresh();

                JOptionPane.showMessageDialog(null, "Standard został pomyślnie usunięty.", "Standard usunięty", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        newStandardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addStandardDialog = new AddStandardDialog(StandardsEditFrame.this, connector);
                addStandardDialog.setVisible(true);
            }
        });

        editStandardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = standardsTable.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz standard, który chcesz edytować.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int ID = (int) standardsTable.getValueAt(row, 0);
                Standard tmpStandard = (Standard) standardsTable.getValueAt(row, StandardTableModel.OBJECT_COL);
                AddStandardDialog addStandardDialog = new AddStandardDialog(connector, StandardsEditFrame.this, tmpStandard, true, ID);
                addStandardDialog.setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = searchTextField.getText();
                List<Standard> standards = null;

                if (ID != null && ID.trim().length() > 0){
                    try {
                        standards = connector.searchStandards(Integer.parseInt(ID));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        standards = connector.getAllStandards();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                StandardTableModel standardTableModel = new StandardTableModel(standards);
                standardsTable.setModel(standardTableModel);
            }
        });

        scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        standardsTable = new JTable();
        scrollPane.setViewportView(standardsTable);
    }



    protected void refresh(){

        try {
            List<Standard> standards = connector.getAllStandards();

            // create the model and update the "table"
            StandardTableModel model = new StandardTableModel(standards);

            standardsTable.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
