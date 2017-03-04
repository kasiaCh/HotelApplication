package GUIGuest;


public class Room {
    int roomNr;
    String standard;
    int numberOfPeople;
    int numberOfBeds;
    int price;
    String ifBalcony;
    String ifSeaView;
    double guestRating;
    private int numberOfReservations;



    public Room(int roomNr, String standard, int numberOfPeople, int numberOfBeds, int price, String ifBalcony, String ifSeaView,
                double guestRating, int numberOfReservations){
        this.roomNr = roomNr;
        this.standard = standard;
        this.numberOfPeople = numberOfPeople;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
        this.ifBalcony = ifBalcony;
        this.ifSeaView = ifSeaView;
        this.guestRating = guestRating;
        this.numberOfReservations = numberOfReservations;

    }

    public int getRoomNr(){
        return roomNr;
    }

    public String getStandard(){
        return standard;
    }

    public int getNumberOfPeople(){
        return numberOfPeople;
    }

    public int getNumberOfBeds(){
        return numberOfBeds;
    }

    public String getIfBalcony(){
        return ifBalcony;
    }

    public String getIfSeaView(){
        return ifSeaView;
    }

    public int getPrice(){
        return price;
    }

    public double getGuestRating(){
        return guestRating;
    }
    public int getNumberOfReservations(){
        return numberOfReservations;
    }
}
