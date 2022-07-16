package entities;

import entities.interfaces.Identifiable;
import entities.interfaces.Nameable;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "teachers")
public class Teacher extends BaseEntity implements Identifiable, Nameable {
    private String degree;

    @OneToMany(mappedBy = "teacher", targetEntity =  Course.class)
    private Set<Course> courses = new LinkedHashSet<>();

    public Teacher() {}

    public Teacher(String name, String degree) {
        this.setName(name);
        this.degree = degree;
    }

    public Teacher(String name, String degree, String id, Set<Course> courses) {
        this(name, degree);
        this.setId(id);
        this.courses = courses;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Set<Course> getCourses() {
        return this.courses.stream()
                .collect(Collectors.toUnmodifiableSet());
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    @Override
    public String toString() {
        return String.format("Name %s, Degree: %s", getName(), getDegree());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(this.getId(), teacher.getId()) &&
                Objects.equals(this.getName(), teacher.getName()) &&
                Objects.equals(this.degree, teacher.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.degree);
    }
}
