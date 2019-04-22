package sample;



import javafx.scene.control.Alert;
import sample.Model.Account;

import java.sql.*;

public class DBConnection {

    private String url1 = "jdbc:mysql://den1.mysql3.gear.host:3306/hkrquiz1?user=hkrquiz1&password=HKRQUIZ1!";
    Connection connection ;


    public DBConnection () throws SQLException {

        connection = DriverManager.getConnection(url1);
    }


    public void saveRegistration (String username, String password, String email, boolean is_admin) {

        String sql = "INSERT INTO hkrquiz1.user (username, password, email, is_admin) values (?,?,?,?)";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,username);
            statement.setString(2,password);
            statement.setString(3,email);
            statement.setBoolean(4,is_admin);

            int i = statement.executeUpdate();

            if (i>0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Registration complete! ");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Registration not complete! ");
                alert.showAndWait();
            }

            connection.close();

        }catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error in access database ");
            alert.showAndWait();

        }
    }

    


}
