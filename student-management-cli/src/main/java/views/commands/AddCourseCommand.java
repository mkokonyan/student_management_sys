package views.commands;

import controllers.Controller;

public class AddCourseCommand implements Command {
    private final Controller controller;
    private final String courseData;

    public AddCourseCommand(Controller controller, String courseData) {
        this.controller = controller;
        this.courseData = courseData;
    }

    @Override
    public void execute() {
        this.controller.addNewCourse(this.courseData);
    }
}
