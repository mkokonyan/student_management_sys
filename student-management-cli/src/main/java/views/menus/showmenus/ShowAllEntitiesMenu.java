package views.menus.showmenus;

import controllers.Controller;
import entities.Course;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;

import java.util.List;
import java.util.Map;

public class ShowAllEntitiesMenu implements ViewInterface {
    private Controller controller;

    private static final String SHOW_ALL = "SHOW ALL:";
    private static final String N_A = "N/A";

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        Map<Course, List<String>> allCourses = this.controller.getAllEntitiesInfo();

        System.out.println(ShowAllEntitiesMenu.SHOW_ALL);
        System.out.println(MenuMessages.ROW_SEPARATOR);

        for (List<String> entityData : allCourses.values()) {
            String currentCourse = entityData.get(0);
            String currentTeacher = entityData.get(1);
            String currentStudent = entityData.get(2);

            System.out.println("Course: " + currentCourse);
            System.out.println("\tTeacher: " + (entityData.get(1) == null ? ShowAllEntitiesMenu.N_A : currentTeacher));
            System.out.println("\tStudents: " + (entityData.get(2).isEmpty() ? ShowAllEntitiesMenu.N_A : currentStudent));
        }
        System.out.println();
    }


}
