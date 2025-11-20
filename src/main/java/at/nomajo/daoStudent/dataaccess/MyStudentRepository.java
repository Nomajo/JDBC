package at.nomajo.daoStudent.dataaccess;

import java.sql.Date;
import java.util.List;
import at.nomajo.daoStudent.domain.Student;

public interface MyStudentRepository extends BaseRepository<Student, Long> {
    List<Student> findStudentbyFirstName(String firstName);

    List<Student> findStudentbyLastName(String lastName);

    List<Student> findStudentbyBirthdate(Date birthdate);
}