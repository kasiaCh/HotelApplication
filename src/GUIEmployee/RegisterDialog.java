package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class RegisterDialog extends JDialog {
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private JPasswordField repeatPasswordTextField;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField PESELTextField;
    private JTextField IDNumberTextField;
    private JButton registerButton;
    private JPanel panel;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel repeatPasswordLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel PESELLabel;
    private JLabel IDNumberLabel;
    private Connector connector;
    private JDialog dialog = this;
    private LogInFrame logInFrame;

    public RegisterDialog(LogInFrame logInFrame, Connector connector){
        this.connector = connector;
        this.logInFrame = logInFrame;

        setTitle("HotelApp");
        setContentPane(panel);
        pack();
        setModal(true);
        getRootPane().setDefaultButton(registerButton);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(600,300,300,280);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginTextField.getText().trim();
                String password = passwordTextField.getText().trim();
                String repeatedPassword = repeatPasswordTextField.getText().trim();
                String name = nameTextField.getText().trim();
                String surname = surnameTextField.getText().trim();
                String PESEL = PESELTextField.getText().trim();
                String IDNumber = IDNumberTextField.getText().trim();


                if (!login.equals("") && !password.equals("") && !repeatedPassword.equals("") && !name.equals("") && !surname.equals("")
                        && !PESEL.equals("") && !IDNumber.equals("")){
                    if(!password.equals(repeatedPassword)){
                        JOptionPane.showMessageDialog(RegisterDialog.this,"Wpisane hasła różnią sie! Spróbuj ponownie.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        try {
                            connector.addUser(Long.parseLong(login),password,name,surname,PESEL,IDNumber);
                            setVisible(false);
                            dispose();
                            JOptionPane.showMessageDialog(logInFrame,"Rejestracja przebiegła pomyślnie! Zaloguj się.", "Rejestracja zakończona", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(RegisterDialog.this,"Konto o wprowadzonych danych już istnieje!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(RegisterDialog.this, "Niepoprawny format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(RegisterDialog.this,"Wypełnij wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
