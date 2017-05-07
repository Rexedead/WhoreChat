package sample.Server;

import java.sql.*;
import sample.ClientData;

public class DBworker {
    private Statement statement;
    private ResultSet resultSet;
    private String idBackToRegisteredUser;


    public DBworker() {
        try {
            String URL = "jdbc:mysql://localhost:3306/whore_chat?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
                    "&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String usernameSQL = "root";
            String passwordSQL = "";
            Connection connectionToSQLbase = DriverManager.getConnection(URL, usernameSQL, passwordSQL);
            statement = connectionToSQLbase.createStatement();
        } catch (SQLException e) {
            System.out.println("Can't connect to Database");
        }
    }


    public String readFromSQLwhenLogining(String loginFromClient, String passwordFromClient) {

        try {
            resultSet = statement.executeQuery("select id from users where " +
                    "(nickname = '" + loginFromClient + "' and password = '" + passwordFromClient + "')");
            while (resultSet.next()) {
                idBackToRegisteredUser = resultSet.getString("id");
            }
            resultSet.close();
        } catch (SQLException e1) {
            System.out.println("Wrong SQL request");
        }

        if (idBackToRegisteredUser != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Cant close statement");
            }
            return idBackToRegisteredUser;

        } else return null;
    }

    public void writeToSQLwhenRegister(ClientData clientDataRegistrationStrings) {
        try {
            String regNickame = clientDataRegistrationStrings.getNickName();
            String regMail = clientDataRegistrationStrings.getMail();
            String regPassword = clientDataRegistrationStrings.getPassword();
            statement.execute(    "insert into users (nickname, password, email ) values ('"+regNickame+"','"+regPassword+"','"+regMail+"')"   );
            statement.close();
        } catch (SQLException e) {
            System.out.println("Wrong SQL request");
        }
    }


}


















