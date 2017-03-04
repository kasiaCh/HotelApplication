package DAO;

import GUIEmployee.*;
import GUIGuest.Booking;

import javax.swing.*;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class Connector {

    private Connection connection;
    private CallableStatement callableStatement;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public Connector() throws Exception{
        Properties properties = new Properties();
        properties.load(new FileInputStream("hoteldatabase.properties"));

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String dburl = properties.getProperty("dburl");

        connection = DriverManager.getConnection(dburl,user,password);

        System.out.println("DB connection successful to: " + dburl);
    }

    public void addUser(Long login, String password, String name, String surname, String PESEL, String IDNumber) throws SQLException {
        callableStatement = connection.prepareCall("{call dodaj_uzytkownika(?,?,?,?,?,?)}");

        callableStatement.setString(1, String.valueOf(login));
        callableStatement.setString(2, password);
        callableStatement.setString(3, name);
        callableStatement.setString(4, surname);
        callableStatement.setString(5, PESEL);
        callableStatement.setString(6, IDNumber);

        callableStatement.execute();
    }

    public String logIn(String login, String password) throws SQLException{
        preparedStatement = connection.prepareStatement("SELECT `typ` FROM `uzytkownicy` WHERE `login`=? AND `haslo`=?");

        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);

        resultSet = preparedStatement.executeQuery();

        if(!resultSet.next()){
            return null;
        }
        else {
            return resultSet.getString(1);
        }
    }

    public String countIncome(java.sql.Date beginning, java.sql.Date end) throws SQLException{
        callableStatement = connection.prepareCall("{call oblicz_dochód_za_okres(?,?,?)}");
        callableStatement.setString(1, String.valueOf(beginning));
        callableStatement.setString(2, String.valueOf(end));
        callableStatement.registerOutParameter(3, Types.INTEGER);
        callableStatement.execute();
        return String.valueOf(callableStatement.getInt(3));
    }

    public List<Room> searchRooms(int numberOfRoom) throws SQLException {
        List<Room> list = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT * FROM pokoje WHERE nr_pokoju=?");
        preparedStatement.setInt(1, numberOfRoom);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Room tmpRoom = convertRowToRoom(resultSet);
            list.add(tmpRoom);
        }
        return list;
    }

    public List<Standard> searchStandards(int ID) throws SQLException {
        List<Standard> list = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT * FROM standard WHERE standard_ID=?");
        preparedStatement.setInt(1, ID);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Standard tmpStandard = convertRowToStandard(resultSet);
            list.add(tmpStandard);
        }
        return list;
    }

    public List<TypeOfRoom> searchTypesOfRooms(int ID) throws  SQLException {
        List<TypeOfRoom> list = new ArrayList<>();
        callableStatement = connection.prepareCall("{call szukaj_rodzaju(?)}");
        callableStatement.setInt(1, ID);

        callableStatement.execute();
        resultSet = callableStatement.getResultSet();
        TypeOfRoom tmpTypeOfRoom = null;

        if (resultSet.next()) {
            tmpTypeOfRoom = new TypeOfRoom(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getInt(5));
            list.add(tmpTypeOfRoom);
        }
        return list;
    }

    public List<Service> searchService(int ID) throws SQLException {
        List<Service> list = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM uslugi WHERE usluga_ID=?");
        preparedStatement.setInt(1, ID);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            GUIEmployee.Service tmpService = convertRowToService(resultSet);
            list.add(tmpService);
        }
        return list;
    }

    public List<Guest> searchGuest(String PESEL) throws SQLException {
        List<Guest> list = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM goscie WHERE PESEL=?");
        preparedStatement.setString(1, PESEL);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Guest tmpGuest = convertRowToGuest(resultSet);
            list.add(tmpGuest);
        }
        return list;
    }

    public List<Reservation> searchReservation(int ID, String query) throws SQLException {
        List<Reservation> list = new ArrayList<>();
        callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, ID);

        callableStatement.execute();
        resultSet = callableStatement.getResultSet();

        if (resultSet.next()) {
            Reservation tmpReservation = convertRowToReservation(resultSet);
            list.add(tmpReservation);
        }
        return list;
    }

    public List<Rating> getAllRatings() throws SQLException {
        List<Rating> list = new ArrayList<>();

        statement = null;
        resultSet = null;

        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM oceny_pokojów");

        while (resultSet.next()) {
            Rating tmpRating = convertRowToRating(resultSet);
            list.add(tmpRating);
        }

        return list;

    }

    public List<Reservation> getReservations(String query) throws SQLException {
        List<Reservation> list = new ArrayList<>();

        statement = null;
        resultSet = null;

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Reservation tmpReservation = convertRowToReservation(resultSet);
            list.add(tmpReservation);
        }

        return list;

    }

    public List<Room> getAllRooms() throws SQLException {
        List<Room> list = new ArrayList<>();

        statement = null;
        resultSet = null;

        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM widok_pokojow");

        while (resultSet.next()) {
            Room tmpRoom = convertRowToRoom(resultSet);
            list.add(tmpRoom);
        }

        return list;
    }

    public List<TypeOfRoom> getAllTypesOfRooms() throws SQLException {
        List<TypeOfRoom> list = new ArrayList<>();

        statement = null;
        resultSet = null;

        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM widok_rodzajów");

        while (resultSet.next()) {
            TypeOfRoom tmpTypeOfRoom = convertRowToTypeOfRoom(resultSet);
            list.add(tmpTypeOfRoom);
        }

        return list;
    }


    public List<Standard> getAllStandards() throws SQLException {
        List<Standard> list = new ArrayList<>();

        statement = null;
        resultSet = null;

        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM widok_standardów");

        while (resultSet.next()) {
            Standard tmpStandard = convertRowToStandard(resultSet);
            list.add(tmpStandard);
        }

        return list;
    }

    public List<Service> getAllServices() throws SQLException {
        List<Service> list = new ArrayList<>();

        statement = null;
        resultSet = null;

        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM widok_usług");

        while (resultSet.next()) {
            Service tmpService = convertRowToService(resultSet);
            list.add(tmpService);
        }

        return list;
    }

    public List<Guest> getGuests(String query) throws SQLException {
        List<Guest> list = new ArrayList<>();

        statement = null;
        resultSet = null;

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Guest tmpGuest = convertRowToGuest(resultSet);
            list.add(tmpGuest);
        }

        return list;
    }

    public String getNumberOfRoom(Long PESEL) throws SQLException {
        String numberOfRoom;
        callableStatement = connection.prepareCall("{call znajdź_pokój_osoby(?,?)}");

        callableStatement.setString(1, String.valueOf(PESEL));
        callableStatement.registerOutParameter(2, Types.INTEGER);
        callableStatement.execute();

        numberOfRoom = Integer.toString(callableStatement.getInt(2));
        return numberOfRoom;
    }

    public String getGuest(int numberOfRoom) throws SQLException {
        String guestName = null;
        String guestSurname = null;
        String guestPESEL = null;

        callableStatement = connection.prepareCall("{call znajdź_wynajmującego(?)}");

        callableStatement.setString(1, String.valueOf(numberOfRoom));
        callableStatement.execute();

        resultSet = callableStatement.getResultSet();

        while (resultSet.next()) {
            guestName = resultSet.getString(1);
            guestSurname = resultSet.getString(2);
            guestPESEL = resultSet.getString(3);
        }

        return guestName + " " + guestSurname + " " + guestPESEL;
    }

    public void deleteRoom(int numberOfRoom) throws SQLException {
        callableStatement = connection.prepareCall("{call usun_pokoj(?)}");
        callableStatement.setInt(1, numberOfRoom);
        callableStatement.execute();
    }

    public void deleteTypeOfRoom(int ID) throws SQLException {
        callableStatement = connection.prepareCall("{call usun_rodzaj(?)}");
        callableStatement.setInt(1, ID);
        callableStatement.execute();
    }

    public void deleteStandard(String standard) throws SQLException {
        callableStatement = connection.prepareCall("{call usun_standard(?)}");
        callableStatement.setString(1, standard);
        callableStatement.execute();
    }

    public void deleteService(String service) throws SQLException {
        callableStatement = connection.prepareCall("{call usun_usluge(?)}");
        callableStatement.setString(1, service);
        callableStatement.execute();
    }

    public void deleteReservation(int ID) throws SQLException {
        callableStatement = connection.prepareCall("{call usun_rezerwacje(?)}");
        callableStatement.setInt(1, ID);
        callableStatement.execute();
    }

    public void updateRoom(Room room) throws SQLException {
        callableStatement = connection.prepareCall("{call edytuj_pokoj(?,?,?,?,?)}");
        callableStatement.setInt(1, room.getNumberOfRoom());
        callableStatement.setInt(2, room.getTypeOfRoom());
        callableStatement.setBoolean(3, room.isIfBalcony());
        callableStatement.setBoolean(4, room.isIfSwimmingPooView());
        callableStatement.setBoolean(5, room.isIfSeaView());
        callableStatement.executeUpdate();
    }

    public void updateService(Service service) throws SQLException {
        callableStatement = connection.prepareCall("{call edytuj_usluge(?,?,?,?)}");
        callableStatement.setInt(1, service.getID());
        callableStatement.setString(2, service.getService());
        callableStatement.setInt(3, service.getPrice());
        callableStatement.setString(4, service.getDescription());
        callableStatement.executeUpdate();
    }

    public void updateTypeOfRoom(TypeOfRoom typeOfRoom) throws SQLException {
        callableStatement = connection.prepareCall("{call edytuj_rodzaj_pokoju(?,?,?,?,?)}");
        callableStatement.setInt(1, typeOfRoom.getID());
        callableStatement.setString(2, typeOfRoom.getStandard());
        callableStatement.setInt(3, typeOfRoom.getNumberOfPeople());
        callableStatement.setInt(4, typeOfRoom.getNumberOfBeds());
        callableStatement.setInt(5, typeOfRoom.getPrice());
        callableStatement.executeUpdate();
    }

    public void updateStandard(Standard standard) throws SQLException {
        callableStatement = connection.prepareCall("{call edytuj_standard(?,?,?,?,?,?,?,?,?,?)}");
        callableStatement.setInt(1, standard.getID());
        callableStatement.setString(2, standard.getStandard());
        callableStatement.setBoolean(3, standard.isIfTV());
        callableStatement.setBoolean(4, standard.isIfWIFI());
        callableStatement.setBoolean(5, standard.isIfAirConditioning());
        callableStatement.setBoolean(6, standard.isIfWakingUp());
        callableStatement.setBoolean(7, standard.isIfHairDryer());
        callableStatement.setBoolean(8, standard.isIfIron());
        callableStatement.setBoolean(9, standard.isIfCosmetics());
        callableStatement.setBoolean(10, standard.isIfSafe());
        callableStatement.executeUpdate();
    }

    public void updateGuest(Guest guest) throws SQLException {
        callableStatement = connection.prepareCall("{call edytuj_goscia(?,?,?,?,?)}");
        callableStatement.setString(1, guest.getLogin());
        callableStatement.setString(2, guest.getName());
        callableStatement.setString(3, guest.getSurname());
        callableStatement.setString(4, String.valueOf(guest.getPESEL()));
        callableStatement.setString(5, guest.getIDNumber());
        callableStatement.executeUpdate();
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        callableStatement = connection.prepareCall("{call edytuj_rezerwacje(?,?,?,?,?,?,?)}");
        callableStatement.setInt(1, reservation.getReservationID());
        callableStatement.setString(2, reservation.getLogin());
        callableStatement.setDate(3, (java.sql.Date) reservation.getBeginning());
        callableStatement.setDate(4, (java.sql.Date) reservation.getEnd());
        callableStatement.setInt(5, reservation.getNumberOfRoom());
        callableStatement.setBoolean(6, reservation.getIfCancelPossible());
        callableStatement.setString(7, reservation.getPayment());
        callableStatement.executeUpdate();

    }

    public void addRoom(Room room) throws SQLException {
        callableStatement = connection.prepareCall("{call dodaj_pokoj(?,?,?,?,?)}");
        callableStatement.setInt(1, room.getNumberOfRoom());
        callableStatement.setInt(2, room.getTypeOfRoom());
        callableStatement.setBoolean(3, room.isIfBalcony());
        callableStatement.setBoolean(4, room.isIfSwimmingPooView());
        callableStatement.setBoolean(5, room.isIfSeaView());
        callableStatement.executeUpdate();
    }

    public void addStandard(Standard standard) throws SQLException {
        callableStatement = connection.prepareCall("{call dodaj_standard(?,?,?,?,?,?,?,?,?)}");
        callableStatement.setString(1, standard.getStandard());
        callableStatement.setBoolean(2, standard.isIfTV());
        callableStatement.setBoolean(3, standard.isIfWIFI());
        callableStatement.setBoolean(4, standard.isIfAirConditioning());
        callableStatement.setBoolean(5, standard.isIfWakingUp());
        callableStatement.setBoolean(6, standard.isIfHairDryer());
        callableStatement.setBoolean(7, standard.isIfIron());
        callableStatement.setBoolean(8, standard.isIfCosmetics());
        callableStatement.setBoolean(9, standard.isIfSafe());
        callableStatement.executeUpdate();
    }

    public void addTypeOfRoom(TypeOfRoom typeOfRoom) throws SQLException {
        callableStatement = connection.prepareCall("{call dodaj_rodzaj(?,?,?,?)}");
        callableStatement.setString(1, typeOfRoom.getStandard());
        callableStatement.setInt(2, typeOfRoom.getNumberOfPeople());
        callableStatement.setInt(3, typeOfRoom.getNumberOfBeds());
        callableStatement.setInt(4, typeOfRoom.getPrice());
        callableStatement.executeUpdate();
    }

    public void addService(Service service) throws SQLException {
        callableStatement = connection.prepareCall("{call dodaj_usluge(?,?,?)}");
        callableStatement.setString(1, service.getService());
        callableStatement.setInt(2, service.getPrice());
        callableStatement.setString(3, service.getDescription());
        callableStatement.executeUpdate();
    }

    public void addGuest(Guest guest) throws SQLException {
        callableStatement = connection.prepareCall("{call dodaj_gościa(?,?,?,?,?)}");
        callableStatement.setString(1, guest.getLogin());
        callableStatement.setString(2, guest.getName());
        callableStatement.setString(3, guest.getSurname());
        callableStatement.setString(4, String.valueOf(guest.getPESEL()));
        callableStatement.setString(5, guest.getIDNumber());
        callableStatement.executeUpdate();
    }

    public void addServiceToTheGuest(String service, String login) throws SQLException {
        callableStatement = connection.prepareCall("{call dodaj_usluge_dla_goscia(?,?)}");
        callableStatement.setString(1, service);
        callableStatement.setString(2, login);
        callableStatement.execute();
    }

    private Guest convertRowToGuest(ResultSet resultSet) throws SQLException {

        String login = resultSet.getString("login");
        String name = resultSet.getString("imie");
        String surname = resultSet.getString("nazwisko");
        String PESEL = resultSet.getString("PESEL");
        String IDNumber = resultSet.getString("nr_dowodu");

        Guest tmpGuest = new Guest(login, name, surname, Long.valueOf(PESEL), IDNumber);

        return tmpGuest;

    }

    private Service convertRowToService(ResultSet resultSet) throws SQLException {

        int ID = resultSet.getInt("usluga_ID");
        String service = resultSet.getString("nazwa");
        int price = resultSet.getInt("cena");
        String description = resultSet.getString("opis");

        Service tmpService = new Service(ID, service, price, description);

        return tmpService;

    }

    private Standard convertRowToStandard(ResultSet resultSet) throws SQLException {

        int ID = resultSet.getInt("standard_ID");
        String standard = resultSet.getString("nazwa");
        boolean ifTV = resultSet.getBoolean("czy_TV");
        boolean ifWIFI = resultSet.getBoolean("czy_WIFI");
        boolean ifAirConditioning = resultSet.getBoolean("czy_klimatyzacja");
        boolean ifWakingUp = resultSet.getBoolean("czy_budzenie_na_życzenie");
        boolean ifHairDryer = resultSet.getBoolean("czy_suszarka_do_włosów");
        boolean ifIron = resultSet.getBoolean("czy_żelazko");
        boolean ifCosmetics = resultSet.getBoolean("czy_zestaw_kosmetyków");
        boolean ifSafe = resultSet.getBoolean("czy_sejf");

        Standard tmpStandard = new Standard(ID, standard, ifTV, ifWIFI, ifAirConditioning, ifWakingUp, ifHairDryer, ifIron, ifCosmetics, ifSafe);

        return tmpStandard;

    }

    private TypeOfRoom convertRowToTypeOfRoom(ResultSet resultSet) throws SQLException {

        int ID = resultSet.getInt("rodzaj_ID");
        String standard = resultSet.getString("nazwa");
        int numberOfPeople = resultSet.getInt("ilość_osób");
        int numberOfBeds = resultSet.getInt("ilość_łóżek");
        int price = resultSet.getInt("cena");

        TypeOfRoom tmpTypeOfRoom = new TypeOfRoom(ID, standard, numberOfPeople, numberOfBeds, price);

        return tmpTypeOfRoom;
    }

    private Room convertRowToRoom(ResultSet resultSet) throws SQLException {

        int numberOfRoom = resultSet.getInt("nr_pokoju");
        int typeOfRoom = resultSet.getInt("rodzaj_ID");
        boolean ifBalcony = resultSet.getBoolean("czy_balkon");
        boolean ifSwimmingPoolView = resultSet.getBoolean("czy_widok_na_basen");
        boolean ifSeaView = resultSet.getBoolean("czy_widok_na_morze");

        Room tmpRoom = new Room(numberOfRoom, typeOfRoom, ifBalcony, ifSwimmingPoolView, ifSeaView);

        return tmpRoom;
    }


    private Reservation convertRowToReservation(ResultSet resultSet) throws SQLException {

        int reservationID = resultSet.getInt("rezerwacja_ID");
        int numberOfRoom = resultSet.getInt("nr_pokoju");
        String guestLogin = resultSet.getString("login_goscia");
        String guestName = resultSet.getString("imie");
        String guestSurname = resultSet.getString("nazwisko");
        Date beginning = resultSet.getDate("poczatek");
        Date end = resultSet.getDate("koniec");
        Date date = resultSet.getDate("data_rezerwacji");
        boolean ifCancelPossible = resultSet.getBoolean("czy_możliwość_odwołania");
        String payment = resultSet.getString("metoda_platnosci");

        Reservation tmpReservation = new Reservation(reservationID, numberOfRoom, guestLogin, guestName, guestSurname, beginning, end, date, ifCancelPossible, payment);

        return tmpReservation;
    }


    private Rating convertRowToRating(ResultSet resultSet) throws SQLException {

        int numberOfRoom = resultSet.getInt("nr_pokoju");
        String guestName = resultSet.getString("imie");
        String guestSurname = resultSet.getString("nazwisko");
        int rating  = resultSet.getInt("ocena");
        double AVGRating = resultSet.getDouble("średnia_ocena");

        Rating tmpRating = new Rating(numberOfRoom, guestName, guestSurname, rating, AVGRating);

        return tmpRating;
    }

    public ArrayList<GUIGuest.Room> searchForRooms(String sort, String standard, String  numberOfPeople, String numberOfBeds, boolean ifBalcony,
                                          boolean ifSeaView, String arrivalDate, String checkOutDate)  {

        ArrayList<GUIGuest.Room> rooms = new ArrayList<>();
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String sortCall = "";
        String standardCall = "";
        String numberOfPeopleCall = "";
        String numberOfBedsCall = "";
        String additionalFeaturesCall = "";
        String  arrivalDateCall = arrivalDate;
        System.out.println(arrivalDateCall);
        String checkOutDateCall = checkOutDate;
        System.out.println(checkOutDateCall);

        switch (sort){
            case "średniej oceny gości":
                sortCall = "Ocena DESC, ilość_rezerwacji DESC";
                break;
            case "popularności":
                sortCall = "ilość_rezerwacji DESC, Ocena DESC";
                break;

        }

        switch (standard){
            case "Standard":
                standardCall = "standard.nazwa = 'Standard'";
                break;
            case "Comfort":
                standardCall = "standard.nazwa = 'Comfort'";
                break;
            case "Superior":
                standardCall = "standard.nazwa = 'Superior'";
        }

        switch (numberOfPeople){
            case "1":
                numberOfPeopleCall = "rodzaj_pokoju.ilość_osób = 1";
                break;
            case "2":
                numberOfPeopleCall = "rodzaj_pokoju.ilość_osób = 2";
                break;
            case "3":
                numberOfPeopleCall = "rodzaj_pokoju.ilość_osób = 3";

        }

        switch (numberOfBeds){
            case "1":
                numberOfBedsCall = "rodzaj_pokoju.ilość_łóżek = 1";
                break;
            case "2":
                numberOfBedsCall = "rodzaj_pokoju.ilość_łóżek = 2";
                break;
            case "3":
                numberOfBedsCall = "rodzaj_pokoju.ilość_łóżek = 3";
                break;
        }

        if(ifBalcony && ifSeaView){
            additionalFeaturesCall = "pokoje.czy_balkon = TRUE AND" +
                    " pokoje.czy_widok_na_morze = TRUE";
        }
        else if (!ifBalcony && ifSeaView){
            additionalFeaturesCall = "pokoje.czy_balkon = FALSE AND" +
                    " pokoje.czy_widok_na_morze = TRUE";
        }
        else if (!ifBalcony && !ifSeaView){
            additionalFeaturesCall = "((pokoje.czy_balkon = FALSE OR pokoje.czy_balkon = TRUE) OR" +
                    " (pokoje.czy_widok_na_morze = FALSE OR pokoje.czy_widok_na_morze = TRUE))";
        }
        else if (ifBalcony && !ifSeaView){
            additionalFeaturesCall = "pokoje.czy_balkon = TRUE AND" +
                    " pokoje.czy_widok_na_morze = FALSE";
        }


        try {
            callableStatement = connection.prepareCall("{call wyszukaj_pokoje(?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.setString(1, sortCall);
            callableStatement.setString(2, standardCall);
            callableStatement.setString(3, numberOfPeopleCall);
            callableStatement.setString(4, numberOfBedsCall);
            callableStatement.setString(5, additionalFeaturesCall);
            callableStatement.setString(6, arrivalDateCall);
            callableStatement.setString(7,checkOutDateCall);

            callableStatement.execute();

            resultSet = callableStatement.getResultSet();

            while(resultSet.next()){
                GUIGuest.Room room = convertRowToRoomGuest(resultSet);
                rooms.add(room);
            }



        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            //close(connection, callableStatement, resultSet);
        }
        return rooms;

    }

    private GUIGuest.Room convertRowToRoomGuest(ResultSet resultSet) throws SQLException {
        int roomNr = resultSet.getInt("pokoje.nr_pokoju");
        String standard = resultSet.getString("standard.nazwa");
        int numberOfPeople = resultSet.getInt("rodzaj_pokoju.ilość_osób");
        int numberOfBeds = resultSet.getInt("rodzaj_pokoju.ilość_łóżek");
        int price = resultSet.getInt("rodzaj_pokoju.cena");
        String ifBalcony = resultSet.getString("pokoje.czy_balkon");
        String ifSeaView = resultSet.getString("pokoje.czy_widok_na_morze");
        double guestRating = resultSet.getDouble("Ocena");
        int numberOfReservations = resultSet.getInt("ilość_rezerwacji");

        switch (ifBalcony){
            case "1":
                ifBalcony = "*";
                break;
            case "0":
                ifBalcony = "";
        }

        switch (ifSeaView){
            case "1":
                ifSeaView = "*";
                break;
            case "0":
                ifSeaView = "";
        }

        GUIGuest.Room tempRoom = new GUIGuest.Room(roomNr,standard,numberOfPeople,numberOfBeds,price,ifBalcony,ifSeaView,guestRating,numberOfReservations);

        return tempRoom;
    }

    public ArrayList<Booking> searchForBookings(String login){
        ArrayList<Booking> bookings = new ArrayList<>();
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call zobacz_rezerwacje_gościa(?)}");
            callableStatement.setString(1, login);

            callableStatement.execute();

            resultSet = callableStatement.getResultSet();

            while(resultSet.next()){
                Booking booking = convertRowToBooking(resultSet);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;

    }

    private Booking convertRowToBooking(ResultSet resultSet) throws SQLException {
        int bookingID = resultSet.getInt("rezerwacje.rezerwacja_ID");
        String bookingDate = resultSet.getString("rezerwacje.data_rezerwacji");
        String startDate = resultSet.getString("rezerwacje.poczatek");
        String endDate = resultSet.getString("rezerwacje.koniec");
        int roomNr = resultSet.getInt("rezerwacje.nr_pokoju");
        String standard = resultSet.getString("standard.nazwa");
        int numberOfPeople = resultSet.getInt("rodzaj_pokoju.ilość_osób");
        int numberOfBeds = resultSet.getInt("rodzaj_pokoju.ilość_łóżek");
        String ifPossibilityOfCancellation = resultSet.getString("rezerwacje.czy_możliwość_odwołania");
        int amountForRoom = resultSet.getInt("platnosci.kwota_za_pokój");
        int amountForServices = resultSet.getInt("platnosci.kwota_za_usługi");
        String  mark = resultSet.getString("oceny.ocena");


        if(mark == null){
            mark = "Brak oceny";
        }

        Booking tempBooking = new Booking(bookingID, bookingDate, startDate, endDate, roomNr, standard, numberOfPeople, numberOfBeds,
                ifPossibilityOfCancellation, amountForRoom, amountForServices, mark);

        return tempBooking;
    }

    public ArrayList<GUIGuest.Service> searchForServices(int bookingID){
        ArrayList<GUIGuest.Service> services = new ArrayList<>();
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call pokaż_wykorzystane_usługi(?)}");
            callableStatement.setInt(1, bookingID );

            callableStatement.execute();

            resultSet = callableStatement.getResultSet();

            while(resultSet.next()){
                GUIGuest.Service service = convertRowToServiceGuest(resultSet);
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    private GUIGuest.Service convertRowToServiceGuest(ResultSet resultSet) throws SQLException{
        String name = resultSet.getString("uslugi.nazwa");
        int price = resultSet.getInt("uslugi.cena");
        String description = resultSet.getString("uslugi.opis");

        GUIGuest.Service tempService = new GUIGuest.Service(name,price,description);

        return tempService;
    }

    public void giveMark( int bookingID, double mark){
        CallableStatement callableStatement = null;


        try {
            callableStatement = connection.prepareCall("{call oceń_pokój(?,?)}");
            callableStatement.setDouble(1, mark );
            callableStatement.setInt(2,bookingID);

            callableStatement.execute();

            JOptionPane.showMessageDialog(null,"Dziękujemy za wystawienie oceny!");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void cancelReservation( int bookingID)  {
        CallableStatement callableStatement = null;
        String message;


        try {
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall("{call odwołaj_rezerwację(?,?)}");
            callableStatement.setInt(1, bookingID );
            callableStatement.registerOutParameter(2, Types.BOOLEAN);

            callableStatement.execute();

            boolean toCancel = callableStatement.getBoolean(2);

            if(toCancel){
                message = "Czy na pewno chcesz odwołać rezerwację?";
            }
            else {
                message = "Czy na pewno chcesz odwołać rezerwację? \nNiestety nie dostaniesz zwrotu pieniędzy, \nponieważ " +
                        "rezerwacja nie posiada statusu\n 'do odwołania'";
            }

            int reply = JOptionPane.showConfirmDialog(null, message, null, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                connection.commit();
                JOptionPane.showMessageDialog(null, "Usunięto rezerwację!");

            }
            else {
                connection.rollback();
                JOptionPane.showMessageDialog(null, "Anulowano usuwanie rezerwacji!");
            }


        } catch (SQLException e) {
            if(e.getMessage().startsWith("Rezerwacja nieaktualna!")){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            else {
                e.printStackTrace();
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void book(String login, String arrivalDate, String checkOutDate, int roomNr, String paymentMethod,
                     boolean possibilityOfCancellation){

        CallableStatement callableStatement = null;


        try {
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall("{call dodaj_rezerwacje(?,?,?,?,?,?)}");
            callableStatement.setString(1, login );
            callableStatement.setInt(2, roomNr);
            callableStatement.setString(3, arrivalDate);
            callableStatement.setString(4, checkOutDate);
            callableStatement.setBoolean(5, possibilityOfCancellation);
            callableStatement.setString(6, paymentMethod);

            callableStatement.execute();

            int reply = JOptionPane.showConfirmDialog(null, "Potwierdzasz rezerwację?", null, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                connection.commit();
                JOptionPane.showMessageDialog(null,"Rezerwacja przebiegła pomyślnie!");
            }
            else {
                connection.rollback();
                JOptionPane.showMessageDialog(null, "Anulowano rezerwację!");
            }

        } catch (SQLException e) {
            if(e.getMessage().startsWith("Pokój został właśnie zajęty!")){
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            else{e.printStackTrace();}
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(Connection myConn, Statement myStmt,
                              ResultSet myRs) throws SQLException {
        if (myRs != null) {
            myRs.close();
        }

        if (myStmt != null) {
            myStmt.close();
        }

        if (myConn != null) {
            myConn.close();
        }
    }
}