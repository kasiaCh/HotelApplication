package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddStandardDialog extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton cancelButton;
    private JTextField standardTextField;
    private JCheckBox wiFiCheckBox;
    private JCheckBox AirConditioningCheckBox;
    private JCheckBox wakingUpCheckBox;
    private JCheckBox ironCheckBox;
    private JCheckBox cosmeticsCheckBox;
    private JCheckBox safeCheckBox;
    private JCheckBox TVCheckBox;
    private JCheckBox hairdryerCheckBox;
    private JLabel standardLabel;
    private Connector connector;
    private StandardsEditFrame standardsEditFrame;
    private Standard tmpStandard = null;
    private boolean updateMode = false;
    private int ID;

    public AddStandardDialog(Connector connector, StandardsEditFrame standardsEditFrame, Standard tmpStandard, boolean updateMode, int ID) {
        this.connector = connector;
        this.standardsEditFrame = standardsEditFrame;
        this.tmpStandard = tmpStandard;
        this.updateMode = updateMode;
        this.ID = ID;

        if (updateMode){
            populateGui(tmpStandard);
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
                String standardName = standardTextField.getText();
                boolean ifTV = false;
                boolean ifWiFi = false;
                boolean ifAirConditioning = false;
                boolean ifWakingUp = false;
                boolean ifHairDryer = false;
                boolean ifIron = false;
                boolean ifCosmetics = false;
                boolean ifSafe = false;
                if (TVCheckBox.isSelected())
                    ifTV = true;
                if (wiFiCheckBox.isSelected())
                    ifWiFi = true;
                if (AirConditioningCheckBox.isSelected())
                    ifAirConditioning = true;
                if (wakingUpCheckBox.isSelected())
                    ifWakingUp = true;
                if (hairdryerCheckBox.isSelected())
                    ifHairDryer = true;
                if (ironCheckBox.isSelected())
                    ifIron = true;
                if (cosmeticsCheckBox.isSelected())
                    ifCosmetics = true;
                if (safeCheckBox.isSelected())
                    ifSafe = true;

                Standard standard;
                if (!standardName.equals("")) {

                    if (updateMode){
                        standard = tmpStandard;
                        standard.setID(ID);
                        standard.setStandard(standardName);
                        standard.setIfTV(ifTV);
                        standard.setIfWIFI(ifWiFi);
                        standard.setIfAirConditioning(ifAirConditioning);
                        standard.setIfWakingUp(ifWakingUp);
                        standard.setIfCosmetics(ifCosmetics);
                        standard.setIfHairDryer(ifHairDryer);
                        standard.setIfIron(ifIron);
                        standard.setIfSafe(ifSafe);
                    } else {
                        standard = new Standard(0, standardName, ifTV, ifWiFi, ifAirConditioning, ifWakingUp, ifHairDryer, ifIron, ifCosmetics, ifSafe);
                    }
                    try {
                        if (updateMode){
                            connector.updateStandard(standard);
                        } else {
                            connector.addStandard(standard);
                        }
                        setVisible(false);
                        dispose();
                        standardsEditFrame.refresh();
                        JOptionPane.showMessageDialog(standardsEditFrame, "Standard został pomyślnie zapisany.", "Standard zapisany", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(AddStandardDialog.this, "Błąd w trakcie zapisywania standardu: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Wpisz nazwę standardu!");
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

    public AddStandardDialog(StandardsEditFrame standardsEditFrame, Connector connector){
        this(connector, standardsEditFrame, null, false, 0);
    }

    private void populateGui(Standard tmpStandard) {

        standardTextField.setText(String.valueOf(tmpStandard.getStandard()));
        if(tmpStandard.isIfWIFI())
            wiFiCheckBox.setSelected(true);
        if(tmpStandard.isIfTV())
            TVCheckBox.setSelected(true);
        if(tmpStandard.isIfAirConditioning())
            AirConditioningCheckBox.setSelected(true);
        if(tmpStandard.isIfIron())
            ironCheckBox.setSelected(true);
        if(tmpStandard.isIfHairDryer())
            hairdryerCheckBox.setSelected(true);
        if(tmpStandard.isIfCosmetics())
            cosmeticsCheckBox.setSelected(true);
        if(tmpStandard.isIfSafe())
            safeCheckBox.setSelected(true);
        if(tmpStandard.isIfWakingUp())
            wakingUpCheckBox.setSelected(true);

        addButton.setText("Edytuj");
    }
}
