package views.menus.forms;

import controllers.Controller;
import views.commands.AddCourseCommand;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;
import views.utils.ScannerSingleton;

public class AddCourseForm implements ViewInterface {
    private Controller controller;

    private static final String NEW_COURSE_FORM = "NEW COURSE FORM:";
    private static final String COURSE_NAME = "Course name: ";
    private static final String COURSE_HOURS = "Course hours: ";

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        System.out.println(AddCourseForm.NEW_COURSE_FORM);
        System.out.println(MenuMessages.ROW_SEPARATOR);
        System.out.print(AddCourseForm.COURSE_NAME);

        String courseName = ScannerSingleton.getInstance().nextLine();

        System.out.print(AddCourseForm.COURSE_HOURS);

        String courseHours = ScannerSingleton.getInstance().nextLine();

        AddCourseCommand command = new AddCourseCommand(this.controller, String.join(System.lineSeparator(), courseName, courseHours));

        command.execute();
    }
}
