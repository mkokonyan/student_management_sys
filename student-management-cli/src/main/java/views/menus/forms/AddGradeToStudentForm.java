package views.menus.forms;

import controllers.Controller;
import entities.Course;
import entities.Student;
import views.commands.AddGradeToStudentCommand;
import views.helpers.MenuMessages;
import views.menus.MainMenu;
import views.menus.interfaces.ViewInterface;
import views.utils.ScannerSingleton;

import java.util.List;



public class AddGradeToStudentForm implements ViewInterface {
    private Controller controller;

    private static final String ADD_GRADE_FORM = "ADD A GRADE FORM";

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        List<Student> allStudents = controller.getAllStudents();

        System.out.println(AddGradeToStudentForm.ADD_GRADE_FORM);
        System.out.println(MenuMessages.ROW_SEPARATOR);
        System.out.println(MenuMessages.CHOOSE_STUDENT);

        try {
            MenuMessages.printAllEntities(allStudents);
            System.out.print(MenuMessages.CHOOSE_OPTION);

            int chosenStudentOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
            int searchedStudentIndex = chosenStudentOption - 1;

            Student selectedStudent = allStudents.get(searchedStudentIndex);

            List<Course> allStudentCourses = controller.getAllStudentCourses(selectedStudent);

            System.out.println();

            if (allStudentCourses.isEmpty()) {

                System.out.println(MenuMessages.NOT_POSSIBLE_TO_STUDENT_TO_COURSE);
                MainMenu welcomeMenu = new MainMenu(this.controller);
                welcomeMenu.perform();

            } else {

                MenuMessages.printAllEntities(allStudentCourses);
                System.out.print(MenuMessages.CHOOSE_OPTION);

                int chosenCourseOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());

                System.out.println();
                System.out.print(MenuMessages.CHOOSE_GRADE);

                String gradeToAdd = ScannerSingleton.getInstance().nextLine();

                System.out.println();

                String studentID = this.controller.getStudentID(selectedStudent);

                int searchedCourseIndex = chosenCourseOption - 1;

                Course selectedCourse = allStudentCourses.get(searchedCourseIndex);
                String courseID = this.controller.getCourseID(selectedCourse);

                AddGradeToStudentCommand command = new AddGradeToStudentCommand(
                        this.controller, studentID, courseID, gradeToAdd);

                command.execute();
            }

        } catch (NumberFormatException | IndexOutOfBoundsException ex) {

            System.out.println(MenuMessages.CHOOSE_VALID_NUMBER);

            this.perform();
        }
    }
}
