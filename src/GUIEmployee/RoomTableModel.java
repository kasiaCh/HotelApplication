package GUIEmployee;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class RoomTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int NUMBER_OF_ROOM_COL = 0;
    private static final int TYPE_OF_ROOM_COL = 1;
    private static final int IF_BALCONY_COL = 2;
    private static final int IF_SWIMMINGPOOL_VIEW_COL = 3;
    private static final int IF_SEA_VIEW_COL = 4;

    private String[] columnNames = { "Nr. pokoju", "ID rodzaju", "Balkon", "Widok na basen", "Widok na morze" };
    private List<Room> rooms;

    public RoomTableModel(List<Room> theRooms) {
        rooms = theRooms;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return rooms.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Room tmpRoom = rooms.get(row);

        switch (col) {
            case NUMBER_OF_ROOM_COL:
                return tmpRoom.getNumberOfRoom();
            case TYPE_OF_ROOM_COL:
                return tmpRoom.getTypeOfRoom();
            case IF_BALCONY_COL:
                return tmpRoom.isIfBalcony();
            case IF_SWIMMINGPOOL_VIEW_COL:
                return tmpRoom.isIfSwimmingPooView();
            case IF_SEA_VIEW_COL:
                return tmpRoom.isIfSeaView();
            case OBJECT_COL:
                return tmpRoom;
            default:
                return tmpRoom.getNumberOfRoom();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
}