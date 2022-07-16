package services;

import entities.Course;
import entities.Student;
import entities.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.CoursesRepository;

import java.util.LinkedHashSet;

public class BaseSetUpTest {
    protected Course courseJavascript;
    protected Course courseJava;
    protected Course coursePython;

    protected Teacher teacherBob;
    protected Teacher teacherBruce;
    protected Teacher teacherAlice;

    protected Student studentMartin;
    protected Student studentTanya;
    protected Student studentBoris;

    @Mock
    protected CoursesRepository coursesRepository;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        generateEntities();
    }

    public void generateEntities() {
        this.courseJavascript = new Course("1", "Javascript", 24, null);
        this.courseJava = new Course("2", "Java", 48, null);
        this.coursePython = new Course("3", "Python", 79, null);

        this.teacherBob = new Teacher("Bob", "PHD", "1", new LinkedHashSet<>());
        this.teacherBruce = new Teacher("Bruce", "MSc", "2", new LinkedHashSet<>());
        this.teacherAlice = new Teacher("Alice", "BSc", "3", new LinkedHashSet<>());

        this.studentMartin = new Student("Martin", 32, "1");
        this.studentTanya = new Student("Tanya", 24, "2");
        this.studentBoris = new Student("Boris", 36, "3");
    }
}