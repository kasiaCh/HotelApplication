package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddServiceDialog extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton cancelButton;
    private JLabel serviceNameLabel;
    private JLabel priceLabel;
    private JLabel descriptionLabel;
    private JTextField serviceTextField;
    private JTextField priceTextField;
    private JEditorPane descriptionEditorPane;
    private Connector connector;
    private ServicesEditFrame servicesEditFrame;
    private Service tmpService = null;
    private boolean updateMode = false;
    private int ID;

    public AddServiceDialog(ServicesEditFrame servicesEditFrame, Connector connector, Service tmpService, boolean updateMode, int ID){
        this.servicesEditFrame = servicesEditFrame;
        this.connector = connector;
        this.tmpService = tmpService;
        this.updateMode = updateMode;
        this.ID = ID;

        if (updateMode) {
            populateGui(tmpService);
        }

        setTitle("HotelApp");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(addButton);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(525,100,450,220);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String serviceName = serviceTextField.getText();
                String price = priceTextField.getText();
                String description = descriptionEditorPane.getText();

                Service service = null;
                if (!serviceName.equals("") && !price.equals("") && !description.equals("")) {
                    if (updateMode){
                        service = tmpService;
                        service.setID(ID);
                        service.setService(serviceName);
                        service.setPrice(Integer.parseInt(price));
                        service.setDescription(description);
                    } else {
                        service = new Service(0, serviceName, Integer.parseInt(price), description);
                    }
                    try {
                        if (updateMode){
                            connector.updateService(service);
                        } else {
                            connector.addService(service);
                        }
                        setVisible(false);
                        dispose();
                        servicesEditFrame.refresh();
                        JOptionPane.showMessageDialog(servicesEditFrame, "Usługa została pomyślnie zapisana.", "Usługa zapisana", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(servicesEditFrame, "Błąd w trakcie zapisywania usługi: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Wypełnij wszystkie pola!");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }

    private void populateGui(Service tmpService) {

        serviceTextField.setText(tmpService.getService());
        priceTextField.setText(String.valueOf(tmpService.getPrice()));
        descriptionEditorPane.setText(tmpService.getDescription());

        addButton.setText("Edytuj");
    }

    public AddServiceDialog(ServicesEditFrame servicesEditFrame, Connector connector) {
        this (servicesEditFrame, connector, null, false, 0);
    }
}