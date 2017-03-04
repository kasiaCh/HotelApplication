package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CountIncomeDialog extends JDialog {
    private JPanel contentPane;
    private JButton countButton;
    private JButton cancelButton;
    private JTextField beginningTextField;
    private JTextField endTextField;
    private JTextArea incomeTextArea;
    private JLabel beginningLabel;
    private JLabel endLabel;
    private Connector connector;

    public CountIncomeDialog(Connector connector) {
        this.connector = connector;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(countButton);
        incomeTextArea.setEditable(false);
        setTitle("HotelApp");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(580, 300, 350, 200);


        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String beginning = beginningTextField.getText();
                String end = endTextField.getText();
                String income = "";

                if (!beginning.equals("") && !end.equals("")) {
                    try {
                        income = connector.countIncome(java.sql.Date.valueOf(beginning), java.sql.Date.valueOf(end));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(CountIncomeDialog.this, "Niepoprawny format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    } catch (IllegalArgumentException exep) {
                        JOptionPane.showMessageDialog(CountIncomeDialog.this, "Niepoprawny format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!income.equals("")) {
                        incomeTextArea.setText(income);
                    } else {
                        incomeTextArea.setText("0");
                    }
                } else {
                    JOptionPane.showMessageDialog(CountIncomeDialog.this, "Wypełnij wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }
}
