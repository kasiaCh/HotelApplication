package GUIEmployee;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class TypesOfRoomsTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int ID_COL = 0;
    private static final int STANDARD_COL = 1;
    private static final int NUMBER_OF_PEOPLE_COL = 2;
    private static final int NUMBER_OF_BEDS_COL = 3;
    private static final int PRICE_COL = 4;

    private String[] columnNames = {"ID", "Standard", "Ilość osób", "Ilość łóżek", "Cena"};
    private List<TypeOfRoom> typesOfRooms;

    public TypesOfRoomsTableModel(List<TypeOfRoom> theTypesOfRooms) {typesOfRooms = theTypesOfRooms;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return typesOfRooms.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        TypeOfRoom tmpTypeOfRoom = typesOfRooms.get(row);

        switch (col) {
            case ID_COL:
                return  tmpTypeOfRoom.getID();
            case STANDARD_COL:
                return tmpTypeOfRoom.getStandard();
            case NUMBER_OF_PEOPLE_COL:
                return tmpTypeOfRoom.getNumberOfPeople();
            case NUMBER_OF_BEDS_COL:
                return tmpTypeOfRoom.getNumberOfBeds();
            case PRICE_COL:
                return tmpTypeOfRoom.getPrice();
            case OBJECT_COL:
                return tmpTypeOfRoom;
            default:
                return tmpTypeOfRoom.getID();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
