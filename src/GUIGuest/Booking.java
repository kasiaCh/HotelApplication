package GUIGuest;


public class Booking {
    private int bookingID;
    private String bookingDate;
    private String startDate;
    private String endDate;
    private int roomNr;
    private String standard;
    private int numberOfPeople;
    private int numberOfBeds;
    private String ifPossibilityOfCancellation;
    private int amountForRoom;
    private int amountForServices;
    private String  mark;




    public Booking(int bookingID, String bookingDate, String  startDate, String endDate, int roomNr, String standard, int numberOfPeople,
                   int numberOfBeds, String ifPossibilityOfCancellation, int amountForRoom, int amountForServices, String mark){
        this.bookingID = bookingID;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomNr = roomNr;
        this.standard = standard;
        this.numberOfPeople = numberOfPeople;
        this.numberOfBeds = numberOfBeds;
        this.ifPossibilityOfCancellation = ifPossibilityOfCancellation;
        this.amountForRoom = amountForRoom;
        this.amountForServices = amountForServices;
        this.mark = mark;

    }

    public int getBookingID(){
        return bookingID;
    }
    public String getBookingDate(){
        return bookingDate;
    }
    public String getStartDate(){
        return startDate;
    }
    public String getEndDate(){
        return endDate;
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
    public String getIfPossibilityOfCancellation(){
        return ifPossibilityOfCancellation;
    }
    public int getAmountForRoom(){
        return amountForRoom;
    }
    public int getAmountForServices(){
        return amountForServices;
    }
    public String getMark(){
        return mark;
    }

}
