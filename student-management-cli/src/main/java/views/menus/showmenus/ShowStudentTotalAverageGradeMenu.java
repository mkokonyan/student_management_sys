package views.menus.showmenus;

import controllers.Controller;
import entities.Student;
import views.helpers.MenuMessages;
import views.menus.MainMenu;
import views.menus.interfaces.ViewInterface;
import views.utils.ScannerSingleton;

import java.util.List;

public class ShowStudentTotalAverageGradeMenu implements ViewInterface {
    private Controller controller;

    private static final String STUDENT_TOTAL_AVERAGE_GRADE = "STUDENT TOTAL AVERAGE GRADE:";
    private static final String N_A = "N/A";
    private static final Double NOT_AVAILABLE_GRADE =  null;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        List<Student> allStudents = this.controller.getAllStudents();

        System.out.println(ShowStudentTotalAverageGradeMenu.STUDENT_TOTAL_AVERAGE_GRADE);
        System.out.println(MenuMessages.ROW_SEPARATOR);
        System.out.println(MenuMessages.CHOOSE_STUDENT);

        try {
            MenuMessages.printAllEntities(allStudents);
            System.out.print(MenuMessages.CHOOSE_OPTION);

            int chosenStudentOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
            int selectedStudentIndex = chosenStudentOption - 1;

            Student selectedStudent = allStudents.get(selectedStudentIndex);

            if (allStudents.isEmpty()) {

                System.out.println(MenuMessages.NOT_POSSIBLE_CHOOSE_STUDENT);
                MainMenu welcomeMenu = new MainMenu(this.controller);
                welcomeMenu.perform();

            } else {

                Double studentTotalAverageGrade = this.controller.getStudentTotalAverageGrade(selectedStudent);

                System.out.println();
                System.out.println(this.controller.getStudentName(selectedStudent) + ":");

                String studentTotalAvgGradeAsString = String.format("%.2f", studentTotalAverageGrade);

                System.out.println("\tAverage grade: " + (studentTotalAverageGrade == ShowStudentTotalAverageGradeMenu.NOT_AVAILABLE_GRADE ? ShowStudentTotalAverageGradeMenu.N_A : studentTotalAvgGradeAsString));
                System.out.println();

            }

        } catch (NumberFormatException | IndexOutOfBoundsException ex) {

            System.out.println(MenuMessages.CHOOSE_VALID_NUMBER);

            this.perform();
        }


    }
}
