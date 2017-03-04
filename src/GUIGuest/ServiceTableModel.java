package GUIGuest;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class ServiceTableModel extends AbstractTableModel {

    private static final int NAME_COL = 0;
    private static final int PRICE_COL = 1;
    private static final int DESCRIPTION_COL = 2;

    private String[] columnNames = {"Nazwa", "Cena", "Opis"};

    private ArrayList<Service> services;

    public ServiceTableModel(ArrayList<Service> services){
        this.services = services;
    }

    @Override
    public int getRowCount() {
        return services.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Service tempService = services.get(rowIndex);

        switch (columnIndex){
            case NAME_COL:
                return tempService.getName();
            case PRICE_COL:
                return tempService.getPrice();
            case DESCRIPTION_COL:
                return tempService.getDescription();
            default:
                return tempService.getName();
        }
    }

    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }

    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
