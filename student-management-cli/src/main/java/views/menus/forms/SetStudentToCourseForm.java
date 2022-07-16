package views.menus.forms;

import controllers.Controller;
import entities.Course;
import entities.Student;
import views.commands.SetStudentToCourseCommand;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;
import views.utils.ScannerSingleton;

import java.util.List;

import static views.helpers.MenuMessages.CHOOSE_OPTION;

public class SetStudentToCourseForm implements ViewInterface {
    private Controller controller;

    private static final String ASSIGN_STUDENT_FORM = "ASSIGN STUDENT FORM";
    private static final String SELECT_STUDENT = "Please select student from list: ";

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        List<Student> allStudents = controller.getAllStudents();
        List<Course> allCourses = controller.getAllCourses();

        System.out.println(SetStudentToCourseForm.ASSIGN_STUDENT_FORM);
        System.out.println(MenuMessages.ROW_SEPARATOR);
        System.out.println(SetStudentToCourseForm.SELECT_STUDENT);

        try {
            MenuMessages.printAllEntities(allStudents);

            System.out.print(CHOOSE_OPTION);
            int chosenStudentOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
            System.out.println();
            MenuMessages.printAllEntities(allCourses);


            System.out.print(CHOOSE_OPTION);
            int chosenCourseOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
            System.out.println();


            int searchedStudentIndex = chosenStudentOption - 1;
            int searchedCourseIndex = chosenCourseOption - 1;

            Student searchedStudent = allStudents.get(searchedStudentIndex);
            Course searchedCourse = allCourses.get(searchedCourseIndex);

            String studentID = this.controller.getStudentID(searchedStudent);
            String courseID = this.controller.getCourseID(searchedCourse);

            SetStudentToCourseCommand command = new SetStudentToCourseCommand(
                    this.controller, studentID, courseID);

            command.execute();

        } catch (NumberFormatException | IndexOutOfBoundsException ex) {

            System.out.println(MenuMessages.CHOOSE_VALID_NUMBER);

            this.perform();
        }
    }
}
