package GUIEmployee;


public class Service {

    private int ID;
    private String service;
    private int price;
    private String description;

    public Service (int ID, String service, int price, String description){
        this.ID = ID;
        this.service = service;
        this.price = price;
        this.description = description;
    }

    public int getID() { return ID; }

    public String getService() {
        return service;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return String
                .format("Service [ID=%s, service=%s, price=%s, description=%s]",
                        ID, service, price, description);
    }

}
