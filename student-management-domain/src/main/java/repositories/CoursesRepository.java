package repositories;

import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CoursesRepository extends BaseRepository<Course> {
    public CoursesRepository(EntityManager entityManager, Class<Course> domainClass) {
        super(entityManager, domainClass);
    }

    public Course createNewCourse(String name, int hours) {
        return new Course(name, hours);
    }

    public String getCourseTeacher(Course course) {
        List<String> teacherCourse = this.getEntityManager().createQuery("SELECT c.teacher.name FROM Course c WHERE c = :course", String.class)
                .setParameter("course", course)
                .getResultList();
        return teacherCourse.size() == 0 ? null : teacherCourse.get(0);
    }

    public List<String> getAllCourses() {
        return this.getEntityManager().createQuery("SELECT c.name FROM Course c", String.class)
                .getResultList();
    }

    public void setCourseTeacher(String teacherName, Course course) {
        EntityTransaction transaction = this.getEntityManager().getTransaction();

        transaction.begin();

        Teacher teacherToUpdate = this.findTeacherByName(teacherName);

        course.setTeacher(teacherToUpdate);

        this.getEntityManager().persist(teacherToUpdate);

        transaction.commit();
    }

    public boolean hasTeacher(Course course) {
        return course.getTeacher() != null;
    }

    public void addStudentToCourse(String studentName, Course course) {
        EntityTransaction transaction = this.getEntityManager().getTransaction();

        transaction.begin();

        Student studentToAdd = this.findStudentByName(studentName);

        CourseJournal studentsCourses = new CourseJournal();

        studentsCourses.setStudent(studentToAdd);
        studentsCourses.setCourse(course);

        course.addStudentsCourses(studentsCourses);

        this.getEntityManager().persist(studentsCourses);

        transaction.commit();
    }

    public List<String> getCourseStudents(Course course) {
        Set<Student> courseStudents = new HashSet<>(this.getEntityManager().createQuery("SELECT sc.student FROM CourseJournal sc WHERE sc.course = :course", Student.class)
                .setParameter("course", course)
                .getResultList());
        return courseStudents.stream()
                .map(BaseEntity::getName)
                .collect(Collectors.toList());
    }

    public boolean isExistingCourse(String courseName) {
        List<String> allCoursesNames = this.findAll().stream()
                .map(BaseEntity::getName)
                .collect(Collectors.toList());

        return allCoursesNames.contains(courseName);
    }

    private Teacher findTeacherByName(String teacherName) {
        return this.getEntityManager().createQuery("SELECT t FROM Teacher t WHERE t.name = :teacherName", Teacher.class)
                .setParameter("teacherName", teacherName)
                .getSingleResult();
    }

    private Student findStudentByName(String studentName) {
        return this.getEntityManager().createQuery("SELECT s FROM Student s WHERE s.name = :studentName", Student.class)
                .setParameter("studentName", studentName)
                .getSingleResult();
    }
}
