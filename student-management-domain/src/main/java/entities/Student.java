package entities;

import entities.interfaces.Identifiable;
import entities.interfaces.Nameable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "students")
public class Student extends BaseEntity implements Identifiable, Nameable {
    private int age;

    @OneToMany(mappedBy = "student")
    private Set<CourseJournal> courseRecords = new LinkedHashSet<>();

    public Student() {}

    public Student(String name, int age) {
        this.setName(name);
        this.age = age;
    }

    public Student(String name, int age, String id) {
        this(name, age);
        this.setId(id);
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<CourseJournal> getCourseRecords() {
        return this.courseRecords.stream()
                .collect(Collectors.toUnmodifiableSet());
    }

    public void setCourseRecords(Set<CourseJournal> grades) {
        this.courseRecords = grades;
    }

    public void addStudentGrade(CourseJournal grade) {
        this.courseRecords.add(grade);
    }


    @Override
    public String toString() {
        return String.format("Name %s, Age: %d", getName(), getAge());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(this.getId(), student.getId()) &&
                Objects.equals(this.getName(), student.getName()) &&
                this.age == student.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.age);
    }
}