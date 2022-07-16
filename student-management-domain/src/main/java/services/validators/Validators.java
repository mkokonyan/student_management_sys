package services.validators;

import entities.Course;
import entities.Student;
import repositories.CoursesRepository;
import repositories.StudentsRepository;

public class Validators {
    public static double MIN_GRADE = 2.0;
    public static double MAX_GRADE = 6.0;

    public static String INVALID_WHITESPACE_MESSAGE = "Name must not have whitespace characters";
    public static String INVALID_CHARACTERS_MESSAGE = "Name must contain only characters";
    public static String INVALID_NUMBER_MESSAGE = "%n*** Please enter valid number! ***%n";
    public static String INVALID_GRADE_MESSAGE = "%n*** Please enter grade between %.1f and %.1f! ***%n";

    public static String EXISTING_COURSE_MESSAGE = "%n*** %s already exists! ***%n";
    public static String EXISTING_TEACHER_TO_COURSE_MESSAGE = "%n*** Course has already a teacher! ***%n";
    public static String EXISTING_STUDENT_TO_COURSE_MESSAGE = "%n*** Student already participates in course %s! ***%n";


    public static void validateName(String name) {
        if (name.matches(".*[\\s].*")) {
            throw new IllegalArgumentException(Validators.INVALID_WHITESPACE_MESSAGE);
        }

        if (!name.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException(Validators.INVALID_CHARACTERS_MESSAGE);
        }
    }

    public static int validateNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(String.format(Validators.INVALID_NUMBER_MESSAGE));
        }

    }

    public static void validateCourseExistence(CoursesRepository coursesRepository, String courseName) {
            if (coursesRepository.isExistingCourse(courseName)) {
                throw new IllegalArgumentException(String.format(Validators.EXISTING_COURSE_MESSAGE, courseName));
            }
    }

    public static void validateCourseHasNoTeacher(CoursesRepository coursesRepository, Course course) {
        if (coursesRepository.hasTeacher(course)) {
            throw new IllegalArgumentException(String.format(Validators.EXISTING_TEACHER_TO_COURSE_MESSAGE));
        }
    }

    public static void validateStudentParticipatesCourse(StudentsRepository studentsRepository, Student student, String courseName) {
        if (studentsRepository.isStudentParticipatingInCourse(student, courseName)) {
            throw new IllegalArgumentException(String.format(Validators.EXISTING_STUDENT_TO_COURSE_MESSAGE, courseName));
        }
    }

    public static double validateGrade(String grade) {
        try {
            double gradeAsDouble = Double.parseDouble(grade);
            if (gradeAsDouble < Validators.MIN_GRADE || gradeAsDouble > Validators.MAX_GRADE) {
                throw new IllegalArgumentException(String.format(Validators.INVALID_GRADE_MESSAGE, Validators.MIN_GRADE, Validators.MAX_GRADE));
            }
            return gradeAsDouble;

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(Validators.INVALID_NUMBER_MESSAGE);
        }
    }
}

