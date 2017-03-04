package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class SearchForRoomFrame extends JDialog{
    public JPanel panel;
    private JLabel headerLabel;
    private JLabel PESELLabel;
    private JTextField PESELTextField;
    private JButton searchButton;
    private JTextArea textArea;
    private JPanel panel1;
    private Connector connector;

    public SearchForRoomFrame (Connector connector) {
        this.connector = connector;
        setTitle("HotelApp");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setBounds(580,300,300,160);
        textArea.setEditable(false);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numberOfRoom = null;
                if(!PESELTextField.getText().equals("")) {
                    try {
                        numberOfRoom = connector.getNumberOfRoom(Long.valueOf(PESELTextField.getText()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(SearchForRoomFrame.this, "Niepoprawny format wpisanych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!numberOfRoom.equals("0")) {
                        textArea.setText("Numer pokoju: " + numberOfRoom);
                    } else
                        JOptionPane.showMessageDialog(SearchForRoomFrame.this, "Obecnie nie ma w hotelu gościa o podanym peselu.");
                        return;
                } else {
                    JOptionPane.showMessageDialog(SearchForRoomFrame.this, "Wpisz PESEL gościa!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
    }

}
