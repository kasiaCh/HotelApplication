package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class ServicesEditFrame extends JFrame{
    public JPanel panel;
    private JButton newServiceButton;
    private JButton editServiceButton;
    private JButton removeServiceButton;
    private JLabel headerLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private Connector connector;
    private JScrollPane scrollPane;
    private JTable servicesTable;

    public ServicesEditFrame(Connector connector) throws SQLException {
        this.connector = connector;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(500,100,500,600);
        setTitle("HotelApp");

        removeServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = servicesTable.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz usługę, którą chcesz usunąć.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć tę usługę?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }

                try {
                    connector.deleteService(servicesTable.getValueAt(servicesTable.getSelectedRow(), 1).toString());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                refresh();

                JOptionPane.showMessageDialog(null, "Usługa została pomyślnie usunięta.", "Usługa usunięta", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        newServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addServiceDialog = new AddServiceDialog(ServicesEditFrame.this, connector);
                addServiceDialog.setVisible(true);
            }
        });

        editServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = servicesTable.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Wybierz usługę, którą chcesz edytować.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int ID = (int) servicesTable.getValueAt(row,0);
                Service tmpService = (Service) servicesTable.getValueAt(row, ServiceTableModel.OBJECT_COL);
                AddServiceDialog addServiceDialog = new AddServiceDialog(ServicesEditFrame.this, connector, tmpService, true, ID);
                addServiceDialog.setVisible(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = searchTextField.getText();
                List<Service> services = null;

                if (ID != null && ID.trim().length() > 0){
                    try {
                        services = connector.searchService(Integer.parseInt(ID));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        services = connector.getAllServices();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                ServiceTableModel serviceTableModel = new ServiceTableModel(services);
                servicesTable.setModel(serviceTableModel);
            }
        });
        scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        servicesTable = new JTable();
        scrollPane.setViewportView(servicesTable);
    }

    protected void refresh(){

        try {
            List<Service> services = connector.getAllServices();

            // create the model and update the "table"
            ServiceTableModel model = new ServiceTableModel(services);

            servicesTable.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
