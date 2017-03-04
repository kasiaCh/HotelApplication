package GUIEmployee;

import DAO.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class EmployeeFrame extends JFrame{
    public JPanel panel;
    public JFrame frame = this;
    private JMenuBar menuBar;
    private Connector connector;
    private JMenu edit;
    private JMenu guests;
    private JMenu search;
    private JMenu reservations;
    private JTable ratingsTable;
    private JTable reservationsTable;
    private JPanel ratingsPanel;
    private JPanel reservationsPanel;
    private JButton refreshButton;
    private JButton obliczDochódButton;
    private JMenuItem rooms;
    private JMenuItem typesOfRooms;
    private JMenuItem standards;
    private JMenuItem services;
    private JMenuItem searchRoom;
    private JMenuItem searchGuest;
    private JMenuItem presentGuests;
    private JMenuItem allGuests;
    private JMenuItem presentReservations;
    private JMenuItem allReservations;
    private JScrollPane ratingsScrollPane;
    private JScrollPane reservationsScrollPane;

    public EmployeeFrame(Connector connector) throws SQLException {
        this.connector = connector;

        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setBounds(0,100,1600,600);
        setTitle("HotelApp");

        rooms = new JMenuItem("Pokoje");
        standards = new JMenuItem("Standardy");
        typesOfRooms = new JMenuItem("Rodzaje pokojów");
        services = new JMenuItem("Usługi");
        searchGuest = new JMenuItem("Gościa");
        searchRoom = new JMenuItem("Pokój");
        presentGuests = new JMenuItem("Aktualni");
        allGuests = new JMenuItem("Wszyscy");
        presentReservations = new JMenuItem("Aktualne");
        allReservations = new JMenuItem("Wszystkie");

        guests.add(presentGuests);
        guests.add(allGuests);

        reservations.add(presentReservations);
        reservations.add(allReservations);

        edit.add(rooms);
        edit.add(typesOfRooms);
        edit.add(standards);
        edit.add(services);

        search.add(searchGuest);
        search.add(searchRoom);

        rooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomsEditFrame roomsEditFrame = null;
                try {
                    roomsEditFrame = new RoomsEditFrame(connector);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                roomsEditFrame.setVisible(true);

            }
        });

        typesOfRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TypesOfRoomsEditFrame typesOfRoomsEditFrame = null;
                try {
                    typesOfRoomsEditFrame = new TypesOfRoomsEditFrame(connector);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                typesOfRoomsEditFrame.setVisible(true);
            }
        });

        standards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StandardsEditFrame standardsEditFrame = null;
                try {
                    standardsEditFrame = new StandardsEditFrame(connector);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                standardsEditFrame.setVisible(true);
            }
        });

        services.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServicesEditFrame servicesEditFrame = null;
                try {
                    servicesEditFrame = new ServicesEditFrame(connector);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                servicesEditFrame.setVisible(true);
            }
        });

        presentGuests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentGuestsFrame presentGuestFrame = null;
                try {
                    presentGuestFrame = new PresentGuestsFrame(connector);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                presentGuestFrame.setVisible(true);
            }
        });

        allGuests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllGuestsFrame allGuestsFrame = null;
                try {
                    allGuestsFrame = new AllGuestsFrame(connector);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                allGuestsFrame.setVisible(true);
            }
        });

        presentReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentReservationsFrame presentReservationsFrame = null;
                try {
                    presentReservationsFrame = new PresentReservationsFrame(connector);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                presentReservationsFrame.setVisible(true);
            }
        });

        allReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllReservationsFrame allReservationsFrame = null;
                try {
                    allReservationsFrame = new AllReservationsFrame(connector);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                allReservationsFrame.setVisible(true);
            }
        });

        searchRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchForRoomFrame searchForRoomFrame = new SearchForRoomFrame(connector);
                searchForRoomFrame.setVisible(true);
            }
        });

        searchGuest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchForGuestFrame searchForGuestFrame = new SearchForGuestFrame(connector);
                searchForGuestFrame.setVisible(true);
            }
        });

        List<Rating> ratings = connector.getAllRatings();
        List<Reservation> reservations = connector.getReservations("SELECT * FROM rezerwacje_na_dziś");

        RatingsTableModel ratingsTableModel = new RatingsTableModel(ratings);
        ReservationTableModel reservationTableModel = new ReservationTableModel(reservations);

        ratingsScrollPane = new JScrollPane();
        ratingsPanel.add(ratingsScrollPane, BorderLayout.CENTER);

        ratingsTable = new JTable();
        ratingsTable.setModel(ratingsTableModel);
        ratingsScrollPane.setViewportView(ratingsTable);

        reservationsScrollPane = new JScrollPane();
        reservationsPanel.add(reservationsScrollPane, BorderLayout.CENTER);
        reservationsTable = new JTable();
        reservationsTable.setModel(reservationTableModel);
        reservationsScrollPane.setViewportView(reservationsTable);


        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        obliczDochódButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CountIncomeDialog countIncomeDialog = new CountIncomeDialog(connector);
                countIncomeDialog.setVisible(true);
            }
        });
    }

    private void refresh() {

        try {
            List<Rating> ratings = connector.getAllRatings();

            // create the model and update the "table"
            RatingsTableModel model = new RatingsTableModel(ratings);

            ratingsTable.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
