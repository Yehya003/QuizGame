package application;


import application.model.Account;
import javafx.scene.control.Alert;

import java.sql.*;

public class DatabaseConnector {

    private String jdbc = "jdbc:mysql://";
    private String host = "den1.mysql5.gear.host";
    private String port = ":3306";
    private String databaseName = "/hkrquiz1";
    private String user = "?user";
    private String userValue = "=hkrquiz1";
    private String password = "&password";
    private String passwordValue = "=HKRQUIZ1!";
    private String databaseUrl = jdbc + host + port + databaseName + port + user + userValue + password + passwordValue;

    private Connection connection;

    public DatabaseConnector() throws SQLException {
        connection = DriverManager.getConnection(databaseUrl);
    }

    public void saveRegistration(String username, String password, String email, boolean is_admin) {

        String sql = "INSERT INTO hkrquiz1.user (username, password, email, is_admin) values (?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setBoolean(4, is_admin);

            int i = statement.executeUpdate();

            if (i > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Registration complete! ");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Registration not complete! ");
                alert.showAndWait();
            }

            connection.close();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error in access database ");
            alert.showAndWait();

        }
    }

    public void validateLogin(String username, String password) {

        String query = "SELECT count(*) FROM hkrquiz1.user WHERE username =? And password =?";
        int i = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    i = resultSet.getInt(1);
                }
            }

            if (i > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Login successful! ");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(" Wrong username or password ! ");
                alert.showAndWait();
            }

            connection.close();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error on fetch data from database ");
            alert.showAndWait();
        }
    }

    public void getRole(String username) {

        String roleQue = "SELECT * From hkrquiz1.user where username =?";

        try (PreparedStatement statement = connection.prepareStatement(roleQue)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    Account.getInstance().setUsername(resultSet.getNString("username"));
                    Account.getInstance().setPassword(resultSet.getNString("password"));
                    Account.getInstance().setEmail(resultSet.getNString("email"));
                    Account.getInstance().setAdmin(resultSet.getBoolean("is_admin"));
                }

                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error on fetch data from database ");
            alert.showAndWait();
        }

    }

    public void checkValidateEmail(String username) {

        String emailQue = "Select email, password from hkrquiz1.user where username =?";
        int x = 0;

        try (PreparedStatement statement = connection.prepareStatement(emailQue)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Account.getInstance().setEmail(resultSet.getString("email"));
                    Account.getInstance().setPassword(resultSet.getNString("password"));
                }

                /*if (x>0){

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("The email entered exist! ");
                    alert.showAndWait();
                }
                else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("The email entered not exist! ");
                    alert.showAndWait();
                }*/
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error on fetch data from database ");
            alert.showAndWait();
        }
    }
}
