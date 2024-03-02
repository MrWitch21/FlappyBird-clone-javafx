package mszzsm.floppybird.controllers.share;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MysqlConnect {
    private Connection connection = null;
    private Statement statement;

    private final ObservableList<users> usersList = FXCollections.observableArrayList();

    public ObservableList<users> getUsersList() {
        return usersList;
    }
    //Adatbázissal létrehozza a kapcsolatot
    public MysqlConnect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/floppy_user_db", "root", "");
            statement = connection.createStatement();
            System.out.print("Database Connected");
        } catch (SQLException e) {
            System.out.print("Database Not Connected");
            e.printStackTrace();
        }
    }
    //bezárja az adatbázissal a kapcsolatot
    public void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Lekérdezi a top 10 legjobban teljesítő játékost
    public void viewData() {
        try {
            String selectQuery = "SELECT username, score FROM leaderboard " +
                    "ORDER BY score DESC LIMIT 10";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                usersList.add(new users(username, score));
            }
        } catch (SQLException ex) {
            System.out.println("Problem Fetching Data");
            ex.printStackTrace();
        }
    }
    //Beszúrja az új adatot az adatbázisba
    public boolean insertUser(String username, int score) {
        try {
            String insertQuery = "INSERT INTO leaderboard (username, score) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, score);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Problem inserting data: this username already exist in the database");
            ex.printStackTrace();
            return false;
        }
    }
}
