package GUIEmployee;


public class Rating {

    private int numberOfRoom;
    private String guestName;
    private String guestSurname;
    private int rating;
    private double AVGrating;

    public Rating(int numberOfRoom, String guestName, String guestSurname, int rating,
                  double AVGrating) {
        super();
        this.numberOfRoom = numberOfRoom;
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.rating = rating;
        this.AVGrating = AVGrating;

    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestSurname() {
        return guestSurname;
    }

    public int getRating() {
        return rating;
    }

    public double getAVGrating() {
        return AVGrating;
    }

    @Override
    public String toString(){
        return String
                .format("Rating [numberOfRoom=%s, guestName=%s, guestSurname=%s, rating=%s, AVGrating=%s]",
                        numberOfRoom, guestName, guestSurname, rating, AVGrating);
    }

}
