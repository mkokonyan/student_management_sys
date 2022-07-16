package views.commands;

import controllers.Controller;

public class AddGradeToStudentCommand implements Command {
    private final Controller controller;
    private final String studentID;
    private final String courseID;
    private final String grade;

    public AddGradeToStudentCommand(Controller controller, String studentID, String courseID, String grade) {
        this.controller = controller;
        this.studentID = studentID;
        this.courseID = courseID;
        this.grade = grade;
    }

    @Override
    public void execute() {
        this.controller.addGradeToStudent(this.studentID, this.courseID, this.grade);
    }
}
