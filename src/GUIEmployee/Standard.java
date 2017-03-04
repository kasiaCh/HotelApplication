package GUIEmployee;


public class Standard {

    private int ID;
    private String standard;
    private boolean ifTV;
    private boolean ifWIFI;
    private boolean ifAirConditioning;
    private boolean ifWakingUp;
    private boolean ifHairDryer;
    private boolean ifIron;
    private boolean ifCosmetics;
    private boolean ifSafe;

    public Standard (int ID, String standard, boolean ifTV, boolean ifWIFI, boolean ifAirConditioning,
                     boolean ifWakingUp, boolean ifHairDryer, boolean ifIron, boolean ifCosmetics, boolean ifSafe){

        this.ID = ID;
        this.standard = standard;
        this.ifTV = ifTV;
        this.ifWIFI = ifWIFI;
        this.ifAirConditioning = ifAirConditioning;
        this.ifWakingUp = ifWakingUp;
        this.ifHairDryer = ifHairDryer;
        this.ifIron = ifIron;
        this.ifCosmetics = ifCosmetics;
        this.ifSafe = ifSafe;
    }

    public int getID() { return ID; }

    public String getStandard() {
        return standard;
    }

    public boolean isIfTV() {
        return ifTV;
    }

    public boolean isIfWIFI() {
        return ifWIFI;
    }

    public boolean isIfAirConditioning() {
        return ifAirConditioning;
    }

    public boolean isIfWakingUp() {
        return ifWakingUp;
    }

    public boolean isIfHairDryer() {
        return ifHairDryer;
    }

    public boolean isIfIron() {
        return ifIron;
    }

    public boolean isIfCosmetics() {
        return ifCosmetics;
    }

    public boolean isIfSafe() {
        return ifSafe;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public void setIfTV(boolean ifTV) {
        this.ifTV = ifTV;
    }

    public void setIfWIFI(boolean ifWIFI) {
        this.ifWIFI = ifWIFI;
    }

    public void setIfAirConditioning(boolean ifAirConditioning) {
        this.ifAirConditioning = ifAirConditioning;
    }

    public void setIfWakingUp(boolean ifWakingUp) {
        this.ifWakingUp = ifWakingUp;
    }

    public void setIfHairDryer(boolean ifHairDryer) {
        this.ifHairDryer = ifHairDryer;
    }

    public void setIfIron(boolean ifIron) {
        this.ifIron = ifIron;
    }

    public void setIfCosmetics(boolean ifCosmetics) {
        this.ifCosmetics = ifCosmetics;
    }

    public void setIfSafe(boolean ifSafe) {
        this.ifSafe = ifSafe;
    }

    @Override
    public String toString(){
        return String
                .format("Standard [ID=%s, standard=%s, ifTV=%s, ifWIFI=%s, ifAirConditioning=%s, ifWakingUp=%s, ifHairDryer=%s, ifIron=%s, ifCosmetics=%s, ifSafe=%s]",
                        ID, standard, ifTV, ifWIFI, ifAirConditioning, ifWakingUp, ifHairDryer, ifIron, ifCosmetics, ifSafe);
    }
}