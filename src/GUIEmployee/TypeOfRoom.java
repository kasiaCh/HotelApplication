package GUIEmployee;


public class TypeOfRoom {

    private int ID;
    private String standard;
    private int numberOfPeople;
    private int numberOfBeds;
    private int price;

    public TypeOfRoom (int ID, String standard, int numberOfPeople, int numberOfBeds, int price){

        this.ID = ID;
        this.standard = standard;
        this.numberOfPeople = numberOfPeople;
        this.numberOfBeds = numberOfBeds;
        this.price = price;

    }

    public int getID() { return ID; }

    public String getStandard() {
        return standard;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getPrice() {
        return price;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return String
                .format("TypeOfRoom [ID=%s, standard=%s, numberOfPeople=%s, numberOfBeds=%s, price=%s]",
                        ID, standard, numberOfPeople, numberOfBeds, price);
    }
}
