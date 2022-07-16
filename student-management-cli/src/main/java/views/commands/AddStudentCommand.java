package views.commands;

import controllers.Controller;

public class AddStudentCommand implements Command {
    private final Controller controller;
    private final String studentData;

    public AddStudentCommand(Controller controller, String studentData) {
        this.controller = controller;
        this.studentData = studentData;
    }

    @Override
    public void execute() {
        this.controller.addNewStudent(this.studentData);
    }
}
