package views.commands;

import controllers.Controller;

public class ExitCommand implements Command {
    private final Controller controller;

    public ExitCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        this.controller.exitApp();
    }
}
