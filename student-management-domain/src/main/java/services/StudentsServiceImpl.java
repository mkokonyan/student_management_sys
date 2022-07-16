package services;

import entities.Course;
import entities.Student;
import jakarta.persistence.NoResultException;
import repositories.CoursesRepository;
import repositories.StudentsRepository;
import services.interfaces.Closeable;
import services.interfaces.StudentsService;
import services.validators.Validators;
import configs.CoursesRepositorySingleton;
import configs.StudentsRepositorySingleton;

import java.util.*;

public class StudentsServiceImpl implements StudentsService, Closeable {
    private final CoursesRepository coursesRepository;
    private final StudentsRepository studentsRepository;

    public StudentsServiceImpl() {
        this.coursesRepository = CoursesRepositorySingleton.getInstance();
        this.studentsRepository = StudentsRepositorySingleton.getInstance();
    }

    public StudentsServiceImpl(CoursesRepository coursesRepository, StudentsRepository studentsRepository) {
        this.coursesRepository = coursesRepository;
        this.studentsRepository = studentsRepository;
    }

    @Override
    public boolean addNewStudent(String studentData) {
        String studentName = studentData.split(System.lineSeparator())[0].strip();
        String studentAgeAsStr = studentData.split(System.lineSeparator())[1].strip();

        try {

            Validators.validateName(studentName);
            int studentAge = Validators.validateNumber(studentAgeAsStr);

            Student newStudent = this.studentsRepository.createNewStudent(studentName, studentAge);

            this.studentsRepository.add(newStudent);

        } catch (IllegalArgumentException ex) {

            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean setStudentToCourse(String studentID, String courseID) {
        Student studentToUpdate = this.studentsRepository.findByID(studentID);
        Course courseToUpdate = this.coursesRepository.findByID(courseID);

        String courseName = this.coursesRepository.getEntityName(courseToUpdate);
        String studentName = this.studentsRepository.getEntityName(studentToUpdate);

        try {
            Validators.validateStudentParticipatesCourse(this.studentsRepository, studentToUpdate, courseName);
        } catch (IllegalArgumentException ex) {

            System.out.println(ex.getMessage());
            return false;
        }

        this.coursesRepository.addStudentToCourse(studentName, courseToUpdate);

        return true;
    }

    @Override
    public boolean addGradeToStudent(String studentID, String courseID, String grade) {
        Student studentToUpdate = this.studentsRepository.findByID(studentID);
        Course courseToUpdate = this.coursesRepository.findByID(courseID);

        String studentName = this.studentsRepository.getEntityName(studentToUpdate);
        String courseName = this.coursesRepository.getEntityName(courseToUpdate);

        try {
            double gradeAsDouble = Validators.validateGrade(grade);

            this.studentsRepository.addGradeToCourse(studentToUpdate, courseName, gradeAsDouble);

        } catch (NoResultException ex) {
            this.coursesRepository.addStudentToCourse(studentName, courseToUpdate);
            this.addGradeToStudent(studentID, courseID, grade);

        } catch (IllegalArgumentException ex) {

            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Map<String, List<Student>> showAllStudents() {
        List<Student> allStudents = this.studentsRepository.findAll();

        Map<String, List<Student>> allStudentsInfo = new TreeMap<>();

        for (Student student : allStudents) {
            for (String course : this.coursesRepository.getAllCourses()) {
                if (this.studentsRepository.isStudentParticipatingInCourse(student, course)) {
                    allStudentsInfo.putIfAbsent(course, new ArrayList<>());
                    allStudentsInfo.get(course).add(student);
                }
            }
        }
        return allStudentsInfo;
    }

    @Override
    public Map<String, String> getAllCoursesAverageGrade() {

        Map<String, String> courseAverageGradeInfo = new HashMap<>();

        for (Course course : this.coursesRepository.findAll()) {
            String courseName = this.coursesRepository.getEntityName(course);
            Double courseAverageGrade = getCourseAverageGrade(courseName);

            courseAverageGradeInfo.put(courseName, courseAverageGrade == null ? "N/A" : String.format("%.2f", courseAverageGrade));
        }
        return courseAverageGradeInfo;
    }

    @Override
    public Double getStudentTotalAverageGrade(Student student) {
        List<Double> allAverageCoursesGrades = new ArrayList<>();
        Set<String> allStudentCourses = this.studentsRepository.getAllStudentCourses(student);


        for (String course : allStudentCourses) {
            List<Double> allCourseGrades = this.studentsRepository.getStudentCourseGrades(student, course);
            if (allCourseGrades.stream().noneMatch(Objects::isNull)) {
                Double courseAverageGrade = getAverageGrade(allCourseGrades);

                allAverageCoursesGrades.add(courseAverageGrade);
            }
        }
        return allAverageCoursesGrades.isEmpty()
                ? null
                : getAverageGrade(allAverageCoursesGrades);
    }

    @Override
    public List<Student> getAllStudents() {
        return this.studentsRepository.findAll();
    }

    @Override
    public String getStudentID(Student student) {
        return this.studentsRepository.getEntityID(student);
    }

    @Override
    public String getStudentName(Student student) {
        return this.studentsRepository.getEntityName(student);
    }

    @Override
    public Set<String> getAllStudentCoursesNames(Student student) {
        return this.studentsRepository.getAllStudentCourses(student);
    }

    @Override
    public Double getCourseAverageGrade(String courseName) {
        List<Student> allCourseStudents = this.studentsRepository.getCourseStudents(courseName);
        List<Double> allCourseGrades = new ArrayList<>();

        for (Student student : allCourseStudents) {
            Double studentAverageCourseGrade = getStudentCourseAverageGrade(courseName, student);

            if (studentAverageCourseGrade != null) {
                allCourseGrades.add(studentAverageCourseGrade);
            }
        }

        return allCourseGrades.isEmpty()
                ? null
                : getAverageGrade(allCourseGrades);
    }

    @Override
    public Double getStudentCourseAverageGrade(String courseName, Student student) {
        List<Double> studentCourseGrades = this.studentsRepository
                .getStudentCourseGrades(student, courseName);

        return studentCourseGrades.stream().anyMatch(Objects::isNull)
                ? null
                : getAverageGrade(studentCourseGrades);
    }

    private double getAverageGrade(List<Double> allAverageCoursesGrades) {
        return allAverageCoursesGrades.stream()
                .mapToDouble(e -> e)
                .average()
                .getAsDouble();
    }
}
