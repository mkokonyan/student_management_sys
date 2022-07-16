package views.menus;


import controllers.Controller;
import views.commands.ExitCommand;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;

public class ExitMenu implements ViewInterface {
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        System.out.println(MenuMessages.EXIT);

        ExitCommand command = new ExitCommand(this.controller);

        System.exit(0);
    }
}
