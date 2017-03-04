package GUIGuest;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class RoomTableModel extends AbstractTableModel {

    private static final int ROOM_NR_COL = 0;
    private static final int STANDARD_COL = 1;
    private static final int NUMBER_OF_PEOPLE_COL = 2;
    private static final int NUMBER_OF_BEDS_COL = 3;
    private static final int PRICE_COL = 4;
    private static final int BALCONY_COL = 5;
    private static final int SEA_VIEW_COL = 6;
    private static final int GUEST_RATING = 7;
    private static final int NUMB_OF_RESERVATIONS_COL = 8;

    private String[] columnNames = {"Nr pokoju", "Standard", "Ilość osób", "Ilość łóżek", "Cena", "Balkon", "Widok na morze",
    "Srednia ocena gości", "Ilość rezerwacji"};

    private ArrayList<Room> rooms;


    public RoomTableModel(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    @Override
    public int getRowCount() {
        return rooms.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Room tempRoom = rooms.get(rowIndex);

        switch (columnIndex){
            case ROOM_NR_COL:
                return tempRoom.getRoomNr();
            case STANDARD_COL:
                return tempRoom.getStandard();
            case NUMBER_OF_PEOPLE_COL:
                return tempRoom.getNumberOfPeople();
            case NUMBER_OF_BEDS_COL:
                return tempRoom.getNumberOfBeds();
            case PRICE_COL:
                return tempRoom.getPrice();
            case BALCONY_COL:
                return tempRoom.getIfBalcony();
            case SEA_VIEW_COL:
                return tempRoom.getIfSeaView();
            case GUEST_RATING:
                return tempRoom.getGuestRating();
            case NUMB_OF_RESERVATIONS_COL:
                return tempRoom.getNumberOfReservations();
            default:
                return tempRoom.getRoomNr();
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
