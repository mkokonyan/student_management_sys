package views.commands;

import controllers.Controller;

public class SetTeacherToCourseCommand implements Command {
    private final Controller controller;
    private final String teacherID;
    private final String courseID;

    public SetTeacherToCourseCommand(Controller controller, String teacherID, String courseID) {
        this.controller = controller;
        this.teacherID = teacherID;
        this.courseID = courseID;
    }

    @Override
    public void execute() {
        this.controller.setTeacherToCourse(this.teacherID, this.courseID);
    }
}
