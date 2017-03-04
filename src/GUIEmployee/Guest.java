package GUIEmployee;


public class Guest {

    private String login;
    private String name;
    private String surname;
    private Long PESEL;
    private String IDNumber;

    public Guest (String login, String name, String surname, Long PESEL, String IDNumber){
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        this.IDNumber = IDNumber;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getPESEL() {
        return PESEL;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPESEL(Long PESEL) {
        this.PESEL = PESEL;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    @Override
    public String toString(){
        return String
                .format("Guest [login=%s, name=%s, surname=%s, PESEL=%s, IDNumber=%s]",
                        login, name, surname, PESEL, IDNumber);
    }


}