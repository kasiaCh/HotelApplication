package GUIEmployee;

import DAO.Connector;
import GUIGuest.GuestWelcomeFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class LogInFrame extends JFrame{
    public JPanel panel;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel headerLabel;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private Connector connector;

    public LogInFrame(Connector connector){
        this.connector = connector;

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(600,300,350,180);
        setTitle("HotelApp");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterDialog registerDialog = new RegisterDialog(LogInFrame.this, connector);
                registerDialog.setVisible(true);
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginTextField.getText().trim();
                String password = passwordField.getText().trim();
                String type = null;
                try {
                    type = connector.logIn(login,password);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if (type == null){
                    JOptionPane.showMessageDialog(null, "Niepoprawny login lub hasło!");

                } else if (type.equals("gość")) {
                    GuestWelcomeFrame guestWelcomeFrame = new GuestWelcomeFrame(connector, login);
                } else if (type.equals("pracownik")){
                    EmployeeFrame employeeFrame = null;
                    try {
                        employeeFrame = new EmployeeFrame(connector);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    employeeFrame.setVisible(true);
                    setVisible(false);
                    dispose();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
