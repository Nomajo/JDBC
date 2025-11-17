package at.nomajo.daoStudent.dataaccess;

import java.sql.Date;
import java.util.List;
import at.nomajo.daoStudent.domain.Student;

public interface MyStudentRepository extends BaseRepository<Student, Long> {
    List<Student> findStudentbyFirstName(String firstName);

    Student findStudentbyID(Long id);

    List<Student> findStudentbyBirthdate(Date birthdate);
}