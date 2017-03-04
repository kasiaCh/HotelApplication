package GUIGuest;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BookingFrame extends JFrame{
    Connector connector;
    private JPanel panel1;
    private JRadioButton cashButton;
    private JRadioButton cardButton;
    private JRadioButton choosePossibilityOfCancellationButton;
    private JRadioButton giveUpThePossibilityOfCancellationButton;
    private JLabel paymentMethodLabel;
    private JLabel askForPossibilityOfCancellation;
    private JButton cancelButton;
    private JButton okButton;

    private String login;
    private String arrivalDate;
    private String  checkOutDate;
    private int roomNr;

    public BookingFrame(Connector connector){
        super("Hotel Application");
        this.connector = connector;
        setContentPane(panel1);
        pack();
        makeOkButtonListener();
        makeRadioButtonsListeners();
        setBounds(600,400,500,230);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void setBookingData(String login, String arrivalDate, String  checkOutDate, int roomNr){
            this.login = login;
            this.arrivalDate = arrivalDate;
            this.checkOutDate = checkOutDate;
            this.roomNr = roomNr;
    }

    private void makeOkButtonListener(){
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String paymentMethod = "";
                Boolean possibilityOfCancellation = false;
                if(cashButton.isSelected()){
                    paymentMethod = "gotówka";
                }
                else if(cardButton.isSelected()){
                    paymentMethod = "karta";
                }

                if(choosePossibilityOfCancellationButton.isSelected()){
                    possibilityOfCancellation = true;
                }
                else if(giveUpThePossibilityOfCancellationButton.isSelected()){
                    possibilityOfCancellation = false;
                }

                connector.book(login,arrivalDate,checkOutDate,roomNr,paymentMethod,possibilityOfCancellation);

            }
        });
    }

    private void makeRadioButtonsListeners(){
        cashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardButton.setSelected(false);
            }
        });
        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cashButton.setSelected(false);
            }
        });
        choosePossibilityOfCancellationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                giveUpThePossibilityOfCancellationButton.setSelected(false);
            }
        });
        giveUpThePossibilityOfCancellationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePossibilityOfCancellationButton.setSelected(false);
            }
        });
    }
}
