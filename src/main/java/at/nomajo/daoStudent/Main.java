package at.nomajo.daoStudent;

import at.nomajo.daoStudent.dataaccess.MySqlStudentRepository;
import at.nomajo.daoStudent.ui.Cli;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Cli myCli = new Cli(new MySqlStudentRepository());
            myCli.start();
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage() + "SQL State: " + e.getSQLState());
        } catch (ClassNotFoundException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }
}
