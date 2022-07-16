package views.menus.enums;

import views.menus.ExitMenu;
import views.menus.forms.*;
import views.menus.interfaces.ViewInterface;
import views.menus.showmenus.ShowAllEntitiesMenu;
import views.menus.showmenus.ShowAllStudentsMenu;
import views.menus.showmenus.ShowAllCoursesAverageGradeMenu;
import views.menus.showmenus.ShowStudentTotalAverageGradeMenu;

public enum MainMenuOptions {
    ADD_NEW_COURSE(1, "Add a new course", new AddCourseForm()),
    ADD_NEW_STUDENT(2, "Add a new student", new AddStudentForm()),
    ADD_NEW_TEACHER(3,"Add a new teacher", new AddTeacherForm()),
    ADD_TEACHER_TO_COURSE(4,"Add a teacher to course", new SetTeacherToCourseForm()),
    ADD_STUDENT_TO_COURSE(5, "Add a student to course", new SetStudentToCourseForm()),
    ADD_GRADE_TO_STUDENT(6,"Add a grade to student", new AddGradeToStudentForm()),
    SHOW_ALL_STUDENTS(7,"Show all students with their average grades", new ShowAllStudentsMenu()),
    SHOW_ALL(8,"Show all courses, teachers and students", new ShowAllEntitiesMenu()),
    SHOW_COURSE_AVERAGE_GRADE(9,"Show average grade for all students in a specific course", new ShowAllCoursesAverageGradeMenu()),
    SHOW_STUDENT_AVERAGE_GRADE(10,"Show total average grade for a student", new ShowStudentTotalAverageGradeMenu()),
    EXIT(11,"Exit", new ExitMenu());


    private int option;
    private String message;
    private ViewInterface nextMenu;

    MainMenuOptions(int option, String message, ViewInterface nextMenu) {
        this.option = option;
        this.message = message;
        this.nextMenu = nextMenu;

    }

    public int getOption() {
        return this.option;
    }

    public String getMessage() {
        return this.message;
    }

    public ViewInterface getNextMenu() {
        return this.nextMenu;
    }

    public void printFullMessage() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(this.getOption())
                .append(") ")
                .append(this.getMessage());

        System.out.println(messageBuilder);;
    }
}
