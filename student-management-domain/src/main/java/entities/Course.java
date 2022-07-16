package entities;

import entities.interfaces.Identifiable;
import entities.interfaces.Nameable;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity implements Comparable<Course>, Identifiable, Nameable {

    @Column(name = "total_hours", nullable = false)
    private int totalHours;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    private Set<CourseJournal> courseRecords = new LinkedHashSet<>();

    public Course() {}

    public Course(String name, int totalHours) {
        this.setName(name);
        this.totalHours = totalHours;
    }

    public Course(String id, String name, int totalHours, Teacher teacher) {
        this(name, totalHours);
        this.setId(id);
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public int compareTo(Course course) {
        return this.getName().compareTo(course.getName());
    }

    @Override
    public String toString() {
        return String.format("Course %s", getName());
    }

    public void addStudentsCourses(CourseJournal studentsCourses) {
        this.courseRecords.add(studentsCourses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(this.getId(), course.getId()) &&
                Objects.equals(this.getName(), course.getName()) &&
                this.totalHours == course.totalHours &&
                Objects.equals(this.teacher, course.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.totalHours, this.teacher);
    }
}
