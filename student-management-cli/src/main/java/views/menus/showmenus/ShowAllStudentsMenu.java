package views.menus.showmenus;

import controllers.Controller;
import entities.Student;
import views.helpers.MenuMessages;
import views.menus.interfaces.ViewInterface;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ShowAllStudentsMenu implements ViewInterface {
    private Controller controller;

    private static final String ALL_STUDENTS = "ALL STUDENTS :";
    private static final String N_A = "N/A";
    private static final Double NOT_AVAILABLE_GRADE = null;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {
        Map<String, List<Student>> allStudentsInfo = this.controller.getAllStudentsInfo();

        System.out.println(ShowAllStudentsMenu.ALL_STUDENTS);
        System.out.println(MenuMessages.ROW_SEPARATOR);

        for (Map.Entry<String, List<Student>> allStudentsInfoEntry : allStudentsInfo.entrySet()) {
            String course = allStudentsInfoEntry.getKey();
            Double courseAverageGrade = controller.getCourseAverageGrade(course);

            String courseAverageGradeAsString = String.format("%.2f", courseAverageGrade);

            System.out.println("Course name " + course + ":");
            System.out.println("Course average grade: " + ((courseAverageGrade == ShowAllStudentsMenu.NOT_AVAILABLE_GRADE) ? ShowAllStudentsMenu.N_A : courseAverageGradeAsString));

            List<Student> sortedStudents = allStudentsInfoEntry.getValue();
            sortedStudents.sort(Comparator.comparing(s -> this.controller.getStudentName((Student) s))
                    .thenComparingDouble(s -> this.controller.getStudentCourseAverageGrade(course, (Student) s)));

            for (Student student : allStudentsInfoEntry.getValue()) {
                Double studentCourseAverageGrade = this.controller.getStudentCourseAverageGrade(course, student);

                String studentAverageCourseGradeAsString = String.format("%.2f", studentCourseAverageGrade);

                System.out.print("\t" + this.controller.getStudentName(student));
                System.out.println(", Average course grade: " + ((studentCourseAverageGrade == ShowAllStudentsMenu.NOT_AVAILABLE_GRADE) ? ShowAllStudentsMenu.N_A : studentAverageCourseGradeAsString));
            }
        }
        System.out.println();
    }


}
