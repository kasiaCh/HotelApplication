package GUIEmployee;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class RatingsTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int NUMBER_OF_ROOM_COL = 0;
    private static final int GUEST_NAME_COL = 1;
    private static final int GUEST_SURNAME_COL = 2;
    private static final int RATING_COL = 3;
    private static final int AVG_RATING_COL = 4;

    private String[] columnNames = { "Nr. pokoju", "Imię", "Nazwsko", "Ocena",
            "Średnia" };
    private List<Rating> ratings;

    public RatingsTableModel(List<Rating> theRatings) {
        ratings = theRatings;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return ratings.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Rating tmpRating = ratings.get(row);

        switch (col) {
            case NUMBER_OF_ROOM_COL:
                return tmpRating.getNumberOfRoom();
            case GUEST_NAME_COL:
                return tmpRating.getGuestName();
            case GUEST_SURNAME_COL:
                return tmpRating.getGuestSurname();
            case RATING_COL:
                return tmpRating.getRating();
            case AVG_RATING_COL:
                return tmpRating.getAVGrating();
            case OBJECT_COL:
                return tmpRating;
            default:
                return tmpRating.getNumberOfRoom();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
