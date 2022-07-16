package repositories;

import entities.Course;
import entities.Student;
import entities.CourseJournal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StudentsRepository extends BaseRepository<Student> {
    public StudentsRepository(EntityManager entityManager,  Class<Student> domainClass) {
        super(entityManager, domainClass);
    }

    public Student createNewStudent(String name, int age) {
        return new Student(name, age);
    }

    public void addGradeToCourse(Student studentToUpdate, String courseName, double gradeAsDouble) {
        Course courseToAddGrade = this.findCourseByName(courseName);

        CourseJournal searchedStudentCourses = this.getEntityManager()
                .createQuery("SELECT sc FROM CourseJournal sc WHERE sc.student = :studentToUpdate AND sc.course = :courseToAddGrade AND sc.grade = null ", CourseJournal.class)
                .setParameter("studentToUpdate", studentToUpdate)
                .setParameter("courseToAddGrade", courseToAddGrade)
                .getSingleResult();

        EntityTransaction transaction = this.getEntityManager().getTransaction();

        transaction.begin();

        searchedStudentCourses.setGrade(gradeAsDouble);

        this.getEntityManager().persist(searchedStudentCourses);

        transaction.commit();

    }

    public Set<String> getAllStudentCourses(Student student) {
        return new LinkedHashSet<>(this.getEntityManager().createQuery("SELECT sc.course.name FROM CourseJournal sc WHERE sc.student = :student", String.class)
                .setParameter("student", student).getResultList());
    }

    public List<Student> getCourseStudents(String courseName) {
        return new ArrayList<>(this.getEntityManager().createQuery("SELECT sc.student FROM CourseJournal sc WHERE sc.course.name = :courseName", Student.class)
                .setParameter("courseName", courseName).getResultList());
    }

    public boolean isStudentParticipatingInCourse(Student student, String courseName) {
        Course searchedCourse = this.findCourseByName(courseName);

        List<CourseJournal> resultList = this.getEntityManager().createQuery("SELECT sc FROM CourseJournal sc WHERE sc.student = :student AND sc.course = :course", CourseJournal.class)
                .setParameter("student", student)
                .setParameter("course", searchedCourse)
                .getResultList();

        return resultList.size() > 0;
    }

    public List<Double> getStudentCourseGrades(Student student, String courseName) {
        Course searchedCourse = this.findCourseByName(courseName);

        return this.getEntityManager().createQuery("SELECT sc.grade FROM CourseJournal sc WHERE sc.student = :student AND sc.course = :course", Double.class)
                .setParameter("student", student)
                .setParameter("course", searchedCourse)
                .getResultList();
    }

    private Course findCourseByName(String courseName) {
        return this.getEntityManager().createQuery("SELECT c FROM Course c WHERE c.name = :courseName", Course.class)
                .setParameter("courseName", courseName)
                .getSingleResult();
    }
}

