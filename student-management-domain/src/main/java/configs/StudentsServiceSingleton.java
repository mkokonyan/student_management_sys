package configs;

import services.StudentsServiceImpl;
import services.interfaces.StudentsService;

public class StudentsServiceSingleton {
    private static StudentsService studentsService = null;

    private StudentsServiceSingleton() {
    }

    public static StudentsService getInstance() {
        if (StudentsServiceSingleton.studentsService == null) {

            StudentsServiceSingleton.studentsService = new StudentsServiceImpl();
        }
        return StudentsServiceSingleton.studentsService;
    }
}
