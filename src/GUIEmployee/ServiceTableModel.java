package GUIEmployee;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class ServiceTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int ID_COL = 0;
    private static final int SERVICE_COL = 1;
    private static final int SERVICE_PRICE_COL = 2;
    private static final int DESCRIPTION_COL = 3;


    private String[] columnNames = { "ID", "Usługa", "Cena", "Opis"};
    private List<Service> services;

    public ServiceTableModel(List<Service> theServices) {
        services = theServices;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return services.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Service tmpService = services.get(row);

        switch (col) {
            case ID_COL:
                return tmpService.getID();
            case SERVICE_COL:
                return tmpService.getService();
            case SERVICE_PRICE_COL:
                return tmpService.getPrice();
            case DESCRIPTION_COL:
                return tmpService.getDescription();
            case OBJECT_COL:
                return tmpService;
            default:
                return tmpService.getID();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}