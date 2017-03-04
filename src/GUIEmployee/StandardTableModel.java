package GUIEmployee;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StandardTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int ID_COL = 0;
    private static final int STANDARD_NAME_COL = 1;
    private static final int IF_TV_COL = 2;
    private static final int IF_WIFI_COL = 3;
    private static final int IF_AIR_CONDITIONING_COL = 4;
    private static final int IF_WAKING_UP_COL = 5;
    private static final int IF_HAIRDRYER_COL = 6;
    private static final int IF_IRON_COL = 7;
    private static final int IF_COSMETICS_COL = 8;
    private static final int IF_SAFE_COL = 9;

    private String[] columnNames = { "ID", "Standard", "TV", "WiFi",
            "Klimatyzacja", "Budzenie na życzenie", "Suszarka", "Żelazko", "Zestaw kosmetyków", "Sejf" };

    private List<Standard> standards;

    public StandardTableModel(List<Standard> theStandards) {
        standards = theStandards;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return standards.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Standard tmpStandard = standards.get(row);

        switch (col) {
            case ID_COL:
                return tmpStandard.getID();
            case STANDARD_NAME_COL:
                return tmpStandard.getStandard();
            case IF_TV_COL:
                return tmpStandard.isIfTV();
            case IF_WIFI_COL:
                return tmpStandard.isIfWIFI();
            case IF_AIR_CONDITIONING_COL:
                return tmpStandard.isIfAirConditioning();
            case IF_WAKING_UP_COL:
                return tmpStandard.isIfWakingUp();
            case IF_HAIRDRYER_COL:
                return tmpStandard.isIfHairDryer();
            case IF_IRON_COL:
                return tmpStandard.isIfIron();
            case IF_COSMETICS_COL:
                return tmpStandard.isIfCosmetics();
            case IF_SAFE_COL:
                return tmpStandard.isIfSafe();
            case OBJECT_COL:
                return tmpStandard;
            default:
                return tmpStandard.getID();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }


}
