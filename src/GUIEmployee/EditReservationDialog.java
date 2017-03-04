package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class EditReservationDialog extends JDialog {
    private JPanel contentPane;
    private JButton editButton;
    private JButton cancelButton;
    private JLabel loginLabel;
    private JTextField loginTextField;
    private JTextField numberOfRoomTextField;
    private JComboBox paymentComboBox;
    private JTextField beginningTextField;
    private JTextField endTextField;
    private JLabel numberOfRoomLabel;
    private JLabel beginningLabel;
    private JLabel endLabel;
    private JLabel paymentLabel;
    private JCheckBox ifCancelPossibleCheckBox;
    private Connector connector;
    private PresentReservationsFrame presentReservationsFrame;
    private Reservation tmpReservation;
    private int ID;

    public EditReservationDialog(PresentReservationsFrame presentReservationsFrame, Connector connector, Reservation tmpReservation, int ID) {
        this.presentReservationsFrame = presentReservationsFrame;
        this.connector = connector;
        this.tmpReservation = tmpReservation;
        this.ID = ID;

        populateGui(tmpReservation);

        setTitle("HotelApp");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(editButton);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(525,100,450,250);

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String login = loginTextField.getText();
                String numberOfRoom = numberOfRoomTextField.getText();
                String beginning = beginningTextField.getText();
                String end = endTextField.getText();
                Boolean ifCancelPossible = false;
                String payment = paymentComboBox.getSelectedItem().toString();
                Reservation reservation = null;

                if(ifCancelPossibleCheckBox.isSelected()){
                    ifCancelPossible = true;
                }
                if(!login.equals("") && !numberOfRoom.equals("") && !beginning.equals("") && !end.equals("")){
                    try {
                        reservation = tmpReservation;
                        reservation.setReservationID(ID);
                        reservation.setLogin(login);
                        reservation.setNumberOfRoom(Integer.parseInt(numberOfRoom));
                        reservation.setBeginning(Date.valueOf(beginning));
                        reservation.setEnd(Date.valueOf(end));
                        reservation.setIfCancelPossible(ifCancelPossible);
                        reservation.setPayment(payment);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(EditReservationDialog.this,"Niepoprawnyh format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    } catch (IllegalArgumentException exep) {
                        JOptionPane.showMessageDialog(EditReservationDialog.this,"Niepoprawnyh format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try{
                        connector.updateReservation(reservation);
                        setVisible(false);
                        dispose();
                        presentReservationsFrame.refresh();
                        JOptionPane.showMessageDialog(presentReservationsFrame, "Rezerwacja została pomyślnie zapisana.", "Rezerwacja zapisana", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(EditReservationDialog.this, "Błąd w trakcie zapisywania rezerwacji: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
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

    public void populateGui (Reservation tmpReservation) {
        loginTextField.setText(tmpReservation.getLogin());
        numberOfRoomTextField.setText(String.valueOf(tmpReservation.getNumberOfRoom()));
        beginningTextField.setText(String.valueOf(tmpReservation.getBeginning()));
        endTextField.setText(String.valueOf(tmpReservation.getEnd()));
        if(tmpReservation.getIfCancelPossible())
            ifCancelPossibleCheckBox.setSelected(true);
        paymentComboBox.setName(tmpReservation.getPayment());
    }
}
