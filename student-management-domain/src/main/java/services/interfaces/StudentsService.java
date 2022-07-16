package services.interfaces;

import entities.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StudentsService {

    boolean addNewStudent(String studentData);

    boolean setStudentToCourse(String studentID, String courseID);

    boolean addGradeToStudent(String studentID, String courseID, String grade);

    Map<String, List<Student>> showAllStudents();

    Map<String, String> getAllCoursesAverageGrade();

    Double getStudentTotalAverageGrade(Student student);

    List<Student> getAllStudents();

    String getStudentID(Student student);

    String getStudentName(Student student);

    Set<String> getAllStudentCoursesNames(Student student);

    Double getCourseAverageGrade(String courseName);

    Double getStudentCourseAverageGrade(String courseName, Student student);
}
