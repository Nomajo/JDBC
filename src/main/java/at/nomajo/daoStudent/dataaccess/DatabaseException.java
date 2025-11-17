package at.nomajo.daoStudent.dataaccess;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
