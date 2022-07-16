package views.helpers;

import java.util.List;

public class MenuMessages {
    public static final String WELCOME = "WELCOME TO THE MAIN MENU";
    public static final String ROW_SEPARATOR = String.format("========================%n");

    public static final String CHOOSE_OPTION = "Please choose an option: ";
    public static final String CHOOSE_GRADE = "Please choose a grade to add: ";
    public static final String CHOOSE_STUDENT = "Please select student from list: ";
    public static final String CHOOSE_VALID_NUMBER = String.format("%nPlease choose a valid number!%n");


    public static final String SUCCESS_ADD = "*** Entity has successfully saved to the database ***";
    public static final String SUCCESS_ASSIGN = "*** Entity has successfully assigned to course ***";
    public static final String SUCCESS_ADDED_GRADE = "*** Grade has successfully added to student ***";

    public static final String NOT_POSSIBLE_TO_STUDENT_TO_COURSE = String.format("*** Current student has no available courses. You can't add a grade ***%n");
    public static final String NOT_POSSIBLE_CHOOSE_STUDENT = String.format("*** There are not available students. ***%n");

    public static final String EXIT = "Thank you for using Student Management System! See you soon!";


    public static void printWelcomeMessage() {
        System.out.println(WELCOME);
        System.out.println(ROW_SEPARATOR);
    }

    public static void printSuccessMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }

    public static <T> void printAllEntities(List<T> allEntities) {
        allEntities.forEach(e -> System.out.printf("%d) %s%n", allEntities.indexOf(e)+1, e.toString()));
    }
}
