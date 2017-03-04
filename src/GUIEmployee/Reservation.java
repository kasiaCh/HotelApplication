package GUIEmployee;

import java.util.Date;


public class Reservation {

    private int reservationID;
    private Date beginning;
    private Date end;
    private Date date;
    private int numberOfRoom;
    private String login;
    private String guestName;
    private String guestSurname;
    private boolean ifCancelPossible;
    private String payment;

    public Reservation (int reservationID, int numberOfRoom, String login, String guestName, String guestSurname, Date beginning, Date end, Date date, boolean ifCancelPossible, String payment){

        this.reservationID = reservationID;
        this.numberOfRoom = numberOfRoom;
        this.login = login;
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.beginning = beginning;
        this.end = end;
        this.date = date;
        this.ifCancelPossible = ifCancelPossible;
        this.payment = payment;

    }

    public int getReservationID() {
        return reservationID;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public String getLogin() { return login; }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestSurname() {
        return guestSurname;
    }

    public Date getEnd() {
        return end;
    }

    public Date getBeginning() { return beginning; }

    public Date getDate() { return date; }

    public boolean getIfCancelPossible(){ return ifCancelPossible; }

    public String getPayment() { return payment; }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setGuestSurname(String guestSurname) {
        this.guestSurname = guestSurname;
    }

    public void setIfCancelPossible(boolean ifCancelPossible) {
        this.ifCancelPossible = ifCancelPossible;
    }

    public void setPayment(String payment){
        this.payment = payment;
    }

    @Override
    public String toString(){
        return String
                .format("Reservation [reservationID=%s, numberOfRoom=%s, login=%s, guestName=%s, guestSurname=%s, beginning=%s, end=%s, date=%s, ifCancelPossible=%s, payment=%s]",
                        reservationID, numberOfRoom, login, guestName, guestSurname, beginning, end, date, ifCancelPossible, payment);
    }
}