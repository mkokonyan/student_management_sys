package services;

import entities.Course;
import entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repositories.StudentsRepository;
import services.validators.Validators;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;

public class StudentsServiceImplTest extends BaseSetUpTest {
    private StudentsServiceImpl studentsService;

    @Mock
    private StudentsRepository studentsRepository;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        super.setUp();

        this.studentsService = new StudentsServiceImpl(this.coursesRepository, this.studentsRepository);
    }

    @Test
    public void testNewStudentWithGivenWhitespaceCharactersNameThrowsException() {
        String studentName = "Tony Ivanov";
        String studentAge = "24";

        String studentData = studentName + System.lineSeparator() + studentAge;

        assertThrows(IllegalArgumentException.class, () -> Validators.validateName(studentName));
        assertFalse(this.studentsService.addNewStudent(studentData));
    }

    @Test
    public void testNewStudentWithGivenInvalidAgeThrowsException() {
        String studentName = "Tony";
        String studentAge = "24 years";

        String studentData = studentName + System.lineSeparator() + studentAge;

        assertThrows(IllegalArgumentException.class, () -> Validators.validateNumber(studentAge));
        assertFalse(this.studentsService.addNewStudent(studentData));
    }

    @Test
    public void testAddNewStudentAndSuccessfullyCreate() {
        String studentName = "Tony";
        String studentAge = "22";

        String studentData = studentName + System.lineSeparator() + studentAge;

        Student newEntity = new Student(studentName, Integer.parseInt(studentAge));

        Mockito.when(this.studentsRepository.add(newEntity)).thenReturn(newEntity);

        assertEquals(22, Validators.validateNumber(studentAge));
        assertTrue(this.studentsService.addNewStudent(studentData));
    }

    @Test
    public void testSetStudentToCourseWithStudentWhoIsAlreadyParticipatingThrowsException() {
        Student studentToSet = this.studentMartin;
        Course courseToSet = this.coursePython;

        String courseName = courseToSet.getName();

        Mockito.when(this.studentsRepository.isStudentParticipatingInCourse(studentToSet, courseName)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> Validators.validateStudentParticipatesCourse(this.studentsRepository, studentToSet, courseName));
    }

    @Test
    public void testSetStudentToCourseSuccessfully() {
        Student studentToSet = this.studentMartin;
        Course courseToSet = this.coursePython;

        assertTrue(this.studentsService.setStudentToCourse(studentToSet.getId(), courseToSet.getId()));
    }

    @Test
    public void testAddGradeToStudentWithNonDigitSymbolThrowsException() {
        Student studentToUpdate = this.studentMartin;
        Course courseToSet = this.coursePython;
        String gradeToAdd = "A+";

        assertThrows(IllegalArgumentException.class, () -> Validators.validateGrade(gradeToAdd));
        assertFalse(this.studentsService.addGradeToStudent(studentToUpdate.getId(), courseToSet.getId(), gradeToAdd));
    }

    @Test
    public void testAddGradeToStudentWithNonValidDigitThrowsException() {
        Student studentToUpdate = this.studentMartin;
        Course courseToSet = this.coursePython;
        String gradeToAdd = "6.50";

        assertThrows(IllegalArgumentException.class, () -> Validators.validateGrade(gradeToAdd));
        assertFalse(this.studentsService.addGradeToStudent(studentToUpdate.getId(), courseToSet.getId(), gradeToAdd));
    }

    @Test
    public void testAddGradeToStudentSuccessfully() {
        Student studentToUpdate = this.studentMartin;
        Course courseToSet = this.coursePython;

        String gradeToAdd = "2.00";

        assertEquals(2.0, Validators.validateGrade(gradeToAdd));
        assertTrue(this.studentsService.addGradeToStudent(studentToUpdate.getId(), courseToSet.getId(), gradeToAdd));
    }

    @Test
    public void testShowAllStudentsReturningMapOfAllValues() {
        Map<String, List<Student>> expected = new TreeMap<>();

        expected.put(this.courseJavascript.getName(), List.of(this.studentBoris));
        expected.put(this.courseJava.getName(), List.of(this.studentMartin, this.studentTanya));
        expected.put(this.coursePython.getName(), List.of(this.studentMartin));

        Mockito.when(this.studentsRepository.findAll()).thenReturn(List.of(this.studentBoris, this.studentMartin, this.studentTanya));
        Mockito.when(this.coursesRepository.getAllCourses()).thenReturn(List.of("Javascript", "Java", "Python"));

        Mockito.when(this.studentsRepository.isStudentParticipatingInCourse(this.studentBoris, "Javascript")).thenReturn(true);

        Mockito.when(this.studentsRepository.isStudentParticipatingInCourse(this.studentMartin, "Java")).thenReturn(true);
        Mockito.when(this.studentsRepository.isStudentParticipatingInCourse(this.studentTanya, "Java")).thenReturn(true);

        Mockito.when(this.studentsRepository.isStudentParticipatingInCourse(this.studentMartin, "Python")).thenReturn(true);

        assertEquals(expected, this.studentsService.showAllStudents());
    }


    @Test
    public void testGetAllCoursesAverageGradeReturningMap() {
        Student firstStudent = this.studentMartin;

        Course firstCourse = this.coursePython;
        Course secondCourse = this.courseJava;
        Course thirdCourse = this.courseJavascript;

        String firstCourseName = firstCourse.getName();
        String secondCourseName = secondCourse.getName();
        String thirdCourseName = thirdCourse.getName();

        Mockito.when(this.coursesRepository.findAll())
                .thenReturn(List.of(firstCourse, secondCourse, thirdCourse));

        Mockito.when(this.coursesRepository.getEntityName(any()))
                .thenReturn(firstCourseName)
                .thenReturn(secondCourseName)
                .thenReturn(thirdCourseName);

        Mockito.when(this.studentsRepository.getCourseStudents(any()))
                .thenReturn(List.of(firstStudent))
                .thenReturn(List.of(firstStudent));

        List<Double> nullList = new ArrayList<>();
        nullList.add(null);

        Mockito.when(this.studentsRepository.getStudentCourseGrades(any(), any()))
                .thenReturn(List.of(3.0, 4.0))
                .thenReturn(List.of(5.0, 6.0))
                .thenReturn(nullList);

        Map<String, String> expected = new HashMap<>();

        expected.put(firstCourseName, "3.50");
        expected.put(secondCourseName, "5.50");
        expected.put(thirdCourseName, "N/A");

        assertEquals(expected, this.studentsService.getAllCoursesAverageGrade());
    }

    @Test
    public void getStudentTotalAverageGradeWithNoGrades() {
        Student currentStudent = this.studentMartin;

        assertNull(this.studentsService.getStudentTotalAverageGrade(currentStudent));
    }

    @Test
    public void getStudentTotalAverageGradeWithGradesIncluded() {
        Student currentStudent = this.studentMartin;

        Course firstCourse = this.coursePython;
        Course secondCourse = this.courseJava;

        String firstCourseName = firstCourse.getName();
        String secondCourseName = secondCourse.getName();

        Mockito.when(this.studentsRepository.getAllStudentCourses(currentStudent))
                .thenReturn(Set.of(firstCourseName, secondCourseName));

        Mockito.when(this.studentsRepository.getStudentCourseGrades(any(), any()))
                .thenReturn(List.of(3.0, 3.0))
                .thenReturn(List.of(5.0, 5.0));

        assertEquals(4.0, this.studentsService.getStudentTotalAverageGrade(currentStudent));
    }

    @Test
    public void testGetCourseAverageGradeForCourseWithNonEmptyListOfGrades() {
        Student firstStudent = this.studentMartin;
        Student secondStudent = this.studentTanya;

        Course currentCourse = this.coursePython;

        String courseName = currentCourse.getName();

        Mockito.when(this.studentsRepository.getCourseStudents(courseName))
                .thenReturn(List.of(firstStudent, secondStudent));

        Mockito.when(this.studentsRepository.getStudentCourseGrades(firstStudent, courseName))
                .thenReturn(List.of(2.0, 4.0));

        Mockito.when(this.studentsRepository.getStudentCourseGrades(secondStudent, courseName))
                .thenReturn(List.of(4.0, 6.0));

        assertEquals(4.00, this.studentsService.getCourseAverageGrade(courseName));
    }

    @Test
    public void testGetStudentCourseAverageGradeWithoutGrades() {
        Student currentStudent = this.studentMartin;

        Course currentCourse = this.coursePython;

        String courseName = currentCourse.getName();

        Mockito.when(this.studentsRepository.getCourseStudents(courseName))
                .thenReturn(List.of(currentStudent));

        List<Double> nullList = new ArrayList<>();
        nullList.add(null);

        Mockito.when(this.studentsRepository.getStudentCourseGrades(currentStudent, courseName))
                .thenReturn(nullList);

        assertNull(this.studentsService.getStudentCourseAverageGrade(courseName, currentStudent));
    }

    @Test
    public void testGetStudentCourseAverageGradeWithGrades() {
        Student currentStudent = this.studentMartin;

        Course currentCourse = this.coursePython;

        String courseName = currentCourse.getName();

        Mockito.when(this.studentsRepository.getCourseStudents(courseName))
                .thenReturn(List.of(currentStudent));

        Mockito.when(this.studentsRepository.getStudentCourseGrades(currentStudent, courseName))
                .thenReturn(List.of(2.0, 4.0, 6.0));

        assertEquals(4.0, this.studentsService.getStudentCourseAverageGrade(courseName, currentStudent));
    }

    @Test
    public void testGetAllStudentsReturningResultListOfStudentObjects() {
        List<Student> expectedResult = List.of(this.studentMartin, this.studentTanya, this.studentBoris);

        Mockito.when(this.studentsRepository.findAll()).thenReturn(List.of(this.studentMartin, this.studentTanya, this.studentBoris));

        assertEquals(expectedResult.size(), this.studentsService.getAllStudents().size());
    }

    @Test
    public void testGetStudentID() {
        Student currentStudent = this.studentMartin;

        String expectedResult = "1";

        Mockito.when(this.studentsRepository.getEntityID(currentStudent)).thenReturn("1");

        assertEquals(expectedResult, this.studentsService.getStudentID(currentStudent));
    }

    @Test
    public void testGetStudentNameReturningString() {
        Student currentStudent = this.studentMartin;

        String expectedResult = "Martin";

        Mockito.when(this.studentsRepository.getEntityName(currentStudent)).thenReturn("Martin");

        assertEquals(expectedResult, this.studentsService.getStudentName(currentStudent));
    }
}