package views.menus.showmenus;

import controllers.Controller;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;

import java.util.Map;

public class ShowAllCoursesAverageGradeMenu implements ViewInterface {
    private Controller controller;

    private static final String ALL_COURSES_AVERAGE_GRADES ="ALL COURSES AVERAGE GRADES:";

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        Map<String, String> allCoursesAverageGrades = this.controller.getAllCoursesAverageGrades();

        System.out.println(ShowAllCoursesAverageGradeMenu.ALL_COURSES_AVERAGE_GRADES);
        System.out.println(MenuMessages.ROW_SEPARATOR);

        for (Map.Entry<String, String> courseEntry : allCoursesAverageGrades.entrySet()) {
            System.out.println("Course: " + courseEntry.getKey());
            System.out.println("\tAverage grade: " + courseEntry.getValue());
        }

        System.out.println();
    }
}
