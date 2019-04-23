package sample;


import javafx.scene.control.Alert;
import sample.Model.Account;
import sample.Model.Question;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

    private String url1 = "jdbc:mysql://den1.mysql5.gear.host:3306/hkrquiz1?user=hkrquiz1&password=HKRQUIZ1!";
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

    public void validateLogin(String username, String password) {

        String query = "SELECT count(*) FROM hkrquiz1.user WHERE username =? And password =?";
        int i= 0;

        try (PreparedStatement statement = connection.prepareStatement(query)){


            statement.setString(1,username);
            statement.setString(2,password);

            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    i = resultSet.getInt(1);
                }
            }


            if (i>0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Login successful! ");
                alert.showAndWait();
            }
            else {
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

    public void getRole (String username){

        String roleQue = "SELECT * From hkrquiz1.user where username =?";

        try (PreparedStatement statement = connection.prepareStatement(roleQue)){
            statement.setString(1,username);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){


                    Account.getInstance().setUsername(resultSet.getNString("username"));
                    Account.getInstance().setPassword(resultSet.getNString("password"));
                    Account.getInstance().setEmail(resultSet.getNString("email"));
                    Account.getInstance().setIs_admin(resultSet.getBoolean("is_admin"));
                }

                connection.close();
            }

        }catch (SQLException ex){
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error on fetch data from database ");
            alert.showAndWait();
        }

    }

    public void checkValidateEmail (String username){

        String emailQue = "Select email, password from hkrquiz1.user where username =?";
        int x = 0;

        try(PreparedStatement statement = connection.prepareStatement(emailQue)){
            statement.setString(1,username);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
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



    public ArrayList<Question> QuizFill(String category) {
        ArrayList<Question> questions = new ArrayList<>();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from question where category = '" + category + "';");
                while (resultSet.next()) {
                    String question = resultSet.getString("question");
                    String difficulty = resultSet.getString("difficulty");
                    String answer = resultSet.getString("answer");
                    String incorrect_answer1 = resultSet.getString("incorrect_answer1");
                    String incorrect_answer2 = resultSet.getString("incorrect_answer2");
                    String incorrect_answer3 = resultSet.getString("incorrect_answer3");

                    questions.add(new Question(category, difficulty, question, answer, incorrect_answer1, incorrect_answer2, incorrect_answer3));

                }
                return questions;
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Could not fetch quiz ");
                alert.showAndWait();
            }
        }
        return null;
    }



}
