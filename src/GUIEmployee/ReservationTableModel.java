package GUIEmployee;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class ReservationTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int RESERVATION_ID_COL = 0;
    private static final int NUMBER_OF_ROOM_COL = 1;
    private static final int LOGIN_COL = 2;
    private static final int GUEST_NAME_COL = 3;
    private static final int GUEST_SURNAME_COL = 4;
    private static final int BEGINNING_COL = 5;
    private static final int END_COL = 6;
    private static final int DATE_COL = 7;
    private static final int IF_CANCEL_POSSIBLE_COL = 8;
    private static final int PAYMENT_COL = 9;

    private String[] columnNames = {"ID", "Nr. pokoju", "Login", "Imię", "Nazwsko", "Początek", "Koniec", "Data", "Do odwołania", "Płatność"};
    private List<Reservation> reservations;

    public ReservationTableModel(List<Reservation> theReservations) {
        reservations = theReservations;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return reservations.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Reservation tmpReservation = reservations.get(row);

        switch (col) {
            case RESERVATION_ID_COL:
                return tmpReservation.getReservationID();
            case NUMBER_OF_ROOM_COL:
                return tmpReservation.getNumberOfRoom();
            case LOGIN_COL:
                return tmpReservation.getLogin();
            case GUEST_NAME_COL:
                return tmpReservation.getGuestName();
            case GUEST_SURNAME_COL:
                return tmpReservation.getGuestSurname();
            case BEGINNING_COL:
                return tmpReservation.getBeginning();
            case END_COL:
                return tmpReservation.getEnd();
            case DATE_COL:
                return tmpReservation.getDate();
            case IF_CANCEL_POSSIBLE_COL:
                return tmpReservation.getIfCancelPossible();
            case OBJECT_COL:
                return tmpReservation;
            case PAYMENT_COL:
                return tmpReservation.getPayment();
            default:
                return tmpReservation.getReservationID();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
