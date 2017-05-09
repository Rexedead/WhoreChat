package sample.Server;

import sample.ClientData;

import java.sql.*;

public class DBworker {
    private Statement statement;
    private ResultSet resultSet;
    private String idReturnForNewUser;
    private String checkDuplId;
    private String checkDuplMail;

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
                idReturnForNewUser = resultSet.getString("id");
            }
            resultSet.close();
        } catch (SQLException e1) {
            System.out.println("Wrong SQL request");
        }

        if (idReturnForNewUser != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Cant close statement");
            }
            return idReturnForNewUser;

        } else return "invalid";
    }

    public String writeToSQLwhenRegister(ClientData clientDataRegistrationStrings) {

        String regNickame = clientDataRegistrationStrings.getNickName();
        String regMail = clientDataRegistrationStrings.getMail();
        String regPassword = clientDataRegistrationStrings.getPassword();

        try {
            if (checkNewRegisterNickname(regNickame) && checkNewRegisterMail(regMail)) {
                statement.execute("insert into users (nickname, password, email ) values" +
                        " ('" + regNickame + "','" + regPassword + "','" + regMail + "')");
                resultSet = statement.executeQuery("select id from users where " +
                        "(nickname = '" + clientDataRegistrationStrings.getNickName() + "' " +
                        "and password = '" + clientDataRegistrationStrings.getPassword() + "')");
                while (resultSet.next()) {
                    idReturnForNewUser = resultSet.getString("id");
                }
            } else {
                statement.close();
                return checkDuplId == null  ?  "nickname exist" : "email exist";
            }

        } catch (SQLException e) {
            System.out.println("Cant close statement");
        }

        if (idReturnForNewUser != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Cant close statement");
            }
            return idReturnForNewUser;

        } else return null;
    }


    private boolean checkNewRegisterMail(String regMail) {
        try {
            resultSet = statement.executeQuery("select email from users where " +
                    "(email = '" + regMail + "')");
            while (resultSet.next()) {
                checkDuplMail = resultSet.getString("email");
            }
        } catch (SQLException e) {
            System.out.println("Wrong SQL request in checkNewRegisterMail");
        }

        return checkDuplMail == null;
    }


    private boolean checkNewRegisterNickname(String regNickame) {

        try {
            resultSet = statement.executeQuery("select nickname from users where " +
                    "(nickname = '" + regNickame + "')");
            while (resultSet.next()) {
                checkDuplId = resultSet.getString("nickname");
            }
        } catch (SQLException e) {
            System.out.println("Wrong SQL request in checkNewRegisterNickname");
        }

        return checkDuplId == null;
    }


    public Statement getStatement() {
        return statement;
    }
}
























