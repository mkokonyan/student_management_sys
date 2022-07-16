package views.commands;

import controllers.Controller;

public class SetStudentToCourseCommand implements Command {
    private final Controller controller;
    private final String studentID;
    private final String courseID;

    public SetStudentToCourseCommand(Controller controller, String studentID, String courseID) {
        this.controller = controller;
        this.studentID = studentID;
        this.courseID = courseID;
    }

    @Override
    public void execute() {
        this.controller.setStudentToCourse(this.studentID, this.courseID);
    }
}
