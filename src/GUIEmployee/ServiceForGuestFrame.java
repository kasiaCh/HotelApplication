package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class ServiceForGuestFrame extends JDialog{
    public JPanel panel;
    private JButton addServiceButton;
    private JLabel PESELLabel;
    private JPanel panel1;
    private JComboBox comboBox;
    private Connector connector;
    private String login;

    public ServiceForGuestFrame (Connector connector, String login){
        this.login = login;
        this.connector = connector;

        addServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (login == null) {
                    JOptionPane.showMessageDialog(null, "Wybierz gościa, który zamówił usługę.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz dodać gościowi tę usługę?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                if (response != JOptionPane.YES_OPTION) {
                    return;
                }

                try {
                    connector.addServiceToTheGuest(comboBox.getSelectedItem().toString(),login);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Usługa została pomyślnie dodana.", "Usługa dodana", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
