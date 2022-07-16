package views.commands;

import controllers.Controller;

public class AddTeacherCommand implements Command {
    private final Controller controller;
    private final String teacherData;

    public AddTeacherCommand(Controller controller, String teacherData) {
        this.controller = controller;
        this.teacherData = teacherData;
    }

    @Override
    public void execute() {
        this.controller.addNewTeacher(this.teacherData);
    }
}
