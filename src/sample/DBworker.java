package sample;

import java.sql.*;

public class DBworker {
    private String URL = "jdbc:mysql://localhost:3306/whore_chat?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String usernameSQL = "root";
    private String passwordSQL = "";
    private Connection connectionToSQLbase;
    private Statement statement; //TODO: не забыть закрыть
    private ResultSet resultSet;
    private String idBackToRegisteredUser;

    public String readFromSQLwhenLogining(String loginFromClient, String passwordFromClient) {
        try {
            connectionToSQLbase = DriverManager.getConnection(URL, usernameSQL, passwordSQL);
            statement = connectionToSQLbase.createStatement();
            //если логин пасс найден возвращаем ид чувака иначе нал
            resultSet = statement.executeQuery("select id from users where nickname = " + loginFromClient +"and password = "+ passwordFromClient);
            while (resultSet.next()) {
                idBackToRegisteredUser = resultSet.getString("id");
            }
        } catch (SQLException e) {
            System.out.println("Can't connect to Database/Wrong SQL request");
        }
        if (idBackToRegisteredUser != null) {
            return idBackToRegisteredUser;
        } else return null;
    }

    public void writeToSQLwhenRegister(ClientData clientDataRegistrationStrings) {
        try {
            connectionToSQLbase = DriverManager.getConnection(URL, usernameSQL, passwordSQL);
            statement = connectionToSQLbase.createStatement();
            String regNickame = clientDataRegistrationStrings.getNickName();
            String regMail = clientDataRegistrationStrings.geteMail();
            String regPassword = clientDataRegistrationStrings.getPassword();
            resultSet = statement.executeQuery("insert into users (nickname,password,email) values ('"+regNickame+
                    " ', ' "+regMail+ " ', '"+regPassword+"')");
        } catch (SQLException e) {
            System.out.println("Can't connect to Database");
        }
    }


}


















