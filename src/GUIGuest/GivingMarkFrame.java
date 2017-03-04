package GUIGuest;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GivingMarkFrame extends JFrame {
    Connector connector;
    int bookingID;
    private JPanel panel1;
    private JLabel label;
    private JButton acceptButton;
    private JTextField markField;
    private JLabel markLabel;
    private JButton cancelButton;

    public GivingMarkFrame(Connector connector){
        super("Hotel Application");
        this.connector = connector;
        setContentPane(panel1);
        pack();
        makeAcceptButtonListener();
        makeCancelButtonListener();
        setBounds(550,400,180,120);
        setVisible(true);
    }

    private void makeAcceptButtonListener(){
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double mark = Double.parseDouble(markField.getText());
                    connector.giveMark(bookingID, mark);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Nieprawidłowy format!");
                }
            }
        });
    }

    private void makeCancelButtonListener(){
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    public void setBookingID(int bookingID){
        this.bookingID = bookingID;
    }


}
