package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddGuestDialog extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton cancelButton;
    private JTextField loginTextField;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField PESELTextField;
    private JTextField IDNumberTextField;
    private JLabel loginLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel PESELLabel;
    private JLabel IDNumberLabel;
    private Connector connector;
    private PresentGuestsFrame presentGuestsFrame;
    private boolean updateMode;
    private Guest tmpGuest;

    public AddGuestDialog(PresentGuestsFrame presentGuestsFrame, Connector connector, Guest tmpGuest, boolean updateMode) {
        this.connector = connector;
        this.presentGuestsFrame = presentGuestsFrame;
        this.tmpGuest = tmpGuest;
        this.updateMode = updateMode;

        if(updateMode){
            populateGui(tmpGuest);
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
                String login = loginTextField.getText();
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                String PESEL = PESELTextField.getText();
                String IDNumber = IDNumberTextField.getText();

                Guest guest = null;
                if (!login.equals("") && !name.equals("") && !surname.equals("") && !PESEL.equals("") && !IDNumber.equals("")) {
                    try {
                        if (updateMode) {
                            guest = tmpGuest;
                            guest.setLogin(login);
                            guest.setName(name);
                            guest.setSurname(surname);
                            guest.setPESEL(Long.valueOf(PESEL));
                            guest.setIDNumber(IDNumber);
                        } else {
                            guest = new Guest(login, name, surname, Long.valueOf(PESEL), IDNumber);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AddGuestDialog.this, "Niepoprawny format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE );
                        return;
                    }
                    try {
                        if (updateMode){
                            connector.updateGuest(guest);
                        } else {
                            connector.addGuest(guest);
                        }
                        setVisible(false);
                        dispose();
                        presentGuestsFrame.refresh();
                        JOptionPane.showMessageDialog(presentGuestsFrame, "Gość został pomyślnie zapisany.", "Gość zapisany", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(presentGuestsFrame, "Błąd w trakcie zapisywania gościa: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
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

    public AddGuestDialog(PresentGuestsFrame presentGuestsFrame, Connector connector){
        this(presentGuestsFrame, connector, null, false);
    }

    private void populateGui(Guest tmpGuest) {

        loginTextField.setText(tmpGuest.getLogin());
        nameTextField.setText(tmpGuest.getName());
        surnameTextField.setText(tmpGuest.getSurname());
        PESELTextField.setText(tmpGuest.getPESEL().toString());
        IDNumberTextField.setText(tmpGuest.getIDNumber());
        loginTextField.setEditable(false);

        addButton.setText("Edytuj");
    }
}