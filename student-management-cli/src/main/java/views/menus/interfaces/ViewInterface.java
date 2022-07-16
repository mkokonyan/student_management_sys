package views.menus.interfaces;

import controllers.Controller;

public interface ViewInterface {
    void perform();

    void setController(Controller controller);
}
