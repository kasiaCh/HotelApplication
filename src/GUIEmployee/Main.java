package GUIEmployee;

import DAO.Connector;


public class Main {

    public static void main (String[] args) throws Exception {
        Connector connector = new Connector();
        LogInFrame logInFrame = new LogInFrame(connector);
        logInFrame.setVisible(true);
    }
}
