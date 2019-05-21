package application;

import application.controller.LoginController;
import application.model.Account;
import application.model.Question;
import application.model.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class DatabaseConnector {

    //Syntax https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
    private String protocol = "jdbc:mysql://";
    private String host = "den1.mysql5.gear.host";
    private String port = ":3306";
    private String databaseName = "/hkrquiz1";
    private String user = "?user";
    private String userValue = "=hkrquiz1";
    private String password = "&password";
    private String passwordValue = "=HKRQUIZ1!";
    private String databaseUrl = protocol + host + port + databaseName + user + userValue + password + passwordValue;
    private String loggedInUser;

    private Connection theConnection;

    public DatabaseConnector() {
        try {
            theConnection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException sqlException) {
            System.out.println("Error on connecting to database");
            System.out.println("Error code is:");
            sqlException.getErrorCode();
            System.out.println("Print stack trace is:");
            sqlException.printStackTrace();
        }
    }

    public void saveRegistration(String username, String password, String email, boolean is_admin) {

        String sql = "INSERT INTO hkrquiz1.user (username, password, email, is_admin) values (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
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
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error in access database ");
            alert.showAndWait();
        }
    }

    public boolean validateLogin(String username, String password) {
        String query = "SELECT count(*) FROM hkrquiz1.user WHERE username =? And password =?";
        int i = 0;

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        i = resultSet.getInt(1);
                    }
                }

                if (i > 0) {
                    setAccountSingleton(username);
                    if (CurrentAccountSingleton.getInstance().getAccount().isAdmin()) {
                        StageManager.getInstance().getAdminScene();
                    } else {
                        StageManager.getInstance().getMainMenu();
                    }
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(" Wrong username or password ! ");
                    alert.showAndWait();
                }
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error on fetch data from database ");
            alert.showAndWait();
        }
        return false;
    }

    public void setAccountSingleton(String username) {
        String roleQue = "SELECT * From hkrquiz1.user where username =?";

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (PreparedStatement statement = connection.prepareStatement(roleQue)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Account newAccount = new Account();
                        newAccount.setUsername(resultSet.getNString("username"));
                        newAccount.setPassword(resultSet.getNString("password"));
                        newAccount.setEmail(resultSet.getNString("email"));
                        newAccount.setAdmin(resultSet.getBoolean("is_admin"));
                        CurrentAccountSingleton.getInstance().setAccount(newAccount);
                    }
                }
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

        try (PreparedStatement statement = theConnection.prepareStatement(emailQue)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CurrentAccountSingleton.getInstance().getAccount().setEmail(resultSet.getString("email"));
                    CurrentAccountSingleton.getInstance().getAccount().setPassword(resultSet.getNString("password"));
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
                theConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error on fetch data from database ");
            alert.showAndWait();
        }
    }

    public void updateDatabase(String updateQuery) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Question> getQuestionsFromDB(String category, String quizDifficulty) {
        ArrayList<Question> questions = new ArrayList<>();
        String queryString = "select * from (select * from question where category = '" + category +
                "') as questions where difficulty = '" + quizDifficulty + "' || difficulty = 'medium';";
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(queryString)) {
                    while (resultSet.next()) {
                        int question_id = resultSet.getInt("question_id");
                        String question = resultSet.getString("question");
                        String questionDifficulty = resultSet.getString("difficulty");
                        String answer = resultSet.getString("answer");
                        String incorrect_answer1 = resultSet.getString("incorrect_answer1");
                        String incorrect_answer2 = resultSet.getString("incorrect_answer2");
                        String incorrect_answer3 = resultSet.getString("incorrect_answer3");
                        questions.add(new Question(question_id, category, questionDifficulty, question, answer, incorrect_answer1, incorrect_answer2, incorrect_answer3));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not fetch quiz ");
            alert.showAndWait();
        }
        return questions;
    }

    public boolean insertQuiz(Quiz quiz) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            //Since id is not auto-increment, will have to make an extra query
            Statement idStatement = connection.createStatement();
            ResultSet resultSet = idStatement.executeQuery("select max(quiz_Id) as quiz_id from quiz;");
            int quizId = 0;
            if (resultSet.next()) {
                quizId = resultSet.getInt("quiz_id");
            }
            if (quizId == 0) {
                //If the query fails for any reason, put a random id to id, to avoid duplication of PK
                //This will most likely never run anyway
                quizId = new Random().nextInt(99999999) + 1000;
            }

            //TODO change all insert queries to look like this (prepared statement with ? and this formatting)
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT " +
                    "INTO quiz " +
                    "(quiz_Id, category, score, duration, date, user_username) " +
                    "VALUES " +
                    "( ? , ? , ? , ? , ? , ? )");

            statement.setInt(1, quizId + 1);
            statement.setString(2, quiz.getCategory());
            statement.setInt(3, quiz.getScore());
            statement.setLong(4, quiz.getDuration());
            statement.setObject(5, LocalDate.now());
            statement.setString(6, quiz.getUserName());

            statement.executeUpdate();
            System.out.println("Quiz inserted: " + quiz);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        boolean ok = checkOldPassword(username, oldPassword);
        boolean done = false;
        String theStatement = "update hkrquiz1.user set password =? where username =?";
        if (ok) {
            try (Connection connection = DriverManager.getConnection(databaseUrl)) {
                try (PreparedStatement myStatement = connection.prepareStatement(theStatement)) {
                    myStatement.setString(1, newPassword);
                    myStatement.setString(2, username);
                    myStatement.execute();
                    done = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return done;
    }

    public boolean checkOldPassword(String username, String oldPassword) {
        String validatePassword = null;
        String sqlStatement = "select password from user where username =?";
        try (PreparedStatement statement = theConnection.prepareStatement(sqlStatement)) {
            statement.setString(1, username);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    validatePassword = result.getString("password");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (validatePassword.equals(oldPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfUserAlreadyExists(String username) {
        String label = "quantity";
        String checkUsernameQuery = "SELECT COUNT(*) " + label + " FROM user WHERE username = '" + username + "'";
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (PreparedStatement statement = connection.prepareStatement(checkUsernameQuery)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(label) >= 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public ObservableList getTheHighestScores() {
        //To work with tableView we will need an ObservableList<The object>
        ObservableList<Quiz> highScoreList = FXCollections.observableArrayList();
        //Here we do the query on the database
        String query = "SELECT user_username,score,category,duration FROM " +
                "hkrquiz1.quiz group by category order by score desc limit 10";
        //Here we instantiate the preparedStatement to execute the query we also call the variable theConnection
        //that is assigned in the databaseConnector() above and call the .prepareStatement(Give it the query string)
        try (PreparedStatement statement = theConnection.prepareStatement(query)) {
            //executing the query by using the resultSet
            try (ResultSet resultSet = statement.executeQuery()) {
                //putting it on a loop to go through the whole database and execute
                while (resultSet.next()) {
                    //adding the result to the ObservableList in the form of a quiz object
                    //using the resultSet.getString(giving it the name of the column in the database) to populate the quiz object
                    highScoreList.add(new Quiz(resultSet.getNString("user_username"), resultSet.getInt("score"),
                            resultSet.getNString("category"), resultSet.getInt("duration")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highScoreList;
    }
    public ObservableList getPlayedCategoriesRatio(){
        String query="select category,count(*) from quiz where user_username=? group by category";
        ObservableList<PieChart.Data> myPieChart = FXCollections.observableArrayList();
        try(PreparedStatement statement = theConnection.prepareStatement(query)){
            statement.setString(1,StageManager.getInstance().getUsername());
            try(ResultSet set = statement.executeQuery()){
                while(set.next()){
                    myPieChart.add(new PieChart.Data(set.getString(1),Double.valueOf(set.getString(2))));
                }
            }
        }catch(Exception e){e.printStackTrace();}
        return myPieChart;
    }
    public ObservableList smartFilterData(String from ,String to,boolean top10){
        ObservableList<Quiz> filtered = FXCollections.observableArrayList();
        String query;
        if (top10){
            query="SELECT distinct user_username,score,category,duration FROM hkrquiz1.quiz where score >=? and score <=? limit 10";

        }else{
            query="SELECT user_username,score,category,duration FROM hkrquiz1.quiz where score >=? and score <=?";
        }

        try (PreparedStatement statement = theConnection.prepareStatement(query)) {
            statement.setString(1,from);
            statement.setString(2,to);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    filtered.add(new Quiz(resultSet.getNString("user_username"), resultSet.getInt("score"),
                            resultSet.getNString("category"), resultSet.getInt("duration")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filtered;
    }

    //this method is designed to retrieve Score data from the database and adds it to XYChart.series to be further used for statistics
    public XYChart.Series<String,Integer> getDataForStatistics() {
      // I create a new object of the XYChart.series<Specifying the data which will be entered from the database columns>
        XYChart.Series<String,Integer> myChart = new XYChart.Series<>();
        //querying the database
        String query = "select category,score from quiz where user_username=? order by score";
        try (PreparedStatement statement = theConnection.prepareStatement(query)) {
           //setting the preparedStatements input when we use ? we need to specify where it will read from;
            statement.setString(1,StageManager.getInstance().getUsername());
            try (ResultSet result = statement.executeQuery()) {
                //looping through the database to check all rows
                while (result.next()) {
                    //adding the matching results with the previously written query to the XYChart.Series
                    myChart.getData().add(new XYChart.Data<>(result.getNString("category"),result.getInt("score")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Returning the chart after filling it if the conditions in the query match
        return myChart;
    }


    public ArrayList<String> getUniqueDifficultyList() {
        ArrayList<String> categories = new ArrayList<>();
        String categoryQuery = "SELECT DISTINCT difficulty FROM question";
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (PreparedStatement statement = connection.prepareStatement(categoryQuery)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        categories.add(resultSet.getNString("difficulty"));
                    }
                }
                return categories;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getUniqueCategoryList() {
        ArrayList<String> categories = new ArrayList<>();

        String categoryQuery = "SELECT DISTINCT category FROM question";
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (PreparedStatement statement = connection.prepareStatement(categoryQuery)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        categories.add(resultSet.getNString("category"));
                    }
                }
            }
            return categories;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Question getAQuestion(String query) {
        Question question = new Question();
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        question.setQuestion_id(resultSet.getInt("question_id"));
                        question.setQuestion(resultSet.getNString("question"));
                        question.setCategory(resultSet.getNString("category"));
                        question.setDifficulty(resultSet.getNString("difficulty"));
                        question.setAnswer(resultSet.getNString("answer"));
                        question.setIncorrect_answer1(resultSet.getNString("incorrect_answer1"));
                        question.setIncorrect_answer2(resultSet.getNString("incorrect_answer2"));
                        question.setIncorrect_answer3(resultSet.getNString("incorrect_answer3"));
                        return question;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return question;
    }

    public void addQuestion(String query) {
        System.out.println("Executing query: " + query);
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        String categoryQuery = "SELECT * FROM question";
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            PreparedStatement statement = connection.prepareStatement(categoryQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(readQuestionFromResultSet(resultSet));
            }
            return questions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Question> getAllQuestionsFromCategory(String category) {
        ArrayList<Question> questions = new ArrayList<>();

        String categoryQuery = "SELECT * FROM question WHERE category = ?";
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            PreparedStatement statement = connection.prepareStatement(categoryQuery);
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(readQuestionFromResultSet(resultSet));
            }
            return questions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Question readQuestionFromResultSet(ResultSet resultSet) throws SQLException {
        Question question = new Question();
        question.setQuestion(resultSet.getNString("question"));
        question.setCategory(resultSet.getNString("category"));
        question.setDifficulty(resultSet.getNString("difficulty"));
        question.setAnswer(resultSet.getNString("answer"));
        question.setIncorrect_answer1(resultSet.getNString("incorrect_answer1"));
        question.setIncorrect_answer2(resultSet.getNString("incorrect_answer2"));
        question.setIncorrect_answer3(resultSet.getNString("incorrect_answer3"));
        return question;
    }
}

