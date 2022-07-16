package views.menus.forms;

import controllers.Controller;
import entities.Course;
import entities.Teacher;
import views.commands.SetTeacherToCourseCommand;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;
import views.utils.ScannerSingleton;

import java.util.List;

import static views.helpers.MenuMessages.CHOOSE_OPTION;

public class SetTeacherToCourseForm implements ViewInterface {
    private Controller controller;

    private static final String ASSIGN_TEACHER_FORM = "ASSIGN TEACHER FORM";
    private static final String SELECT_TEACHER = "Please select teacher from list: ";

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        List<Teacher> allTeachers = controller.getAllTeachers();
        List<Course> allCourses = controller.getAllCourses();

        System.out.println(SetTeacherToCourseForm.ASSIGN_TEACHER_FORM);
        System.out.println(MenuMessages.ROW_SEPARATOR);
        System.out.println(SetTeacherToCourseForm.SELECT_TEACHER);

        try {
            MenuMessages.printAllEntities(allTeachers);

            System.out.print(CHOOSE_OPTION);

            int chosenTeacherOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());

            System.out.println();
            MenuMessages.printAllEntities(allCourses);
            System.out.print(CHOOSE_OPTION);

            int chosenCourseOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());

            System.out.println();

            int searchedTeacherIndex = chosenTeacherOption - 1;
            int searchedCourseIndex = chosenCourseOption - 1;

            Teacher searchedTeacher = allTeachers.get(searchedTeacherIndex);
            Course searchedCourse = allCourses.get(searchedCourseIndex);

            String teacherID = this.controller.getTeacherID(searchedTeacher);
            String courseID = this.controller.getCourseID(searchedCourse);

            SetTeacherToCourseCommand command = new SetTeacherToCourseCommand(
                    this.controller, teacherID, courseID);

            command.execute();

        } catch (NumberFormatException | IndexOutOfBoundsException ex) {

            System.out.println(MenuMessages.CHOOSE_VALID_NUMBER);

            this.perform();
        }
    }
}
