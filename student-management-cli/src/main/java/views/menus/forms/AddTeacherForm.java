package views.menus.forms;

import controllers.Controller;
import views.commands.AddTeacherCommand;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;
import views.utils.ScannerSingleton;

import java.util.List;

import static views.helpers.MenuMessages.CHOOSE_OPTION;

public class AddTeacherForm implements ViewInterface {
    private Controller controller;

    private static final String NEW_TEACHER_FORM = "NEW TEACHER FORM";
    private static final String TEACHER_NAME = "Teacher name: ";
    private static final String TEACHER_DEGREE = "Teacher degree: ";

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        List<String> teachersDegreesTypes = this.controller.getTeacherDegreesTypes();

        System.out.println(AddTeacherForm.NEW_TEACHER_FORM);
        System.out.println(MenuMessages.ROW_SEPARATOR);
        System.out.print(AddTeacherForm.TEACHER_NAME);

        String teacherName = ScannerSingleton.getInstance().nextLine();

        System.out.println(AddTeacherForm.TEACHER_DEGREE);

        printTeacherDegreeOptions(teachersDegreesTypes);

        try {

            int chosenOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());

            System.out.println();

            int searchedTeacherDegreeTypeIndex = chosenOption - 1;

            String teacherDegree = teachersDegreesTypes.get(searchedTeacherDegreeTypeIndex);

            AddTeacherCommand command = new AddTeacherCommand(this.controller, String.join(System.lineSeparator(), teacherName, teacherDegree));

            command.execute();

        } catch (NumberFormatException | IndexOutOfBoundsException ex) {

            System.out.println(MenuMessages.CHOOSE_VALID_NUMBER);

            this.perform();
        }
    }

    private void printTeacherDegreeOptions(List<String> teachersDegreesType) {
        for (int i = 0; i < teachersDegreesType.size(); i++) {
            printFullMessage(i, teachersDegreesType.get(i));
        }
        System.out.print(CHOOSE_OPTION);
    }

    public void printFullMessage(int index, String degree) {
        int selectedOption = index + 1;
        String message =
                selectedOption +
                        ") " +
                        degree;

        System.out.println(message);
    }

}
