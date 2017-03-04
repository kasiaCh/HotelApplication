package GUIEmployee;


public class Room {

    private int numberOfRoom;
    private int typeOfRoom;
    private boolean ifBalcony;
    private boolean ifSwimmingPooView;
    private boolean ifSeaView;

    public Room (int numberOfRoom, int typeOfRoom, boolean ifBalcony, boolean ifSwimmingPooView, boolean ifSeaView){

        this.numberOfRoom = numberOfRoom;
        this.typeOfRoom = typeOfRoom;
        this.ifBalcony = ifBalcony;
        this.ifSwimmingPooView = ifSwimmingPooView;
        this.ifSeaView = ifSeaView;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public int getTypeOfRoom(){ return typeOfRoom; }

    public boolean isIfBalcony() {
        return ifBalcony;
    }

    public boolean isIfSwimmingPooView() {
        return ifSwimmingPooView;
    }

    public boolean isIfSeaView() {
        return ifSeaView;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public void setTypeOfRoom(int typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
    }

    public void setIfBalcony(boolean ifBalcony) {
        this.ifBalcony = ifBalcony;
    }

    public void setIfSwimmingPooView(boolean ifSwimmingPooView) {
        this.ifSwimmingPooView = ifSwimmingPooView;
    }

    public void setIfSeaView(boolean ifSeaView) {
        this.ifSeaView = ifSeaView;
    }


    @Override
    public String toString(){
        return String
                .format("Room [numberOfRoom=%s, typeOfRoom=%s, ifBalcony=%s, ifSwimmingPoolView=%s, ifSeaView=%s]",
                        numberOfRoom, typeOfRoom, ifBalcony, ifSwimmingPooView, ifSeaView);
    }

}


