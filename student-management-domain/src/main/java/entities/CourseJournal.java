package entities;

import jakarta.persistence.*;


@Entity
@Table(name = "course_journal")
public class CourseJournal extends BaseIDEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    private Double grade;

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getGrade() {
        return this.grade;
    }

    public void setGrade(double grades) {
        this.grade = grades;
    }
}
