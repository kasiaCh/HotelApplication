package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class SearchForGuestFrame extends JDialog{
    public JPanel panel;
    private JTextField textField;
    private JButton searchButton;
    private JTextArea textArea;
    private JLabel numberOfRoomLabel;
    private Connector connector;

    public SearchForGuestFrame (Connector connector) {
        this.connector = connector;
        setTitle("HotelApp");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(580,300,350,150);
        textArea.setEditable(false);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guest = "";
                if (!textField.getText().equals("")) {
                    try {
                        guest = connector.getGuest(Integer.parseInt(textField.getText()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(SearchForGuestFrame.this, "Niepoprawny format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!guest.equals("")) {
                        textArea.setText(guest);
                    } else
                        JOptionPane.showMessageDialog(SearchForGuestFrame.this, "Pokój jest wolny.", "Wolny pokój", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    JOptionPane.showMessageDialog(SearchForGuestFrame.this, "Wpisz numer pokoju!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
