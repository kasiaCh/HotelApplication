package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddTypeOfRoomDialog extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton cancelButton;
    private JLabel standardLabel;
    private JLabel numberOfPeopleLabel;
    private JLabel numberOfBedsLabel;
    private JLabel priceLabel;
    private JTextField standardTextField;
    private JTextField numberOfPeopleTextField;
    private JTextField numberOfBedsTextField;
    private JTextField priceTextField;
    private Connector connector;
    private TypesOfRoomsEditFrame typesOfRoomsEditFrame;
    private TypeOfRoom tmpTypeOfRoom = null;
    private boolean updateMode = false;
    private int ID;

    public AddTypeOfRoomDialog(TypesOfRoomsEditFrame typesOfRoomsEditFrame, Connector connector, TypeOfRoom tmpTypeOfRoom, boolean updateMode, int ID) {
        this.connector = connector;
        this.typesOfRoomsEditFrame = typesOfRoomsEditFrame;
        this.tmpTypeOfRoom = tmpTypeOfRoom;
        this.updateMode = updateMode;
        this.ID = ID;

        if(updateMode){
            populateGui(tmpTypeOfRoom);
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
                String standard = standardTextField.getText();
                String numberOfPeople = numberOfPeopleTextField.getText();
                String numberOfBeds = numberOfBedsTextField.getText();
                String price = priceTextField.getText();

                TypeOfRoom typeOfRoom = null;

                if (!standard.equals("") && !numberOfPeople.equals("") && !numberOfBeds.equals("") && !price.equals("")) {
                    try {
                        if (updateMode) {
                            typeOfRoom = tmpTypeOfRoom;
                            typeOfRoom.setID(ID);
                            typeOfRoom.setStandard(standard);
                            typeOfRoom.setNumberOfPeople(Integer.parseInt(numberOfPeople));
                            typeOfRoom.setNumberOfBeds(Integer.parseInt(numberOfBeds));
                            typeOfRoom.setPrice(Integer.parseInt(price));
                        } else {
                            typeOfRoom = new TypeOfRoom(0, standard, Integer.parseInt(numberOfPeople), Integer.parseInt(numberOfBeds), Integer.parseInt(price));
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AddTypeOfRoomDialog.this, "Niepoprawny format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        if(updateMode){
                            connector.updateTypeOfRoom(typeOfRoom);
                        } else {
                            connector.addTypeOfRoom(typeOfRoom);
                        }
                        setVisible(false);
                        dispose();
                        typesOfRoomsEditFrame.refresh();
                        JOptionPane.showMessageDialog(typesOfRoomsEditFrame, "Rodzaj pokoju został pomyślnie zapisany.", "Rodzaj zapisany", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(AddTypeOfRoomDialog.this, "Błąd w trakcie zapisywania rodzaju pokoju: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
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

    public AddTypeOfRoomDialog(TypesOfRoomsEditFrame typesOfRoomsEditFrame, Connector connector){
        this(typesOfRoomsEditFrame, connector, null, false, 0);
    }

    private void populateGui(TypeOfRoom tmpTypeOfRoom) {

        standardTextField.setText(tmpTypeOfRoom.getStandard());
        numberOfPeopleTextField.setText(String.valueOf(tmpTypeOfRoom.getNumberOfPeople()));
        numberOfBedsTextField.setText(String.valueOf(tmpTypeOfRoom.getNumberOfBeds()));
        priceTextField.setText(String.valueOf(tmpTypeOfRoom.getPrice()));

        addButton.setText("Edytuj");
    }
}
