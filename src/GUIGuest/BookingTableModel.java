package GUIGuest;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class BookingTableModel extends AbstractTableModel {

    private static final int BOOKING_ID_COL = 0;
    private static final int BOOKING_DATE_COL = 1;
    private static final int START_DATE_COL = 2;
    private static final int END_DATE_COL = 3;
    private static final int ROOM_NR_COL = 4;
    private static final int STANDARD_COL = 5;
    private static final int NUMB_OF_PEOPLE_COL = 6;
    private static final int NUMB_OF_BEDS_COL = 7;
    private static final int IF_POSSIBILITY_OF_CANCELLATION_COL = 8;
    private static final int AMOUNT_FOR_ROOM = 9;
    private static final int AMOUNT_FOR_SERVICES = 10;
    private static final int MARK_COL = 11;


    private String[] columnNames = {"Nr rezerwacji", "Data rezerwacji", "Data przyjazdu", "Data wyjazdu", "Nr pokoju", "Standard",
            "Liczba osób", "Liczba łóżek", "Możliwość odwołania", "Kwota za pokój", "Kwata za usługi", "Ocena pokoju"};

    private ArrayList<Booking> bookings;

    public BookingTableModel(ArrayList<Booking> bookings){
        this.bookings = bookings;
    }

    @Override
    public int getRowCount() {
        return bookings.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Booking tempBooking = bookings.get(rowIndex);

        switch (columnIndex){
            case(BOOKING_ID_COL):
                return tempBooking.getBookingID();
            case BOOKING_DATE_COL:
                return tempBooking.getBookingDate();
            case START_DATE_COL:
                return tempBooking.getStartDate();
            case END_DATE_COL:
                return tempBooking.getEndDate();
            case ROOM_NR_COL:
                return tempBooking.getRoomNr();
            case STANDARD_COL:
                return tempBooking.getStandard();
            case NUMB_OF_PEOPLE_COL:
                return tempBooking.getNumberOfPeople();
            case NUMB_OF_BEDS_COL:
                return tempBooking.getNumberOfBeds();
            case IF_POSSIBILITY_OF_CANCELLATION_COL:
                return tempBooking.getIfPossibilityOfCancellation();
            case AMOUNT_FOR_ROOM:
                return tempBooking.getAmountForRoom();
            case AMOUNT_FOR_SERVICES:
                return tempBooking.getAmountForServices();
            case MARK_COL:
                return tempBooking.getMark();
            default:
                return tempBooking.getBookingDate();
        }
    }

    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }

    @Override
    public Class getColumnClass(int c){
        Object value=this.getValueAt(0,c);
        return (value==null?Object.class:value.getClass());
    }
}
