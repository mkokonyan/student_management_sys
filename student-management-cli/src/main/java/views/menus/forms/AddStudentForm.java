package views.menus.forms;

import controllers.Controller;
import views.commands.AddStudentCommand;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;
import views.utils.ScannerSingleton;

public class AddStudentForm implements ViewInterface {
    private Controller controller;

    public static final String NEW_STUDENT_FORM = "NEW STUDENT FORM";
    public static final String STUDENT_NAME = "Student name: ";
    public static final String STUDENT_AGE = "Student age: ";

    public void setController(Controller controller) {
        this.controller = controller;
    }
    @Override
    public void perform() {
        System.out.println(AddStudentForm.NEW_STUDENT_FORM);
        System.out.println(MenuMessages.ROW_SEPARATOR);
        System.out.print(AddStudentForm.STUDENT_NAME);

        String studentName = ScannerSingleton.getInstance().nextLine();

        System.out.print(AddStudentForm.STUDENT_AGE);

        String studentAge = ScannerSingleton.getInstance().nextLine();

        AddStudentCommand command = new AddStudentCommand(this.controller, String.join(System.lineSeparator(), studentName, studentAge));

        command.execute();
    }
}
