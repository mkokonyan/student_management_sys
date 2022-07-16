import controllers.Controller;
import services.CoursesServiceImpl;
import services.StudentsServiceImpl;
import services.TeachersServiceImpl;
import views.helpers.MenuMessages;
import views.menus.MainMenu;

public class StudentManagementApp {

    public static void main(String[] args) {
        CoursesServiceImpl courseService = new CoursesServiceImpl();
        StudentsServiceImpl studentService = new StudentsServiceImpl();
        TeachersServiceImpl teacherService = new TeachersServiceImpl();

        Controller controller = new Controller(courseService, studentService, teacherService);

        MenuMessages.printWelcomeMessage();

        MainMenu welcomeMenu = new MainMenu(controller);

        while (true) {
            welcomeMenu.perform();
        }
    }
}
