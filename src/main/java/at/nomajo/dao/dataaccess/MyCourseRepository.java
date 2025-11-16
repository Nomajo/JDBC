package at.nomajo.dao.dataaccess;

import at.nomajo.dao.domain.Course;
import at.nomajo.dao.domain.CourseType;

import java.util.Date;
import java.util.List;

public interface MyCourseRepository extends BaseRepository<Course, Long> {
    List<Course> findAllCoursesByName(String name);

    List<Course> findAllCoursesByDescription(String description);

    List<Course> findAllCoursesByNameOrDescription(String searchText);

    List<Course> findAllCoursesByCourseType(CourseType courseType);

    List<Course> findAllCoursesByStartDate(Date startDate);

    List<Course> findAllRunningCourses();
}
