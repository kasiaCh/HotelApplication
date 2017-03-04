package GUIEmployee;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class GuestTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int LOGIN_COL = 0;
    private static final int NAME_COL = 1;
    private static final int SURNAME_COL = 2;
    private static final int PESEL_COL = 3;
    private static final int ID_NUMBER_COL = 4;

    private String[] columnNames = {"Login", "Imię", "Nazwsko", "PESEL", "Nr. dowodu"};
    private List<Guest> guests;

    public GuestTableModel(List<Guest> theGuests) {guests = theGuests;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return guests.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Guest tmpGuest = guests.get(row);

        switch (col) {
            case LOGIN_COL:
                return tmpGuest.getLogin();
            case NAME_COL:
                return tmpGuest.getName();
            case SURNAME_COL:
                return tmpGuest.getSurname();
            case PESEL_COL:
                return tmpGuest.getPESEL();
            case ID_NUMBER_COL:
                return tmpGuest.getIDNumber();
            case OBJECT_COL:
                return tmpGuest;
            default:
                return tmpGuest.getLogin();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
