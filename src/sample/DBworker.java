package sample;

import java.sql.*;

public class DBworker {
    private String URL = "jdbc:mysql://localhost:3306/whore_chat?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String usernameSQL = "root";
    private String passwordSQL = "";
    Connection connectionToSQLbase;
    Statement statement; //TODO: не забыть закрыть

    public DBworker() {
        try {
            connectionToSQLbase = DriverManager.getConnection(URL, usernameSQL, passwordSQL);
            statement = connectionToSQLbase.createStatement();
        } catch (SQLException e) {
            System.out.println("Can't connect to Database");
        }
    }
    
    public String readFromSQLwhenLogining(String loginFromClient, String passwordFromClient) {
        return null;
    }
    
    public void writeToSQLwhenRegister(ClientData clientDataRegistrationStrings){
        
    }

    public Connection getConnectionToSQLbase() {
        return connectionToSQLbase;
    }
}
