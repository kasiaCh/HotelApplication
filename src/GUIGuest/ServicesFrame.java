package GUIGuest;

import DAO.Connector;

import javax.swing.*;
import java.util.ArrayList;


public class ServicesFrame extends JFrame{
    private Connector connector;
    private int bookingID;
    private JPanel panel1;
    private JTable servicesTable;


    public ServicesFrame(Connector connector){
        super("Hotel Application");
        this.connector = connector;
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(400,400,500,230);
        setVisible(true);
    }

    public void showServices(){
        ArrayList<Service> services = null;
        services = connector.searchForServices(bookingID);
        ServiceTableModel serviceTableModel = new ServiceTableModel(services);
        servicesTable.setModel(serviceTableModel);
        if(services.size()==0){
            JOptionPane.showMessageDialog(null, "Brak wykorzystanych usług!");
        }
    }

    public void setBookingID(int bookingID){
        this.bookingID = bookingID;
    }


}
