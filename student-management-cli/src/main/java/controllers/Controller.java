package controllers;

import entities.Course;
import entities.Student;
import entities.Teacher;
import services.CoursesServiceImpl;
import services.StudentsServiceImpl;
import services.TeachersServiceImpl;
import services.interfaces.Closeable;
import views.helpers.MenuMessages;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
    private final CoursesServiceImpl courseService;
    private final StudentsServiceImpl studentService;
    private final TeachersServiceImpl teacherService;

    public Controller(CoursesServiceImpl courseService, StudentsServiceImpl studentService, TeachersServiceImpl teacherService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public void addNewCourse(String courseData) {
        boolean isSuccessOperation = this.courseService.addNewCourse(courseData);
        if (isSuccessOperation) {
            MenuMessages.printSuccessMessage(MenuMessages.SUCCESS_ADD);
        }
    }

    public void addNewStudent(String studentData) {
        boolean isSuccessOperation = this.studentService.addNewStudent(studentData);
        if (isSuccessOperation) {
            MenuMessages.printSuccessMessage(MenuMessages.SUCCESS_ADD);
        }
    }

    public void addNewTeacher(String teacherData) {
        boolean isSuccessOperation = this.teacherService.addNewTeacher(teacherData);
        if (isSuccessOperation) {
            MenuMessages.printSuccessMessage(MenuMessages.SUCCESS_ADD);
        }
    }

    public void setTeacherToCourse(String teacherID, String courseID) {

        boolean isSuccessOperation = this.teacherService.setTeacherToCourse(teacherID, courseID);
        if (isSuccessOperation) {
            MenuMessages.printSuccessMessage(MenuMessages.SUCCESS_ASSIGN);
        }

    }

    public void setStudentToCourse(String studentID, String courseID) {

        boolean isSuccessOperation = this.studentService.setStudentToCourse(studentID, courseID);
        if (isSuccessOperation) {
            MenuMessages.printSuccessMessage(MenuMessages.SUCCESS_ASSIGN);
        }
    }

    public void addGradeToStudent(String studentID, String courseID, String grade) {

        boolean isSuccessOperation = this.studentService.addGradeToStudent(studentID, courseID, grade);
        if (isSuccessOperation) {
            MenuMessages.printSuccessMessage(MenuMessages.SUCCESS_ADDED_GRADE);
        }
    }

    public Map<String, List<Student>> getAllStudentsInfo() {
        return this.studentService.showAllStudents();
    }

    public Map<Course, List<String>> getAllEntitiesInfo() {
        return this.courseService.showAllEntities();
    }

    public Map<String, String> getAllCoursesAverageGrades() {
        return this.studentService.getAllCoursesAverageGrade();
    }

    public Double getStudentTotalAverageGrade(Student student) {
        return this.studentService.getStudentTotalAverageGrade(student);
    }

    public Double getStudentCourseAverageGrade(String courseName, Student student) {
        return this.studentService.getStudentCourseAverageGrade(courseName, student);
    }

    public List<Student> getAllStudents() {
        return this.studentService.getAllStudents();
    }

    public List<Course> getAllCourses() {
        return this.courseService.getAllCourses();
    }

    public List<Teacher> getAllTeachers() {
        return this.teacherService.getAllTeachers();
    }

    public String getCourseID(Course course) {
        return this.courseService.getCourseID(course);
    }

    public String getStudentID(Student student) {
        return this.studentService.getStudentID(student);
    }

    public String getStudentName(Student student) {
        return this.studentService.getStudentName(student);
    }

    public String getTeacherID(Teacher teacher) {
        return this.teacherService.getTeacherID(teacher);
    }

    public Double getCourseAverageGrade(String courseName) {
        return this.studentService.getCourseAverageGrade(courseName);
    }

    public List<Course> getAllStudentCourses(Student student) {
        Set<String> allStudentCoursesNames = this.studentService.getAllStudentCoursesNames(student);
        return allStudentCoursesNames.stream()
                .map(this.courseService::findCourseByName)
                .collect(Collectors.toList());
    }

    public List<String> getTeacherDegreesTypes() {
        return  teacherService.getTeacherDegreesTypes();
    }

    public void exitApp() {
        (new Closeable() {}).exit();
    }
}
