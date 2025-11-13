package at.nomajo.jdbc;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("JDBC Demo");

        //INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, 'Noel Frey', 'nofrey@tsn.at');

        selectAllDemo();

        insertStudentDemo("Name des Studenten", "Email@prov.at");

        updateStudentDemo("Neuer Name","neueemail@provider.at");

        selectAllDemo();

        deleteStudentDemo(5 );

        selectAllDemo();

        findAllByNameLike("Frey");

    }

    public static void findAllByNameLike(String pattern){

        System.out.println("Find all By Name Demo");

        String sqlSelectAllPerson = "SELECT * FROM `student`";

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        String user = "root";

        String pwd = "";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user   ,pwd ))

        {

            System.out.println("Verbindung zur Db hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `student` WHERE  `student`.`name` LIKE  ?" );

            preparedStatement.setString(1,"%" +pattern + "%");

            ResultSet resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()){

                int id = resultSet.getInt("id");

                String name = resultSet.getString("name");

                String email = resultSet.getString("email");

                System.out.println("Student aus der DB: [ID] " + id + " [NAME] " + name + " [EMAIL] " + email);

            }

        }

        catch (SQLException sqlException){

            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + sqlException.getMessage());

        }

    }

    public static void  deleteStudentDemo(int studentID){

        System.out.println("DELETE Demo mit JDBC");

        String sqlSelectAllPerson = "SELECT * FROM `student`";

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        String user = "root";

        String pwd = "";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user   ,pwd ))

        {

            System.out.println("Verbindung zur Db hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement(

                    "DELETE FROM `student` WHERE `student`.`id` = ?");

            try {

                preparedStatement.setInt(1,studentID);

                int rowAffected = preparedStatement.executeUpdate();

                System.out.println("Anzahl der gelöschten Datensätze " + rowAffected);

            }

            catch (SQLException sqlException){

                System.out.println("Fehler im SQL-DELETE Statement" + sqlException.getMessage());

            }


        }

        catch (SQLException sqlException){

            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + sqlException.getMessage());

        }

    }

    public static void  updateStudentDemo(String neuerName, String neueEmail){

        System.out.println("UPDATE Demo mit JDBC");

        String sqlSelectAllPerson = "SELECT * FROM `student`";

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        String user = "root";

        String pwd = "";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user   ,pwd ))

        {

            System.out.println("Verbindung zur Db hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement(

                    "UPDATE `student` SET `name` = ?, `email` = ? WHERE `student`.`id` = 4");

            try {

                preparedStatement.setString(1,neuerName);

                preparedStatement.setString(2,neueEmail);

                int affectedRows = preparedStatement.executeUpdate();

                System.out.println("Anzahl der Aktualisierten Datensätze: " + affectedRows);

            }

            catch (SQLException sqlException){

                System.out.println("Fehler im SQL-UPDATE Statement" + sqlException.getMessage());

            }


        }

        catch (SQLException sqlException){

            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + sqlException.getMessage());

        }

    }

    public static void  insertStudentDemo(String name, String email){

        System.out.println("INSERT Demo mit JDBC");

        String sqlSelectAllPerson = "SELECT * FROM `student`";

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        String user = "root";

        String pwd = "";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user   ,pwd ))

        {

            System.out.println("Verbindung zur Db hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement(

                    "INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, ?, ? ); ");

            try {

                preparedStatement.setString(1,name);

                preparedStatement.setString(2,email);

                int rowAffected = preparedStatement.executeUpdate();

                System.out.println(rowAffected + " Datensätze eingefügt");

            }

            catch (SQLException sqlException){

                System.out.println("Fehler im SQL-INSERT Statement");

            }


        }

        catch (SQLException sqlException){

            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + sqlException.getMessage());

        }

    }


    public static void  selectAllDemo(){

        System.out.println("Select Demo mit JDBC");

        String sqlSelectAllPerson = "SELECT * FROM `student`";

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        String user = "root";

        String pwd = "";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user   ,pwd ))

        {

            System.out.println("Verbindung zur Db hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `student`");

            ResultSet resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()){

                int id = resultSet.getInt("id");

                String name = resultSet.getString("name");

                String email = resultSet.getString("email");

                System.out.println("Student aus der DB: [ID] " + id + " [NAME] " + name + " [EMAIL] " + email);

            }

        }

        catch (SQLException sqlException){

            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + sqlException.getMessage());

        }

    }

}