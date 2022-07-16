package services;

import entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.validators.Validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CoursesServiceImplTest extends BaseSetUpTest {
    protected CoursesServiceImpl coursesService;

    @BeforeEach
    public void setUp() {
        super.setUp();

        this.coursesService = new CoursesServiceImpl(this.coursesRepository);
    }

    @Test
    public void testAddNewCourseWithGivenInvalidHoursThrowsException() {
        String courseName = "Golang";
        String courseHours = "45s";
        String courseData = courseName + System.lineSeparator() + courseHours;

        assertThrows(IllegalArgumentException.class, () -> Validators.validateNumber(courseHours));
        assertFalse(this.coursesService.addNewCourse(courseData));
    }

    @Test
    public void testAddNewCourseWithGivenExistingCourseThrowsException() {
        String courseName = "Javascript";
        String courseHours = "45s";
        String courseData = courseName + System.lineSeparator() + courseHours;

        Mockito.when(this.coursesRepository.isExistingCourse(courseName)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> Validators.validateCourseExistence(coursesRepository, courseName));
        assertFalse(this.coursesService.addNewCourse(courseData));
    }

    @Test
    public void testAddNewCourseAndSuccessfullyCreate() {
        String courseName = "Golang";
        String courseHours = "45";
        String courseData = courseName + System.lineSeparator() + courseHours;

        assertEquals(45, Validators.validateNumber(courseHours));

        assertTrue(this.coursesService.addNewCourse(courseData));
    }

    @Test
    public void testShowAllEntitiesReturningMapOfAllValues() {
        Map<Course, List<String>> expected = new HashMap<>();

        expected.put(this.courseJavascript, List.of("Javascript", "Bob", ""));
        expected.put(this.courseJava, List.of("Java", "Alice", ""));
        expected.put(this.coursePython, List.of("Python", "Bruce", ""));

        this.courseJavascript.setTeacher(this.teacherBob);
        this.courseJava.setTeacher(this.teacherAlice);
        this.coursePython.setTeacher(this.teacherBruce);

        when(this.coursesRepository
                .findAll()).thenReturn(List.of(courseJavascript, courseJava, coursePython));

        when(this.coursesRepository
                .getEntityName(this.courseJavascript)).thenReturn("Javascript");
        when(this.coursesRepository
                .getEntityName(this.courseJava)).thenReturn("Java");
        when(this.coursesRepository
                .getEntityName(this.coursePython)).thenReturn("Python");

        when(this.coursesRepository
                .getCourseTeacher(this.courseJavascript)).thenReturn("Bob");
        when(this.coursesRepository
                .getCourseTeacher(this.courseJava)).thenReturn("Alice");
        when(this.coursesRepository
                .getCourseTeacher(this.coursePython)).thenReturn("Bruce");

        when(this.coursesRepository
                .getCourseStudents(this.courseJavascript)).thenReturn(List.of(""));
        when(this.coursesRepository
                .getCourseStudents(this.courseJava)).thenReturn(List.of(""));
        when(this.coursesRepository
                .getCourseStudents(this.coursePython)).thenReturn(List.of(""));


        Map<Course, List<String>> actual = this.coursesService.showAllEntities();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllCoursesReturningResultListOfCourseObjects() {
        List<Course> expected = List.of(this.courseJavascript, this.courseJava, this.coursePython);

        when(this.coursesRepository
                .findAll()).thenReturn(List.of(this.courseJavascript, this.courseJava, this.coursePython));

        assertEquals(expected, this.coursesService.getAllCourses());
    }

    @Test
    public void testGetCourseID() {
        String expected = "1";

        when(this.coursesRepository
                .getEntityID(this.courseJavascript)).thenReturn("1");

        assertEquals(expected, this.coursesService.getCourseID(this.courseJavascript));
    }

    @Test
    public void testFindCourseByNameReturningCourse() {
        Course expected = this.courseJavascript;

        when(this.coursesRepository
                .findByName("Javascript")).thenReturn(expected);

        assertEquals(expected, this.coursesService.findCourseByName("Javascript"));
    }
}