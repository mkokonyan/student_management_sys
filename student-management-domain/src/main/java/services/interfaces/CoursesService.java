package services.interfaces;

import entities.Course;

import java.util.List;
import java.util.Map;

public interface CoursesService {

    boolean addNewCourse(String courseData);

    Map<Course, List<String>> showAllEntities();

    List<Course> getAllCourses();

    String getCourseID(Course course);

    Course findCourseByName(String courseName);
}
