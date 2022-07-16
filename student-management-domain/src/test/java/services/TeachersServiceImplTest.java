package services;

import entities.Course;
import entities.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repositories.StudentsRepository;
import repositories.TeachersRepository;
import services.validators.Validators;

import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeachersServiceImplTest extends BaseSetUpTest {
    private TeachersServiceImpl teachersService;

    @Mock
    private TeachersRepository teachersRepository;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        super.setUp();

        this.teachersService = new TeachersServiceImpl(this.coursesRepository, this.teachersRepository);
    }

    @Test
    public void testNewTeacherWithGivenWhitespaceCharactersNameThrowsException() {
        String teacherName = "Bob Johnson";
        String teacherDegree = "PHD";

        String teacherData = teacherName + System.lineSeparator() + teacherDegree;

        assertThrows(IllegalArgumentException.class, () -> Validators.validateName(teacherName));
        assertFalse(this.teachersService.addNewTeacher(teacherData));
    }

    @Test
    public void testNewTeacherWithGivenInvalidSymbolsNameThrowsException() {
        String teacherName = "Bob$Johnson";
        String teacherDegree = "PHD";

        String teacherData = teacherName + System.lineSeparator() + teacherDegree;

        assertThrows(IllegalArgumentException.class, () -> Validators.validateName(teacherName));
        assertFalse(this.teachersService.addNewTeacher(teacherData));
    }

    @Test
    public void testAddNewTeacherAndSuccessfullyCreate() {
        String teacherName = "Bob";
        String degreeOption = "3";

        String teacherData = teacherName + System.lineSeparator() + degreeOption;

        assertEquals(3, Validators.validateNumber(degreeOption));
        assertTrue(this.teachersService.addNewTeacher(teacherData));
    }

    @Test
    public void testSetTeacherToCourseWithAlreadySetTeacherThrowException() {
        Teacher teacherToSet = this.teacherAlice;
        Course courseToSet = this.courseJavascript;

        Mockito.when(this.coursesRepository.hasTeacher(courseToSet)).thenReturn(true);

        courseToSet.setTeacher(teacherToSet);

        assertThrows(IllegalArgumentException.class, () -> Validators.validateCourseHasNoTeacher(this.coursesRepository, courseToSet));
    }

    @Test
    public void testSetTeacherToCourseSuccessfully() {
        Teacher teacherToSet = this.teacherAlice;
        Course courseToSet = this.courseJavascript;

        assertTrue(this.teachersService.setTeacherToCourse(teacherToSet.getId(), courseToSet.getId()));
    }

    @Test
    public void testGetAllTeachersReturningResultListOfTeacherObjects() {
        List<Teacher> expectedResult = List.of(this.teacherBruce, this.teacherBob, this.teacherAlice);

        Mockito.when(this.teachersRepository.findAll()).thenReturn(List.of(this.teacherBruce, this.teacherBob, this.teacherAlice));

        assertEquals(expectedResult.size(), this.teachersService.getAllTeachers().size());
    }

    @Test
    public void testGetTeacherID () {
        Teacher currentTeacher = this.teacherBob;
        String expectedResult = "1";

        Mockito.when(this.teachersRepository.getEntityID(currentTeacher)).thenReturn("1");

        assertEquals(expectedResult, this.teachersService.getTeacherID(currentTeacher));
    }

    @Test
    public void testGetAllTeacherDegreeTypes () {
        assertEquals(List.of("MSc", "BSc", "PHD"), this.teachersService.getTeacherDegreesTypes());
    }

}