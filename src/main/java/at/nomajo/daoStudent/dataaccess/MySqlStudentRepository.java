package at.nomajo.daoStudent.dataaccess;

import at.nomajo.daoStudent.domain.Student;
import at.nomajo.daoStudent.util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository {

    private Connection con;

    public MySqlStudentRepository() throws SQLException, ClassNotFoundException {
        this.con = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/studentDemo", "user", "12345");
    }

    @Override
    public List<Student> findStudentbyFirstName(String firstName) {
        return List.of();
    }

    @Override
    public Student findStudentbyID(Long id) {
        return null;
    }

    @Override
    public List<Student> findStudentbyBirthdate(Date birthdate) {
        return List.of();
    }

    @Override
    public Optional<Student> insert(Student entity) {
        Assert.notNull(entity);
        try {
            String sql = "INSERT INTO `students` (`vorname`, `nachname`, `birthdate`) VALUES (?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getVorname());
            preparedStatement.setString(2, entity.getNachname());
            preparedStatement.setDate(3, entity.getGeburtsdatum());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0) {
                return Optional.empty();
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                return this.getById(generatedKeys.getLong(1));
            } else {
                return Optional.empty();
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public Optional<Student> getById(Long id) {
        Assert.notNull(id);
        if(countStudentsInDbWithId(id) == 0) {
            return Optional.empty();
        } else {
            try {
                String sql = "SELECT * FROM `students` WHERE `id`=?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();
                Student student = new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("vorname"),
                        resultSet.getString("nachname"),
                        resultSet.getDate("birthdate")
                );
                return Optional.of(student);

            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }

    private int countStudentsInDbWithId(Long id) {
        try {
            String countSql = "SELECT COUNT(*) FROM `courses` WHERE `id`=?";
            PreparedStatement preparedStatement = con.prepareStatement(countSql);
            preparedStatement.setLong(1, id);
            ResultSet resultSetCount = preparedStatement.executeQuery();
            resultSetCount.next();
            int courseCount = resultSetCount.getInt(1);
            return courseCount;
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM `students`";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> courseList = new ArrayList<>();

            while(resultSet.next()) {
                courseList.add(new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("beginDate"),
                        resultSet.getDate("endDate"),
                        CourseType.valueOf(resultSet.getString("coursetype"))
                    )
                );
            }
            return courseList;
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred!");
        }
    }

    @Override
    public Optional<Student> update(Student entity) {
        Assert.notNull(entity);

        String sql = "UPDATE `courses` SET `name`=?, `description`=?, `hours`=?, `begindate`=?, `enddate`=?, `coursetype`=? WHERE `courses`.`id`=?";

        if(countStudentsInDbWithId(entity.getId()) == 0) {
            return Optional.empty();
        } else {
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setInt(3, entity.getHours());
                preparedStatement.setDate(4, entity.getBeginDate());
                preparedStatement.setDate(5, entity.getEndDate());
                preparedStatement.setString(6, entity.getCourseType().toString());
                preparedStatement.setLong(7, entity.getId());

                int affectedRows = preparedStatement.executeUpdate();

                if(affectedRows == 0) {
                    return Optional.empty();
                }
                return this.getById(entity.getId());

            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }

    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id);
        String sql = "DELETE FROM `courses` WHERE `id`=?";
        try {
            if(countStudentsInDbWithId(id) == 1) {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch(SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }
}
